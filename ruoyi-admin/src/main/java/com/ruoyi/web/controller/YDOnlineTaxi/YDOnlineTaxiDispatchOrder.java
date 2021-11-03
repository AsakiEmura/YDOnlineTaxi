package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.*;
import com.ruoyi.YDOnlineTaxi.service.IFinancialStatisticsService;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;
import com.ruoyi.YDOnlineTaxi.service.IRewardsPunishmentsLogService;
import com.ruoyi.YDOnlineTaxi.service.OrderDetailsService;
import com.ruoyi.YDOnlineTaxi.utils.DateUtil;
import com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.RabbitMQConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.aspectj.weaver.ast.Or;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/YDOnlineTaxi/WxService/Order")
public class YDOnlineTaxiDispatchOrder extends BaseController {
    @Autowired
    private IOrderInformationService orderInformationService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IRewardsPunishmentsLogService rewardsPunishmentsLogService;

    @Autowired
    private IFinancialStatisticsService financialStatisticsService;

    public Boolean getOrderStatus(OrderInformation orderInformation) {
        String orderStatus = orderInformationService.selectOrderStatusByOrderId(orderInformation.getOrderId());
        if (orderStatus.equals("待派单") || orderStatus.equals("已超时")) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public List<OrderInformation> getOrder(DriverInformation driverInformation, OrderInformation orderInformation) {
        String orderStatus = orderInformation.getOrderStatus();
        String minTransportTime = null;
        String maxTransportTime = null;
        if (orderInformation.getTransportTime() != null) {
            minTransportTime = DateUtil.getStartOfDay(orderInformation.getTransportTime());
            maxTransportTime = DateUtil.getEndOfDay(orderInformation.getTransportTime());
        }


        switch (driverInformation.getDriverCarType()) {
            case "舒适型":
                orderInformation.setCarType("舒适型");
                return orderInformationService.getOrderInformationListByConditions(orderInformation.getCarType(), null, null, orderStatus, minTransportTime, maxTransportTime, orderInformation.getNote());
            case "商务型":
                if (orderInformation.getCarType() != null) {
                    return orderInformationService.getOrderInformationListByConditions(orderInformation.getCarType(), null, null, orderStatus, minTransportTime, maxTransportTime, orderInformation.getNote());
                } else {
                    return orderInformationService.getOrderInformationListByConditions("舒适型", "商务型", null, orderStatus, minTransportTime, maxTransportTime, orderInformation.getNote());
                }
            case "豪华型":
                if (orderInformation.getCarType() != null) {
                    return orderInformationService.getOrderInformationListByConditions(orderInformation.getCarType(), null, null, orderStatus, minTransportTime, maxTransportTime, orderInformation.getNote());
                } else {
                    return orderInformationService.getOrderInformationListByConditions("舒适型", "豪华型", null, orderStatus, minTransportTime, maxTransportTime, orderInformation.getNote());
                }
            case "豪华商务型":
                if (orderInformation.getCarType() != null) {
                    return orderInformationService.getOrderInformationListByConditions(orderInformation.getCarType(), null, null, orderStatus, minTransportTime, maxTransportTime, orderInformation.getNote());
                } else {
                    return orderInformationService.getOrderInformationListByConditions("舒适型", "豪华型", "商务型", orderStatus, minTransportTime, maxTransportTime, orderInformation.getNote());
                }
            default:
                return null;
        }
    }

    @GetMapping("/RPLog")
    public TableDataInfo getRPLog(String phoneNumber,Integer year, Integer month, Integer day) {
        if(year != null)
        {
            Map<String, String> map = DateUtil.getIntervalDate(year, month, day);
            String minOperatingTime = map.get("minTransportTime");
            String maxOperatingTime = map.get("maxTransportTime");

            List<RewardsPunishmentsLog> rewardsPunishmentsLogs = rewardsPunishmentsLogService.selectAllByOperatingTimeBetweenAndPhoneNumber(minOperatingTime, maxOperatingTime, phoneNumber);
            startPage();
            return getDataTable(rewardsPunishmentsLogs);
        }
        else
        {
            List<RewardsPunishmentsLog> rewardsPunishmentsLogs = rewardsPunishmentsLogService.selectAllByPhoneNumber(phoneNumber);
            startPage();
            return getDataTable(rewardsPunishmentsLogs);
        }
    }

    @GetMapping("/getOrder")
    public TableDataInfo list(DriverInformation driverInformation, OrderInformation orderInformation) {
        startPage();
        return getDataTable(getOrder(driverInformation, orderInformation));
    }

    @PostMapping("/checkOrderStatus")
    public AjaxResult checkOrderStatus(OrderInformation orderInformation) {
        boolean orderStatus = getOrderStatus(orderInformation);
        if (!orderStatus) {
            return AjaxResult.error("dispatched");
        } else {
            return AjaxResult.success("waitDispatch");
        }
    }

    @PostMapping("/changeOrderStatus")
    public AjaxResult changeOrderStatus(OrderInformation orderInformation, DriverInformation driverInformation, OrderDetails orderDetails) {
        OrderInformation originalOrder = orderInformationService.selectOrderInformationByOrderId(orderInformation.getOrderId());

        String orderStatus = originalOrder.getOrderStatus();
        switch (orderStatus) {
            case "已超时":
            case "待派单":
                try {
                    OrderDetails order = orderDetailsService.selectByPrimaryKey(orderInformation.getOrderId());
                    order.setDriverName(driverInformation.getDriverName());
                    order.setDriverPhoneNumber(driverInformation.getDriverPhoneNumber());
                    order.setDriverCarId(driverInformation.getDriverCarId());
                    order.setOrderTookTime(new Date());

                    orderInformation.setOrderStatus("已派单");
                    orderInformation.setDriverInformation(driverInformation.SimpleToString());

                    orderDetailsService.updateByPrimaryKeySelective(order);
                    orderInformationService.updateOrderInformation(orderInformation);
                    return AjaxResult.success("success");
                } catch (Exception e) {
                    return AjaxResult.error("error");
                }
            case "已派单":
                OrderDetails order1 = orderDetailsService.selectByPrimaryKey(orderInformation.getOrderId());
                order1.setDepartureTime(new Date());
                order1.setDepartureLocation(orderDetails.getDepartureLocation());

                orderInformation.setOrderStatus("司机已出发");

                orderDetailsService.updateByPrimaryKeySelective(order1);
                orderInformationService.updateOrderInformation(orderInformation);
                return AjaxResult.success("success");
            case "司机已出发":
                OrderDetails order2 = orderDetailsService.selectByPrimaryKey(orderInformation.getOrderId());
                order2.setArrivalRime(new Date());
                order2.setArrivalLocation(orderDetails.getArrivalLocation());


                orderInformation.setOrderStatus("司机已到达");

                orderDetailsService.updateByPrimaryKeySelective(order2);
                orderInformationService.updateOrderInformation(orderInformation);
                return AjaxResult.success("success");
            case "司机已到达":
                OrderDetails order3 = orderDetailsService.selectByPrimaryKey(orderInformation.getOrderId());
                order3.setOrderFinishTime(new Date());

                orderInformation.setOrderStatus("待审核");
                rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE_NAME, RabbitMQConfig.DIRECT_ROUTINGKEY_NAME, "订单编号为: " + orderInformation.getOrderId() + " 的订单待审核,请刷新订单审核界面!");
                orderDetailsService.updateByPrimaryKeySelective(order3);
                orderInformationService.updateOrderInformation(orderInformation);

                return AjaxResult.success("success");
            case "待审核":
                OrderDetails order4 = orderDetailsService.selectByPrimaryKey(orderInformation.getOrderId());
                order4.setOrderFinishTime(new Date());

                orderInformation.setOrderStatus("已结算");

                orderDetailsService.updateByPrimaryKeySelective(order4);
                orderInformationService.updateOrderInformation(orderInformation);
                return AjaxResult.success("success");
            default:
                return AjaxResult.error("error");
        }
    }

