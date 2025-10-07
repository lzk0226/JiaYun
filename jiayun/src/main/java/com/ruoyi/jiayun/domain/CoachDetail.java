package com.ruoyi.jiayun.domain;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
@Data
public class CoachDetail {
    private Long id;
    private String name;
    private String title;
    private String badge;
    private String level;
    private Integer experience;
    private Integer studentsCount;
    private BigDecimal rating;
    private Integer reviewsCount;
    private String passRate;
    private List<String> features;
    private String avatar;
    private String description;
    private String subjectsName;
    private String subjectsCode;

    // 可以包含教练的评价列表
    private List<Review> reviews;
}

