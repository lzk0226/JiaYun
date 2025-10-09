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
import com.ruoyi.jiayun.domain.Subjectadmin;
import com.ruoyi.jiayun.service.ISubjectadminService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 科目Controller
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/jiayun/subjectadmin")
public class SubjectadminController extends BaseController
{
    @Autowired
    private ISubjectadminService subjectadminService;

    /**
     * 查询科目列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:subjectadmin:list')")
    @GetMapping("/list")
    public TableDataInfo list(Subjectadmin subjectadmin)
    {
        startPage();
        List<Subjectadmin> list = subjectadminService.selectSubjectadminList(subjectadmin);
        return getDataTable(list);
    }

    /**
     * 导出科目列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:subjectadmin:export')")
    @Log(title = "科目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Subjectadmin subjectadmin)
    {
        List<Subjectadmin> list = subjectadminService.selectSubjectadminList(subjectadmin);
        ExcelUtil<Subjectadmin> util = new ExcelUtil<Subjectadmin>(Subjectadmin.class);
        util.exportExcel(response, list, "科目数据");
    }

    /**
     * 获取科目详细信息
     */
    @PreAuthorize("@ss.hasPermi('jiayun:subjectadmin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(subjectadminService.selectSubjectadminById(id));
    }

    /**
     * 新增科目
     */
    @PreAuthorize("@ss.hasPermi('jiayun:subjectadmin:add')")
    @Log(title = "科目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Subjectadmin subjectadmin)
    {
        return toAjax(subjectadminService.insertSubjectadmin(subjectadmin));
    }

    /**
     * 修改科目
     */
    @PreAuthorize("@ss.hasPermi('jiayun:subjectadmin:edit')")
    @Log(title = "科目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Subjectadmin subjectadmin)
    {
        return toAjax(subjectadminService.updateSubjectadmin(subjectadmin));
    }

    /**
     * 删除科目
     */
    @PreAuthorize("@ss.hasPermi('jiayun:subjectadmin:remove')")
    @Log(title = "科目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(subjectadminService.deleteSubjectadminByIds(ids));
    }
}
