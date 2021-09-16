package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.DriverInformation;
import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import com.ruoyi.YDOnlineTaxi.service.IDriverInformationService;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        public static String getStartOfDay(Date time) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        }
        // 返回时间格式如：2020-02-19 23:59:59
        public static String getEndOfDay(Date time) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        }

        @GetMapping("/getOrder")
        public TableDataInfo list (DriverInformation driverInformation,OrderInformation orderInformation) throws ParseException
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
                                return getDataTable(orderInformationService.selectOrderInformationList(orderInformation));
                        case "商务型":
                                if(orderInformation.getCarType() != null){
                                        startPage();
                                        return getDataTable(orderInformationService.getOrderInformationListByConditions("舒适型",null,null,"待派单",minTransportTime,maxTransportTime,orderInformation.getRequirementTypes()));
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

        @GetMapping("/checkOrderStatus")
        public AjaxResult checkOrderStatus(OrderInformation orderInformation){
                boolean orderStatus = getOrderStatus(orderInformation);
                if(!orderStatus){
                        return AjaxResult.error("dispatched");
                }
                else{
                        return AjaxResult.success("dispatch");
                }
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

//        @PostMapping("/changeOrderStatus")
//        public AjaxResult changeOrderStatus(OrderInformation orderInformation){
//                String orderStatus = orderInformation.getOrderStatus();
//                String originalOrderStatus = orderInformationService.selectOrderStatusByOrderId(orderInformation.getOrderId());
//                switch (orderStatus){
//                        case "已接单":
//                                if(!originalOrderStatus.equals("待派单"))
//                                {
//                                        return AjaxResult.error("error!");
//                                }
//                        case "出发":
//                                if(!originalOrderStatus.equals("已派单"))
//                                {
//                                        return AjaxResult.error("error!");
//                                }
//                        case "到达":
//                                if(!originalOrderStatus.equals("出发"))
//                                {
//                                        return AjaxResult.error("error!");
//                                }
//                        case "":
//
//                }
//        }
}
