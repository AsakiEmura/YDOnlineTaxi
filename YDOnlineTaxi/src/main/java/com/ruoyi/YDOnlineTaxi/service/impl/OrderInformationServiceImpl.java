package com.ruoyi.YDOnlineTaxi.service.impl;

import java.util.List;

import com.ruoyi.YDOnlineTaxi.constant.enums.OrderStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.YDOnlineTaxi.mapper.OrderInformationMapper;
import com.ruoyi.YDOnlineTaxi.domain.VO.OrderInformation;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;

/**
 * 订单信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-09-08
 */
@Service
public class OrderInformationServiceImpl implements IOrderInformationService 
{
    private static final Logger log = LoggerFactory.getLogger(OrderInformationServiceImpl.class);

    @Autowired(required = false)
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

    /**
     * 导入用户数据
     *
     * @param orderList 订单数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importOrder(List<OrderInformation> orderList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(orderList) || orderList.size() == 0)
        {
            throw new ServiceException("导入订单数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (OrderInformation order : orderList)
        {
            try
            {
                if(order.getOrderId() != "")
                {
                    // 验证是否存在这个用户
                    OrderInformation o = orderInformationMapper.selectOrderInformationByOrderId(order.getOrderId());
                    if (StringUtils.isNull(o))
                    {
                        order.setCreateBy(operName);
                        order.setTransportTime(order.getTransportTime());
                        order.setOrderStatus(OrderStatus.WAIT_DISPATCHED.toString());
                        this.insertOrderInformation(order);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、订单信息 " + order.getOrderId() + " 导入成功");
                    }
                    else if (isUpdateSupport)
                    {
                        order.setUpdateBy(operName);
                        order.setTransportTime(order.getTransportTime());
                        order.setOrderStatus(OrderStatus.WAIT_DISPATCHED.toString());
                        this.insertOrderInformation(order);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、订单信息 " + order.getOrderId() + " 更新成功");
                    }
                    else
                    {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、订单信息 " + order.getOrderId() + " 已存在");
                    }
                }
                else
                {
                    order.setOrderId(randomID());
                    order.setCreateBy(operName);
                    order.setOrderStatus(OrderStatus.WAIT_DISPATCHED.toString());
                    order.setTransportTime(order.getTransportTime());
                    this.insertOrderInformation(order);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、订单信息 " + order.getOrderId() + " 导入成功");
                }

            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、订单信息 " + order.getOrderId() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }


    @Override
    public String randomID() {
        String id = UUID.fastUUID().toString().trim().replace("-","");
        return id;
    }

    @Override
    public String selectOrderStatusByOrderId(String orderId) {
        return orderInformationMapper.selectOrderStatusByOrderId(orderId);
    }
}
