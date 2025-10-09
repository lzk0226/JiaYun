package com.ruoyi.jiayun.mapper;

import java.util.List;
import com.ruoyi.jiayun.domain.StudentCourseadmin;

/**
 * 学员课程关联Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public interface StudentCourseadminMapper 
{
    /**
     * 查询学员课程关联
     * 
     * @param id 学员课程关联主键
     * @return 学员课程关联
     */
    public StudentCourseadmin selectStudentCourseadminById(Long id);

    /**
     * 查询学员课程关联列表
     * 
     * @param studentCourseadmin 学员课程关联
     * @return 学员课程关联集合
     */
    public List<StudentCourseadmin> selectStudentCourseadminList(StudentCourseadmin studentCourseadmin);

    /**
     * 新增学员课程关联
     * 
     * @param studentCourseadmin 学员课程关联
     * @return 结果
     */
    public int insertStudentCourseadmin(StudentCourseadmin studentCourseadmin);

    /**
     * 修改学员课程关联
     * 
     * @param studentCourseadmin 学员课程关联
     * @return 结果
     */
    public int updateStudentCourseadmin(StudentCourseadmin studentCourseadmin);

    /**
     * 删除学员课程关联
     * 
     * @param id 学员课程关联主键
     * @return 结果
     */
    public int deleteStudentCourseadminById(Long id);

    /**
     * 批量删除学员课程关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentCourseadminByIds(Long[] ids);
}
