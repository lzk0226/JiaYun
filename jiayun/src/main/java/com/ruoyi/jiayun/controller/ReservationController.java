package com.ruoyi.jiayun.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.jiayun.domain.Reservation;
import com.ruoyi.jiayun.domain.Coach;
import com.ruoyi.jiayun.server.ReservationService;
import com.ruoyi.jiayun.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user/reservation")
@Tag(name = "预约管理", description = "学员预约相关接口")
@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Resource
    private JwtUtils jwtUtils;

    /**
     * 从请求头中获取用户ID
     */
    private Long getUserIdFromToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtUtils.validateToken(token)) {
            throw new RuntimeException("Token已过期，请重新登录");
        }

        return jwtUtils.getUserIdFromToken(token);
    }

    /**
     * 根据科目获取学员在该科目下的教练（基于已报名课程）
     */
    @GetMapping("/coach")
    @Operation(summary = "获取学员在指定科目下的教练")
    @Parameter(name = "subjectId", description = "科目ID: 2(科目二) 或 3(科目三)", required = true)
    public AjaxResult getStudentCoach(
            @RequestHeader("Authorization") String token,
            @RequestParam Integer subjectId) {
        try {
            Long studentId = getUserIdFromToken(token);

            if (subjectId == null || (subjectId != 2 && subjectId != 3)) {
                return AjaxResult.error("科目ID必须为2或3");
            }

            log.info("获取学员教练 - studentId: {}, subjectId: {}", studentId, subjectId);

            Coach coach = reservationService.getStudentCoachBySubject(studentId, subjectId);
            return AjaxResult.success(coach);
        } catch (RuntimeException e) {
            log.error("获取学员教练失败", e);
            return AjaxResult.error(e.getMessage());
        } catch (Exception e) {
            log.error("获取学员教练异常", e);
            return AjaxResult.error("获取教练失败：" + e.getMessage());
        }
    }

    /**
     * 获取指定日期和教练的可用时段
     */
    @GetMapping("/available-times")
    @Operation(summary = "获取可用时段")
    public AjaxResult getAvailableTimeSlots(
            @Parameter(description = "预约日期 yyyy-MM-dd", required = true)
            @RequestParam String date,
            @Parameter(description = "教练ID", required = true)
            @RequestParam Long coachId) {
        try {
            LocalDate reservationDate = LocalDate.parse(date);
            List<Map<String, Object>> slots = reservationService.getAvailableTimeSlots(reservationDate, coachId);
            return AjaxResult.success(slots);
        } catch (Exception e) {
            log.error("获取可用时段失败", e);
            return AjaxResult.error("获取可用时段失败：" + e.getMessage());
        }
    }

    /**
     * 获取指定日期、时段和科目的可用车辆
     */
    @GetMapping("/available-vehicles")
    @Operation(summary = "获取可用车辆")
    public AjaxResult getAvailableVehicles(
            @Parameter(description = "预约日期 yyyy-MM-dd", required = true)
            @RequestParam String date,
            @Parameter(description = "科目ID: 2 或 3", required = true)
            @RequestParam Integer subjectId,
            @Parameter(description = "时间段", required = true)
            @RequestParam String timeSlot) {
        try {
            log.info("查询可用车辆 - date: {}, subjectId: {}, timeSlot: {}", date, subjectId, timeSlot);
            LocalDate reservationDate = LocalDate.parse(date);
            List<?> vehicles = reservationService.getAvailableVehicles(reservationDate, subjectId, timeSlot);
            log.info("可用车辆数量: {}", vehicles.size());
            return AjaxResult.success(vehicles);
        } catch (Exception e) {
            log.error("获取可用车辆失败", e);
            return AjaxResult.error("获取可用车辆失败：" + e.getMessage());
        }
    }

    /**
     * 创建预约
     * 请求体中需要包含：coachId, vehicleId, subjectId, reservationDate, timeSlot, remarks(可选)
     */
    @PostMapping
    @Operation(summary = "创建预约")
    public AjaxResult createReservation(
            @RequestHeader("Authorization") String token,
            @RequestBody Reservation reservation) {
        try {
            // 从token获取学员ID
            Long studentId = getUserIdFromToken(token);
            reservation.setStudentId(studentId);

            log.info("创建预约 - studentId: {}, subjectId: {}, coachId: {}, vehicleId: {}",
                    studentId, reservation.getSubjectId(), reservation.getCoachId(), reservation.getVehicleId());

            // 验证必要字段
            if (reservation.getSubjectId() == null || reservation.getCoachId() == null ||
                    reservation.getVehicleId() == null || reservation.getReservationDate() == null ||
                    reservation.getTimeSlot() == null) {
                return AjaxResult.error("缺少必要参数：subjectId, coachId, vehicleId, reservationDate, timeSlot");
            }

            int result = reservationService.createReservation(reservation);
            return result > 0 ? AjaxResult.success("预约成功") : AjaxResult.error("预约失败");
        } catch (RuntimeException e) {
            log.error("创建预约失败", e);
            return AjaxResult.error(e.getMessage());
        } catch (Exception e) {
            log.error("创建预约异常", e);
            return AjaxResult.error("预约失败：" + e.getMessage());
        }
    }

    /**
     * 获取当前学员的预约记录
     */
    @GetMapping("/history")
    @Operation(summary = "获取预约历史记录")
    public AjaxResult getReservationHistory(
            @RequestHeader("Authorization") String token,
            @Parameter(description = "预约状态筛选（可选）: upcoming/completed/cancelled")
            @RequestParam(required = false) String status) {
        try {
            // 从token获取学员ID
            Long studentId = getUserIdFromToken(token);

            log.info("查询预约历史 - studentId: {}, status: {}", studentId, status);

            List<Reservation> history = reservationService.getStudentReservations(studentId, status);
            return AjaxResult.success(history);
        } catch (RuntimeException e) {
            log.error("获取预约历史失败", e);
            return AjaxResult.error(e.getMessage());
        } catch (Exception e) {
            log.error("获取预约历史异常", e);
            return AjaxResult.error("获取预约历史失败：" + e.getMessage());
        }
    }

    /**
     * 取消预约
     */
    @PutMapping("/{id}/cancel")
    @Operation(summary = "取消预约")
    public AjaxResult cancelReservation(
            @RequestHeader("Authorization") String token,
            @Parameter(description = "预约ID", required = true)
            @PathVariable Long id) {
        try {
            // 从token获取学员ID
            Long studentId = getUserIdFromToken(token);

            log.info("取消预约 - studentId: {}, reservationId: {}", studentId, id);

            int result = reservationService.cancelReservation(id, studentId);
            return result > 0 ? AjaxResult.success("取消成功") : AjaxResult.error("取消失败");
        } catch (RuntimeException e) {
            log.error("取消预约失败", e);
            return AjaxResult.error(e.getMessage());
        } catch (Exception e) {
            log.error("取消预约异常", e);
            return AjaxResult.error("取消失败：" + e.getMessage());
        }
    }

    /**
     * 删除预约记录（仅已取消的记录）
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除预约记录")
    public AjaxResult deleteReservation(
            @RequestHeader("Authorization") String token,
            @Parameter(description = "预约ID", required = true)
            @PathVariable Long id) {
        try {
            // 从token获取学员ID
            Long studentId = getUserIdFromToken(token);

            log.info("删除预约 - studentId: {}, reservationId: {}", studentId, id);

            int result = reservationService.deleteReservation(id, studentId);
            return result > 0 ? AjaxResult.success("删除成功") : AjaxResult.error("删除失败");
        } catch (RuntimeException e) {
            log.error("删除预约失败", e);
            return AjaxResult.error(e.getMessage());
        } catch (Exception e) {
            log.error("删除预约异常", e);
            return AjaxResult.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 获取预约详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取预约详情")
    public AjaxResult getReservationDetail(
            @Parameter(description = "预约ID", required = true)
            @PathVariable Long id) {
        try {
            log.info("查询预约详情 - reservationId: {}", id);

            Reservation detail = reservationService.getReservationDetail(id);
            return detail != null ? AjaxResult.success(detail) : AjaxResult.error("预约不存在");
        } catch (Exception e) {
            log.error("获取预约详情失败", e);
            return AjaxResult.error("获取预约详情失败：" + e.getMessage());
        }
    }
}