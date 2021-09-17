package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.DriverInformation;
import com.ruoyi.YDOnlineTaxi.domain.OrderDetails;
import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import com.ruoyi.YDOnlineTaxi.service.IDriverInformationService;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;
import com.ruoyi.YDOnlineTaxi.service.OrderDetailsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Order;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/YDOnlineTaxi/WxService/Order")
public class YDOnlineTaxiDispatchOrder extends BaseController {
        @Autowired
        private IOrderInformationService orderInformationService;

        @Autowired
        private IDriverInformationService driverInformationService;

        @Autowired
        private OrderDetailsService orderDetailsService;

        public static String getStartOfDay(Date time) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        }

        public static String getEndOfDay(Date time) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        }

        public Boolean getOrderStatus(OrderInformation orderInformation){
                String orderStatus = orderInformationService.selectOrderStatusByOrderId(orderInformation.getOrderId());
                if(!orderStatus.equals("待派单")){
                        return Boolean.FALSE;
                }
                else{
                        return Boolean.TRUE;
                }
        }

        @GetMapping("/getOrder")
        public TableDataInfo list (DriverInformation driverInformation,OrderInformation orderInformation)
        {
                orderInformation.setOrderStatus("待派单");
                String minTransportTime = null;
                String maxTransportTime = null;
                if(orderInformation.getTransportTime() != null)
                {
                        minTransportTime = getStartOfDay(orderInformation.getTransportTime());
                        maxTransportTime = getEndOfDay(orderInformation.getTransportTime());
                }


                switch (driverInformation.getDriverCarType()){
                        case "舒适型":
                                orderInformation.setCarType("舒适型");
                                startPage();
                                return getDataTable(orderInformationService.getOrderInformationListByConditions(orderInformation.getCarType(),null,null,"待派单",minTransportTime,maxTransportTime,orderInformation.getRequirementTypes()));
                        case "商务型":
                                if(orderInformation.getCarType() != null){
                                        startPage();
                                        return getDataTable(orderInformationService.getOrderInformationListByConditions(orderInformation.getCarType(),null,null,"待派单",minTransportTime,maxTransportTime,orderInformation.getRequirementTypes()));
                                }
                                else
                                {
                                        startPage();
                                        return getDataTable(orderInformationService.getOrderInformationListByConditions("舒适型","商务型",null,"待派单",minTransportTime,maxTransportTime,orderInformation.getRequirementTypes()));
                                }
                        case "豪华型":
                                if(orderInformation.getCarType() != null){
                                        startPage();
                                        return getDataTable(orderInformationService.getOrderInformationListByConditions(orderInformation.getCarType(),null,null,"待派单",minTransportTime,maxTransportTime,orderInformation.getRequirementTypes()));
                                }
                                else
                                {
                                        startPage();
                                        return getDataTable(orderInformationService.getOrderInformationListByConditions("舒适型","豪华型",null,"待派单",minTransportTime,maxTransportTime,orderInformation.getRequirementTypes()));
                                }
                        case "豪华商务型":
                                if (orderInformation.getCarType() != null) {
                                        startPage();
                                        return getDataTable(orderInformationService.getOrderInformationListByConditions(orderInformation.getCarType(),null,null,"待派单",minTransportTime,maxTransportTime,orderInformation.getRequirementTypes()));
                                } else {
                                        startPage();
                                        return getDataTable(orderInformationService.getOrderInformationListByConditions("舒适型","豪华型","商务型","待派单",minTransportTime,maxTransportTime,orderInformation.getRequirementTypes()));
                                }
                        default:
                                return getDataTable(null);
                }
        }

        @PostMapping("/checkOrderStatus")
        public AjaxResult checkOrderStatus(OrderInformation orderInformation){
                boolean orderStatus = getOrderStatus(orderInformation);
                if(!orderStatus){
                        return AjaxResult.error("dispatched");
                }
                else{
                        return AjaxResult.success("waitDispatch");
                }
        }

        @PostMapping("/changeOrderStatus")
        public AjaxResult changeOrderStatus(OrderInformation orderInformation,DriverInformation driverInformation,OrderDetails orderDetails){
                OrderInformation originalOrder = orderInformationService.selectOrderInformationByOrderId(orderInformation.getOrderId());

                String orderStatus = originalOrder.getOrderStatus();
                switch (orderStatus)
                {
                        case "待派单":
                        try{
                                OrderDetails order = orderDetailsService.selectByPrimaryKey(orderInformation.getOrderId());
                                order.setDriverName(driverInformation.getDriverName());
                                order.setDriverPhoneNumber(driverInformation.getDriverPhoneNumber());
                                order.setDriverCarId(driverInformation.getDriverCarId());
                                order.setOrderTookTime(new Date());

                                orderInformation.setOrderStatus("已派单");

                                orderDetailsService.updateByPrimaryKeySelective(order);
                                orderInformationService.updateOrderInformation(orderInformation);
                                return AjaxResult.success("success");
                        }
                        catch (Exception e)
                        {
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
                                order2.setDepartureTime(new Date());
                                order2.setDepartureLocation(orderDetails.getArrivalLocation());


                                orderInformation.setOrderStatus("司机已到达");

                                orderDetailsService.updateByPrimaryKeySelective(order2);
                                orderInformationService.updateOrderInformation(orderInformation);
                                return AjaxResult.success("success");
                        case "司机已到达":
                                OrderDetails order3 = orderDetailsService.selectByPrimaryKey(orderInformation.getOrderId());
                                order3.setArrivalRime(new Date());
                                order3.setArrivalLocation(orderDetails.getArrivalLocation());

                                orderInformation.setOrderStatus("订单待审核");

                                orderDetailsService.updateByPrimaryKeySelective(order3);
                                orderInformationService.updateOrderInformation(orderInformation);
                                return AjaxResult.success("success");
                        case "待审核":
                                OrderDetails order4 = orderDetailsService.selectByPrimaryKey(orderInformation.getOrderId());
                                order4.setOrderFinishTime(new Date());

                                orderInformation.setOrderStatus("订单已结算");

                                orderDetailsService.updateByPrimaryKeySelective(order4);
                                orderInformationService.updateOrderInformation(orderInformation);
                                return AjaxResult.success("success");
                        default:
                                return AjaxResult.error("error");
                }
        }

        @GetMapping("/getPersonalOrderList")
        public TableDataInfo listOrderDetails(DriverInformation driverInformation){
                List<String> orderIdList = orderDetailsService.selectOrderIdByDriverPhoneNumber(driverInformation.getDriverPhoneNumber());
                List<OrderInformation> orderInformationList = new ArrayList<>();
                for(String orderId : orderIdList){
                        OrderInformation orderInformation = orderInformationService.selectOrderInformationByOrderId(orderId);
                        orderInformationList.add(orderInformation);
                }
                startPage();
                return getDataTable(orderInformationList);
        }

}
