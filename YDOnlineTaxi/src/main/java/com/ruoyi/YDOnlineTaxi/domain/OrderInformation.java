package com.ruoyi.YDOnlineTaxi.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单信息对象 order_information
 *
 * @author ruoyi
 * @date 2021-09-08
 */
public class OrderInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预约号 */
    @Excel(name = "预约号")
    private String orderId;

    /** 客户属性 */
    @Excel(name = "客户属性")
    private String passengerProperty;

    /** 客人姓名 */
    @Excel(name = "客人姓名")
    private String passenger;

    /** 性别 */
    @Excel(name = "性别")
    private String passengerSex;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String passengerPhone;

    /** 航班号 */
    @Excel(name = "航班号")
    private String flightNumber;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date creationDate;

    /** 出发时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "出发时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date transportTime;

    /** 上车地点 */
    @Excel(name = "上车地点")
    private String departure;

    /** 中途停靠 */
    @Excel(name = "中途停靠")
    private String intermediatePort;

    /** 下车地点 */
    @Excel(name = "下车地点")
    private String destination;

    /** 车型 */
    @Excel(name = "车型")
    private String carType;

    /** 司机信息 */
    @Excel(name = "司机信息")
    private String driverInformation;

    /** 司机积分 */
    @Excel(name = "司机积分")
    private Integer driverBase;

    /** 客户积分 */
    @Excel(name = "客户积分")
    private Integer passengerPrice;

    /** 停车积分 */
    @Excel(name = "停车积分")
    private Integer parkingFees;

    /** 高速积分 */
    @Excel(name = "高速积分")
    private Integer tollFees;

    /** 积分已入 */
    @Excel(name = "积分已入")
    private Integer points;

    /** 订单备注 */
    @Excel(name = "备注")
    private String note;

    /** 状态 */
    private String orderStatus;

    /** 拒绝理由 */
    private String refuseReason;

    /** 超时时间 */
    private Integer expireTime;

    /** 司机手机号 */
    private String driverPhoneNumber;

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderId()
    {
        return orderId;
    }
    public void setDeparture(String departure)
    {
        this.departure = departure;
    }

    public String getDeparture()
    {
        return departure;
    }
    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public String getDestination()
    {
        return destination;
    }
    public void setTransportTime(Date transportTime)
    {
        this.transportTime = transportTime;
    }

    public Date getTransportTime()
    {
        return transportTime;
    }

    public void setCarType(String carType)
    {
        this.carType = carType;
    }

    public String getCarType()
    {
        return carType;
    }
    public void setPassenger(String passenger)
    {
        this.passenger = passenger;
    }

    public String getPassenger()
    {
        return passenger;
    }
    public void setPassengerPhone(String passengerPhone)
    {
        this.passengerPhone = passengerPhone;
    }

    public String getPassengerPhone()
    {
        return passengerPhone;
    }
    public void setPoints(Integer points)
    {
        this.points = points;
    }

    public Integer getPoints()
    {
        return points;
    }
    public void setNote(String note)
    {
        this.note = note;
    }

    public String getNote()
    {
        return note;
    }
    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }
    public void setRefuseReason(String refuseReason)
    {
        this.refuseReason = refuseReason;
    }

    public String getRefuseReason()
    {
        return refuseReason;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    public String getPassengerProperty() {
        return passengerProperty;
    }

    public void setPassengerProperty(String passengerProperty) {
        this.passengerProperty = passengerProperty;
    }

    public String getPassengerSex() {
        return passengerSex;
    }

    public void setPassengerSex(String passengerSex) {
        this.passengerSex = passengerSex;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getIntermediatePort() {
        return intermediatePort;
    }

    public void setIntermediatePort(String intermediatePort) {
        this.intermediatePort = intermediatePort;
    }

    public String getDriverInformation() {
        return driverInformation;
    }

    public void setDriverInformation(String driverInformation) {
        this.driverInformation = driverInformation;
    }

    public Integer getDriverBase() {
        return driverBase;
    }

    public void setDriverBase(Integer driverBase) {
        this.driverBase = driverBase;
    }

    public Integer getPassengerPrice() {
        return passengerPrice;
    }

    public void setPassengerPrice(Integer passengerPrice) {
        this.passengerPrice = passengerPrice;
    }

    public Integer getParkingFees() {
        return parkingFees;
    }

    public void setParkingFees(Integer parkingFees) {
        this.parkingFees = parkingFees;
    }

    public Integer getTollFees() {
        return tollFees;
    }

    public void setTollFees(Integer tollFee) {
        this.tollFees = tollFee;
    }

    @Override
    public String toString() {
        return "OrderInformation{" +
                "orderId='" + orderId + '\'' +
                ", passengerProperty='" + passengerProperty + '\'' +
                ", passenger='" + passenger + '\'' +
                ", passengerSex='" + passengerSex + '\'' +
                ", passengerPhone='" + passengerPhone + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", transportTime=" + transportTime +
                ", departure='" + departure + '\'' +
                ", intermediatePort='" + intermediatePort + '\'' +
                ", destination='" + destination + '\'' +
                ", carType='" + carType + '\'' +
                ", driverInformation='" + driverInformation + '\'' +
                ", driverBase=" + driverBase +
                ", passengerPrice=" + passengerPrice +
                ", parkingFees=" + parkingFees +
                ", tollFee=" + tollFees +
                ", points=" + points +
                ", note='" + note + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", refuseReason='" + refuseReason + '\'' +
                ", expireTime=" + expireTime +
                ", driverPhoneNumber='" + driverPhoneNumber + '\'' +
                '}';
    }
}
