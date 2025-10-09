package com.ruoyi.jiayun.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车辆对象 vehicle
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public class Vehicleadmin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 车辆ID */
    private Long id;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String plateNumber;

    /** 车辆型号 */
    @Excel(name = "车辆型号")
    private String model;

    /** 车辆图片base64编码 */
    @Excel(name = "车辆图片base64编码")
    private String vehicleImage;

    /** 车辆状态 */
    @Excel(name = "车辆状态")
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

    public void setPlateNumber(String plateNumber) 
    {
        this.plateNumber = plateNumber;
    }

    public String getPlateNumber() 
    {
        return plateNumber;
    }

    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }

    public void setVehicleImage(String vehicleImage) 
    {
        this.vehicleImage = vehicleImage;
    }

    public String getVehicleImage() 
    {
        return vehicleImage;
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
            .append("plateNumber", getPlateNumber())
            .append("model", getModel())
            .append("vehicleImage", getVehicleImage())
            .append("status", getStatus())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
