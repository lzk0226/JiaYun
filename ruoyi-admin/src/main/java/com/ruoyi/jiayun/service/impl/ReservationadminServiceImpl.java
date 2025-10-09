package com.ruoyi.jiayun.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jiayun.mapper.ReservationadminMapper;
import com.ruoyi.jiayun.domain.Reservationadmin;
import com.ruoyi.jiayun.service.IReservationadminService;

/**
 * 预约记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@Service
public class ReservationadminServiceImpl implements IReservationadminService 
{
    @Autowired
    private ReservationadminMapper reservationadminMapper;

    /**
     * 查询预约记录
     * 
     * @param id 预约记录主键
     * @return 预约记录
     */
    @Override
    public Reservationadmin selectReservationadminById(Long id)
    {
        return reservationadminMapper.selectReservationadminById(id);
    }

    /**
     * 查询预约记录列表
     * 
     * @param reservationadmin 预约记录
     * @return 预约记录
     */
    @Override
    public List<Reservationadmin> selectReservationadminList(Reservationadmin reservationadmin)
    {
        return reservationadminMapper.selectReservationadminList(reservationadmin);
    }

    /**
     * 新增预约记录
     * 
     * @param reservationadmin 预约记录
     * @return 结果
     */
    @Override
    public int insertReservationadmin(Reservationadmin reservationadmin)
    {
        return reservationadminMapper.insertReservationadmin(reservationadmin);
    }

    /**
     * 修改预约记录
     * 
     * @param reservationadmin 预约记录
     * @return 结果
     */
    @Override
    public int updateReservationadmin(Reservationadmin reservationadmin)
    {
        return reservationadminMapper.updateReservationadmin(reservationadmin);
    }

    /**
     * 批量删除预约记录
     * 
     * @param ids 需要删除的预约记录主键
     * @return 结果
     */
    @Override
    public int deleteReservationadminByIds(Long[] ids)
    {
        return reservationadminMapper.deleteReservationadminByIds(ids);
    }

    /**
     * 删除预约记录信息
     * 
     * @param id 预约记录主键
     * @return 结果
     */
    @Override
    public int deleteReservationadminById(Long id)
    {
        return reservationadminMapper.deleteReservationadminById(id);
    }
}
