package com.ruoyi.web.controller.YDOnlineTaxi;

import java.util.List;

import com.ruoyi.YDOnlineTaxi.domain.OrderDetails;
import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.Producer.RabbitMQProducer;
import com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.RabbitMQConfig;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.web.service.TokenService;
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
import com.ruoyi.YDOnlineTaxi.domain.FinancialStatistics;
import com.ruoyi.YDOnlineTaxi.service.IFinancialStatisticsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 财务统计Controller
 * 
 * @author Asaki
 * @date 2021-11-03
 */
@RestController
@RequestMapping("/YDOnlineTaxi/FinancialStatistics")
public class FinancialStatisticsController extends BaseController
{
    @Autowired
    private IFinancialStatisticsService financialStatisticsService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    /**
     * 查询财务统计列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:FinancialStatistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(FinancialStatistics financialStatistics)
    {
        startPage();
        List<FinancialStatistics> list = financialStatisticsService.selectFinancialStatisticsList(financialStatistics);
        return getDataTable(list);
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
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") String orderId)
    {
        return AjaxResult.success(financialStatisticsService.selectFinancialStatisticsByOrderId(orderId));
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
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable String[] orderIds)
    {
        return toAjax(financialStatisticsService.deleteFinancialStatisticsByOrderIds(orderIds));
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<FinancialStatistics> util = new ExcelUtil<FinancialStatistics>(FinancialStatistics.class);
        return util.importTemplateExcel("财务统计");
    }

    @Log(title = "财务统计导入", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:FinancialStatistics:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        OrderDetails orderDetails = new OrderDetails();

        ExcelUtil<FinancialStatistics> util = new ExcelUtil<FinancialStatistics>(FinancialStatistics.class);
        List<FinancialStatistics> orderList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = financialStatisticsService.importOrder(orderList, updateSupport, operName);

        return AjaxResult.success(message);
    }
}
