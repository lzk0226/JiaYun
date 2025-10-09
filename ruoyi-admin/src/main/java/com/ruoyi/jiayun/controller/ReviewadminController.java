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
import com.ruoyi.jiayun.domain.Reviewadmin;
import com.ruoyi.jiayun.service.IReviewadminService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评价Controller
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/jiayun/reviewadmin")
public class ReviewadminController extends BaseController
{
    @Autowired
    private IReviewadminService reviewadminService;

    /**
     * 查询评价列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:reviewadmin:list')")
    @GetMapping("/list")
    public TableDataInfo list(Reviewadmin reviewadmin)
    {
        startPage();
        List<Reviewadmin> list = reviewadminService.selectReviewadminList(reviewadmin);
        return getDataTable(list);
    }

    /**
     * 导出评价列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:reviewadmin:export')")
    @Log(title = "评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Reviewadmin reviewadmin)
    {
        List<Reviewadmin> list = reviewadminService.selectReviewadminList(reviewadmin);
        ExcelUtil<Reviewadmin> util = new ExcelUtil<Reviewadmin>(Reviewadmin.class);
        util.exportExcel(response, list, "评价数据");
    }

    /**
     * 获取评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('jiayun:reviewadmin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(reviewadminService.selectReviewadminById(id));
    }

    /**
     * 新增评价
     */
    @PreAuthorize("@ss.hasPermi('jiayun:reviewadmin:add')")
    @Log(title = "评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Reviewadmin reviewadmin)
    {
        return toAjax(reviewadminService.insertReviewadmin(reviewadmin));
    }

    /**
     * 修改评价
     */
    @PreAuthorize("@ss.hasPermi('jiayun:reviewadmin:edit')")
    @Log(title = "评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Reviewadmin reviewadmin)
    {
        return toAjax(reviewadminService.updateReviewadmin(reviewadmin));
    }

    /**
     * 删除评价
     */
    @PreAuthorize("@ss.hasPermi('jiayun:reviewadmin:remove')")
    @Log(title = "评价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(reviewadminService.deleteReviewadminByIds(ids));
    }
}
