package com.ruoyi.jiayun.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.jiayun.domain.StudentCourseadmin;
import com.ruoyi.jiayun.service.IStudentCourseadminService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学员课程关联Controller
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/jiayun/student_courseadmin")
public class StudentCourseadminController extends BaseController
{
    @Autowired
    private IStudentCourseadminService studentCourseadminService;

    /**
     * 查询学员课程关联列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:student_courseadmin:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentCourseadmin studentCourseadmin)
    {
        startPage();
        List<StudentCourseadmin> list = studentCourseadminService.selectStudentCourseadminList(studentCourseadmin);
        return getDataTable(list);
    }

    /**
     * 导出学员课程关联列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:student_courseadmin:export')")
    @Log(title = "学员课程关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentCourseadmin studentCourseadmin)
    {
        List<StudentCourseadmin> list = studentCourseadminService.selectStudentCourseadminList(studentCourseadmin);
        ExcelUtil<StudentCourseadmin> util = new ExcelUtil<StudentCourseadmin>(StudentCourseadmin.class);
        util.exportExcel(response, list, "学员课程关联数据");
    }

    /**
     * 获取学员课程关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('jiayun:student_courseadmin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(studentCourseadminService.selectStudentCourseadminById(id));
    }

    /**
     * 新增学员课程关联
     */
    @PreAuthorize("@ss.hasPermi('jiayun:student_courseadmin:add')")
    @Log(title = "学员课程关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudentCourseadmin studentCourseadmin)
    {
        return toAjax(studentCourseadminService.insertStudentCourseadmin(studentCourseadmin));
    }

    /**
     * 修改学员课程关联
     */
    @PreAuthorize("@ss.hasPermi('jiayun:student_courseadmin:edit')")
    @Log(title = "学员课程关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudentCourseadmin studentCourseadmin)
    {
        return toAjax(studentCourseadminService.updateStudentCourseadmin(studentCourseadmin));
    }

    /**
     * 删除学员课程关联
     */
    @PreAuthorize("@ss.hasPermi('jiayun:student_courseadmin:remove')")
    @Log(title = "学员课程关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(studentCourseadminService.deleteStudentCourseadminByIds(ids));
    }
}
