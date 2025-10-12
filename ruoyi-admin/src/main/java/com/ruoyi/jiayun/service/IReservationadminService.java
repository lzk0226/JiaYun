package com.ruoyi.jiayun.service;

import java.util.List;
import com.ruoyi.jiayun.domain.Reservationadmin;

/**
 * 预约记录Service接口
 * 
 * @author ruoyi
 * @date 2025-10-12
 */
public interface IReservationadminService 
{
    /**
     * 查询预约记录
     * 
     * @param id 预约记录主键
     * @return 预约记录
     */
    public Reservationadmin selectReservationadminById(Long id);

    /**
     * 查询预约记录列表
     * 
     * @param reservationadmin 预约记录
     * @return 预约记录集合
     */
    public List<Reservationadmin> selectReservationadminList(Reservationadmin reservationadmin);

    /**
     * 新增预约记录
     * 
     * @param reservationadmin 预约记录
     * @return 结果
     */
    public int insertReservationadmin(Reservationadmin reservationadmin);

    /**
     * 修改预约记录
     * 
     * @param reservationadmin 预约记录
     * @return 结果
     */
    public int updateReservationadmin(Reservationadmin reservationadmin);

    /**
     * 批量删除预约记录
     * 
     * @param ids 需要删除的预约记录主键集合
     * @return 结果
     */
    public int deleteReservationadminByIds(Long[] ids);

    /**
     * 删除预约记录信息
     * 
     * @param id 预约记录主键
     * @return 结果
     */
    public int deleteReservationadminById(Long id);
}
