package com.ruoyi.jiayun.mapper;

import com.ruoyi.jiayun.domain.Coach;
import com.ruoyi.jiayun.domain.Reservation;
import com.ruoyi.jiayun.domain.Vehicle;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReservationMapper {

    /**
     * 获取所有在职教练
     */
    @Select("SELECT id, name, title, badge, level FROM coach WHERE status = 1 ORDER BY level DESC, rating DESC")
    List<Coach> selectAvailableCoaches();

    /**
     * 获取指定日期和教练已预约的时段
     */
    @Select("SELECT DISTINCT time_slot FROM reservation " +
            "WHERE coach_id = #{coachId} " +
            "AND reservation_date = #{date} " +
            "AND status = 'upcoming'")
    List<String> selectBookedTimeSlots(@Param("date") LocalDate date, @Param("coachId") Long coachId);

    /**
     * 获取指定日期和时段已被预约的车辆ID
     */
    @Select("SELECT DISTINCT vehicle_id FROM reservation " +
            "WHERE reservation_date = #{date} " +
            "AND time_slot = #{timeSlot} " +
            "AND status = 'upcoming'")
    List<Long> selectBookedVehicles(@Param("date") LocalDate date, @Param("timeSlot") String timeSlot);

    /**
     * 获取可用车辆（排除已预约的）
     */
    @Select("<script>" +
            "SELECT id, plate_number, model, vehicle_image, status " +
            "FROM vehicle " +
            "WHERE status = 'available' " +
            "<if test='bookedIds != null and bookedIds.size() > 0'>" +
            "AND id NOT IN " +
            "<foreach collection='bookedIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</if>" +
            "ORDER BY id" +
            "</script>")
    List<Vehicle> selectAvailableVehicles(@Param("bookedIds") List<Long> bookedIds);

    /**
     * 检查教练在指定时段是否可用
     */
    @Select("SELECT COUNT(*) FROM reservation " +
            "WHERE coach_id = #{coachId} " +
            "AND reservation_date = #{date} " +
            "AND time_slot = #{timeSlot} " +
            "AND status = 'upcoming'")
    int checkCoachAvailable(@Param("date") LocalDate date,
                            @Param("coachId") Long coachId,
                            @Param("timeSlot") String timeSlot);

    /**
     * 检查车辆在指定时段是否可用
     */
    @Select("SELECT COUNT(*) FROM reservation " +
            "WHERE vehicle_id = #{vehicleId} " +
            "AND reservation_date = #{date} " +
            "AND time_slot = #{timeSlot} " +
            "AND status = 'upcoming'")
    int checkVehicleAvailable(@Param("date") LocalDate date,
                              @Param("vehicleId") Long vehicleId,
                              @Param("timeSlot") String timeSlot);

    /**
     * 插入预约记录
     */
    @Insert("INSERT INTO reservation(student_id, coach_id, vehicle_id, reservation_date, time_slot, remarks, status) " +
            "VALUES(#{studentId}, #{coachId}, #{vehicleId}, #{reservationDate}, #{timeSlot}, #{remarks}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertReservation(Reservation reservation);

    /**
     * 查询学员的预约记录
     */
    @Select("<script>" +
            "SELECT r.id, r.student_id, r.coach_id, r.vehicle_id, r.reservation_date, " +
            "r.time_slot, r.remarks, r.status, r.created_at, r.updated_at " +
            "FROM reservation r " +
            "WHERE r.student_id = #{studentId} " +
            "<if test='status != null and status != \"\"'>" +
            "AND r.status = #{status} " +
            "</if>" +
            "ORDER BY r.reservation_date DESC, r.time_slot DESC" +
            "</script>")
    @Results(id = "reservationMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "coachId", column = "coach_id"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "reservationDate", column = "reservation_date"),
            @Result(property = "timeSlot", column = "time_slot"),
            @Result(property = "remarks", column = "remarks"),
            @Result(property = "status", column = "status"),
            @Result(property = "created_at", column = "created_at"),
            @Result(property = "updated_at", column = "updated_at"),
            @Result(property = "coach", column = "coach_id",
                    one = @One(select = "selectCoachById")),
            @Result(property = "vehicle", column = "vehicle_id",
                    one = @One(select = "selectVehicleById"))
    })
    List<Reservation> selectStudentReservations(@Param("studentId") Long studentId,
                                                @Param("status") String status);

    /**
     * 根据ID查询教练信息
     */
    @Select("SELECT id, name, title, avatar FROM coach WHERE id = #{id}")
    Coach selectCoachById(@Param("id") Long id);

    /**
     * 根据ID查询车辆信息
     */
    @Select("SELECT id, plate_number, model, vehicle_image FROM vehicle WHERE id = #{id}")
    Vehicle selectVehicleById(@Param("id") Long id);

    /**
     * 根据ID查询预约记录（不含关联）
     */
    /**
     * 根据ID查询预约记录（不含关联）
     */
    @Select("SELECT id, student_id, coach_id, vehicle_id, reservation_date, " +
            "time_slot, remarks, status, created_at, updated_at " +
            "FROM reservation WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "coachId", column = "coach_id"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "reservationDate", column = "reservation_date"),
            @Result(property = "timeSlot", column = "time_slot"),
            @Result(property = "remarks", column = "remarks"),
            @Result(property = "status", column = "status"),
            @Result(property = "created_at", column = "created_at"),
            @Result(property = "updated_at", column = "updated_at")
    })
    Reservation selectReservationById(@Param("id") Long id);

    /**
     * 查询预约详情（含关联信息）
     */
    @Select("SELECT r.id, r.student_id, r.coach_id, r.vehicle_id, r.reservation_date, " +
            "r.time_slot, r.remarks, r.status, r.created_at, r.updated_at " +
            "FROM reservation r " +
            "WHERE r.id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "coachId", column = "coach_id"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "reservationDate", column = "reservation_date"),
            @Result(property = "timeSlot", column = "time_slot"),
            @Result(property = "remarks", column = "remarks"),
            @Result(property = "status", column = "status"),
            @Result(property = "created_at", column = "created_at"),
            @Result(property = "updated_at", column = "updated_at"),
            @Result(property = "coach", column = "coach_id",
                    one = @One(select = "selectCoachById")),
            @Result(property = "vehicle", column = "vehicle_id",
                    one = @One(select = "selectVehicleById"))
    })
    Reservation selectReservationDetailById(@Param("id") Long id);

    /**
     * 更新预约状态
     */
    @Update("UPDATE reservation SET status = #{status}, updated_at = NOW() WHERE id = #{id}")
    int updateReservationStatus(@Param("id") Long id, @Param("status") String status);

    /**
     * 删除预约记录
     */
    @Delete("DELETE FROM reservation WHERE id = #{id}")
    int deleteReservation(@Param("id") Long id);
}