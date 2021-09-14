package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.VO.FinancialStatistics;
import com.ruoyi.YDOnlineTaxi.service.IFinancialStatisticsService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 财务统计Controller
 * 
 * @author ruoyi
 * @date 2021-08-30
 */
@RestController
@RequestMapping("/YDOnlineTaxi/FinancialStatistics")
public class FinancialStatisticsController extends BaseController
{
    @Autowired
    private IFinancialStatisticsService financialStatisticsService;

    /**
     * 查询财务统计列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:FinancialStatistics:list')")
    @GetMapping("/list")
    public AjaxResult list(FinancialStatistics financialStatistics)
    {
        List<FinancialStatistics> list = financialStatisticsService.selectFinancialStatisticsList(financialStatistics);
        return AjaxResult.success(list);
    }

    /**
     * 导出财务统计列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:FinancialStatistics:export')")
    @Log(title = "财务统计", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FinancialStatistics financialStatistics)
    {
        List<FinancialStatistics> list = financialStatisticsService.selectFinancialStatisticsList(financialStatistics);
        ExcelUtil<FinancialStatistics> util = new ExcelUtil<FinancialStatistics>(FinancialStatistics.class);
        return util.exportExcel(list, "财务统计数据");
    }

    /**
     * 获取财务统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:FinancialStatistics:query')")
    @GetMapping(value = "/{driverPhoneNumber}")
    public AjaxResult getInfo(@PathVariable("driverPhoneNumber") String driverPhoneNumber)
    {
        return AjaxResult.success(financialStatisticsService.selectFinancialStatisticsByDriverPhoneNumber(driverPhoneNumber));
    }

    /**
     * 新增财务统计
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:FinancialStatistics:add')")
    @Log(title = "财务统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinancialStatistics financialStatistics)
    {
        return toAjax(financialStatisticsService.insertFinancialStatistics(financialStatistics));
    }

    /**
     * 修改财务统计
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:FinancialStatistics:edit')")
    @Log(title = "财务统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinancialStatistics financialStatistics)
    {
        return toAjax(financialStatisticsService.updateFinancialStatistics(financialStatistics));
    }

    /**
     * 删除财务统计
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:FinancialStatistics:remove')")
    @Log(title = "财务统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{driverPhoneNumbers}")
    public AjaxResult remove(@PathVariable String[] driverPhoneNumbers)
    {
        return toAjax(financialStatisticsService.deleteFinancialStatisticsByDriverPhoneNumbers(driverPhoneNumbers));
    }
}
