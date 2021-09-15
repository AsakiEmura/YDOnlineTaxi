package com.ruoyi.YDOnlineTaxi.service;

import java.util.List;
import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import org.apache.ibatis.annotations.Param;

/**
 * 订单信息Service接口
 * 
 * @author ruoyi
 * @date 2021-09-08
 */
public interface IOrderInformationService 
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
     * 批量删除订单信息
     * 
     * @param orderIds 需要删除的订单信息主键集合
     * @return 结果
     */
    public int deleteOrderInformationByOrderIds(String[] orderIds);

    /**
     * 删除订单信息信息
     * 
     * @param orderId 订单信息主键
     * @return 结果
     */
    public int deleteOrderInformationByOrderId(String orderId);

    /**
     * 导入用户数据
     *
     * @param orderList       用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importOrder(List<OrderInformation> orderList, Boolean isUpdateSupport, String operName);


    public String randomID();

    public String selectOrderStatusByOrderId(@Param("orderId")String orderId);

    List<OrderInformation> selectAllByCarTypeLikeOrCarTypeLikeAndOrderStatusLike(@Param("likeCarType1")String likeCarType1,@Param("likeCarType2")String likeCarType2,@Param("likeOrderStatus")String likeOrderStatus);
}
