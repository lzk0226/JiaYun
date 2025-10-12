package com.ruoyi.jiayun.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
import java.util.Date;

@Data
public class Reservation {
    private Long id;
    private Long studentId;
    private Long coachId;
    private Long vehicleId;
    private Integer subjectId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;
    private String timeSlot;
    private String remarks;
    private String status;
    private Date created_at;
    private Date updated_at;

    // 关联信息
    private Coach coach;
    private Vehicle vehicle;
}