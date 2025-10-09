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
import com.ruoyi.jiayun.domain.CoachSubjectadmin;
import com.ruoyi.jiayun.service.ICoachSubjectadminService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 教练科目关联Controller
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/jiayun/coach_subjectadmin")
public class CoachSubjectadminController extends BaseController
{
    @Autowired
    private ICoachSubjectadminService coachSubjectadminService;

    /**
     * 查询教练科目关联列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:coach_subjectadmin:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoachSubjectadmin coachSubjectadmin)
    {
        startPage();
        List<CoachSubjectadmin> list = coachSubjectadminService.selectCoachSubjectadminList(coachSubjectadmin);
        return getDataTable(list);
    }

    /**
     * 导出教练科目关联列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:coach_subjectadmin:export')")
    @Log(title = "教练科目关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CoachSubjectadmin coachSubjectadmin)
    {
        List<CoachSubjectadmin> list = coachSubjectadminService.selectCoachSubjectadminList(coachSubjectadmin);
        ExcelUtil<CoachSubjectadmin> util = new ExcelUtil<CoachSubjectadmin>(CoachSubjectadmin.class);
        util.exportExcel(response, list, "教练科目关联数据");
    }

    /**
     * 获取教练科目关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('jiayun:coach_subjectadmin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(coachSubjectadminService.selectCoachSubjectadminById(id));
    }

    /**
     * 新增教练科目关联
     */
    @PreAuthorize("@ss.hasPermi('jiayun:coach_subjectadmin:add')")
    @Log(title = "教练科目关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CoachSubjectadmin coachSubjectadmin)
    {
        return toAjax(coachSubjectadminService.insertCoachSubjectadmin(coachSubjectadmin));
    }

    /**
     * 修改教练科目关联
     */
    @PreAuthorize("@ss.hasPermi('jiayun:coach_subjectadmin:edit')")
    @Log(title = "教练科目关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoachSubjectadmin coachSubjectadmin)
    {
        return toAjax(coachSubjectadminService.updateCoachSubjectadmin(coachSubjectadmin));
    }

    /**
     * 删除教练科目关联
     */
    @PreAuthorize("@ss.hasPermi('jiayun:coach_subjectadmin:remove')")
    @Log(title = "教练科目关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(coachSubjectadminService.deleteCoachSubjectadminByIds(ids));
    }
}
