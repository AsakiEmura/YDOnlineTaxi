package com.ruoyi.YDOnlineTaxi.mapper;

import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;import org.apache.ibatis.annotations.Param;import java.util.List;

public interface OrderInformationMapper {

    /**
     * 查询订单信息
     *
     * @param orderId 订单信息主键
     * @return 订单信息
     */
    OrderInformation selectOrderInformationByOrderId(String orderId);

    /**
     * 查询订单信息列表
     *
     * @param orderInformation 订单信息
     * @return 订单信息集合
     */
    List<OrderInformation> selectOrderInformationList(OrderInformation orderInformation);

    /**
     * 新增订单信息
     *
     * @param orderInformation 订单信息
     * @return 结果
     */
    int insertOrderInformation(OrderInformation orderInformation);

    /**
     * 修改订单信息
     *
     * @param orderInformation 订单信息
     * @return 结果
     */
    int updateOrderInformation(OrderInformation orderInformation);

    /**
     * 删除订单信息
     *
     * @param orderId 订单信息主键
     * @return 结果
     */
    int deleteOrderInformationByOrderId(String orderId);

    /**
     * 批量删除订单信息
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteOrderInformationByOrderIds(String[] orderIds);

    String selectOrderStatusByOrderId(@Param("orderId") String orderId);

    List<OrderInformation> getOrderInformationListByConditions(@Param("likeCarType1") String likeCarType1, @Param("likeCarType2") String likeCarType2, @Param("likeCarType3") String likeCarType3, @Param("likeOrderStatus") String likeOrderStatus, @Param("minTransportTime") String minTransportTime, @Param("maxTransportTime") String maxTransportTime, @Param("note") String note);

    OrderInformation selectAllByOrderIdAndTransportTimeBetween(@Param("orderId") String orderId, @Param("minTransportTime") String minTransportTime, @Param("maxTransportTime") String maxTransportTime);

    List<OrderInformation> selectOrderByStatus(@Param("orderStatus") String orderStatus);

    List<OrderInformation> selectOrderByReceived(@Param("orderInformation") OrderInformation orderInformation, @Param("status") String[] status);

    List<OrderInformation> selectPersonalOrderByConditions(@Param("driverPhoneNumber") String driverPhoneNumber, @Param("minTransportTime") String minTransportTime, @Param("maxTransportTime") String maxTransportTime, @Param("likeOrderStatus1") String likeOrderStatus1, @Param("likeOrderStatus2") String likeOrderStatus2, @Param("likeOrderStatus3") String likeOrderStatus3, @Param("likeOrderStatus4") String likeOrderStatus4);

    List<OrderInformation> selectAllByDriverPhoneNumber(@Param("driverPhoneNumber") String driverPhoneNumber, @Param("minTransportTime") String minTransportTime, @Param("maxTransportTime") String maxTransportTime);

    List<String> selectOrderId();


}