package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.Properties.MQProperties;
import com.ruoyi.YDOnlineTaxi.domain.VO.OrderDetails;
import com.ruoyi.YDOnlineTaxi.domain.VO.OrderInformation;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;
import com.ruoyi.YDOnlineTaxi.service.OrderDetailsService;
import com.ruoyi.YDOnlineTaxi.utils.WxService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.TokenService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @Autowired
    private TokenService tokenService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired(required = false)
    private MQProperties mqProperties;

    private RestTemplate restTemplate;

    private YDOnlineTaxiWxService ydOnlineTaxiWxService;


    /**
     * 查询订单信息列表
     */

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

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<OrderInformation> util = new ExcelUtil<OrderInformation>(OrderInformation.class);
        return util.importTemplateExcel("订单数据");
    }

    @Log(title = "订单信息管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:OrderInformation:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        try
            {
                OrderDetails orderDetails = new OrderDetails();

                ExcelUtil<OrderInformation> util = new ExcelUtil<OrderInformation>(OrderInformation.class);
                List<OrderInformation> orderList = util.importExcel(file.getInputStream());
                LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
                String operName = loginUser.getUsername();
                String message = orderInformationService.importOrder(orderList, updateSupport, operName);

                for(OrderInformation order : orderList)
                {
                    orderDetails.setOrderId(order.getOrderId());
                    orderDetailsService.insert(orderDetails);
                }
                WxService.pushNotification("oLQhU5fwZctlTA19fhTxvVRBc9Po","有大量订单,请到小程序查看详情");

                return AjaxResult.success(message);
            }
        catch (Exception e)
        {
            e.printStackTrace();
            return AjaxResult.error("400");
        }
    }

}
