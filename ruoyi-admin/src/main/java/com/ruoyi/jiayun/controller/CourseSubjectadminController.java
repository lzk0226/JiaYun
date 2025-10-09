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
import com.ruoyi.jiayun.domain.CourseSubjectadmin;
import com.ruoyi.jiayun.service.ICourseSubjectadminService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 课程科目关联Controller
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/jiayun/course_subjectadmin")
public class CourseSubjectadminController extends BaseController
{
    @Autowired
    private ICourseSubjectadminService courseSubjectadminService;

    /**
     * 查询课程科目关联列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:course_subjectadmin:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseSubjectadmin courseSubjectadmin)
    {
        startPage();
        List<CourseSubjectadmin> list = courseSubjectadminService.selectCourseSubjectadminList(courseSubjectadmin);
        return getDataTable(list);
    }

    /**
     * 导出课程科目关联列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:course_subjectadmin:export')")
    @Log(title = "课程科目关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourseSubjectadmin courseSubjectadmin)
    {
        List<CourseSubjectadmin> list = courseSubjectadminService.selectCourseSubjectadminList(courseSubjectadmin);
        ExcelUtil<CourseSubjectadmin> util = new ExcelUtil<CourseSubjectadmin>(CourseSubjectadmin.class);
        util.exportExcel(response, list, "课程科目关联数据");
    }

    /**
     * 获取课程科目关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('jiayun:course_subjectadmin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(courseSubjectadminService.selectCourseSubjectadminById(id));
    }

    /**
     * 新增课程科目关联
     */
    @PreAuthorize("@ss.hasPermi('jiayun:course_subjectadmin:add')")
    @Log(title = "课程科目关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseSubjectadmin courseSubjectadmin)
    {
        return toAjax(courseSubjectadminService.insertCourseSubjectadmin(courseSubjectadmin));
    }

    /**
     * 修改课程科目关联
     */
    @PreAuthorize("@ss.hasPermi('jiayun:course_subjectadmin:edit')")
    @Log(title = "课程科目关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseSubjectadmin courseSubjectadmin)
    {
        return toAjax(courseSubjectadminService.updateCourseSubjectadmin(courseSubjectadmin));
    }

    /**
     * 删除课程科目关联
     */
    @PreAuthorize("@ss.hasPermi('jiayun:course_subjectadmin:remove')")
    @Log(title = "课程科目关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(courseSubjectadminService.deleteCourseSubjectadminByIds(ids));
    }
}
