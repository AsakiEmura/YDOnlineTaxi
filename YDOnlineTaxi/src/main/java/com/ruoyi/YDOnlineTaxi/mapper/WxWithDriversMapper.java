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

    public List<String> selectOpenId();

    public List<String> selectOpenIdByPushTimesGreaterThan(@Param("minPushTimes") Integer minPushTimes);

    public WxWithDrivers selectAllByOpenId(@Param("openId") String openId);

    public int updateByOpenId(WxWithDrivers updated);

    public List<String> selectOpenIdByDriverLevel(@Param("driverLevel")String driverLevel);

    public List<WxWithDrivers> selectAllByDriverLevel(@Param("driverLevel")String driverLevel);

    public String selectOpenIdByPhoneNumber(@Param("phoneNumber")String phoneNumber);

    public int addPushTimeByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    public Integer countByPhoneNumber(@Param("phoneNumber")String phoneNumber);


}