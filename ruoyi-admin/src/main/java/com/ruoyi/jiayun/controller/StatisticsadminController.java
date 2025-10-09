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
import com.ruoyi.jiayun.domain.Statisticsadmin;
import com.ruoyi.jiayun.service.IStatisticsadminService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 统计数据Controller
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/jiayun/statisticsadmin")
public class StatisticsadminController extends BaseController
{
    @Autowired
    private IStatisticsadminService statisticsadminService;

    /**
     * 查询统计数据列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:statisticsadmin:list')")
    @GetMapping("/list")
    public TableDataInfo list(Statisticsadmin statisticsadmin)
    {
        startPage();
        List<Statisticsadmin> list = statisticsadminService.selectStatisticsadminList(statisticsadmin);
        return getDataTable(list);
    }

    /**
     * 导出统计数据列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:statisticsadmin:export')")
    @Log(title = "统计数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Statisticsadmin statisticsadmin)
    {
        List<Statisticsadmin> list = statisticsadminService.selectStatisticsadminList(statisticsadmin);
        ExcelUtil<Statisticsadmin> util = new ExcelUtil<Statisticsadmin>(Statisticsadmin.class);
        util.exportExcel(response, list, "统计数据数据");
    }

    /**
     * 获取统计数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('jiayun:statisticsadmin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(statisticsadminService.selectStatisticsadminById(id));
    }

    /**
     * 新增统计数据
     */
    @PreAuthorize("@ss.hasPermi('jiayun:statisticsadmin:add')")
    @Log(title = "统计数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Statisticsadmin statisticsadmin)
    {
        return toAjax(statisticsadminService.insertStatisticsadmin(statisticsadmin));
    }

    /**
     * 修改统计数据
     */
    @PreAuthorize("@ss.hasPermi('jiayun:statisticsadmin:edit')")
    @Log(title = "统计数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Statisticsadmin statisticsadmin)
    {
        return toAjax(statisticsadminService.updateStatisticsadmin(statisticsadmin));
    }

    /**
     * 删除统计数据
     */
    @PreAuthorize("@ss.hasPermi('jiayun:statisticsadmin:remove')")
    @Log(title = "统计数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(statisticsadminService.deleteStatisticsadminByIds(ids));
    }
}
