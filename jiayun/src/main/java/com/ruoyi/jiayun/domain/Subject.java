package com.ruoyi.jiayun.domain;

import lombok.Data;

/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/3下午 12:28
 * @Author : SoakLightDust
 */
@Data
public class Subject {
    private Integer id;
    private String subjectCode;
    private String subjectName;
    private String description;
    private Integer sortOrder;
    // 不包含 status, created_at
}
