package com.ruoyi.YDOnlineTaxi.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.ruoyi.YDOnlineTaxi.domain.VO.WxWithDrivers;

public interface WxWithDriversMapper {
    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(WxWithDrivers record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(WxWithDrivers record);

    public List<String> selectOpenId();

    public List<String> selectOpenIdByPushTimesGreaterThan(@Param("minPushTimes")Integer minPushTimes);

    public List<WxWithDrivers> selectAllByPhoneNumberLikeAndDriverNameLikeAndOpenIdAndPushTimesGreaterThan(WxWithDrivers wxWithDrivers);

    public WxWithDrivers selectAllByOpenId(@Param("openId")String openId);

    public int updateByOpenId(WxWithDrivers updated);




}