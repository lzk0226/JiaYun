package com.ruoyi.jiayun.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 教练对象 coach
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public class Coachadmin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 教练ID */
    private Long id;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 职称 */
    @Excel(name = "职称")
    private String title;

    /** 徽章 */
    @Excel(name = "徽章")
    private String badge;

    /** 等级 */
    @Excel(name = "等级")
    private String level;

    /** 教学经验（年） */
    @Excel(name = "教学经验", readConverterExp = "年=")
    private Long experience;

    /** 培训学员数 */
    @Excel(name = "培训学员数")
    private Long studentsCount;

    /** 评分（5分制） */
    @Excel(name = "评分", readConverterExp = "5=分制")
    private BigDecimal rating;

    /** 评价数量 */
    @Excel(name = "评价数量")
    private Long reviewsCount;

    /** 通过率 */
    @Excel(name = "通过率")
    private String passRate;

    /** 教学特点标签 */
    @Excel(name = "教学特点标签")
    private String features;

    /** 头像base64编码 */
    @Excel(name = "头像base64编码")
    private String avatar;

    /** 教练简介 */
    @Excel(name = "教练简介")
    private String description;

    /** 状态：1-在职，0-离职 */
    @Excel(name = "状态：1-在职，0-离职")
    private Long status;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createdAt;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updatedAt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setBadge(String badge) 
    {
        this.badge = badge;
    }

    public String getBadge() 
    {
        return badge;
    }

    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }

    public void setExperience(Long experience) 
    {
        this.experience = experience;
    }

    public Long getExperience() 
    {
        return experience;
    }

    public void setStudentsCount(Long studentsCount) 
    {
        this.studentsCount = studentsCount;
    }

    public Long getStudentsCount() 
    {
        return studentsCount;
    }

    public void setRating(BigDecimal rating) 
    {
        this.rating = rating;
    }

    public BigDecimal getRating() 
    {
        return rating;
    }

    public void setReviewsCount(Long reviewsCount) 
    {
        this.reviewsCount = reviewsCount;
    }

    public Long getReviewsCount() 
    {
        return reviewsCount;
    }

    public void setPassRate(String passRate) 
    {
        this.passRate = passRate;
    }

    public String getPassRate() 
    {
        return passRate;
    }

    public void setFeatures(String features) 
    {
        this.features = features;
    }

    public String getFeatures() 
    {
        return features;
    }

    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("title", getTitle())
            .append("badge", getBadge())
            .append("level", getLevel())
            .append("experience", getExperience())
            .append("studentsCount", getStudentsCount())
            .append("rating", getRating())
            .append("reviewsCount", getReviewsCount())
            .append("passRate", getPassRate())
            .append("features", getFeatures())
            .append("avatar", getAvatar())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
