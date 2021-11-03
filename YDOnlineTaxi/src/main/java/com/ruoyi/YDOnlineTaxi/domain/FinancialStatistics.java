package com.ruoyi.YDOnlineTaxi.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 财务统计对象 financial_statistics
 * 
 * @author Asaki
 * @date 2021-11-03
 */
public class FinancialStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预约号 */
    @Excel(name = "预约号")
    private String orderId;

    /** 客人姓名 */
    @Excel(name = "客人姓名")
    private String passenger;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String passengerPhone;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date dateOf;

    /** 上车地点 */
    @Excel(name = "上车地点")
    private String departure;

    /** 下车地点 */
    @Excel(name = "下车地点")
    private String destination;

    /** 车型 */
    @Excel(name = "车型")
    private String carType;

    /** 司机信息 */
    @Excel(name = "司机信息")
    private String driverInformation;

    /** 司机基价 */
    @Excel(name = "司机基价")
    private BigDecimal driverBase;

    /** 合同价 */
    @Excel(name = "合同价")
    private BigDecimal contractPrice;

    /** 停车费 */
    @Excel(name = "停车费")
    private BigDecimal parkingFees;

    /** 已收款 */
    @Excel(name = "已收现金")
    private BigDecimal receivedCash;

    /** 应付司机 */
    @Excel(name = "应付司机")
    private BigDecimal accountsPayable;

    /** 应收客户 */
    @Excel(name = "应收客户")
    private BigDecimal accountsReceivable;

    /** 利润 */
    @Excel(name = "利润")
    private BigDecimal profits;

    /** 行程类型 */
    @Excel(name = "行程类型")
    private String tripType;

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
    public void setCreationDate(Date creationDate) 
    {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() 
    {
        return creationDate;
    }
    public void setDateOf(Date dateOf) 
    {
        this.dateOf = dateOf;
    }

    public Date getDateOf() 
    {
        return dateOf;
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
    public void setCarType(String carType) 
    {
        this.carType = carType;
    }

    public String getCarType() 
    {
        return carType;
    }
    public void setDriverInformation(String driverInformation) 
    {
        this.driverInformation = driverInformation;
    }

    public String getDriverInformation() 
    {
        return driverInformation;
    }
    public void setDriverBase(BigDecimal driverBase) 
    {
        this.driverBase = driverBase;
    }

    public BigDecimal getDriverBase() 
    {
        return driverBase;
    }
    public void setContractPrice(BigDecimal contractPrice) 
    {
        this.contractPrice = contractPrice;
    }

    public BigDecimal getContractPrice() 
    {
        return contractPrice;
    }
    public void setParkingFees(BigDecimal parkingFees) 
    {
        this.parkingFees = parkingFees;
    }

    public BigDecimal getParkingFees() 
    {
        return parkingFees;
    }
    public void setReceivedCash(BigDecimal receivedCash) 
    {
        this.receivedCash = receivedCash;
    }

    public BigDecimal getReceivedCash() 
    {
        return receivedCash;
    }
    public void setAccountsPayable(BigDecimal accountsPayable) 
    {
        this.accountsPayable = accountsPayable;
    }

    public BigDecimal getAccountsPayable() 
    {
        return accountsPayable;
    }
    public void setAccountsReceivable(BigDecimal accountsReceivable) 
    {
        this.accountsReceivable = accountsReceivable;
    }

    public BigDecimal getAccountsReceivable() 
    {
        return accountsReceivable;
    }
    public void setProfits(BigDecimal profits) 
    {
        this.profits = profits;
    }

    public BigDecimal getProfits() 
    {
        return profits;
    }
    public void setTripType(String tripType) 
    {
        this.tripType = tripType;
    }

    public String getTripType() 
    {
        return tripType;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("passenger", getPassenger())
            .append("passengerPhone", getPassengerPhone())
            .append("creationDate", getCreationDate())
            .append("dateOf", getDateOf())
            .append("departure", getDeparture())
            .append("destination", getDestination())
            .append("carType", getCarType())
            .append("driverInformation", getDriverInformation())
            .append("driverBase", getDriverBase())
            .append("contractPrice", getContractPrice())
            .append("parkingFees", getParkingFees())
            .append("receivedCash", getReceivedCash())
            .append("accountsPayable", getAccountsPayable())
            .append("accountsReceivable", getAccountsReceivable())
            .append("profits", getProfits())
            .append("tripType", getTripType())
            .toString();
    }
}
