package com.ruoyi.YDOnlineTaxi.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 额外积分申请对象 audit_extra_order
 * 
 * @author PenPen
 * @date 2021-10-13
 */
public class AuditExtraOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键-订单号 */
    private String orderId;

    /** 出发地 */
    @Excel(name = "出发地")
    private String departure;

    /** 到达地 */
    @Excel(name = "到达地")
    private String destination;

    /** 额外积分 */
    @Excel(name = "额外积分")
    private BigDecimal extraOrderPoints;

    /** 备注
 */
    @Excel(name = "备注")
    private String notes;

    /** 用车类型 */
    @Excel(name = "用车类型")
    private String carType;

    /** 需求类型 */
    @Excel(name = "需求类型")
    private String requirementTypes;

    /** 照片1 */
    @Excel(name = "照片1")
    private String proofPhoto1;

    /** 照片2 */
    @Excel(name = "照片2")
    private String proofPhoto2;

    /** 用车时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "用车时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date transportTime;

    /** 拒绝理由 */
    @Excel(name = "拒绝理由")
    private String refuseReason;

    /** 额外积分审核状态 */
    @Excel(name = "额外积分审核状态")
    private String extraPointsStatus;

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
    public void setExtraOrderPoints(BigDecimal extraOrderPoints)
    {
        this.extraOrderPoints = extraOrderPoints;
    }

    public BigDecimal getExtraOrderPoints()
    {
        return extraOrderPoints;
    }
    public void setNotes(String notes) 
    {
        this.notes = notes;
    }

    public String getNotes() 
    {
        return notes;
    }
    public void setcarType(String carType) 
    {
        this.carType = carType;
    }

    public String getcarType() 
    {
        return carType;
    }
    public void setRequirementTypes(String requirementTypes) 
    {
        this.requirementTypes = requirementTypes;
    }

    public String getRequirementTypes() 
    {
        return requirementTypes;
    }
    public void setProofPhoto1(String proofPhoto1) 
    {
        this.proofPhoto1 = proofPhoto1;
    }

    public String getProofPhoto1() 
    {
        return proofPhoto1;
    }
    public void setProofPhoto2(String proofPhoto2) 
    {
        this.proofPhoto2 = proofPhoto2;
    }

    public String getProofPhoto2() 
    {
        return proofPhoto2;
    }
    public void setTransportTime(Date transportTime) 
    {
        this.transportTime = transportTime;
    }

    public Date getTransportTime() 
    {
        return transportTime;
    }
    public void setRefuseReason(String refuseReason) 
    {
        this.refuseReason = refuseReason;
    }

    public String getRefuseReason() 
    {
        return refuseReason;
    }
    public void setExtraPointsStatus(String extraPointsStatus) 
    {
        this.extraPointsStatus = extraPointsStatus;
    }

    public String getExtraPointsStatus() 
    {
        return extraPointsStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("departure", getDeparture())
            .append("destination", getDestination())
            .append("extraOrderPoints", getExtraOrderPoints())
            .append("notes", getNotes())
            .append("carType", getcarType())
            .append("requirementTypes", getRequirementTypes())
            .append("proofPhoto1", getProofPhoto1())
            .append("proofPhoto2", getProofPhoto2())
            .append("transportTime", getTransportTime())
            .append("refuseReason", getRefuseReason())
            .append("extraPointsStatus", getExtraPointsStatus())
            .toString();
    }
}
