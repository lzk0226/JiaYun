package com.ruoyi.jiayun.server.impl;

import com.ruoyi.jiayun.domain.Coach;
import com.ruoyi.jiayun.domain.Reservation;
import com.ruoyi.jiayun.domain.Vehicle;
import com.ruoyi.jiayun.mapper.ReservationMapper;
import com.ruoyi.jiayun.server.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    // 预定义的时段列表
    private static final List<String> ALL_TIME_SLOTS = Arrays.asList(
            "08:00-10:00",
            "10:00-12:00",
            "14:00-16:00",
            "16:00-18:00"
    );

    @Override
    public Coach getStudentCoachBySubject(Long studentId, Integer subjectId) {
        if (subjectId == null || (subjectId != 2 && subjectId != 3)) {
            throw new RuntimeException("无效的科目ID，只支持科目二(2)和科目三(3)");
        }

        // 检查学员是否已报名该科目
        int enrolled = reservationMapper.checkStudentEnrolledSubject(studentId, subjectId);
        if (enrolled == 0) {
            throw new RuntimeException("您还未报名该科目的课程，请先报名");
        }

        // 获取学员在该科目下的教练
        Coach coach = reservationMapper.selectStudentCoachBySubject(studentId, subjectId);
        if (coach == null) {
            throw new RuntimeException("未找到您在该科目下的教练，请联系管理员");
        }

        return coach;
    }


    @Override
    public List<Map<String, Object>> getAvailableTimeSlots(LocalDate date, Long coachId) {
        // 获取该教练在该日期已预约的时段
        List<String> bookedSlots = reservationMapper.selectBookedTimeSlots(date, coachId);

        List<Map<String, Object>> result = new ArrayList<>();
        for (String slot : ALL_TIME_SLOTS) {
            Map<String, Object> slotMap = new HashMap<>();
            slotMap.put("time", slot);
            slotMap.put("period", slot.startsWith("08") || slot.startsWith("10") ? "上午" : "下午");
            slotMap.put("available", !bookedSlots.contains(slot));
            result.add(slotMap);
        }
        return result;
    }

    @Override
    public List<Vehicle> getAvailableVehicles(LocalDate date, Integer subjectId, String timeSlot) {
        // 根据科目获取已被预约的车辆ID
        List<Long> bookedVehicleIds = reservationMapper.selectBookedVehicles(date, timeSlot);

        // 根据科目获取可用车辆（排除已预约的）
        return reservationMapper.selectAvailableVehicles(bookedVehicleIds);
    }

    @Override
    @Transactional
    public int createReservation(Reservation reservation) {
        // 验证科目、教练、车辆和时段参数
        if (reservation.getSubjectId() == null ||
                reservation.getCoachId() == null ||
                reservation.getVehicleId() == null ||
                reservation.getReservationDate() == null ||
                reservation.getTimeSlot() == null) {
            throw new RuntimeException("缺少必要参数");
        }

        // 验证学员是否已报名该科目并分配了该教练
        Coach assignedCoach = reservationMapper.selectStudentCoachBySubject(
                reservation.getStudentId(),
                reservation.getSubjectId()
        );

        if (assignedCoach == null) {
            throw new RuntimeException("您还未报名该科目的课程");
        }

        if (!assignedCoach.getId().equals(reservation.getCoachId())) {
            throw new RuntimeException("教练信息不匹配，请刷新页面重试");
        }

        // 检查该时段该教练是否已被预约
        int count = reservationMapper.checkCoachAvailable(
                reservation.getReservationDate(),
                reservation.getCoachId(),
                reservation.getTimeSlot()
        );
        if (count > 0) {
            throw new RuntimeException("该教练在此时段已被预约");
        }

        // 检查该时段该车辆是否已被预约
        count = reservationMapper.checkVehicleAvailable(
                reservation.getReservationDate(),
                reservation.getVehicleId(),
                reservation.getTimeSlot()
        );
        if (count > 0) {
            throw new RuntimeException("该车辆在此时段已被预约");
        }

        reservation.setStatus("upcoming");
        return reservationMapper.insertReservation(reservation);
    }

    @Override
    public List<Reservation> getStudentReservations(Long studentId, String status) {
        return reservationMapper.selectStudentReservations(studentId, status);
    }

    @Override
    @Transactional
    public int cancelReservation(Long id, Long studentId) {
        // 验证预约是否属于该学员且状态为upcoming
        Reservation reservation = reservationMapper.selectReservationById(id);
        if (reservation == null || !reservation.getStudentId().equals(studentId)) {
            throw new RuntimeException("无权操作此预约");
        }
        if (!"upcoming".equals(reservation.getStatus())) {
            throw new RuntimeException("只能取消即将开始的预约");
        }

        return reservationMapper.updateReservationStatus(id, "cancelled");
    }

    @Override
    @Transactional
    public int deleteReservation(Long id, Long studentId) {
        // 验证预约是否属于该学员且状态为cancelled
        Reservation reservation = reservationMapper.selectReservationById(id);
        if (reservation == null || !reservation.getStudentId().equals(studentId)) {
            throw new RuntimeException("无权操作此预约");
        }
        if (!"cancelled".equals(reservation.getStatus())) {
            throw new RuntimeException("只能删除已取消的预约");
        }

        return reservationMapper.deleteReservation(id);
    }

    @Override
    public Reservation getReservationDetail(Long id) {
        return reservationMapper.selectReservationDetailById(id);
    }
}