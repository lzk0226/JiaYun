package com.ruoyi.jiayun.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jiayun.mapper.CoachadminMapper;
import com.ruoyi.jiayun.domain.Coachadmin;
import com.ruoyi.jiayun.service.ICoachadminService;

/**
 * 教练Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@Service
public class CoachadminServiceImpl implements ICoachadminService 
{
    @Autowired
    private CoachadminMapper coachadminMapper;

    /**
     * 查询教练
     * 
     * @param id 教练主键
     * @return 教练
     */
    @Override
    public Coachadmin selectCoachadminById(Long id)
    {
        return coachadminMapper.selectCoachadminById(id);
    }

    /**
     * 查询教练列表
     * 
     * @param coachadmin 教练
     * @return 教练
     */
    @Override
    public List<Coachadmin> selectCoachadminList(Coachadmin coachadmin)
    {
        return coachadminMapper.selectCoachadminList(coachadmin);
    }

    /**
     * 新增教练
     * 
     * @param coachadmin 教练
     * @return 结果
     */
    @Override
    public int insertCoachadmin(Coachadmin coachadmin)
    {
        return coachadminMapper.insertCoachadmin(coachadmin);
    }

    /**
     * 修改教练
     * 
     * @param coachadmin 教练
     * @return 结果
     */
    @Override
    public int updateCoachadmin(Coachadmin coachadmin)
    {
        return coachadminMapper.updateCoachadmin(coachadmin);
    }

    /**
     * 批量删除教练
     * 
     * @param ids 需要删除的教练主键
     * @return 结果
     */
    @Override
    public int deleteCoachadminByIds(Long[] ids)
    {
        return coachadminMapper.deleteCoachadminByIds(ids);
    }

    /**
     * 删除教练信息
     * 
     * @param id 教练主键
     * @return 结果
     */
    @Override
    public int deleteCoachadminById(Long id)
    {
        return coachadminMapper.deleteCoachadminById(id);
    }
}
