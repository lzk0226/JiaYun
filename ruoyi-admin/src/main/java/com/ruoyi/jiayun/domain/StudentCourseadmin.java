package com.ruoyi.jiayun.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学员课程关联对象 student_course
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public class StudentCourseadmin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报名ID */
    private Long id;

    /** 学员ID */
    @Excel(name = "学员ID")
    private Long studentId;

    /** 课程ID */
    @Excel(name = "课程ID")
    private Long courseId;

    /** 指定教练ID */
    @Excel(name = "指定教练ID")
    private Long coachId;

    /** 报名日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报名日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date enrollDate;

    /** 开课日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开课日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 已完成课时 */
    @Excel(name = "已完成课时")
    private Long completedLessons;

    /** 总课时 */
    @Excel(name = "总课时")
    private Long totalLessons;

    /** 进度百分比 */
    @Excel(name = "进度百分比")
    private BigDecimal progress;

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

    public void setCourseId(Long courseId) 
    {
        this.courseId = courseId;
    }

    public Long getCourseId() 
    {
        return courseId;
    }

    public void setCoachId(Long coachId) 
    {
        this.coachId = coachId;
    }

    public Long getCoachId() 
    {
        return coachId;
    }

    public void setEnrollDate(Date enrollDate) 
    {
        this.enrollDate = enrollDate;
    }

    public Date getEnrollDate() 
    {
        return enrollDate;
    }

    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
    }

    public void setCompletedLessons(Long completedLessons) 
    {
        this.completedLessons = completedLessons;
    }

    public Long getCompletedLessons() 
    {
        return completedLessons;
    }

    public void setTotalLessons(Long totalLessons) 
    {
        this.totalLessons = totalLessons;
    }

    public Long getTotalLessons() 
    {
        return totalLessons;
    }

    public void setProgress(BigDecimal progress) 
    {
        this.progress = progress;
    }

    public BigDecimal getProgress() 
    {
        return progress;
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
            .append("courseId", getCourseId())
            .append("coachId", getCoachId())
            .append("enrollDate", getEnrollDate())
            .append("startDate", getStartDate())
            .append("completedLessons", getCompletedLessons())
            .append("totalLessons", getTotalLessons())
            .append("progress", getProgress())
            .append("status", getStatus())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
