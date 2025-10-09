package com.ruoyi.jiayun.mapper;

import java.util.List;
import com.ruoyi.jiayun.domain.Examadmin;

/**
 * 考试记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public interface ExamadminMapper 
{
    /**
     * 查询考试记录
     * 
     * @param id 考试记录主键
     * @return 考试记录
     */
    public Examadmin selectExamadminById(Long id);

    /**
     * 查询考试记录列表
     * 
     * @param examadmin 考试记录
     * @return 考试记录集合
     */
    public List<Examadmin> selectExamadminList(Examadmin examadmin);

    /**
     * 新增考试记录
     * 
     * @param examadmin 考试记录
     * @return 结果
     */
    public int insertExamadmin(Examadmin examadmin);

    /**
     * 修改考试记录
     * 
     * @param examadmin 考试记录
     * @return 结果
     */
    public int updateExamadmin(Examadmin examadmin);

    /**
     * 删除考试记录
     * 
     * @param id 考试记录主键
     * @return 结果
     */
    public int deleteExamadminById(Long id);

    /**
     * 批量删除考试记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExamadminByIds(Long[] ids);
}
