package com.ruoyi.jiayun.mapper;

import java.util.List;
import com.ruoyi.jiayun.domain.CourseSubjectadmin;

/**
 * 课程科目关联Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public interface CourseSubjectadminMapper 
{
    /**
     * 查询课程科目关联
     * 
     * @param id 课程科目关联主键
     * @return 课程科目关联
     */
    public CourseSubjectadmin selectCourseSubjectadminById(Long id);

    /**
     * 查询课程科目关联列表
     * 
     * @param courseSubjectadmin 课程科目关联
     * @return 课程科目关联集合
     */
    public List<CourseSubjectadmin> selectCourseSubjectadminList(CourseSubjectadmin courseSubjectadmin);

    /**
     * 新增课程科目关联
     * 
     * @param courseSubjectadmin 课程科目关联
     * @return 结果
     */
    public int insertCourseSubjectadmin(CourseSubjectadmin courseSubjectadmin);

    /**
     * 修改课程科目关联
     * 
     * @param courseSubjectadmin 课程科目关联
     * @return 结果
     */
    public int updateCourseSubjectadmin(CourseSubjectadmin courseSubjectadmin);

    /**
     * 删除课程科目关联
     * 
     * @param id 课程科目关联主键
     * @return 结果
     */
    public int deleteCourseSubjectadminById(Long id);

    /**
     * 批量删除课程科目关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseSubjectadminByIds(Long[] ids);
}
