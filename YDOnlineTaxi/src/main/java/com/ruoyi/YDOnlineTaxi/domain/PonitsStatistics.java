package com.ruoyi.YDOnlineTaxi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 积分统计对象 ponits_statistics
 * 
 * @author ruoyi
 * @date 2021-09-08
 */
public class PonitsStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 姓名 */
    @Excel(name = "姓名")
    private String driverName;

    /** 手机号 */
    @Excel(name = "手机号")
    private String driverPhoneNumber;

    /** 未支付 */
    @Excel(name = "未支付")
    private BigDecimal notPaid;

    /** 已支付 */
    @Excel(name = "已支付")
    private BigDecimal paid;

    /** 未结算 */
    @Excel(name = "未结算")
    private BigDecimal noSettlement;

    /** 已结算 */
    @Excel(name = "已结算")
    private BigDecimal settlemented;

    /** 月积分 */
    @Excel(name = "月积分")
    private BigDecimal monthPoints;

    /** 总积分 */
    @Excel(name = "总积分")
    private BigDecimal totalPoints;

    /** 奖惩积分 */
    @Excel(name = "奖惩积分")
    private BigDecimal rewardsPunishmentPoints;

    public void setDriverName(String driverName) 
    {
        this.driverName = driverName;
    }

    public String getDriverName() 
    {
        return driverName;
    }
    public void setDriverPhoneNumber(String driverPhoneNumber) 
    {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    public String getDriverPhoneNumber() 
    {
        return driverPhoneNumber;
    }
    public void setNotPaid(BigDecimal notPaid)
    {
        this.notPaid = notPaid;
    }

    public BigDecimal getNotPaid()
    {
        return notPaid;
    }
    public void setPaid(BigDecimal paid)
    {
        this.paid = paid;
    }

    public BigDecimal getPaid()
    {
        return paid;
    }
    public void setNoSettlement(BigDecimal noSettlement)
    {
        this.noSettlement = noSettlement;
    }

    public BigDecimal getNoSettlement()
    {
        return noSettlement;
    }
    public void setSettlemented(BigDecimal settlemented)
    {
        this.settlemented = settlemented;
    }

    public BigDecimal getSettlemented()
    {
        return settlemented;
    }
    public void setMonthPoints(BigDecimal monthPoints)
    {
        this.monthPoints = monthPoints;
    }

    public BigDecimal getMonthPoints()
    {
        return monthPoints;
    }
    public void setTotalPoints(BigDecimal totalPoints)
    {
        this.totalPoints = totalPoints;
    }

    public BigDecimal getTotalPoints()
    {
        return totalPoints;
    }
    public void setRewardsPunishmentPoints(BigDecimal rewardsPunishmentPoints)
    {
        this.rewardsPunishmentPoints = rewardsPunishmentPoints;
    }

    public BigDecimal getRewardsPunishmentPoints()
    {
        return rewardsPunishmentPoints;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("driverName", getDriverName())
            .append("driverPhoneNumber", getDriverPhoneNumber())
            .append("notPaid", getNotPaid())
            .append("paid", getPaid())
            .append("noSettlement", getNoSettlement())
            .append("settlemented", getSettlemented())
            .append("monthPoints", getMonthPoints())
            .append("totalPoints", getTotalPoints())
            .append("rewardsPunishmentPoints", getRewardsPunishmentPoints())
            .toString();
    }
}
