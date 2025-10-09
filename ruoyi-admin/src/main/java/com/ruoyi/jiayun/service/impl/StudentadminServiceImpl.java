package com.ruoyi.jiayun.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jiayun.mapper.StudentadminMapper;
import com.ruoyi.jiayun.domain.Studentadmin;
import com.ruoyi.jiayun.service.IStudentadminService;

/**
 * 学员Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@Service
public class StudentadminServiceImpl implements IStudentadminService 
{
    @Autowired
    private StudentadminMapper studentadminMapper;

    /**
     * 查询学员
     * 
     * @param id 学员主键
     * @return 学员
     */
    @Override
    public Studentadmin selectStudentadminById(Long id)
    {
        return studentadminMapper.selectStudentadminById(id);
    }

    /**
     * 查询学员列表
     * 
     * @param studentadmin 学员
     * @return 学员
     */
    @Override
    public List<Studentadmin> selectStudentadminList(Studentadmin studentadmin)
    {
        return studentadminMapper.selectStudentadminList(studentadmin);
    }

    /**
     * 新增学员
     * 
     * @param studentadmin 学员
     * @return 结果
     */
    @Override
    public int insertStudentadmin(Studentadmin studentadmin)
    {
        return studentadminMapper.insertStudentadmin(studentadmin);
    }

    /**
     * 修改学员
     * 
     * @param studentadmin 学员
     * @return 结果
     */
    @Override
    public int updateStudentadmin(Studentadmin studentadmin)
    {
        return studentadminMapper.updateStudentadmin(studentadmin);
    }

    /**
     * 批量删除学员
     * 
     * @param ids 需要删除的学员主键
     * @return 结果
     */
    @Override
    public int deleteStudentadminByIds(Long[] ids)
    {
        return studentadminMapper.deleteStudentadminByIds(ids);
    }

    /**
     * 删除学员信息
     * 
     * @param id 学员主键
     * @return 结果
     */
    @Override
    public int deleteStudentadminById(Long id)
    {
        return studentadminMapper.deleteStudentadminById(id);
    }
}
