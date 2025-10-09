package com.ruoyi.jiayun.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jiayun.mapper.SubjectadminMapper;
import com.ruoyi.jiayun.domain.Subjectadmin;
import com.ruoyi.jiayun.service.ISubjectadminService;

/**
 * 科目Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@Service
public class SubjectadminServiceImpl implements ISubjectadminService 
{
    @Autowired
    private SubjectadminMapper subjectadminMapper;

    /**
     * 查询科目
     * 
     * @param id 科目主键
     * @return 科目
     */
    @Override
    public Subjectadmin selectSubjectadminById(Long id)
    {
        return subjectadminMapper.selectSubjectadminById(id);
    }

    /**
     * 查询科目列表
     * 
     * @param subjectadmin 科目
     * @return 科目
     */
    @Override
    public List<Subjectadmin> selectSubjectadminList(Subjectadmin subjectadmin)
    {
        return subjectadminMapper.selectSubjectadminList(subjectadmin);
    }

    /**
     * 新增科目
     * 
     * @param subjectadmin 科目
     * @return 结果
     */
    @Override
    public int insertSubjectadmin(Subjectadmin subjectadmin)
    {
        return subjectadminMapper.insertSubjectadmin(subjectadmin);
    }

    /**
     * 修改科目
     * 
     * @param subjectadmin 科目
     * @return 结果
     */
    @Override
    public int updateSubjectadmin(Subjectadmin subjectadmin)
    {
        return subjectadminMapper.updateSubjectadmin(subjectadmin);
    }

    /**
     * 批量删除科目
     * 
     * @param ids 需要删除的科目主键
     * @return 结果
     */
    @Override
    public int deleteSubjectadminByIds(Long[] ids)
    {
        return subjectadminMapper.deleteSubjectadminByIds(ids);
    }

    /**
     * 删除科目信息
     * 
     * @param id 科目主键
     * @return 结果
     */
    @Override
    public int deleteSubjectadminById(Long id)
    {
        return subjectadminMapper.deleteSubjectadminById(id);
    }
}
