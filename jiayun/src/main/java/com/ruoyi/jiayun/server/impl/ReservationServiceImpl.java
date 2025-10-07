package com.ruoyi.jiayun.server.impl;
import com.ruoyi.jiayun.domain.Coach;
import com.ruoyi.jiayun.domain.Reservation;
import com.ruoyi.jiayun.domain.Vehicle;
import com.ruoyi.jiayun.mapper.ReservationMapper;
import com.ruoyi.jiayun.server.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;

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
    public List<Coach> getAvailableCoaches() {
        return reservationMapper.selectAvailableCoaches();
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
    public List<Vehicle> getAvailableVehicles(LocalDate date, Long coachId, String timeSlot) {
        // 获取该时段已被预约的车辆ID
        List<Long> bookedVehicleIds = reservationMapper.selectBookedVehicles(date, timeSlot);

        // 获取所有可用车辆，排除已预约的
        return reservationMapper.selectAvailableVehicles(bookedVehicleIds);
    }

    @Override
    @Transactional
    public int createReservation(Reservation reservation) {
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