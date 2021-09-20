package com.ruoyi.YDOnlineTaxi.service;

import com.ruoyi.YDOnlineTaxi.domain.OrderDetails;

import java.util.List;

public interface OrderDetailsService {


    int deleteByPrimaryKey(String orderId);

    int insert(OrderDetails record);

    int insertSelective(OrderDetails record);

    OrderDetails selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderDetails record);

    int updateByPrimaryKey(OrderDetails record);

    List<OrderDetails> selectAllByOrderId(String orderId);

    List<OrderDetails> selectAllByDriverPhoneNumber(String driverPhoneNumber);

    List<String> selectOrderIdByDriverPhoneNumber(String driverPhoneNumber);
}


