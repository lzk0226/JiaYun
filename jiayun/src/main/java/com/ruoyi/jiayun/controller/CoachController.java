package com.ruoyi.jiayun.controller;

import com.ruoyi.jiayun.R.Result;
import com.ruoyi.jiayun.domain.Coach;
import com.ruoyi.jiayun.domain.CoachDetail;
import com.ruoyi.jiayun.domain.StudentCourse;
import com.ruoyi.jiayun.server.CoachService;
import com.ruoyi.jiayun.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/coaches")
@Tag(name = "教练管理", description = "教练相关接口")
@CrossOrigin
public class CoachController {

    @Autowired
    private CoachService coachService;

    @Resource
    private JwtUtils jwtUtils;

    @GetMapping
    @Operation(summary = "获取教练列表", description = "支持科目、级别、排序筛选")
    public Result<List<Coach>> getCoaches(
            @Parameter(description = "科目代码: subject2/subject3/all")
            @RequestParam(required = false) String subject,
            @Parameter(description = "教练级别: gold/excellent/normal")
            @RequestParam(required = false) String level,
            @Parameter(description = "排序方式: default/rating/experience")
            @RequestParam(required = false, defaultValue = "default") String sort) {

        log.info("查询教练列表: subject={}, level={}, sort={}", subject, level, sort);

        List<Coach> coaches = coachService.getCoachesByFilters(subject, level, sort);
        return Result.success(coaches);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取教练详情")
    public Result<CoachDetail> getCoachDetail(
            @Parameter(description = "教练ID")
            @PathVariable Long id) {

        log.info("查询教练详情: id={}", id);

        CoachDetail detail = coachService.getCoachDetail(id);
        if (detail == null) {
            return Result.error("教练不存在");
        }
        return Result.success(detail);
    }

    @GetMapping("/my-courses")
    @Operation(summary = "获取可选择教练的课程列表",
            description = "返回学员正在进行的课程，标注哪些已有教练")
    public Result<List<StudentCourse>> getCoursesForCoachSelection(
            @RequestHeader("Authorization") String token) {

        try {
            // 移除Bearer前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 验证token
            if (!jwtUtils.validateToken(token)) {
                return Result.error("Token已过期，请重新登录");
            }

            // 从token获取学员ID
            Long studentId = jwtUtils.getUserIdFromToken(token);

            log.info("获取可选择教练的课程列表: studentId={}", studentId);

            List<StudentCourse> courses = coachService.getCoursesForCoachSelection(studentId);

            return Result.success(courses);

        } catch (Exception e) {
            log.error("获取课程列表失败", e);
            return Result.error("获取课程列表失败：" + e.getMessage());
        }
    }

    @PostMapping("/select")
    @Operation(summary = "为课程选择教练",
            description = "学员为指定课程选择教练，一个课程只能有一个教练")
    public Result<String> selectCoachForCourse(
            @RequestHeader("Authorization") String token,
            @Parameter(description = "课程ID")
            @RequestParam Long courseId,
            @Parameter(description = "教练ID")
            @RequestParam Long coachId) {

        try {
            // 移除Bearer前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 验证token
            if (!jwtUtils.validateToken(token)) {
                return Result.error("Token已过期，请重新登录");
            }

            // 从token获取学员ID
            Long studentId = jwtUtils.getUserIdFromToken(token);

            log.info("选择教练: studentId={}, courseId={}, coachId={}",
                    studentId, courseId, coachId);

            // 调用service进行教练选择（包含资格检查）
            String result = coachService.selectCoachForCourse(studentId, courseId, coachId);

            if ("success".equals(result)) {
                return Result.success("教练选择成功");
            } else {
                return Result.error(result); // 返回具体错误信息
            }

        } catch (Exception e) {
            log.error("选择教练失败", e);
            return Result.error("选择教练失败：" + e.getMessage());
        }
    }
}
