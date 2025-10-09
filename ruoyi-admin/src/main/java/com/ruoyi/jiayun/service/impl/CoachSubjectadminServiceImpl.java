package com.ruoyi.jiayun.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jiayun.mapper.CoachSubjectadminMapper;
import com.ruoyi.jiayun.domain.CoachSubjectadmin;
import com.ruoyi.jiayun.service.ICoachSubjectadminService;

/**
 * 教练科目关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@Service
public class CoachSubjectadminServiceImpl implements ICoachSubjectadminService 
{
    @Autowired
    private CoachSubjectadminMapper coachSubjectadminMapper;

    /**
     * 查询教练科目关联
     * 
     * @param id 教练科目关联主键
     * @return 教练科目关联
     */
    @Override
    public CoachSubjectadmin selectCoachSubjectadminById(Long id)
    {
        return coachSubjectadminMapper.selectCoachSubjectadminById(id);
    }

    /**
     * 查询教练科目关联列表
     * 
     * @param coachSubjectadmin 教练科目关联
     * @return 教练科目关联
     */
    @Override
    public List<CoachSubjectadmin> selectCoachSubjectadminList(CoachSubjectadmin coachSubjectadmin)
    {
        return coachSubjectadminMapper.selectCoachSubjectadminList(coachSubjectadmin);
    }

    /**
     * 新增教练科目关联
     * 
     * @param coachSubjectadmin 教练科目关联
     * @return 结果
     */
    @Override
    public int insertCoachSubjectadmin(CoachSubjectadmin coachSubjectadmin)
    {
        return coachSubjectadminMapper.insertCoachSubjectadmin(coachSubjectadmin);
    }

    /**
     * 修改教练科目关联
     * 
     * @param coachSubjectadmin 教练科目关联
     * @return 结果
     */
    @Override
    public int updateCoachSubjectadmin(CoachSubjectadmin coachSubjectadmin)
    {
        return coachSubjectadminMapper.updateCoachSubjectadmin(coachSubjectadmin);
    }

    /**
     * 批量删除教练科目关联
     * 
     * @param ids 需要删除的教练科目关联主键
     * @return 结果
     */
    @Override
    public int deleteCoachSubjectadminByIds(Long[] ids)
    {
        return coachSubjectadminMapper.deleteCoachSubjectadminByIds(ids);
    }

    /**
     * 删除教练科目关联信息
     * 
     * @param id 教练科目关联主键
     * @return 结果
     */
    @Override
    public int deleteCoachSubjectadminById(Long id)
    {
        return coachSubjectadminMapper.deleteCoachSubjectadminById(id);
    }
}
