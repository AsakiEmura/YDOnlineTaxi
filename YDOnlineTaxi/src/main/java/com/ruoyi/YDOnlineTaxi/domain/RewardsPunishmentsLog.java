package com.ruoyi.YDOnlineTaxi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 积分奖惩日志对象 rewards_punishments_log
 *
 * @author ruoyi
 * @date 2021-09-16
 */
public class RewardsPunishmentsLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String driverName;

    /**
     * 奖惩原因
     */
    @Excel(name = "奖惩原因")
    private String rpReason;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date operatingTime;

    /**
     * 操作人
     */
    @Excel(name = "操作人")
    private String operatingPeople;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setRpReason(String rpReason) {
        this.rpReason = rpReason;
    }

    public String getRpReason() {
        return rpReason;
    }

    public void setOperatingTime(Date operatingTime) {
        this.operatingTime = operatingTime;
    }

    public Date getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingPeople(String operatingPeople) {
        this.operatingPeople = operatingPeople;
    }

    public String getOperatingPeople() {
        return operatingPeople;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("phoneNumber", getPhoneNumber())
                .append("driverName", getDriverName())
                .append("rpReason", getRpReason())
                .append("operatingTime", getOperatingTime())
                .append("operatingPeople", getOperatingPeople())
                .toString();
    }
}
