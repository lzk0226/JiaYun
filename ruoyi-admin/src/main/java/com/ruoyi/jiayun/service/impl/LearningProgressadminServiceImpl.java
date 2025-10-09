package com.ruoyi.jiayun.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jiayun.mapper.LearningProgressadminMapper;
import com.ruoyi.jiayun.domain.LearningProgressadmin;
import com.ruoyi.jiayun.service.ILearningProgressadminService;

/**
 * 学习进度Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@Service
public class LearningProgressadminServiceImpl implements ILearningProgressadminService 
{
    @Autowired
    private LearningProgressadminMapper learningProgressadminMapper;

    /**
     * 查询学习进度
     * 
     * @param id 学习进度主键
     * @return 学习进度
     */
    @Override
    public LearningProgressadmin selectLearningProgressadminById(Long id)
    {
        return learningProgressadminMapper.selectLearningProgressadminById(id);
    }

    /**
     * 查询学习进度列表
     * 
     * @param learningProgressadmin 学习进度
     * @return 学习进度
     */
    @Override
    public List<LearningProgressadmin> selectLearningProgressadminList(LearningProgressadmin learningProgressadmin)
    {
        return learningProgressadminMapper.selectLearningProgressadminList(learningProgressadmin);
    }

    /**
     * 新增学习进度
     * 
     * @param learningProgressadmin 学习进度
     * @return 结果
     */
    @Override
    public int insertLearningProgressadmin(LearningProgressadmin learningProgressadmin)
    {
        return learningProgressadminMapper.insertLearningProgressadmin(learningProgressadmin);
    }

    /**
     * 修改学习进度
     * 
     * @param learningProgressadmin 学习进度
     * @return 结果
     */
    @Override
    public int updateLearningProgressadmin(LearningProgressadmin learningProgressadmin)
    {
        return learningProgressadminMapper.updateLearningProgressadmin(learningProgressadmin);
    }

    /**
     * 批量删除学习进度
     * 
     * @param ids 需要删除的学习进度主键
     * @return 结果
     */
    @Override
    public int deleteLearningProgressadminByIds(Long[] ids)
    {
        return learningProgressadminMapper.deleteLearningProgressadminByIds(ids);
    }

    /**
     * 删除学习进度信息
     * 
     * @param id 学习进度主键
     * @return 结果
     */
    @Override
    public int deleteLearningProgressadminById(Long id)
    {
        return learningProgressadminMapper.deleteLearningProgressadminById(id);
    }
}
