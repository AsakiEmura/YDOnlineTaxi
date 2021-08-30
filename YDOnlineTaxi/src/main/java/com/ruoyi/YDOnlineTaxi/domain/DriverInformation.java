package com.ruoyi.YDOnlineTaxi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 司机账户信息对象 driver_information
 * 
 * @author ruoyi
 * @date 2021-08-30
 */
public class DriverInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 司机姓名 */
    @Excel(name = "司机姓名")
    private String driverName;

    /** 司机电话 */
    @Excel(name = "司机电话")
    private String driverPhoneNumber;

    /** 司机车型 */
    @Excel(name = "司机车型")
    private String driverCarType;

    /** 司机紧急联系电话 */
    @Excel(name = "司机紧急联系电话")
    private String driverEmergencyContactPhoneNumber;

    /** 司机车牌号 */
    @Excel(name = "司机车牌号")
    private String driverCarId;

    /** 司机完成单子数量 */
    @Excel(name = "司机完成单子数量")
    private String driverCompleteOrderNumber;

    /** 司机月度完成单子数量 */
    @Excel(name = "司机月度完成单子数量")
    private String driverCompleteOrderNumberMonthly;

    /** 司机等级 */
    @Excel(name = "司机等级")
    private String driverLevel;

    /** 司机评分 */
    @Excel(name = "司机评分")
    private String driverRateNumber;

    /** 汽车型号 */
    @Excel(name = "汽车型号")
    private String carModel;

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
    public void setDriverCarType(String driverCarType) 
    {
        this.driverCarType = driverCarType;
    }

    public String getDriverCarType() 
    {
        return driverCarType;
    }
    public void setDriverEmergencyContactPhoneNumber(String driverEmergencyContactPhoneNumber) 
    {
        this.driverEmergencyContactPhoneNumber = driverEmergencyContactPhoneNumber;
    }

    public String getDriverEmergencyContactPhoneNumber() 
    {
        return driverEmergencyContactPhoneNumber;
    }
    public void setDriverCarId(String driverCarId) 
    {
        this.driverCarId = driverCarId;
    }

    public String getDriverCarId() 
    {
        return driverCarId;
    }
    public void setDriverCompleteOrderNumber(String driverCompleteOrderNumber) 
    {
        this.driverCompleteOrderNumber = driverCompleteOrderNumber;
    }

    public String getDriverCompleteOrderNumber() 
    {
        return driverCompleteOrderNumber;
    }
    public void setDriverCompleteOrderNumberMonthly(String driverCompleteOrderNumberMonthly) 
    {
        this.driverCompleteOrderNumberMonthly = driverCompleteOrderNumberMonthly;
    }

    public String getDriverCompleteOrderNumberMonthly() 
    {
        return driverCompleteOrderNumberMonthly;
    }
    public void setDriverLevel(String driverLevel) 
    {
        this.driverLevel = driverLevel;
    }

    public String getDriverLevel() 
    {
        return driverLevel;
    }
    public void setDriverRateNumber(String driverRateNumber) 
    {
        this.driverRateNumber = driverRateNumber;
    }

    public String getDriverRateNumber() 
    {
        return driverRateNumber;
    }
    public void setCarModel(String carModel) 
    {
        this.carModel = carModel;
    }

    public String getCarModel() 
    {
        return carModel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("driverName", getDriverName())
            .append("driverPhoneNumber", getDriverPhoneNumber())
            .append("driverCarType", getDriverCarType())
            .append("driverEmergencyContactPhoneNumber", getDriverEmergencyContactPhoneNumber())
            .append("driverCarId", getDriverCarId())
            .append("driverCompleteOrderNumber", getDriverCompleteOrderNumber())
            .append("driverCompleteOrderNumberMonthly", getDriverCompleteOrderNumberMonthly())
            .append("driverLevel", getDriverLevel())
            .append("driverRateNumber", getDriverRateNumber())
            .append("carModel", getCarModel())
            .toString();
    }
}
