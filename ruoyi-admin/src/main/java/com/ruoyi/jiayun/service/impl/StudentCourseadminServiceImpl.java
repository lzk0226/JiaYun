package com.ruoyi.jiayun.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jiayun.mapper.StudentCourseadminMapper;
import com.ruoyi.jiayun.domain.StudentCourseadmin;
import com.ruoyi.jiayun.service.IStudentCourseadminService;

/**
 * 学员课程关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@Service
public class StudentCourseadminServiceImpl implements IStudentCourseadminService 
{
    @Autowired
    private StudentCourseadminMapper studentCourseadminMapper;

    /**
     * 查询学员课程关联
     * 
     * @param id 学员课程关联主键
     * @return 学员课程关联
     */
    @Override
    public StudentCourseadmin selectStudentCourseadminById(Long id)
    {
        return studentCourseadminMapper.selectStudentCourseadminById(id);
    }

    /**
     * 查询学员课程关联列表
     * 
     * @param studentCourseadmin 学员课程关联
     * @return 学员课程关联
     */
    @Override
    public List<StudentCourseadmin> selectStudentCourseadminList(StudentCourseadmin studentCourseadmin)
    {
        return studentCourseadminMapper.selectStudentCourseadminList(studentCourseadmin);
    }

    /**
     * 新增学员课程关联
     * 
     * @param studentCourseadmin 学员课程关联
     * @return 结果
     */
    @Override
    public int insertStudentCourseadmin(StudentCourseadmin studentCourseadmin)
    {
        return studentCourseadminMapper.insertStudentCourseadmin(studentCourseadmin);
    }

    /**
     * 修改学员课程关联
     * 
     * @param studentCourseadmin 学员课程关联
     * @return 结果
     */
    @Override
    public int updateStudentCourseadmin(StudentCourseadmin studentCourseadmin)
    {
        return studentCourseadminMapper.updateStudentCourseadmin(studentCourseadmin);
    }

    /**
     * 批量删除学员课程关联
     * 
     * @param ids 需要删除的学员课程关联主键
     * @return 结果
     */
    @Override
    public int deleteStudentCourseadminByIds(Long[] ids)
    {
        return studentCourseadminMapper.deleteStudentCourseadminByIds(ids);
    }

    /**
     * 删除学员课程关联信息
     * 
     * @param id 学员课程关联主键
     * @return 结果
     */
    @Override
    public int deleteStudentCourseadminById(Long id)
    {
        return studentCourseadminMapper.deleteStudentCourseadminById(id);
    }
}
