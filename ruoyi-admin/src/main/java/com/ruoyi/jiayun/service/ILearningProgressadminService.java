package com.ruoyi.jiayun.service;

import java.util.List;
import com.ruoyi.jiayun.domain.LearningProgressadmin;

/**
 * 学习进度Service接口
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public interface ILearningProgressadminService 
{
    /**
     * 查询学习进度
     * 
     * @param id 学习进度主键
     * @return 学习进度
     */
    public LearningProgressadmin selectLearningProgressadminById(Long id);

    /**
     * 查询学习进度列表
     * 
     * @param learningProgressadmin 学习进度
     * @return 学习进度集合
     */
    public List<LearningProgressadmin> selectLearningProgressadminList(LearningProgressadmin learningProgressadmin);

    /**
     * 新增学习进度
     * 
     * @param learningProgressadmin 学习进度
     * @return 结果
     */
    public int insertLearningProgressadmin(LearningProgressadmin learningProgressadmin);

    /**
     * 修改学习进度
     * 
     * @param learningProgressadmin 学习进度
     * @return 结果
     */
    public int updateLearningProgressadmin(LearningProgressadmin learningProgressadmin);

    /**
     * 批量删除学习进度
     * 
     * @param ids 需要删除的学习进度主键集合
     * @return 结果
     */
    public int deleteLearningProgressadminByIds(Long[] ids);

    /**
     * 删除学习进度信息
     * 
     * @param id 学习进度主键
     * @return 结果
     */
    public int deleteLearningProgressadminById(Long id);
}
