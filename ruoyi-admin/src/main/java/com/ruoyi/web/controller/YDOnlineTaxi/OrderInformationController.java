package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.DriverInformation;
import com.ruoyi.YDOnlineTaxi.domain.OrderDetails;
import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import com.ruoyi.YDOnlineTaxi.domain.PonitsStatistics;
import com.ruoyi.YDOnlineTaxi.service.*;
import com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.Producer.RabbitMQProducer;
import com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.RabbitMQConfig;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ruoyi.YDOnlineTaxi.utils.JPushUtils.sendToRegistrationId;

/**
 * 订单信息Controller
 *
 * @author ruoyi
 * @date 2021-08-28
 */
@RestController
@RequestMapping("/YDOnlineTaxi/OrderInformation")
public class OrderInformationController extends BaseController {
    @Autowired
    private IOrderInformationService orderInformationService;

    @Autowired
    private TokenService tokenService;

    @Autowired(required = false)
    private OrderDetailsService orderDetailsService;

    @Autowired
    private IPonitsStatisticsService ponitsStatisticsService;

    @Autowired
    private IDriverInformationService driverInformationService;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Autowired
    private IDriverAccountService driverAccountService;

    /**
     * 查询订单信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(OrderInformation orderInformation) {
        startPage();
        List<OrderInformation> list = orderInformationService.selectOrderInformationList(orderInformation);
        return getDataTable(list);
    }

    /**
     * 查询单个订单信息列表
     */
    @PostMapping("/singleStatusList")
    public TableDataInfo timeOutList(@RequestBody Map<String, Object> data) {
        startPage();
        List<OrderInformation> list = orderInformationService.selectOrderByStatus(data.get("status").toString());
        return getDataTable(list);
    }

    /**
     * 查询多个订单信息列表
     */
    @GetMapping("/receivedList")
    public TableDataInfo receivedList(OrderInformation orderInformation) {
        startPage();
        String[] status = {
                "已派单",
                "司机已出发",
                "司机已到达"
        };
        List<OrderInformation> list = orderInformationService.selectOrderByReceived(orderInformation,status);
        return getDataTable(list);
    }

    /**
     * 查询待审核已结算未结算订单信息列表
     */
    @GetMapping("/auditSettlementList")
    public TableDataInfo auditSettlementList(OrderInformation orderInformation)
    {
        startPage();
        String[] status = {
                "待审核",
                "未通过",
                "未结算"
        };
        List<OrderInformation> list = orderInformationService.selectOrderByReceived(orderInformation,status);
        return getDataTable(list);
    }

    /**
     * 查询订单详细到达信息
     */
    @GetMapping(value = "/arrival_information/{orderId}")
    public AjaxResult arrival_information(@PathVariable("orderId") String orderId)
    {
        return AjaxResult.success(orderDetailsService.selectByPrimaryKey(orderId));
    }

    /**
     * 指派司机
     */
    @PutMapping("/orderDriver")
    public AjaxResult orderDriver(@RequestBody Map<String, Object> data)
    {
        try{
            String orderId = data.get("orderId").toString();
            String phoneNumber = data.get("phoneNumber").toString();
            DriverInformation driverInformation = driverInformationService.selectDriverInformationByDriverPhoneNumber(phoneNumber);
            OrderDetails orderDetails = orderDetailsService.selectByPrimaryKey(orderId);

            orderDetails.setDriverName(driverInformation.getDriverName());
            orderDetails.setDriverPhoneNumber(driverInformation.getDriverPhoneNumber());
            orderDetails.setDriverCarId(driverInformation.getDriverCarId());
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date date = new Date();
            df.format(date);
            orderDetails.setOrderTookTime(date);
            orderDetailsService.updateByPrimaryKey(orderDetails);

            OrderInformation orderInformation = orderInformationService.selectOrderInformationByOrderId(orderId);
            orderInformation.setOrderStatus("已派单");
            orderInformation.setExpireTime(0);
            orderInformation.setDriverPhoneNumber(phoneNumber);
            orderInformation.setDriverInformation(driverInformation.SimpleToString());
            orderInformationService.updateOrderInformation(orderInformation);

            List<String> registrationIds = new ArrayList<>();
            String machineId = driverAccountService.selectMachineIdByPhoneNumber(phoneNumber);
            registrationIds.add(machineId);
            sendToRegistrationId(registrationIds,
                    "您有一个指定订单",
                    "您有一个指定订单",
                    "您有一个指定订单，订单编号为：" + orderId);

            return AjaxResult.success();
        }catch (Exception e){
            return AjaxResult.error(e.toString());
        }
    }

