package com.ruoyi.jiayun.domain;

import lombok.Data;

/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/3下午 12:32
 * @Author : SoakLightDust
 */
@Data
public class LearningProgress {
    private Long id;
    private Long studentId;
    private Integer subjectId;
    private String completedLessons;
    private Integer progressPercent;
    private String status; // not_started/in_progress/completed

    // 关联信息
    private Subject subject;
    // 不包含 created_at, updated_at
}
