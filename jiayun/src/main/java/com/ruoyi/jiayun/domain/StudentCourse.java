package com.ruoyi.jiayun.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class StudentCourse {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Long coachId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate enrollDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private Integer completedLessons;
    private Integer totalLessons;
    private BigDecimal progress;
    private String status; // ongoing/completed/cancelled

    // 关联信息
    private Course course;
    private CourseDetail courseDetail;
    private Coach coach;
}