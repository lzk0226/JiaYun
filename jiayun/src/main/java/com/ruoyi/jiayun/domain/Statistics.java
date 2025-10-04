package com.ruoyi.jiayun.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/3下午 12:32
 * @Author : SoakLightDust
 */
@Data
public class Statistics {
    private Long id;
    private Long studentId;
    private Integer completedLessons;
    private Integer passedSubjects;
    private Integer totalSubjects;
    private Integer reservationCount;
    private BigDecimal overallProgress;
    // 不包含 updated_at
}