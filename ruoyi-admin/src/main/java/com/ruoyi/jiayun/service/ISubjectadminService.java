package com.ruoyi.jiayun.service;

import java.util.List;
import com.ruoyi.jiayun.domain.Subjectadmin;

/**
 * 科目Service接口
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public interface ISubjectadminService 
{
    /**
     * 查询科目
     * 
     * @param id 科目主键
     * @return 科目
     */
    public Subjectadmin selectSubjectadminById(Long id);

    /**
     * 查询科目列表
     * 
     * @param subjectadmin 科目
     * @return 科目集合
     */
    public List<Subjectadmin> selectSubjectadminList(Subjectadmin subjectadmin);

    /**
     * 新增科目
     * 
     * @param subjectadmin 科目
     * @return 结果
     */
    public int insertSubjectadmin(Subjectadmin subjectadmin);

    /**
     * 修改科目
     * 
     * @param subjectadmin 科目
     * @return 结果
     */
    public int updateSubjectadmin(Subjectadmin subjectadmin);

    /**
     * 批量删除科目
     * 
     * @param ids 需要删除的科目主键集合
     * @return 结果
     */
    public int deleteSubjectadminByIds(Long[] ids);

    /**
     * 删除科目信息
     * 
     * @param id 科目主键
     * @return 结果
     */
    public int deleteSubjectadminById(Long id);
}
