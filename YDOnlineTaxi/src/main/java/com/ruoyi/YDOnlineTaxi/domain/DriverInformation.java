package com.ruoyi.YDOnlineTaxi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 司机线上账户信息
对象 driver_information
 * 
 * @author ruoyi
 * @date 2021-09-08
 */
public class DriverInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 姓名 */
    @Excel(name = "姓名")
    private String driverName;

    /** 电话 */
    @Excel(name = "电话")
    private String driverPhoneNumber;

    /** 紧急电话 */
    @Excel(name = "紧急电话")
    private String driverEmergencyContactPhoneNumber;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String driverCarId;

    /** 车型 */
    @Excel(name = "车型")
    private String driverCarType;

    /** 汽车型号 */
    @Excel(name = "汽车型号")
    private String carModel;

    /** 车辆颜色 */
    @Excel(name = "车辆颜色")
    private String carColor;

    /** 已完成单数 */
    @Excel(name = "已完成单数")
    private String driverCompleteOrderNumber;

    /** 本月完成单数 */
    @Excel(name = "本月完成单数")
    private String driverCompleteOrderNumberMonthly;

    /** 等级 */
    @Excel(name = "等级")
    private String driverLevel;

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
    public void setDriverCarType(String driverCarType) 
    {
        this.driverCarType = driverCarType;
    }

    public String getDriverCarType() 
    {
        return driverCarType;
    }
    public void setCarModel(String carModel) 
    {
        this.carModel = carModel;
    }

    public String getCarModel() 
    {
        return carModel;
    }
    public void setCarColor(String carColor) 
    {
        this.carColor = carColor;
    }

    public String getCarColor() 
    {
        return carColor;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("driverName", getDriverName())
            .append("driverPhoneNumber", getDriverPhoneNumber())
            .append("driverEmergencyContactPhoneNumber", getDriverEmergencyContactPhoneNumber())
            .append("driverCarId", getDriverCarId())
            .append("driverCarType", getDriverCarType())
            .append("carModel", getCarModel())
            .append("carColor", getCarColor())
            .append("driverCompleteOrderNumber", getDriverCompleteOrderNumber())
            .append("driverCompleteOrderNumberMonthly", getDriverCompleteOrderNumberMonthly())
            .append("driverLevel", getDriverLevel())
            .toString();
    }
}
