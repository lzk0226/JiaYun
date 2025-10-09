package com.ruoyi.jiayun.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jiayun.mapper.CourseSubjectadminMapper;
import com.ruoyi.jiayun.domain.CourseSubjectadmin;
import com.ruoyi.jiayun.service.ICourseSubjectadminService;

/**
 * 课程科目关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@Service
public class CourseSubjectadminServiceImpl implements ICourseSubjectadminService 
{
    @Autowired
    private CourseSubjectadminMapper courseSubjectadminMapper;

    /**
     * 查询课程科目关联
     * 
     * @param id 课程科目关联主键
     * @return 课程科目关联
     */
    @Override
    public CourseSubjectadmin selectCourseSubjectadminById(Long id)
    {
        return courseSubjectadminMapper.selectCourseSubjectadminById(id);
    }

    /**
     * 查询课程科目关联列表
     * 
     * @param courseSubjectadmin 课程科目关联
     * @return 课程科目关联
     */
    @Override
    public List<CourseSubjectadmin> selectCourseSubjectadminList(CourseSubjectadmin courseSubjectadmin)
    {
        return courseSubjectadminMapper.selectCourseSubjectadminList(courseSubjectadmin);
    }

    /**
     * 新增课程科目关联
     * 
     * @param courseSubjectadmin 课程科目关联
     * @return 结果
     */
    @Override
    public int insertCourseSubjectadmin(CourseSubjectadmin courseSubjectadmin)
    {
        return courseSubjectadminMapper.insertCourseSubjectadmin(courseSubjectadmin);
    }

    /**
     * 修改课程科目关联
     * 
     * @param courseSubjectadmin 课程科目关联
     * @return 结果
     */
    @Override
    public int updateCourseSubjectadmin(CourseSubjectadmin courseSubjectadmin)
    {
        return courseSubjectadminMapper.updateCourseSubjectadmin(courseSubjectadmin);
    }

    /**
     * 批量删除课程科目关联
     * 
     * @param ids 需要删除的课程科目关联主键
     * @return 结果
     */
    @Override
    public int deleteCourseSubjectadminByIds(Long[] ids)
    {
        return courseSubjectadminMapper.deleteCourseSubjectadminByIds(ids);
    }

    /**
     * 删除课程科目关联信息
     * 
     * @param id 课程科目关联主键
     * @return 结果
     */
    @Override
    public int deleteCourseSubjectadminById(Long id)
    {
        return courseSubjectadminMapper.deleteCourseSubjectadminById(id);
    }
}
