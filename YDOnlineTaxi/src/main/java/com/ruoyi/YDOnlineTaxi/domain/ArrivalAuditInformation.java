package com.ruoyi.YDOnlineTaxi.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 到达审核信息对象 arrival_audit_information
 *
 * @author ruoyi
 * @date 2021-09-19
 */
public class ArrivalAuditInformation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private String orderid;

    /**
     * 额外订单积分
     */
    @Excel(name = "额外订单积分")
    private Long extraOrderPoints;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String notes;

    /**
     * 证明照片1
     */
    @Excel(name = "证明照片1")
    private String proofPhoto1;

    /**
     * 证明照片2
     */
    @Excel(name = "证明照片2")
    private String proofPhoto2;

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setExtraOrderPoints(Long extraOrderPoints) {
        this.extraOrderPoints = extraOrderPoints;
    }

    public Long getExtraOrderPoints() {
        return extraOrderPoints;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setProofPhoto1(String proofPhoto1) {
        this.proofPhoto1 = proofPhoto1;
    }

    public String getProofPhoto1() {
        return proofPhoto1;
    }

    public void setProofPhoto2(String proofPhoto2) {
        this.proofPhoto2 = proofPhoto2;
    }

    public String getProofPhoto2() {
        return proofPhoto2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orderid", getOrderid())
                .append("extraOrderPoints", getExtraOrderPoints())
                .append("notes", getNotes())
                .append("proofPhoto1", getProofPhoto1())
                .append("proofPhoto2", getProofPhoto2())
                .toString();
    }
}
