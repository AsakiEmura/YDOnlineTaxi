package com.ruoyi.YDOnlineTaxi.domain;

import java.math.BigDecimal;

/**
    * 到达审核信息
    */
public class ArrivalAuditInformation {
    /**
    * 订单号
    */
    private String orderId;

    /**
    * 额外订单积分
    */
    private BigDecimal extraOrderPoints;

    /**
    * 证明照片1
    */
    private String proofPhoto1;

    /**
    * 证明照片2
    */
    private String proofPhoto2;

    /**
    * 备注
    */
    private String notes;

    /**
    * 拒绝理由
    */
    private String refuseReason;

    /**
    * 额外积分申请状态
    */
    private String extraPointsStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getExtraOrderPoints() {
        return extraOrderPoints;
    }

    public void setExtraOrderPoints(BigDecimal extraOrderPoints) {
        this.extraOrderPoints = extraOrderPoints;
    }

    public String getProofPhoto1() {
        return proofPhoto1;
    }

    public void setProofPhoto1(String proofPhoto1) {
        this.proofPhoto1 = proofPhoto1;
    }

    public String getProofPhoto2() {
        return proofPhoto2;
    }

    public void setProofPhoto2(String proofPhoto2) {
        this.proofPhoto2 = proofPhoto2;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getExtraPointsStatus() {
        return extraPointsStatus;
    }

    public void setExtraPointsStatus(String extraPointsStatus) {
        this.extraPointsStatus = extraPointsStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", extraOrderPoints=").append(extraOrderPoints);
        sb.append(", proofPhoto1=").append(proofPhoto1);
        sb.append(", proofPhoto2=").append(proofPhoto2);
        sb.append(", notes=").append(notes);
        sb.append(", refuseReason=").append(refuseReason);
        sb.append(", extraPointsStatus=").append(extraPointsStatus);
        sb.append("]");
        return sb.toString();
    }
}