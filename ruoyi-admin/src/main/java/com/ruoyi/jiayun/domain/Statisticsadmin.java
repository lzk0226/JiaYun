package com.ruoyi.jiayun.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 统计数据对象 statistics
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public class Statisticsadmin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 统计ID */
    private Long id;

    /** 学员ID */
    @Excel(name = "学员ID")
    private Long studentId;

    /** 已完成课时 */
    @Excel(name = "已完成课时")
    private Long completedLessons;

    /** 已通过科目数 */
    @Excel(name = "已通过科目数")
    private Long passedSubjects;

    /** 总科目数 */
    @Excel(name = "总科目数")
    private Long totalSubjects;

    /** 预约次数 */
    @Excel(name = "预约次数")
    private Long reservationCount;

    /** 整体进度 */
    @Excel(name = "整体进度")
    private BigDecimal overallProgress;

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

    public void setCompletedLessons(Long completedLessons) 
    {
        this.completedLessons = completedLessons;
    }

    public Long getCompletedLessons() 
    {
        return completedLessons;
    }

    public void setPassedSubjects(Long passedSubjects) 
    {
        this.passedSubjects = passedSubjects;
    }

    public Long getPassedSubjects() 
    {
        return passedSubjects;
    }

    public void setTotalSubjects(Long totalSubjects) 
    {
        this.totalSubjects = totalSubjects;
    }

    public Long getTotalSubjects() 
    {
        return totalSubjects;
    }

    public void setReservationCount(Long reservationCount) 
    {
        this.reservationCount = reservationCount;
    }

    public Long getReservationCount() 
    {
        return reservationCount;
    }

    public void setOverallProgress(BigDecimal overallProgress) 
    {
        this.overallProgress = overallProgress;
    }

    public BigDecimal getOverallProgress() 
    {
        return overallProgress;
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
            .append("completedLessons", getCompletedLessons())
            .append("passedSubjects", getPassedSubjects())
            .append("totalSubjects", getTotalSubjects())
            .append("reservationCount", getReservationCount())
            .append("overallProgress", getOverallProgress())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
