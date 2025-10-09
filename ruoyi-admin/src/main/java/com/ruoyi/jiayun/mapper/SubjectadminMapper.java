package com.ruoyi.jiayun.mapper;

import java.util.List;
import com.ruoyi.jiayun.domain.Subjectadmin;

/**
 * 科目Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public interface SubjectadminMapper 
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
     * 删除科目
     * 
     * @param id 科目主键
     * @return 结果
     */
    public int deleteSubjectadminById(Long id);

    /**
     * 批量删除科目
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSubjectadminByIds(Long[] ids);
}
