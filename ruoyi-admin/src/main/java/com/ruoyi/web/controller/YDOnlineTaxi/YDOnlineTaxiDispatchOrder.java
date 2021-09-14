package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.VO.DriverInformation;
import com.ruoyi.YDOnlineTaxi.domain.VO.OrderInformation;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/YDOnlineTaxi/WxService/Order")
public class YDOnlineTaxiDispatchOrder extends BaseController {
        @Autowired
        private IOrderInformationService orderInformationService;

        @GetMapping("/getOrder")
        public TableDataInfo list(DriverInformation driverInformation,OrderInformation orderInformation)
        {
                startPage();
                orderInformation.setOrderStatus("待派单");
                switch (driverInformation.getDriverCarType()){
                        case "舒适型":
                                orderInformation.setCarType("舒适型");
                                return getDataTable(orderInformationService.selectOrderInformationList(orderInformation));
                        case "商务型":
                                orderInformation.setCarType("商务型");
                                List<OrderInformation> list1 = orderInformationService.selectOrderInformationList(orderInformation);
                                orderInformation.setCarType("舒适型");
                                List<OrderInformation> list2 = orderInformationService.selectOrderInformationList(orderInformation);
                                List<OrderInformation> list3 = new ArrayList<>();
                                list3.addAll(list1);
                                list3.addAll(list2);
                                return getDataTable(list3);
                        case "豪华型":
                                orderInformation.setCarType("豪华型");
                                List<OrderInformation> list4 = orderInformationService.selectOrderInformationList(orderInformation);
                                orderInformation.setCarType("舒适型");
                                List<OrderInformation> list5 = orderInformationService.selectOrderInformationList(orderInformation);
                                List<OrderInformation> list6 = new ArrayList<>();
                                list6.addAll(list4);
                                list6.addAll(list5);
                                return getDataTable(list6);
                        case "豪华商务型":
                                return getDataTable(orderInformationService.selectOrderInformationList(orderInformation));
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
