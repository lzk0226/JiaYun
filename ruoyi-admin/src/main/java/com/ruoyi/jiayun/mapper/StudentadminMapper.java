package com.ruoyi.jiayun.mapper;

import java.util.List;
import com.ruoyi.jiayun.domain.Studentadmin;

/**
 * 学员Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public interface StudentadminMapper 
{
    /**
     * 查询学员
     * 
     * @param id 学员主键
     * @return 学员
     */
    public Studentadmin selectStudentadminById(Long id);

    /**
     * 查询学员列表
     * 
     * @param studentadmin 学员
     * @return 学员集合
     */
    public List<Studentadmin> selectStudentadminList(Studentadmin studentadmin);

    /**
     * 新增学员
     * 
     * @param studentadmin 学员
     * @return 结果
     */
    public int insertStudentadmin(Studentadmin studentadmin);

    /**
     * 修改学员
     * 
     * @param studentadmin 学员
     * @return 结果
     */
    public int updateStudentadmin(Studentadmin studentadmin);

    /**
     * 删除学员
     * 
     * @param id 学员主键
     * @return 结果
     */
    public int deleteStudentadminById(Long id);

    /**
     * 批量删除学员
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentadminByIds(Long[] ids);
}
