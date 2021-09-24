package com.ruoyi.YDOnlineTaxi.service;

import com.ruoyi.YDOnlineTaxi.domain.WxWithDrivers;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxWithDriversService{

    public int insert(WxWithDrivers record);

    public int insertSelective(WxWithDrivers record);

    public List<String> selectOpenId();

    public List<String> selectOpenIdByPushTimesGreaterThan(@Param("minPushTimes")Integer minPushTimes);

    public WxWithDrivers selectAllByOpenId(@Param("openId")String openId);

    public int updateByOpenId(WxWithDrivers updated);

    public List<String> selectOpenIdByDriverLevel(String driverLevel);

    public String selectOpenIdByPhoneNumber(String phoneNumber);

    public int addPushTimeByPhoneNumber(String phoneNumber);
}
