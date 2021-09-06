package com.ruoyi.YDOnlineTaxi.mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;

/**
 * 订单信息Mapper接口
 * 
 * @author ruoyi
 * @date 2021-08-28
 */
public interface OrderInformationMapper 
{
    /**
     * 查询订单信息
     * 
     * @param orderId 订单信息主键
     * @return 订单信息
     */
    public OrderInformation selectOrderInformationByOrderId(String orderId);

    /**
     * 查询订单信息列表
     * 
     * @param orderInformation 订单信息
     * @return 订单信息集合
     */
    public List<OrderInformation> selectOrderInformationList(OrderInformation orderInformation);

    /**
     * 新增订单信息
     * 
     * @param orderInformation 订单信息
     * @return 结果
     */
    public int insertOrderInformation(OrderInformation orderInformation);

    /**
     * 修改订单信息
     * 
     * @param orderInformation 订单信息
     * @return 结果
     */
    public int updateOrderInformation(OrderInformation orderInformation);

    /**
     * 删除订单信息
     * 
     * @param orderId 订单信息主键
     * @return 结果
     */
    public int deleteOrderInformationByOrderId(String orderId);

    /**
     * 批量删除订单信息
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderInformationByOrderIds(String[] orderIds);

    public List<OrderInformation> selectAllByDriverPhoneNumber(@Param("driverPhoneNumber")String driverPhoneNumber);

}
