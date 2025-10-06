package com.ruoyi.jiayun.server;

import com.ruoyi.jiayun.domain.Coach;
import com.ruoyi.jiayun.domain.Reservation;
import com.ruoyi.jiayun.domain.Vehicle;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReservationService {

    /**
     * 获取所有可用教练
     */
    List<Coach> getAvailableCoaches();

    /**
     * 获取指定日期和教练的可用时段
     */
    List<Map<String, Object>> getAvailableTimeSlots(LocalDate date, Long coachId);

    /**
     * 获取指定条件下的可用车辆
     */
    List<Vehicle> getAvailableVehicles(LocalDate date, Long coachId, String timeSlot);

    /**
     * 创建预约
     */
    int createReservation(Reservation reservation);

    /**
     * 获取学员的预约记录
     */
    List<Reservation> getStudentReservations(Long studentId, String status);

    /**
     * 取消预约
     */
    int cancelReservation(Long id, Long studentId);

    /**
     * 删除预约记录
     */
    int deleteReservation(Long id, Long studentId);

    /**
     * 获取预约详情
     */
    Reservation getReservationDetail(Long id);
}