    @GetMapping("/getPersonalOrderList")
    public TableDataInfo getPersonalOrderList(DriverInformation driverInformation, OrderInformation orderInformation, Integer year, Integer month, Integer day)  {
        Map<String, String> map = DateUtil.getIntervalDate(year, month, day);
        String minTransportTime = map.get("minTransportTime");
        String maxTransportTime = map.get("maxTransportTime");

        startPage();
        List<OrderInformation> orderList = new ArrayList<>();
        switch (orderInformation.getOrderStatus()) {
            case "已派单":
                orderList = orderInformationService.selectPersonalOrderByConditions(driverInformation.getDriverPhoneNumber(), minTransportTime, maxTransportTime, "已派单", null, null, null);
                return getDataTable(orderList);
            case "正在进行":
                orderList = orderInformationService.selectPersonalOrderByConditions(driverInformation.getDriverPhoneNumber(), minTransportTime, maxTransportTime, "司机已出发", "司机已到达", null, null);
                return getDataTable(orderList);
            case "已完成":
                orderList = orderInformationService.selectPersonalOrderByConditions(driverInformation.getDriverPhoneNumber(), minTransportTime, maxTransportTime, "未通过", "待审核", "未结算", "已结算");
                return getDataTable(orderList);
            case "已获积分":
                orderList = orderInformationService.selectPersonalOrderByConditions(driverInformation.getDriverPhoneNumber(), minTransportTime, maxTransportTime, "已结算", null, null, null);
                return getDataTable(orderList);
            case "未获积分":
                orderList = orderInformationService.selectPersonalOrderByConditions(driverInformation.getDriverPhoneNumber(), minTransportTime, maxTransportTime, "已派单", "司机已", "未", "待审核");
                return getDataTable(orderList);
            case "全部":
                orderList = orderInformationService.selectAllByDriverPhoneNumber(driverInformation.getDriverPhoneNumber(), minTransportTime, maxTransportTime);
                return getDataTable(orderList);
            case "财务统计":
                List<FinancialStatistics> doneOrderList;
                doneOrderList = financialStatisticsService.selectAllByConditions(driverInformation.getDriverPhoneNumber(),minTransportTime,maxTransportTime);
                return getDataTable(doneOrderList);
            default:
                return getDataTable(orderList);
        }
    }

    @GetMapping("/getPersonalPoints")
    public BigDecimal getIntegralObtainedWithinSpecifiedPeriod(DriverInformation driverInformation, OrderInformation orderInformation, Integer year, Integer month, Integer day) {
        Map<String, String> map = DateUtil.getIntervalDate(year, month, day);
        String minTransportTime = map.get("minTransportTime");
        String maxTransportTime = map.get("maxTransportTime");

        List<FinancialStatistics> doneOrderList;
        doneOrderList = financialStatisticsService.selectAllByConditions(driverInformation.getDriverPhoneNumber(), minTransportTime, maxTransportTime);

        BigDecimal total = BigDecimal.ZERO;
        for(FinancialStatistics order : doneOrderList)
        {
            total = total .add(order.getAccountsPayable());
        }
        return total;
    }

    @GetMapping("/getOrderFinishTime")
    public Date orderFinishTime(String orderId) {
        OrderDetails order = orderDetailsService.selectByPrimaryKey(orderId);
        Date orderFinishTime;
        orderFinishTime = order.getOrderFinishTime();
        return orderFinishTime;
    }

}
