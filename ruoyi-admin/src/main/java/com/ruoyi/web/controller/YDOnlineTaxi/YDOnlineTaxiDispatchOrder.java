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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/YDOnlineTaxi/WxService/Order")
public class YDOnlineTaxiDispatchOrder extends BaseController {
        @Autowired
        private IOrderInformationService orderInformationService;

        @Autowired
        private IDriverInformationService driverInformationService;

        @GetMapping("/getOrder")
        public TableDataInfo list(DriverInformation driverInformation,OrderInformation orderInformation)
        {
                orderInformation.setOrderStatus("待派单");
                switch (driverInformation.getDriverCarType()){
                        case "舒适型":
                                orderInformation.setCarType("舒适型");
                                startPage();
                                return getDataTable(orderInformationService.selectOrderInformationList(orderInformation));
                        case "商务型":
                                if(orderInformation.getCarType().equals("商务型") || orderInformation.getCarType().equals("舒适型")){
                                        startPage();
                                        return getDataTable(orderInformationService.selectOrderInformationList(orderInformation));
                                }
                                else
                                {
                                        startPage();
                                        return getDataTable(orderInformationService.selectAllByCarTypeLikeOrCarTypeLikeAndOrderStatusLike("舒适型","商务型","待派单"));
                                }
                        case "豪华型":
                                if(orderInformation.getCarType().equals("豪华型") || orderInformation.getCarType().equals("舒适型")){
                                        startPage();
                                        return getDataTable(orderInformationService.selectOrderInformationList(orderInformation));
                                }
                                else
                                {
                                        startPage();
                                        return getDataTable(orderInformationService.selectAllByCarTypeLikeOrCarTypeLikeAndOrderStatusLike("舒适型","豪华型","待派单"));
                                }
                        case "豪华商务型":
                                if (!orderInformation.getCarType().equals("商务型") && !orderInformation.getCarType().equals("豪华型") && !orderInformation.getCarType().equals("舒适型")) {
                                        startPage();
                                        return getDataTable(orderInformationService.selectOrderInformationList(orderInformation));
                                } else {
                                        startPage();
                                        return getDataTable(orderInformationService.selectOrderInformationList(orderInformation));
                                }
                        default:
                                startPage();
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
