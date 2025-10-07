package com.ruoyi.jiayun.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class Review {
    private Long id;
    private Long studentId;
    private Long coachId;
    private String studentName;
    private Integer rating;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reviewDate;
    // 不包含 created_at
}