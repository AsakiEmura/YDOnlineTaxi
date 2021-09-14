package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.VO.PonitsStatistics;
import com.ruoyi.YDOnlineTaxi.service.IPonitsStatisticsService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 积分统计Controller
 * 
 * @author ruoyi
 * @date 2021-09-08
 */
@RestController
@RequestMapping("/YDOnlineTaxi/PointsStatistics")
public class PonitsStatisticsController extends BaseController
{
    @Autowired
    private IPonitsStatisticsService ponitsStatisticsService;

    /**
     * 查询积分统计列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:PointsStatistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(PonitsStatistics ponitsStatistics)
    {
        startPage();
        List<PonitsStatistics> list = ponitsStatisticsService.selectPonitsStatisticsList(ponitsStatistics);
        return getDataTable(list);
    }

    /**
     * 导出积分统计列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:PointsStatistics:export')")
    @Log(title = "积分统计", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PonitsStatistics ponitsStatistics)
    {
        List<PonitsStatistics> list = ponitsStatisticsService.selectPonitsStatisticsList(ponitsStatistics);
        ExcelUtil<PonitsStatistics> util = new ExcelUtil<PonitsStatistics>(PonitsStatistics.class);
        return util.exportExcel(list, "积分统计数据");
    }

    /**
     * 获取积分统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:PointsStatistics:query')")
    @GetMapping(value = "/{driverPhoneNumber}")
    public AjaxResult getInfo(@PathVariable("driverPhoneNumber") String driverPhoneNumber)
    {
        return AjaxResult.success(ponitsStatisticsService.selectPonitsStatisticsByDriverPhoneNumber(driverPhoneNumber));
    }

    /**
     * 新增积分统计
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:PointsStatistics:add')")
    @Log(title = "积分统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PonitsStatistics ponitsStatistics)
    {
        return toAjax(ponitsStatisticsService.insertPonitsStatistics(ponitsStatistics));
    }

    /**
     * 修改积分统计
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:PointsStatistics:edit')")
    @Log(title = "积分统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PonitsStatistics ponitsStatistics)
    {
        return toAjax(ponitsStatisticsService.updatePonitsStatistics(ponitsStatistics));
    }

    /**
     * 删除积分统计
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:PointsStatistics:remove')")
    @Log(title = "积分统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{driverPhoneNumbers}")
    public AjaxResult remove(@PathVariable String[] driverPhoneNumbers)
    {
        return toAjax(ponitsStatisticsService.deletePonitsStatisticsByDriverPhoneNumbers(driverPhoneNumbers));
    }
}
