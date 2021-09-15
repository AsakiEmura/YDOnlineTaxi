package com.ruoyi.YDOnlineTaxi.utils;

import com.ruoyi.YDOnlineTaxi.domain.OrderDetails;
import com.ruoyi.YDOnlineTaxi.domain.PonitsStatistics;
import com.ruoyi.YDOnlineTaxi.service.IPonitsStatisticsService;
import com.ruoyi.YDOnlineTaxi.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class OrderUtils {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private IPonitsStatisticsService ponitsStatisticsService;

    public void changeOrderStatus(String orderId,String driverName,String driverPhoneNumber,String driverCarId,Date orderDateTime,Integer orderPonits){
        OrderDetails orderDetails = orderDetailsService.selectByPrimaryKey(orderId);
        orderDetails.setDriverName(driverName);
        orderDetails.setDriverPhoneNumber(driverPhoneNumber);
        orderDetails.setDriverCarId(driverCarId);
        orderDetails.setOrderTookTime(orderDateTime);

        PonitsStatistics ponitsStatistics = ponitsStatisticsService.selectPonitsStatisticsByDriverPhoneNumber(driverPhoneNumber);
        Long notpaid = ponitsStatistics.getNotPaid();
        Long new_notpaid = notpaid+orderPonits;
        ponitsStatistics.setNotPaid(new_notpaid);

        orderDetailsService.updateByPrimaryKey(orderDetails);
        ponitsStatisticsService.updatePonitsStatistics(ponitsStatistics);
    }

    public void changeOrderStatus(Date orderDateTime,String location){

    }
}
