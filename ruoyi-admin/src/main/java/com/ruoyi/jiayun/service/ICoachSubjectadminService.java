package com.ruoyi.jiayun.service;

import java.util.List;
import com.ruoyi.jiayun.domain.CoachSubjectadmin;

/**
 * 教练科目关联Service接口
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public interface ICoachSubjectadminService 
{
    /**
     * 查询教练科目关联
     * 
     * @param id 教练科目关联主键
     * @return 教练科目关联
     */
    public CoachSubjectadmin selectCoachSubjectadminById(Long id);

    /**
     * 查询教练科目关联列表
     * 
     * @param coachSubjectadmin 教练科目关联
     * @return 教练科目关联集合
     */
    public List<CoachSubjectadmin> selectCoachSubjectadminList(CoachSubjectadmin coachSubjectadmin);

    /**
     * 新增教练科目关联
     * 
     * @param coachSubjectadmin 教练科目关联
     * @return 结果
     */
    public int insertCoachSubjectadmin(CoachSubjectadmin coachSubjectadmin);

    /**
     * 修改教练科目关联
     * 
     * @param coachSubjectadmin 教练科目关联
     * @return 结果
     */
    public int updateCoachSubjectadmin(CoachSubjectadmin coachSubjectadmin);

    /**
     * 批量删除教练科目关联
     * 
     * @param ids 需要删除的教练科目关联主键集合
     * @return 结果
     */
    public int deleteCoachSubjectadminByIds(Long[] ids);

    /**
     * 删除教练科目关联信息
     * 
     * @param id 教练科目关联主键
     * @return 结果
     */
    public int deleteCoachSubjectadminById(Long id);
}
