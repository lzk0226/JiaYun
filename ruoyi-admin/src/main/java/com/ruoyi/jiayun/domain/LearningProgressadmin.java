package com.ruoyi.jiayun.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学习进度对象 learning_progress
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public class LearningProgressadmin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 进度ID */
    private Long id;

    /** 学员ID */
    @Excel(name = "学员ID")
    private Long studentId;

    /** 科目ID */
    @Excel(name = "科目ID")
    private Long subjectId;

    /** 已完成课时 */
    @Excel(name = "已完成课时")
    private String completedLessons;

    /** 进度百分比 */
    @Excel(name = "进度百分比")
    private Long progressPercent;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

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

    public void setStudentId(Long studentId) 
    {
        this.studentId = studentId;
    }

    public Long getStudentId() 
    {
        return studentId;
    }

    public void setSubjectId(Long subjectId) 
    {
        this.subjectId = subjectId;
    }

    public Long getSubjectId() 
    {
        return subjectId;
    }

    public void setCompletedLessons(String completedLessons) 
    {
        this.completedLessons = completedLessons;
    }

    public String getCompletedLessons() 
    {
        return completedLessons;
    }

    public void setProgressPercent(Long progressPercent) 
    {
        this.progressPercent = progressPercent;
    }

    public Long getProgressPercent() 
    {
        return progressPercent;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
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
            .append("studentId", getStudentId())
            .append("subjectId", getSubjectId())
            .append("completedLessons", getCompletedLessons())
            .append("progressPercent", getProgressPercent())
            .append("status", getStatus())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
