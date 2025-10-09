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
import com.ruoyi.jiayun.domain.Vehicleadmin;
import com.ruoyi.jiayun.service.IVehicleadminService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车辆Controller
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/jiayun/vehicleadmin")
public class VehicleadminController extends BaseController
{
    @Autowired
    private IVehicleadminService vehicleadminService;

    /**
     * 查询车辆列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:vehicleadmin:list')")
    @GetMapping("/list")
    public TableDataInfo list(Vehicleadmin vehicleadmin)
    {
        startPage();
        List<Vehicleadmin> list = vehicleadminService.selectVehicleadminList(vehicleadmin);
        return getDataTable(list);
    }

    /**
     * 导出车辆列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:vehicleadmin:export')")
    @Log(title = "车辆", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Vehicleadmin vehicleadmin)
    {
        List<Vehicleadmin> list = vehicleadminService.selectVehicleadminList(vehicleadmin);
        ExcelUtil<Vehicleadmin> util = new ExcelUtil<Vehicleadmin>(Vehicleadmin.class);
        util.exportExcel(response, list, "车辆数据");
    }

    /**
     * 获取车辆详细信息
     */
    @PreAuthorize("@ss.hasPermi('jiayun:vehicleadmin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(vehicleadminService.selectVehicleadminById(id));
    }

    /**
     * 新增车辆
     */
    @PreAuthorize("@ss.hasPermi('jiayun:vehicleadmin:add')")
    @Log(title = "车辆", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Vehicleadmin vehicleadmin)
    {
        return toAjax(vehicleadminService.insertVehicleadmin(vehicleadmin));
    }

    /**
     * 修改车辆
     */
    @PreAuthorize("@ss.hasPermi('jiayun:vehicleadmin:edit')")
    @Log(title = "车辆", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Vehicleadmin vehicleadmin)
    {
        return toAjax(vehicleadminService.updateVehicleadmin(vehicleadmin));
    }

    /**
     * 删除车辆
     */
    @PreAuthorize("@ss.hasPermi('jiayun:vehicleadmin:remove')")
    @Log(title = "车辆", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(vehicleadminService.deleteVehicleadminByIds(ids));
    }
}
