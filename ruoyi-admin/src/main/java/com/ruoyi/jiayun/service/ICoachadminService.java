package com.ruoyi.jiayun.service;

import java.util.List;
import com.ruoyi.jiayun.domain.Coachadmin;

/**
 * 教练Service接口
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public interface ICoachadminService 
{
    /**
     * 查询教练
     * 
     * @param id 教练主键
     * @return 教练
     */
    public Coachadmin selectCoachadminById(Long id);

    /**
     * 查询教练列表
     * 
     * @param coachadmin 教练
     * @return 教练集合
     */
    public List<Coachadmin> selectCoachadminList(Coachadmin coachadmin);

    /**
     * 新增教练
     * 
     * @param coachadmin 教练
     * @return 结果
     */
    public int insertCoachadmin(Coachadmin coachadmin);

    /**
     * 修改教练
     * 
     * @param coachadmin 教练
     * @return 结果
     */
    public int updateCoachadmin(Coachadmin coachadmin);

    /**
     * 批量删除教练
     * 
     * @param ids 需要删除的教练主键集合
     * @return 结果
     */
    public int deleteCoachadminByIds(Long[] ids);

    /**
     * 删除教练信息
     * 
     * @param id 教练主键
     * @return 结果
     */
    public int deleteCoachadminById(Long id);
}
