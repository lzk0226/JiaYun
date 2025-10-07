package com.ruoyi.jiayun.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Exam {
    private Long id;
    private Long studentId;
    private Integer subjectId;
    private String title;
    private String examPlace;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime examTime;
    private Integer score;
    private String status; // scheduled/passed/failed/cancelled

    // 关联信息
    private Subject subject;
    // 不包含 created_at, updated_at
}