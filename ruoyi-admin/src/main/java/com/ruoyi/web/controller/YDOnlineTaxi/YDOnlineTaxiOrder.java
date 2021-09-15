package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.DriverInformation;
import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import com.ruoyi.YDOnlineTaxi.service.IDriverInformationService;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;
import com.ruoyi.YDOnlineTaxi.utils.OrderUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/YDOnlineTaxi/WxService/Order")
public class YDOnlineTaxiOrder {
    @Autowired
    private IOrderInformationService orderInformationService;

    @Autowired
    private IDriverInformationService driverInformationService;

    @Autowired(required = false)
    private OrderUtils orderUtils;

    @PostMapping(value ="/{oderId}")
    public AjaxResult acceptOrder(@PathVariable("orderId") String orderId,@RequestBody DriverInformation driverInformation){
        OrderInformation orderInformation = orderInformationService.selectOrderInformationByOrderId(orderId);
        String carType = orderInformation.getCarType();
        Date date = new Date();
        switch (driverInformation.getDriverCarType()){
            case "舒适型":
                if(!carType.equals("舒适型")){
                    return AjaxResult.error("notQualified");
                }
                else{
                    orderUtils.changeOrderStatus(orderInformation.getOrderId(),driverInformation.getDriverName(),driverInformation.getDriverPhoneNumber(),driverInformation.getDriverCarId(), date,orderInformation.getPoints());
                    orderInformation.setOrderStatus("已接单");
                    orderInformationService.insertOrderInformation(orderInformation);
                    return AjaxResult.success("success");
                }
            case "商务型":
                if(carType.equals("豪华型")){
                    return AjaxResult.error("notQualified");
                }
                else{
                    orderUtils.changeOrderStatus(orderInformation.getOrderId(),driverInformation.getDriverName(),driverInformation.getDriverPhoneNumber(),driverInformation.getDriverCarId(), date,orderInformation.getPoints());
                    orderInformation.setOrderStatus("已接单");
                    orderInformationService.insertOrderInformation(orderInformation);
                    return AjaxResult.success("success");
                }
            case "豪华型":
                if(carType.equals("商务型")){
                    return AjaxResult.error("notQualified");
                }
                else{
                    orderUtils.changeOrderStatus(orderInformation.getOrderId(),driverInformation.getDriverName(),driverInformation.getDriverPhoneNumber(),driverInformation.getDriverCarId(), date,orderInformation.getPoints());
                    orderInformation.setOrderStatus("已接单");
                    orderInformationService.insertOrderInformation(orderInformation);
                    return AjaxResult.success("success");
                }
            case "豪华商务型":
                orderUtils.changeOrderStatus(orderInformation.getOrderId(),driverInformation.getDriverName(),driverInformation.getDriverPhoneNumber(),driverInformation.getDriverCarId(), date,orderInformation.getPoints());
                orderInformation.setOrderStatus("已接单");
                orderInformationService.insertOrderInformation(orderInformation);
                return AjaxResult.success("success");
            default:
                return AjaxResult.error("notQualified");
        }
    }


}
