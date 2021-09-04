package com.ruoyi.YDOnlineTaxi.domain;

import com.ruoyi.YDOnlineTaxi.utils.OrderStatus;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 订单信息对象 order_information
 *
 * @author ruoyi
 * @date 2021-08-28
 */
public class OrderInformation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 出发地
     */
    @Excel(name = "出发地")
    private String departure;

    /**
     * 到达地
     */
    @Excel(name = "到达地")
    private String destination;

    /**
     * 乘客称呼
     */
    @Excel(name = "乘客称呼")
    private String passenger;

    /**
     * 乘客联系手机
     */
    @Excel(name = "乘客联系手机")
    private String passengerPhone;

    /**
     * 用车时间
     */
    @Excel(name = "用车时间")
    private String transportTime;

    /**
     * 需求类型
     */
    @Excel(name = "需求类型")
    private String requirementTypes;

    /**
     * 用车类型
     */
    @Excel(name = "用车类型")
    private String carType;

    /**
     * 订单价格
     */
    @Excel(name = "订单价格")
    private String remuneration;

    /**
     * 订单备注
     */
    @Excel(name = "订单备注")
    private String note;

    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
    private String orderId;

    /**
     * 订单状态
     */
    @Excel(name = "订单状态")
    private String orderStatus;

    /**
     * 司机手机号
     */
    @Excel(name = "司机手机号")
    private String driverPhoneNumber;

    /**
     * 接单时间
     */
    @Excel(name = "接单时间")
    private String orderTime;

    /**
     * 结单时间
     */
    @Excel(name = "结单时间")
    private String statementTime;

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }


    public void setTransportTime(String transportTime) {

        this.transportTime = transportTime;
    }


    public String getTransportTime() {
        return transportTime;
    }

    public void setRequirementTypes(String requirementTypes) {
        this.requirementTypes = requirementTypes;
    }

    public String getRequirementTypes() {
        return requirementTypes;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarType() {
        return carType;
    }

    public void setRemuneration(String remuneration) {
        this.remuneration = remuneration;
    }

    public String getRemuneration() {
        return remuneration;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setStatementTime(String statementTime) {
        this.statementTime = statementTime;
    }

    public String getStatementTime() {
        return statementTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("departure", getDeparture())
                .append("destination", getDestination())
                .append("passenger", getPassenger())
                .append("passengerPhone", getPassengerPhone())
                .append("transportTime", getTransportTime())
                .append("requirementTypes", getRequirementTypes())
                .append("carType", getCarType())
                .append("remuneration", getRemuneration())
                .append("note", getNote())
                .append("orderId", getOrderId())
                .append("orderStatus", getOrderStatus())
                .append("driverPhoneNumber", getDriverPhoneNumber())
                .append("orderTime", getOrderTime())
                .append("statementTime", getStatementTime())
                .toString();
    }
}
