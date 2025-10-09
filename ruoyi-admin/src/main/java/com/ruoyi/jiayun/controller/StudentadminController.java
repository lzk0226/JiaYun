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
import com.ruoyi.jiayun.domain.Studentadmin;
import com.ruoyi.jiayun.service.IStudentadminService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学员Controller
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/jiayun/studentadmin")
public class StudentadminController extends BaseController
{
    @Autowired
    private IStudentadminService studentadminService;

    /**
     * 查询学员列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:studentadmin:list')")
    @GetMapping("/list")
    public TableDataInfo list(Studentadmin studentadmin)
    {
        startPage();
        List<Studentadmin> list = studentadminService.selectStudentadminList(studentadmin);
        return getDataTable(list);
    }

    /**
     * 导出学员列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:studentadmin:export')")
    @Log(title = "学员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Studentadmin studentadmin)
    {
        List<Studentadmin> list = studentadminService.selectStudentadminList(studentadmin);
        ExcelUtil<Studentadmin> util = new ExcelUtil<Studentadmin>(Studentadmin.class);
        util.exportExcel(response, list, "学员数据");
    }

    /**
     * 获取学员详细信息
     */
    @PreAuthorize("@ss.hasPermi('jiayun:studentadmin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(studentadminService.selectStudentadminById(id));
    }

    /**
     * 新增学员
     */
    @PreAuthorize("@ss.hasPermi('jiayun:studentadmin:add')")
    @Log(title = "学员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Studentadmin studentadmin)
    {
        return toAjax(studentadminService.insertStudentadmin(studentadmin));
    }

    /**
     * 修改学员
     */
    @PreAuthorize("@ss.hasPermi('jiayun:studentadmin:edit')")
    @Log(title = "学员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Studentadmin studentadmin)
    {
        return toAjax(studentadminService.updateStudentadmin(studentadmin));
    }

    /**
     * 删除学员
     */
    @PreAuthorize("@ss.hasPermi('jiayun:studentadmin:remove')")
    @Log(title = "学员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(studentadminService.deleteStudentadminByIds(ids));
    }
}
