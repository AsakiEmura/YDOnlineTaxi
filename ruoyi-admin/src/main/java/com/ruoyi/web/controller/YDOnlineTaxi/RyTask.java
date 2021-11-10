package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.OrderDetails;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;
import com.ruoyi.YDOnlineTaxi.service.IPonitsStatisticsService;
import com.ruoyi.YDOnlineTaxi.service.OrderDetailsService;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("ryTaskTest")
public class RyTask {
    @Autowired
    private IPonitsStatisticsService ponitsStatisticsService;

    @Autowired
    private IOrderInformationService orderInformationService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void clearPoints(){
        ponitsStatisticsService.updateMonthPoints();
    }

    public void testTask(){
        ponitsStatisticsService.updateMonthPoints();
        System.out.println("现在时间为"+ new Date());
    }

    public void checkOrderDetails(){
        List<String> orderIdList1 = orderInformationService.selectOrderId();
        List<String> orderIdList2 = orderDetailsService.selectOrderId();
        for(String orderId1 : orderIdList1){
            for(String orderId2 : orderIdList2){
                if(!orderIdList1.contains(orderId2)){
                    orderDetailsService.deleteByPrimaryKey(orderId2);
                    logger.debug("order details 未删除干净,请尽快排查!");
                }
            }
            if (!orderIdList2.contains(orderId1)) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderId(orderId1);
                orderDetailsService.insert(orderDetails);
                logger.debug("order details 未入库,请尽快排查!");
            }
        }
    }
}
