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
import com.ruoyi.jiayun.domain.Courseadmin;
import com.ruoyi.jiayun.service.ICourseadminService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 课程Controller
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/jiayun/courseadmin")
public class CourseadminController extends BaseController
{
    @Autowired
    private ICourseadminService courseadminService;

    /**
     * 查询课程列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:courseadmin:list')")
    @GetMapping("/list")
    public TableDataInfo list(Courseadmin courseadmin)
    {
        startPage();
        List<Courseadmin> list = courseadminService.selectCourseadminList(courseadmin);
        return getDataTable(list);
    }

    /**
     * 导出课程列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:courseadmin:export')")
    @Log(title = "课程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Courseadmin courseadmin)
    {
        List<Courseadmin> list = courseadminService.selectCourseadminList(courseadmin);
        ExcelUtil<Courseadmin> util = new ExcelUtil<Courseadmin>(Courseadmin.class);
        util.exportExcel(response, list, "课程数据");
    }

    /**
     * 获取课程详细信息
     */
    @PreAuthorize("@ss.hasPermi('jiayun:courseadmin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(courseadminService.selectCourseadminById(id));
    }

    /**
     * 新增课程
     */
    @PreAuthorize("@ss.hasPermi('jiayun:courseadmin:add')")
    @Log(title = "课程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Courseadmin courseadmin)
    {
        return toAjax(courseadminService.insertCourseadmin(courseadmin));
    }

    /**
     * 修改课程
     */
    @PreAuthorize("@ss.hasPermi('jiayun:courseadmin:edit')")
    @Log(title = "课程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Courseadmin courseadmin)
    {
        return toAjax(courseadminService.updateCourseadmin(courseadmin));
    }

    /**
     * 删除课程
     */
    @PreAuthorize("@ss.hasPermi('jiayun:courseadmin:remove')")
    @Log(title = "课程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(courseadminService.deleteCourseadminByIds(ids));
    }
}
