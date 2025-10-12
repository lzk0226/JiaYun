package com.ruoyi.jiayun.server;

import com.ruoyi.jiayun.domain.Coach;
import com.ruoyi.jiayun.domain.Reservation;
import com.ruoyi.jiayun.domain.Vehicle;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReservationService {

    /**
     * 获取学员在指定科目下的教练（基于已报名课程）
     * @param studentId 学员ID
     * @param subjectId 科目ID
     * @return 教练信息
     */
    Coach getStudentCoachBySubject(Long studentId, Integer subjectId);

    /**
     * 获取指定日期和教练的可用时段
     * @param date 预约日期
     * @param coachId 教练ID
     * @return 时段列表
     */
    List<Map<String, Object>> getAvailableTimeSlots(LocalDate date, Long coachId);

    /**
     * 获取指定日期、时段和科目的可用车辆
     * @param date 预约日期
     * @param subjectId 科目ID
     * @param timeSlot 时段
     * @return 车辆列表
     */
    List<Vehicle> getAvailableVehicles(LocalDate date, Integer subjectId, String timeSlot);

    /**
     * 创建预约
     * @param reservation 预约信息
     * @return 影响行数
     */
    int createReservation(Reservation reservation);

    /**
     * 获取学员的预约记录
     * @param studentId 学员ID
     * @param status 状态筛选（可选）
     * @return 预约列表
     */
    List<Reservation> getStudentReservations(Long studentId, String status);

    /**
     * 取消预约
     * @param id 预约ID
     * @param studentId 学员ID
     * @return 影响行数
     */
    int cancelReservation(Long id, Long studentId);

    /**
     * 删除预约记录
     * @param id 预约ID
     * @param studentId 学员ID
     * @return 影响行数
     */
    int deleteReservation(Long id, Long studentId);

    /**
     * 获取预约详情
     * @param id 预约ID
     * @return 预约详情
     */
    Reservation getReservationDetail(Long id);
}