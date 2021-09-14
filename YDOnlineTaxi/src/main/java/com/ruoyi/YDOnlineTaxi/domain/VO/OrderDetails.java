package com.ruoyi.YDOnlineTaxi.domain.VO;

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
     * 到达时间
     */
    private Date arrivalRime;

    /**
     * 订单接单时间
     */
    private Date orderTookTime;

    /**
     * 到达地点
     */
    private String arrivalLocation;

    /**
     * 司机车牌号
     */
    private String driverLicensePlateNumber;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 结单时间
     */
    private Date orderFinishTime;

    /**
     * 出发时间
     */
    private Date departureTime;

    /**
     * 出发地点
     */
    private String departureLocation;

    /**
     * 司机手机
     */
    private String driverPhoneNumber;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getArrivalRime() {
        return arrivalRime;
    }

    public void setArrivalRime(Date arrivalRime) {
        this.arrivalRime = arrivalRime;
    }

    public Date getOrderTookTime() {
        return orderTookTime;
    }

    public void setOrderTookTime(Date orderTookTime) {
        this.orderTookTime = orderTookTime;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public String getDriverLicensePlateNumber() {
        return driverLicensePlateNumber;
    }

    public void setDriverLicensePlateNumber(String driverLicensePlateNumber) {
        this.driverLicensePlateNumber = driverLicensePlateNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Date getOrderFinishTime() {
        return orderFinishTime;
    }

    public void setOrderFinishTime(Date orderFinishTime) {
        this.orderFinishTime = orderFinishTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", arrivalRime=" + arrivalRime +
                ", orderTookTime=" + orderTookTime +
                ", arrivalLocation='" + arrivalLocation + '\'' +
                ", driverLicensePlateNumber='" + driverLicensePlateNumber + '\'' +
                ", driverName='" + driverName + '\'' +
                ", orderFinishTime=" + orderFinishTime +
                ", departureTime=" + departureTime +
                ", departureLocation='" + departureLocation + '\'' +
                ", driverPhoneNumber='" + driverPhoneNumber + '\'' +
                '}';
    }
}