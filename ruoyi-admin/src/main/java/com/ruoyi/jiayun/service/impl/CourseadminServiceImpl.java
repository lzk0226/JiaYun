package com.ruoyi.jiayun.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jiayun.mapper.CourseadminMapper;
import com.ruoyi.jiayun.domain.Courseadmin;
import com.ruoyi.jiayun.service.ICourseadminService;

/**
 * 课程Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@Service
public class CourseadminServiceImpl implements ICourseadminService 
{
    @Autowired
    private CourseadminMapper courseadminMapper;

    /**
     * 查询课程
     * 
     * @param id 课程主键
     * @return 课程
     */
    @Override
    public Courseadmin selectCourseadminById(Long id)
    {
        return courseadminMapper.selectCourseadminById(id);
    }

    /**
     * 查询课程列表
     * 
     * @param courseadmin 课程
     * @return 课程
     */
    @Override
    public List<Courseadmin> selectCourseadminList(Courseadmin courseadmin)
    {
        return courseadminMapper.selectCourseadminList(courseadmin);
    }

    /**
     * 新增课程
     * 
     * @param courseadmin 课程
     * @return 结果
     */
    @Override
    public int insertCourseadmin(Courseadmin courseadmin)
    {
        return courseadminMapper.insertCourseadmin(courseadmin);
    }

    /**
     * 修改课程
     * 
     * @param courseadmin 课程
     * @return 结果
     */
    @Override
    public int updateCourseadmin(Courseadmin courseadmin)
    {
        return courseadminMapper.updateCourseadmin(courseadmin);
    }

    /**
     * 批量删除课程
     * 
     * @param ids 需要删除的课程主键
     * @return 结果
     */
    @Override
    public int deleteCourseadminByIds(Long[] ids)
    {
        return courseadminMapper.deleteCourseadminByIds(ids);
    }

    /**
     * 删除课程信息
     * 
     * @param id 课程主键
     * @return 结果
     */
    @Override
    public int deleteCourseadminById(Long id)
    {
        return courseadminMapper.deleteCourseadminById(id);
    }
}
