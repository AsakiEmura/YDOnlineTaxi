package com.ruoyi.YDOnlineTaxi.mapper;

import com.ruoyi.YDOnlineTaxi.domain.OrderDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDetailsMapper {
    /**
     * delete by primary key
     *
     * @param orderId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(String orderId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(OrderDetails record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(OrderDetails record);

    /**
     * select by primary key
     *
     * @param orderId primary key
     * @return object by primary key
     */
    OrderDetails selectByPrimaryKey(String orderId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OrderDetails record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OrderDetails record);

    List<OrderDetails> selectAllByOrderId(@Param("orderId") String orderId);

    List<OrderDetails> selectAllByDriverPhoneNumber(@Param("driverPhoneNumber") String driverPhoneNumber);

    List<String> selectOrderIdByDriverPhoneNumber(@Param("driverPhoneNumber") String driverPhoneNumber);

    List<String> selectOrderId();


}