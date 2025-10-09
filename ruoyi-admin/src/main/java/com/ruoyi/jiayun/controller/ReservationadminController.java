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
import com.ruoyi.jiayun.domain.Reservationadmin;
import com.ruoyi.jiayun.service.IReservationadminService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 预约记录Controller
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/jiayun/reservationadmin")
public class ReservationadminController extends BaseController
{
    @Autowired
    private IReservationadminService reservationadminService;

    /**
     * 查询预约记录列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:reservationadmin:list')")
    @GetMapping("/list")
    public TableDataInfo list(Reservationadmin reservationadmin)
    {
        startPage();
        List<Reservationadmin> list = reservationadminService.selectReservationadminList(reservationadmin);
        return getDataTable(list);
    }

    /**
     * 导出预约记录列表
     */
    @PreAuthorize("@ss.hasPermi('jiayun:reservationadmin:export')")
    @Log(title = "预约记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Reservationadmin reservationadmin)
    {
        List<Reservationadmin> list = reservationadminService.selectReservationadminList(reservationadmin);
        ExcelUtil<Reservationadmin> util = new ExcelUtil<Reservationadmin>(Reservationadmin.class);
        util.exportExcel(response, list, "预约记录数据");
    }

    /**
     * 获取预约记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('jiayun:reservationadmin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(reservationadminService.selectReservationadminById(id));
    }

    /**
     * 新增预约记录
     */
    @PreAuthorize("@ss.hasPermi('jiayun:reservationadmin:add')")
    @Log(title = "预约记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Reservationadmin reservationadmin)
    {
        return toAjax(reservationadminService.insertReservationadmin(reservationadmin));
    }

    /**
     * 修改预约记录
     */
    @PreAuthorize("@ss.hasPermi('jiayun:reservationadmin:edit')")
    @Log(title = "预约记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Reservationadmin reservationadmin)
    {
        return toAjax(reservationadminService.updateReservationadmin(reservationadmin));
    }

    /**
     * 删除预约记录
     */
    @PreAuthorize("@ss.hasPermi('jiayun:reservationadmin:remove')")
    @Log(title = "预约记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(reservationadminService.deleteReservationadminByIds(ids));
    }
}
