package com.ruoyi.jiayun.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/3下午 5:43
 * @Author : SoakLightDust
 */
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