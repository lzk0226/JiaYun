package com.ruoyi.jiayun.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jiayun.mapper.ExamadminMapper;
import com.ruoyi.jiayun.domain.Examadmin;
import com.ruoyi.jiayun.service.IExamadminService;

/**
 * 考试记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@Service
public class ExamadminServiceImpl implements IExamadminService 
{
    @Autowired
    private ExamadminMapper examadminMapper;

    /**
     * 查询考试记录
     * 
     * @param id 考试记录主键
     * @return 考试记录
     */
    @Override
    public Examadmin selectExamadminById(Long id)
    {
        return examadminMapper.selectExamadminById(id);
    }

    /**
     * 查询考试记录列表
     * 
     * @param examadmin 考试记录
     * @return 考试记录
     */
    @Override
    public List<Examadmin> selectExamadminList(Examadmin examadmin)
    {
        return examadminMapper.selectExamadminList(examadmin);
    }

    /**
     * 新增考试记录
     * 
     * @param examadmin 考试记录
     * @return 结果
     */
    @Override
    public int insertExamadmin(Examadmin examadmin)
    {
        return examadminMapper.insertExamadmin(examadmin);
    }

    /**
     * 修改考试记录
     * 
     * @param examadmin 考试记录
     * @return 结果
     */
    @Override
    public int updateExamadmin(Examadmin examadmin)
    {
        return examadminMapper.updateExamadmin(examadmin);
    }

    /**
     * 批量删除考试记录
     * 
     * @param ids 需要删除的考试记录主键
     * @return 结果
     */
    @Override
    public int deleteExamadminByIds(Long[] ids)
    {
        return examadminMapper.deleteExamadminByIds(ids);
    }

    /**
     * 删除考试记录信息
     * 
     * @param id 考试记录主键
     * @return 结果
     */
    @Override
    public int deleteExamadminById(Long id)
    {
        return examadminMapper.deleteExamadminById(id);
    }
}
