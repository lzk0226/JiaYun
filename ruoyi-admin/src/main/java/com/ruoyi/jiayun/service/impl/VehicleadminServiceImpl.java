package com.ruoyi.jiayun.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jiayun.mapper.VehicleadminMapper;
import com.ruoyi.jiayun.domain.Vehicleadmin;
import com.ruoyi.jiayun.service.IVehicleadminService;

/**
 * 车辆Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@Service
public class VehicleadminServiceImpl implements IVehicleadminService 
{
    @Autowired
    private VehicleadminMapper vehicleadminMapper;

    /**
     * 查询车辆
     * 
     * @param id 车辆主键
     * @return 车辆
     */
    @Override
    public Vehicleadmin selectVehicleadminById(Long id)
    {
        return vehicleadminMapper.selectVehicleadminById(id);
    }

    /**
     * 查询车辆列表
     * 
     * @param vehicleadmin 车辆
     * @return 车辆
     */
    @Override
    public List<Vehicleadmin> selectVehicleadminList(Vehicleadmin vehicleadmin)
    {
        return vehicleadminMapper.selectVehicleadminList(vehicleadmin);
    }

    /**
     * 新增车辆
     * 
     * @param vehicleadmin 车辆
     * @return 结果
     */
    @Override
    public int insertVehicleadmin(Vehicleadmin vehicleadmin)
    {
        return vehicleadminMapper.insertVehicleadmin(vehicleadmin);
    }

    /**
     * 修改车辆
     * 
     * @param vehicleadmin 车辆
     * @return 结果
     */
    @Override
    public int updateVehicleadmin(Vehicleadmin vehicleadmin)
    {
        return vehicleadminMapper.updateVehicleadmin(vehicleadmin);
    }

    /**
     * 批量删除车辆
     * 
     * @param ids 需要删除的车辆主键
     * @return 结果
     */
    @Override
    public int deleteVehicleadminByIds(Long[] ids)
    {
        return vehicleadminMapper.deleteVehicleadminByIds(ids);
    }

    /**
     * 删除车辆信息
     * 
     * @param id 车辆主键
     * @return 结果
     */
    @Override
    public int deleteVehicleadminById(Long id)
    {
        return vehicleadminMapper.deleteVehicleadminById(id);
    }
}
