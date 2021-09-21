package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.DriverInformation;
import com.ruoyi.YDOnlineTaxi.domain.OrderDetails;
import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;
import com.ruoyi.YDOnlineTaxi.service.OrderDetailsService;
import com.ruoyi.YDOnlineTaxi.utils.DateUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public Boolean getOrderStatus(OrderInformation orderInformation) {
        String orderStatus = orderInformationService.selectOrderStatusByOrderId(orderInformation.getOrderId());
        if (!orderStatus.equals("待派单")) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
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
                return orderInformationService.getOrderInformationListByConditions(orderInformation.getCarType(), null, null, orderStatus, minTransportTime, maxTransportTime, orderInformation.getRequirementTypes());
            case "商务型":
                if (orderInformation.getCarType() != null) {
                    return orderInformationService.getOrderInformationListByConditions(orderInformation.getCarType(), null, null, orderStatus, minTransportTime, maxTransportTime, orderInformation.getRequirementTypes());
                } else {
                    return orderInformationService.getOrderInformationListByConditions("舒适型", "商务型", null, orderStatus, minTransportTime, maxTransportTime, orderInformation.getRequirementTypes());
                }
            case "豪华型":
                if (orderInformation.getCarType() != null) {
                    return orderInformationService.getOrderInformationListByConditions(orderInformation.getCarType(), null, null, orderStatus, minTransportTime, maxTransportTime, orderInformation.getRequirementTypes());
                } else {
                    return orderInformationService.getOrderInformationListByConditions("舒适型", "豪华型", null, orderStatus, minTransportTime, maxTransportTime, orderInformation.getRequirementTypes());
                }
            case "豪华商务型":
                if (orderInformation.getCarType() != null) {
                    return orderInformationService.getOrderInformationListByConditions(orderInformation.getCarType(), null, null, orderStatus, minTransportTime, maxTransportTime, orderInformation.getRequirementTypes());
                } else {
                    return orderInformationService.getOrderInformationListByConditions("舒适型", "豪华型", "商务型", orderStatus, minTransportTime, maxTransportTime, orderInformation.getRequirementTypes());
                }
            default:
                return null;
        }
    }

    @GetMapping("/getOrder")
    public TableDataInfo list(DriverInformation driverInformation, OrderInformation orderInformation, Integer year, Integer month) {
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
            case "待派单":
                try {
                    OrderDetails order = orderDetailsService.selectByPrimaryKey(orderInformation.getOrderId());
                    order.setDriverName(driverInformation.getDriverName());
                    order.setDriverPhoneNumber(driverInformation.getDriverPhoneNumber());
                    order.setDriverCarId(driverInformation.getDriverCarId());
                    order.setOrderTookTime(new Date());

                    orderInformation.setOrderStatus("已派单");

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
                //TODO(inform administrator)

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
    public TableDataInfo getPersonalOrderList(DriverInformation driverInformation, OrderInformation orderInformation, Integer year, Integer month, Integer day) {
        Map<String, String> map = DateUtil.getIntervalDate(year, month, day);
        String minTransportTime = map.get("minTransportTime");
        String maxTransportTime = map.get("maxTransportTime");

        List<String> orderIdList;
        orderIdList = orderDetailsService.selectOrderIdByDriverPhoneNumber(driverInformation.getDriverPhoneNumber());

        List<OrderInformation> orderInformationList1 = new ArrayList<>();
        List<OrderInformation> orderInformationList2 = new ArrayList<>();
        List<OrderInformation> orderInformationList3 = new ArrayList<>();

        List<OrderInformation> orderList = new ArrayList<>();

        for (String orderId : orderIdList) {
            OrderInformation order = new OrderInformation();

            if (minTransportTime == null && maxTransportTime == null) {
                order = orderInformationService.selectOrderInformationByOrderId(orderId);
            } else {
                order = orderInformationService.selectAllByOrderIdAndTransportTimeBetween(orderId, minTransportTime, maxTransportTime);
            }
            if (order == null)
                continue;
            else {
                orderList.add(order);
            }
        }
        if (orderIdList != null) {
            for (OrderInformation order : orderList) {
                switch (orderInformation.getOrderStatus()) {
                    case "已派单":
                        if (order.getOrderStatus().equals("已派单")) {
                            orderInformationList1.add(order);
                        }
                        break;
                    case "正在进行":
                        if (order.getOrderStatus().equals("司机已出发") || order.getOrderStatus().equals("司机已到达")) {
                            orderInformationList2.add(order);
                        }
                        break;
                    case "已完成":
                        if (order.getOrderStatus().equals("待审核") || order.getOrderStatus().equals("审核未通过") || order.getOrderStatus().equals("未结算") || order.getOrderStatus().equals("已结算")) {
                            orderInformationList3.add(order);
                        }
                        break;
                }
            }
        } else {
            startPage();
            return getDataTable(null);
        }
        switch (orderInformation.getOrderStatus()) {
            case "已派单":
                startPage();
                return getDataTable(orderInformationList1);
            case "正在进行":
                startPage();
                return getDataTable(orderInformationList2);
            case "已完成":
                startPage();
                return getDataTable(orderInformationList3);
            default:
                startPage();
                return getDataTable(null);
        }

    }

    @GetMapping("/getOrderFinishTime")
    public Date orderFinishTime(String orderId) {
        OrderDetails order = orderDetailsService.selectByPrimaryKey(orderId);
        Date orderFinishTime;
        orderFinishTime = order.getOrderFinishTime();
        return orderFinishTime;
    }
}
