package com.ruoyi.web.controller.YDOnlineTaxi;

import java.util.List;
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
import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单信息Controller
 * 
 * @author ruoyi
 * @date 2021-08-28
 */
@RestController
@RequestMapping("/YDOnlineTaxi/OrderInformation")
public class OrderInformationController extends BaseController
{
    @Autowired
    private IOrderInformationService orderInformationService;

    /**
     * 查询订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:OrderInformation:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderInformation orderInformation)
    {
        startPage();
        List<OrderInformation> list = orderInformationService.selectOrderInformationList(orderInformation);
        return getDataTable(list);
    }

    /**
     * 导出订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:OrderInformation:export')")
    @Log(title = "订单信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OrderInformation orderInformation)
    {
        List<OrderInformation> list = orderInformationService.selectOrderInformationList(orderInformation);
        ExcelUtil<OrderInformation> util = new ExcelUtil<OrderInformation>(OrderInformation.class);
        return util.exportExcel(list, "订单信息数据");
    }

    /**
     * 获取订单信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:OrderInformation:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") String orderId)
    {
        return AjaxResult.success(orderInformationService.selectOrderInformationByOrderId(orderId));
    }

    /**
     * 新增订单信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:OrderInformation:add')")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderInformation orderInformation)
    {
        return toAjax(orderInformationService.insertOrderInformation(orderInformation));
    }

    /**
     * 修改订单信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:OrderInformation:edit')")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderInformation orderInformation)
    {
        return toAjax(orderInformationService.updateOrderInformation(orderInformation));
    }

    /**
     * 删除订单信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:OrderInformation:remove')")
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable String[] orderIds)
    {
        return toAjax(orderInformationService.deleteOrderInformationByOrderIds(orderIds));
    }
}
