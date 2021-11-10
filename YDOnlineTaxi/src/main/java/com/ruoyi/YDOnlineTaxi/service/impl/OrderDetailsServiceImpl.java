package com.ruoyi.YDOnlineTaxi.service.impl;

import com.ruoyi.YDOnlineTaxi.domain.OrderDetails;
import com.ruoyi.YDOnlineTaxi.mapper.OrderDetailsMapper;
import com.ruoyi.YDOnlineTaxi.service.OrderDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Resource
    private OrderDetailsMapper orderDetailsMapper;

    @Override
    public int deleteByPrimaryKey(String orderId) {
        return orderDetailsMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public int insert(OrderDetails record) {
        return orderDetailsMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderDetails record) {
        return orderDetailsMapper.insertSelective(record);
    }

    @Override
    public OrderDetails selectByPrimaryKey(String orderId) {
        return orderDetailsMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderDetails record) {
        return orderDetailsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderDetails record) {
        return orderDetailsMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<OrderDetails> selectAllByOrderId(String orderId) {
        return orderDetailsMapper.selectAllByOrderId(orderId);
    }

    @Override
    public List<OrderDetails> selectAllByDriverPhoneNumber(String driverPhoneNumber) {
        return orderDetailsMapper.selectAllByDriverPhoneNumber(driverPhoneNumber);
    }

    @Override
    public List<String> selectOrderIdByDriverPhoneNumber(String driverPhoneNumber) {
        return orderDetailsMapper.selectOrderIdByDriverPhoneNumber(driverPhoneNumber);
    }

    @Override
    public List<String> selectOrderId() {
        return orderDetailsMapper.selectOrderId();
    }
}
