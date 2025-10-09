package com.ruoyi.jiayun.service;

import java.util.List;
import com.ruoyi.jiayun.domain.Courseadmin;

/**
 * 课程Service接口
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public interface ICourseadminService 
{
    /**
     * 查询课程
     * 
     * @param id 课程主键
     * @return 课程
     */
    public Courseadmin selectCourseadminById(Long id);

    /**
     * 查询课程列表
     * 
     * @param courseadmin 课程
     * @return 课程集合
     */
    public List<Courseadmin> selectCourseadminList(Courseadmin courseadmin);

    /**
     * 新增课程
     * 
     * @param courseadmin 课程
     * @return 结果
     */
    public int insertCourseadmin(Courseadmin courseadmin);

    /**
     * 修改课程
     * 
     * @param courseadmin 课程
     * @return 结果
     */
    public int updateCourseadmin(Courseadmin courseadmin);

    /**
     * 批量删除课程
     * 
     * @param ids 需要删除的课程主键集合
     * @return 结果
     */
    public int deleteCourseadminByIds(Long[] ids);

    /**
     * 删除课程信息
     * 
     * @param id 课程主键
     * @return 结果
     */
    public int deleteCourseadminById(Long id);
}
