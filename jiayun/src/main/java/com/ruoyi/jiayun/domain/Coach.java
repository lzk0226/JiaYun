package com.ruoyi.jiayun.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/3下午 12:27
 * @Author : SoakLightDust
 */
@Data
public class Coach {
    private Long id;
    private String name;
    private String title;
    private String badge; // 金牌教练/优秀教练/普通教练
    private String level; // gold/excellent/normal
    private Integer experience;
    private Integer studentsCount;
    private BigDecimal rating;
    private Integer reviewsCount;
    private String passRate;
    private List<String> features;
    private String avatar;
    private String description;
    // 不包含 status, created_at, updated_at
}