package com.ruoyi.jiayun.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/3下午 12:31
 * @Author : SoakLightDust
 */
@Data
public class Reservation {
    private Long id;
    private Long studentId;
    private Long coachId;
    private Long vehicleId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;
    private String timeSlot;
    private String remarks;
    private String status; // upcoming/completed/cancelled

    // 关联信息
    private Coach coach;
    private Vehicle vehicle;
    // 不包含 created_at, updated_at
}