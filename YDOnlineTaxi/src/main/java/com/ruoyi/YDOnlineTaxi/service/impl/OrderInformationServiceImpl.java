package com.ruoyi.YDOnlineTaxi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.YDOnlineTaxi.mapper.OrderInformationMapper;
import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;

/**
 * 订单信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-08-28
 */
@Service
public class OrderInformationServiceImpl implements IOrderInformationService 
{
    @Autowired
    private OrderInformationMapper orderInformationMapper;

    /**
     * 查询订单信息
     * 
     * @param orderId 订单信息主键
     * @return 订单信息
     */
    @Override
    public OrderInformation selectOrderInformationByOrderId(String orderId)
    {
        return orderInformationMapper.selectOrderInformationByOrderId(orderId);
    }

    /**
     * 查询订单信息列表
     * 
     * @param orderInformation 订单信息
     * @return 订单信息
     */
    @Override
    public List<OrderInformation> selectOrderInformationList(OrderInformation orderInformation)
    {
        return orderInformationMapper.selectOrderInformationList(orderInformation);
    }

    /**
     * 新增订单信息
     * 
     * @param orderInformation 订单信息
     * @return 结果
     */
    @Override
    public int insertOrderInformation(OrderInformation orderInformation)
    {
        return orderInformationMapper.insertOrderInformation(orderInformation);
    }

    /**
     * 修改订单信息
     * 
     * @param orderInformation 订单信息
     * @return 结果
     */
    @Override
    public int updateOrderInformation(OrderInformation orderInformation)
    {
        return orderInformationMapper.updateOrderInformation(orderInformation);
    }

    /**
     * 批量删除订单信息
     * 
     * @param orderIds 需要删除的订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderInformationByOrderIds(String[] orderIds)
    {
        return orderInformationMapper.deleteOrderInformationByOrderIds(orderIds);
    }

    /**
     * 删除订单信息信息
     * 
     * @param orderId 订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderInformationByOrderId(String orderId)
    {
        return orderInformationMapper.deleteOrderInformationByOrderId(orderId);
    }
}
