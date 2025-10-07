package com.ruoyi.jiayun.domain;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class Course {
    private Long id;
    private String name;
    private Integer subjectId;
    private String type; // standard/vip/intensive
    private String subtitle;
    private String badge;
    private Integer duration;
    private String period;
    private List<String> features;
    private BigDecimal price;
    private Integer popular;
    private Integer studentsCount;
    private Boolean isCombined;
    private String subjectsName; // 关联科目名称
    // 不包含 status, created_at, updated_at
}