    /**
     * 一键结算
     */
    @PutMapping("settlement")
    public AjaxResult settlement()
    {
        try{
            List<OrderInformation> list = orderInformationService.selectOrderByStatus("未结算");
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date date = new Date();
            df.format(date);
            for(int i=0; i<list.size(); i++){
                OrderInformation orderInformation = list.get(i);
                String orderId = orderInformation.getOrderId();
                OrderDetails orderDetails = orderDetailsService.selectByPrimaryKey(orderId);
                long tempHour = date.getTime()-orderDetails.getOrderFinishTime().getTime()/(60*60*1000);
                if(tempHour > 24){
                    PonitsStatistics ponitsStatistics = ponitsStatisticsService.selectPonitsStatisticsByDriverPhoneNumber(orderDetails.getDriverPhoneNumber());
                    ponitsStatistics.setTotalPoints(orderInformation.getDriverBase() + ponitsStatistics.getTotalPoints());
                    ponitsStatistics.setMonthPoints(orderInformation.getDriverBase() + ponitsStatistics.getMonthPoints());
                    ponitsStatisticsService.updatePonitsStatistics(ponitsStatistics);

                    DriverInformation driverInformation =(driverInformationService.selectDriverInformationByDriverPhoneNumber(orderDetails.getDriverPhoneNumber()));
                    driverInformation.setDriverCompleteOrderNumber(driverInformation.getDriverCompleteOrderNumber() + 1);
                    driverInformation.setDriverCompleteOrderNumberMonthly(driverInformation.getDriverCompleteOrderNumberMonthly() + 1);
                    driverInformationService.updateDriverInformation(driverInformation);

                    orderInformation.setOrderStatus("已结算");
                    orderInformationService.updateOrderInformation(orderInformation);
                }
            }
            return AjaxResult.success();
        }catch (Exception e){
            return AjaxResult.error(e.toString());
        }
    }

    /**
     * 导出订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:OrderInformation:export')")
    @Log(title = "订单信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OrderInformation orderInformation) {
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
        rabbitMQProducer.sendMsg(RabbitMQConfig.DELAY_EXCHANGE_NAME, RabbitMQConfig.DELAY_ROUTINGKEY_NAME_K, "有少量订单导入,请到程序内查看", 0);
        rabbitMQProducer.sendMsg(RabbitMQConfig.DELAY_EXCHANGE_NAME, RabbitMQConfig.DELAY_ROUTINGKEY_NAME_D, "有少量订单导入,请到程序内查看", 20 * 1000);
        rabbitMQProducer.sendMsg(RabbitMQConfig.DELAY_EXCHANGE_NAME, RabbitMQConfig.DELAY_ROUTINGKEY_NAME_G, "有少量订单导入,请到程序内查看", 40 * 1000);
        //order details入库
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderInformation.getOrderId());
        orderDetailsService.insert(orderDetails);
        return toAjax(orderInformationService.insertOrderInformation(orderInformation));
    }

    /**
     * 重新发布
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:OrderInformation:edit')")
    @Log(title = "超时订单重新发布", businessType = BusinessType.UPDATE)
    @PutMapping("/resetOrderStatus")
    public AjaxResult resetOrderStatus(@RequestBody OrderInformation orderInformation)
    {
        rabbitMQProducer.sendMsg(RabbitMQConfig.DELAY_EXCHANGE_NAME, RabbitMQConfig.DELAY_ROUTINGKEY_NAME_K, "有少量订单导入,请到程序内查看", 0);
        rabbitMQProducer.sendMsg(RabbitMQConfig.DELAY_EXCHANGE_NAME, RabbitMQConfig.DELAY_ROUTINGKEY_NAME_D, "有少量订单导入,请到程序内查看", 20 * 1000);
        rabbitMQProducer.sendMsg(RabbitMQConfig.DELAY_EXCHANGE_NAME, RabbitMQConfig.DELAY_ROUTINGKEY_NAME_G, "有少量订单导入,请到程序内查看", 40 * 1000);
        return toAjax(orderInformationService.updateOrderInformation(orderInformation));
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
        try
        {
            String tempId = null;
            orderInformationService.deleteOrderInformationByOrderIds(orderIds);
            try
            {
                for(String orderId:orderIds)
                {
                    orderDetailsService.deleteByPrimaryKey(orderId);
                    tempId = orderId;
                }
                return AjaxResult.success();
            }
            catch (Exception e)
            {
                AjaxResult.error("此预约号的订单删除失败: "+tempId);
            }
        }
        catch (Exception e)
        {
            return AjaxResult.error();
        }
        finally
        {
            return AjaxResult.success();
        }
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<OrderInformation> util = new ExcelUtil<OrderInformation>(OrderInformation.class);
        return util.importTemplateExcel("订单数据");
    }

    @Log(title = "批量导入订单", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:OrderInformation:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        try {
            OrderDetails orderDetails = new OrderDetails();

            ExcelUtil<OrderInformation> util = new ExcelUtil<OrderInformation>(OrderInformation.class);
            List<OrderInformation> orderList = util.importExcel(file.getInputStream());
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            String message = orderInformationService.importOrder(orderList, updateSupport, operName);

            for (OrderInformation order : orderList) {
                orderDetails.setOrderId(order.getOrderId());
                orderDetailsService.insert(orderDetails);
                rabbitMQProducer.sendMsg(RabbitMQConfig.DELAY_EXCHANGE_NAME, RabbitMQConfig.DELAY_ROUTINGKEY_NAME_DEAD, order.getOrderId(), 40 * 60 * 1000);
            }

            rabbitMQProducer.sendMsg(RabbitMQConfig.DELAY_EXCHANGE_NAME, RabbitMQConfig.DELAY_ROUTINGKEY_NAME_K, "有大量订单导入,请到程序内查看", 0);
            rabbitMQProducer.sendMsg(RabbitMQConfig.DELAY_EXCHANGE_NAME, RabbitMQConfig.DELAY_ROUTINGKEY_NAME_D, "有大量订单导入,请到程序内查看", 5 * 60 * 1000);
            rabbitMQProducer.sendMsg(RabbitMQConfig.DELAY_EXCHANGE_NAME, RabbitMQConfig.DELAY_ROUTINGKEY_NAME_G, "有大量订单导入,请到程序内查看", 10 * 60 * 1000);

            return AjaxResult.success(message);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("400");
        }
    }

}
