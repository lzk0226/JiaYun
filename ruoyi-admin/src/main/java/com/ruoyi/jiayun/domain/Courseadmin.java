package com.ruoyi.jiayun.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课程对象 course
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public class Courseadmin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 课程ID */
    private Long id;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String name;

    /** 主要科目ID（单科课程） */
    @Excel(name = "主要科目ID", readConverterExp = "单=科课程")
    private Long subjectId;

    /** 课程类型 */
    @Excel(name = "课程类型")
    private String type;

    /** 副标题 */
    @Excel(name = "副标题")
    private String subtitle;

    /** 标签 */
    @Excel(name = "标签")
    private String badge;

    /** 课时数 */
    @Excel(name = "课时数")
    private Long duration;

    /** 完成周期 */
    @Excel(name = "完成周期")
    private String period;

    /** 课程特点 */
    @Excel(name = "课程特点")
    private String features;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 热度值 */
    @Excel(name = "热度值")
    private Long popular;

    /** 报名人数 */
    @Excel(name = "报名人数")
    private Long studentsCount;

    /** 是否联报课程：1-是，0-否 */
    @Excel(name = "是否联报课程：1-是，0-否")
    private Long isCombined;

    /** 状态：1-启用，0-停用 */
    @Excel(name = "状态：1-启用，0-停用")
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

    public void setSubjectId(Long subjectId) 
    {
        this.subjectId = subjectId;
    }

    public Long getSubjectId() 
    {
        return subjectId;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    public void setSubtitle(String subtitle) 
    {
        this.subtitle = subtitle;
    }

    public String getSubtitle() 
    {
        return subtitle;
    }

    public void setBadge(String badge) 
    {
        this.badge = badge;
    }

    public String getBadge() 
    {
        return badge;
    }

    public void setDuration(Long duration) 
    {
        this.duration = duration;
    }

    public Long getDuration() 
    {
        return duration;
    }

    public void setPeriod(String period) 
    {
        this.period = period;
    }

    public String getPeriod() 
    {
        return period;
    }

    public void setFeatures(String features) 
    {
        this.features = features;
    }

    public String getFeatures() 
    {
        return features;
    }

    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    public void setPopular(Long popular) 
    {
        this.popular = popular;
    }

    public Long getPopular() 
    {
        return popular;
    }

    public void setStudentsCount(Long studentsCount) 
    {
        this.studentsCount = studentsCount;
    }

    public Long getStudentsCount() 
    {
        return studentsCount;
    }

    public void setIsCombined(Long isCombined) 
    {
        this.isCombined = isCombined;
    }

    public Long getIsCombined() 
    {
        return isCombined;
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
            .append("subjectId", getSubjectId())
            .append("type", getType())
            .append("subtitle", getSubtitle())
            .append("badge", getBadge())
            .append("duration", getDuration())
            .append("period", getPeriod())
            .append("features", getFeatures())
            .append("price", getPrice())
            .append("popular", getPopular())
            .append("studentsCount", getStudentsCount())
            .append("isCombined", getIsCombined())
            .append("status", getStatus())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
