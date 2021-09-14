package com.ruoyi.YDOnlineTaxi.domain.VO;

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
    private String openId;

    /**
    * 可推送次数
    */
    private Integer pushTimes;

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getPushTimes() {
        return pushTimes;
    }

    public void setPushTimes(Integer pushTimes) {
        this.pushTimes = pushTimes;
    }

    @Override
    public String toString() {
        return "WxWithDrivers{" +
                "driverName='" + driverName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", openId='" + openId + '\'' +
                ", pushTimes=" + pushTimes +
                '}';
    }
}