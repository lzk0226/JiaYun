package com.ruoyi.jiayun.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/3下午 12:32
 * @Author : SoakLightDust
 */
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