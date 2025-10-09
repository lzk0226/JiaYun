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
import com.ruoyi.jiayun.domain.LearningProgressadmin;
import com.ruoyi.jiayun.service.ILearningProgressadminService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学习进度Controller
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/jiayun/learning_progressadmin")
public class LearningProgressadminController extends BaseController
{
    @Autowired
    private ILearningProgressadminService learningProgressadminService;

    /**
     * 查询学习进度列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:learning_progressadmin:list')")
    @GetMapping("/list")
    public TableDataInfo list(LearningProgressadmin learningProgressadmin)
    {
        startPage();
        List<LearningProgressadmin> list = learningProgressadminService.selectLearningProgressadminList(learningProgressadmin);
        return getDataTable(list);
    }

    /**
     * 导出学习进度列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:learning_progressadmin:export')")
    @Log(title = "学习进度", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LearningProgressadmin learningProgressadmin)
    {
        List<LearningProgressadmin> list = learningProgressadminService.selectLearningProgressadminList(learningProgressadmin);
        ExcelUtil<LearningProgressadmin> util = new ExcelUtil<LearningProgressadmin>(LearningProgressadmin.class);
        util.exportExcel(response, list, "学习进度数据");
    }

    /**
     * 获取学习进度详细信息
     */
    @PreAuthorize("@ss.hasPermi('jiayun:learning_progressadmin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(learningProgressadminService.selectLearningProgressadminById(id));
    }

    /**
     * 新增学习进度
     */
    @PreAuthorize("@ss.hasPermi('jiayun:learning_progressadmin:add')")
    @Log(title = "学习进度", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LearningProgressadmin learningProgressadmin)
    {
        return toAjax(learningProgressadminService.insertLearningProgressadmin(learningProgressadmin));
    }

    /**
     * 修改学习进度
     */
    @PreAuthorize("@ss.hasPermi('jiayun:learning_progressadmin:edit')")
    @Log(title = "学习进度", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LearningProgressadmin learningProgressadmin)
    {
        return toAjax(learningProgressadminService.updateLearningProgressadmin(learningProgressadmin));
    }

    /**
     * 删除学习进度
     */
    @PreAuthorize("@ss.hasPermi('jiayun:learning_progressadmin:remove')")
    @Log(title = "学习进度", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(learningProgressadminService.deleteLearningProgressadminByIds(ids));
    }
}
