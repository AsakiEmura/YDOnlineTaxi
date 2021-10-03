package com.ruoyi.YDOnlineTaxi.service;

import com.ruoyi.YDOnlineTaxi.domain.WxWithDrivers;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxWithDriversService{

    public int insert(WxWithDrivers record);

    public int insertSelective(WxWithDrivers record);

    public List<String> selectMachineId();

    public WxWithDrivers selectAllByMachineId(@Param("machineId")String machineId);

    public int updateByMachineId(WxWithDrivers updated);

    public List<String> selectMachineIdByDriverLevel(String driverLevel);

    public String selectMachineIdByPhoneNumber(String phoneNumber);

    public int countByPhoneNumber(String phoneNumber);
}
