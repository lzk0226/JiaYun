package com.ruoyi.jiayun.domain;
import lombok.Data;

@Data
public class Subject {
    private Integer id;
    private String subjectCode;
    private String subjectName;
    private String description;
    private Integer sortOrder;
    // 不包含 status, created_at
}
