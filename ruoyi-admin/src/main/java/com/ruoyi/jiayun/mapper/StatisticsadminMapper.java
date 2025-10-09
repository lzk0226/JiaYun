package com.ruoyi.jiayun.mapper;

import java.util.List;
import com.ruoyi.jiayun.domain.Statisticsadmin;

/**
 * 统计数据Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public interface StatisticsadminMapper 
{
    /**
     * 查询统计数据
     * 
     * @param id 统计数据主键
     * @return 统计数据
     */
    public Statisticsadmin selectStatisticsadminById(Long id);

    /**
     * 查询统计数据列表
     * 
     * @param statisticsadmin 统计数据
     * @return 统计数据集合
     */
    public List<Statisticsadmin> selectStatisticsadminList(Statisticsadmin statisticsadmin);

    /**
     * 新增统计数据
     * 
     * @param statisticsadmin 统计数据
     * @return 结果
     */
    public int insertStatisticsadmin(Statisticsadmin statisticsadmin);

    /**
     * 修改统计数据
     * 
     * @param statisticsadmin 统计数据
     * @return 结果
     */
    public int updateStatisticsadmin(Statisticsadmin statisticsadmin);

    /**
     * 删除统计数据
     * 
     * @param id 统计数据主键
     * @return 结果
     */
    public int deleteStatisticsadminById(Long id);

    /**
     * 批量删除统计数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStatisticsadminByIds(Long[] ids);
}
