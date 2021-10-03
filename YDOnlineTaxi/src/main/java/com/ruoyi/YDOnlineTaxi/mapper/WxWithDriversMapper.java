package com.ruoyi.YDOnlineTaxi.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.ruoyi.YDOnlineTaxi.domain.WxWithDrivers;

public interface WxWithDriversMapper {
    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(WxWithDrivers record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(WxWithDrivers record);

    public List<String> selectMachineId();


    public WxWithDrivers selectAllByMachineId(@Param("machineId") String machineId);

    public int updateByMachineId(WxWithDrivers updated);

    public List<String> selectMachineIdByDriverLevel(@Param("driverLevel")String driverLevel);

    public List<WxWithDrivers> selectAllByDriverLevel(@Param("driverLevel")String driverLevel);

    public String selectMachineIdByPhoneNumber(@Param("phoneNumber")String phoneNumber);

    public Integer countByPhoneNumber(@Param("phoneNumber")String phoneNumber);


}