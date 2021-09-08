package com.ruoyi.YDOnlineTaxi.service;

import com.ruoyi.YDOnlineTaxi.domain.OrderDetails;

public interface OrderDetailsService {


    int deleteByPrimaryKey(String orderId);

    int insert(OrderDetails record);

    int insertSelective(OrderDetails record);

    OrderDetails selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderDetails record);

    int updateByPrimaryKey(OrderDetails record);

}

