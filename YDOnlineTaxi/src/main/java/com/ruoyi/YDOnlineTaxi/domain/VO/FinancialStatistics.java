package com.ruoyi.YDOnlineTaxi.domain.VO;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 财务统计对象 financial_statistics
 * 
 * @author ruoyi
 * @date 2021-08-30
 */
public class FinancialStatistics extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 司机姓名
 */
    @Excel(name = "司机姓名")
    private String driverName;

    /** 手机号 */
    @Excel(name = "手机号")
    private String driverPhoneNumber;

    /** 总收入 */
    @Excel(name = "总收入")
    private BigDecimal totalIncome;

    /** 月收入 */
    @Excel(name = "月收入")
    private BigDecimal monthIncome;

    /** 未支付报酬 */
    @Excel(name = "未支付报酬")
    private BigDecimal unpaidRemuneration;

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
    public void setTotalIncome(BigDecimal totalIncome) 
    {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalIncome() 
    {
        return totalIncome;
    }
    public void setMonthIncome(BigDecimal monthIncome) 
    {
        this.monthIncome = monthIncome;
    }

    public BigDecimal getMonthIncome() 
    {
        return monthIncome;
    }
    public void setUnpaidRemuneration(BigDecimal unpaidRemuneration) 
    {
        this.unpaidRemuneration = unpaidRemuneration;
    }

    public BigDecimal getUnpaidRemuneration() 
    {
        return unpaidRemuneration;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("driverName", getDriverName())
            .append("driverPhoneNumber", getDriverPhoneNumber())
            .append("totalIncome", getTotalIncome())
            .append("monthIncome", getMonthIncome())
            .append("unpaidRemuneration", getUnpaidRemuneration())
            .toString();
    }
}
