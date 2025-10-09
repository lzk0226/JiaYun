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
import com.ruoyi.jiayun.domain.Examadmin;
import com.ruoyi.jiayun.service.IExamadminService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考试记录Controller
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/jiayun/examadmin")
public class ExamadminController extends BaseController
{
    @Autowired
    private IExamadminService examadminService;

    /**
     * 查询考试记录列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:examadmin:list')")
    @GetMapping("/list")
    public TableDataInfo list(Examadmin examadmin)
    {
        startPage();
        List<Examadmin> list = examadminService.selectExamadminList(examadmin);
        return getDataTable(list);
    }

    /**
     * 导出考试记录列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:examadmin:export')")
    @Log(title = "考试记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Examadmin examadmin)
    {
        List<Examadmin> list = examadminService.selectExamadminList(examadmin);
        ExcelUtil<Examadmin> util = new ExcelUtil<Examadmin>(Examadmin.class);
        util.exportExcel(response, list, "考试记录数据");
    }

    /**
     * 获取考试记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('jiayun:examadmin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(examadminService.selectExamadminById(id));
    }

    /**
     * 新增考试记录
     */
    @PreAuthorize("@ss.hasPermi('jiayun:examadmin:add')")
    @Log(title = "考试记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Examadmin examadmin)
    {
        return toAjax(examadminService.insertExamadmin(examadmin));
    }

    /**
     * 修改考试记录
     */
    @PreAuthorize("@ss.hasPermi('jiayun:examadmin:edit')")
    @Log(title = "考试记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Examadmin examadmin)
    {
        return toAjax(examadminService.updateExamadmin(examadmin));
    }

    /**
     * 删除考试记录
     */
    @PreAuthorize("@ss.hasPermi('jiayun:examadmin:remove')")
    @Log(title = "考试记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(examadminService.deleteExamadminByIds(ids));
    }
}
