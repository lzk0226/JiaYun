package com.ruoyi.jiayun.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 预约记录对象 reservation
 * 
 * @author ruoyi
 * @date 2025-10-12
 */
public class Reservationadmin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预约ID */
    private Long id;

    /** 学员ID */
    @Excel(name = "学员ID")
    private Long studentId;

    /** 教练ID */
    @Excel(name = "教练ID")
    private Long coachId;

    /** 科目ID */
    @Excel(name = "科目ID")
    private Long subjectId;

    /** 车辆ID */
    @Excel(name = "车辆ID")
    private Long vehicleId;

    /** 预约日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预约日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reservationDate;

    /** 时间段 */
    @Excel(name = "时间段")
    private String timeSlot;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String remarks;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** $column.columnComment */
    private Date createdAt;

    /** $column.columnComment */
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

    public void setCoachId(Long coachId) 
    {
        this.coachId = coachId;
    }

    public Long getCoachId() 
    {
        return coachId;
    }

    public void setSubjectId(Long subjectId) 
    {
        this.subjectId = subjectId;
    }

    public Long getSubjectId() 
    {
        return subjectId;
    }

    public void setVehicleId(Long vehicleId) 
    {
        this.vehicleId = vehicleId;
    }

    public Long getVehicleId() 
    {
        return vehicleId;
    }

    public void setReservationDate(Date reservationDate) 
    {
        this.reservationDate = reservationDate;
    }

    public Date getReservationDate() 
    {
        return reservationDate;
    }

    public void setTimeSlot(String timeSlot) 
    {
        this.timeSlot = timeSlot;
    }

    public String getTimeSlot() 
    {
        return timeSlot;
    }

    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
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
            .append("coachId", getCoachId())
            .append("subjectId", getSubjectId())
            .append("vehicleId", getVehicleId())
            .append("reservationDate", getReservationDate())
            .append("timeSlot", getTimeSlot())
            .append("remarks", getRemarks())
            .append("status", getStatus())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
