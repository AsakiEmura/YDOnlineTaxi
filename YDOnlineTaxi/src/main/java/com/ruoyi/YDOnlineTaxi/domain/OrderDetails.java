package com.ruoyi.YDOnlineTaxi.domain;

import java.util.Date;

/**
    * 订单详细信息
    */
public class OrderDetails {
    /**
    * 订单编号
    */
    private String orderId;

    /**
    * 司机姓名
    */
    private String driverName;

    /**
    * 司机手机
    */
    private String driverPhoneNumber;

    /**
    * 司机车牌号
    */
    private String driverCarId;

    /**
    * 订单接单时间
    */
    private Date orderTookTime;

    /**
    * 出发地点
    */
    private String departureLocation;

    /**
    * 出发时间
    */
    private Date departureTime;

    /**
    * 到达地点
    */
    private String arrivalLocation;

    /**
    * 到达时间
    */
    private Date arrivalRime;

    /**
    * 结单时间
    */
    private Date orderFinishTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    public String getDriverCarId() {
        return driverCarId;
    }

    public void setDriverCarId(String driverCarId) {
        this.driverCarId = driverCarId;
    }

    public Date getOrderTookTime() {
        return orderTookTime;
    }

    public void setOrderTookTime(Date orderTookTime) {
        this.orderTookTime = orderTookTime;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public Date getArrivalRime() {
        return arrivalRime;
    }

    public void setArrivalRime(Date arrivalRime) {
        this.arrivalRime = arrivalRime;
    }

    public Date getOrderFinishTime() {
        return orderFinishTime;
    }

    public void setOrderFinishTime(Date orderFinishTime) {
        this.orderFinishTime = orderFinishTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", driverName=").append(driverName);
        sb.append(", driverPhoneNumber=").append(driverPhoneNumber);
        sb.append(", driverCarId=").append(driverCarId);
        sb.append(", orderTookTime=").append(orderTookTime);
        sb.append(", departureLocation=").append(departureLocation);
        sb.append(", departureTime=").append(departureTime);
        sb.append(", arrivalLocation=").append(arrivalLocation);
        sb.append(", arrivalRime=").append(arrivalRime);
        sb.append(", orderFinishTime=").append(orderFinishTime);
        sb.append("]");
        return sb.toString();
    }
}