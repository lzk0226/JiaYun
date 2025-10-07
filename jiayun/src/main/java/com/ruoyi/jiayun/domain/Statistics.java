package com.ruoyi.jiayun.domain;
import lombok.Data;
import java.math.BigDecimal;

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