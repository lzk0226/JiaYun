package com.ruoyi.jiayun.service;

import java.util.List;
import com.ruoyi.jiayun.domain.Vehicleadmin;

/**
 * 车辆Service接口
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public interface IVehicleadminService 
{
    /**
     * 查询车辆
     * 
     * @param id 车辆主键
     * @return 车辆
     */
    public Vehicleadmin selectVehicleadminById(Long id);

    /**
     * 查询车辆列表
     * 
     * @param vehicleadmin 车辆
     * @return 车辆集合
     */
    public List<Vehicleadmin> selectVehicleadminList(Vehicleadmin vehicleadmin);

    /**
     * 新增车辆
     * 
     * @param vehicleadmin 车辆
     * @return 结果
     */
    public int insertVehicleadmin(Vehicleadmin vehicleadmin);

    /**
     * 修改车辆
     * 
     * @param vehicleadmin 车辆
     * @return 结果
     */
    public int updateVehicleadmin(Vehicleadmin vehicleadmin);

    /**
     * 批量删除车辆
     * 
     * @param ids 需要删除的车辆主键集合
     * @return 结果
     */
    public int deleteVehicleadminByIds(Long[] ids);

    /**
     * 删除车辆信息
     * 
     * @param id 车辆主键
     * @return 结果
     */
    public int deleteVehicleadminById(Long id);
}
