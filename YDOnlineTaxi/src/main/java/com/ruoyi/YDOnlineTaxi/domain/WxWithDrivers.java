package com.ruoyi.YDOnlineTaxi.domain;

/**
    * 微信司机连接表
    */
public class WxWithDrivers {
    /**
    * 司机真实姓名
    */
    private String driverName;

    /**
    * 手机号
    */
    private String phoneNumber;

    /**
    * 微信用户唯一标识
    */
    private String machineId;


    /**
     * 司机等级
     */
    private String driverLevel;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getDriverLevel() {
        return driverLevel;
    }

    public void setDriverLevel(String driverLevel) {
        this.driverLevel = driverLevel;
    }

    @Override
    public String toString() {
        return "WxWithDrivers{" +
                "driverName='" + driverName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", machineId='" + machineId + '\''+
                '}';
    }
}