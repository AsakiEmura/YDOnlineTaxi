package com.ruoyi.YDOnlineTaxi.domain.VO;

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

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderId;

    /** 出发地 */
    @Excel(name = "出发地")
    private String departure;

    /** 到达地 */
    @Excel(name = "到达地")
    private String destination;

    /** 用车时间 */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    @Excel(name = "用车时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm")
    private Date transportTime;

    /** 需求类型 */
    @Excel(name = "需求类型")
    private String requirementTypes;

    /** 用车类型 */
    @Excel(name = "用车类型")
    private String carType;

    /** 乘客称呼 */
    @Excel(name = "乘客称呼")
    private String passenger;

    /** 乘客手机 */
    @Excel(name = "乘客手机")
    private String passengerPhone;

    /** 积分 */
    @Excel(name = "积分")
    private Integer points;

    /** 订单备注 */
    @Excel(name = "订单备注")
    private String note;

    /** 状态 */
    @Excel(name = "状态")
    private String orderStatus;

    /** 拒绝理由 */
    @Excel(name = "拒绝理由")
    private String refuseReason;

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
    public void setRequirementTypes(String requirementTypes) 
    {
        this.requirementTypes = requirementTypes;
    }

    public String getRequirementTypes() 
    {
        return requirementTypes;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("departure", getDeparture())
            .append("destination", getDestination())
            .append("transportTime", getTransportTime())
            .append("requirementTypes", getRequirementTypes())
            .append("carType", getCarType())
            .append("passenger", getPassenger())
            .append("passengerPhone", getPassengerPhone())
            .append("points", getPoints())
            .append("note", getNote())
            .append("orderStatus", getOrderStatus())
            .append("refuseReason", getRefuseReason())
            .toString();
    }
}
