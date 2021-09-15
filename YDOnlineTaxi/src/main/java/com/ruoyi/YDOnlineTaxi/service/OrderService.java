package com.ruoyi.YDOnlineTaxi.service;

import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import java.util.Map;

public interface OrderService {
    //创建订单
    OrderInformation create();

    //发起支付
    OrderInformation dispatch(String orderId);

    //订单发货
    OrderInformation audit(String orderId);

    //订单收货
    OrderInformation payment(String orderId);

    //获取所有订单信息
    Map<String, OrderInformation> getOrders();
}