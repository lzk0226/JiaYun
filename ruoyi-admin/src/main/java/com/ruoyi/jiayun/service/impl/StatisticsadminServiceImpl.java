package com.ruoyi.jiayun.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jiayun.mapper.StatisticsadminMapper;
import com.ruoyi.jiayun.domain.Statisticsadmin;
import com.ruoyi.jiayun.service.IStatisticsadminService;

/**
 * 统计数据Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@Service
public class StatisticsadminServiceImpl implements IStatisticsadminService 
{
    @Autowired
    private StatisticsadminMapper statisticsadminMapper;

    /**
     * 查询统计数据
     * 
     * @param id 统计数据主键
     * @return 统计数据
     */
    @Override
    public Statisticsadmin selectStatisticsadminById(Long id)
    {
        return statisticsadminMapper.selectStatisticsadminById(id);
    }

    /**
     * 查询统计数据列表
     * 
     * @param statisticsadmin 统计数据
     * @return 统计数据
     */
    @Override
    public List<Statisticsadmin> selectStatisticsadminList(Statisticsadmin statisticsadmin)
    {
        return statisticsadminMapper.selectStatisticsadminList(statisticsadmin);
    }

    /**
     * 新增统计数据
     * 
     * @param statisticsadmin 统计数据
     * @return 结果
     */
    @Override
    public int insertStatisticsadmin(Statisticsadmin statisticsadmin)
    {
        return statisticsadminMapper.insertStatisticsadmin(statisticsadmin);
    }

    /**
     * 修改统计数据
     * 
     * @param statisticsadmin 统计数据
     * @return 结果
     */
    @Override
    public int updateStatisticsadmin(Statisticsadmin statisticsadmin)
    {
        return statisticsadminMapper.updateStatisticsadmin(statisticsadmin);
    }

    /**
     * 批量删除统计数据
     * 
     * @param ids 需要删除的统计数据主键
     * @return 结果
     */
    @Override
    public int deleteStatisticsadminByIds(Long[] ids)
    {
        return statisticsadminMapper.deleteStatisticsadminByIds(ids);
    }

    /**
     * 删除统计数据信息
     * 
     * @param id 统计数据主键
     * @return 结果
     */
    @Override
    public int deleteStatisticsadminById(Long id)
    {
        return statisticsadminMapper.deleteStatisticsadminById(id);
    }
}
