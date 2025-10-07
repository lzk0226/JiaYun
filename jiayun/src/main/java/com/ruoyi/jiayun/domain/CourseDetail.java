package com.ruoyi.jiayun.domain;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CourseDetail {
    private Long id;
    private String name;
    private Integer subjectId;
    private String type;
    private String subtitle;
    private String badge;
    private Integer duration;
    private String period;
    private List<String> features;
    private BigDecimal price;
    private Integer popular;
    private Integer studentsCount;
    private Boolean isCombined;
    private String subjectsName;
}