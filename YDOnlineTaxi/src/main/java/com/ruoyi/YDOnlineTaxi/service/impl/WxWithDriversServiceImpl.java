package com.ruoyi.YDOnlineTaxi.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ruoyi.YDOnlineTaxi.mapper.WxWithDriversMapper;
import com.ruoyi.YDOnlineTaxi.domain.VO.WxWithDrivers;
import com.ruoyi.YDOnlineTaxi.service.WxWithDriversService;

import java.util.List;

@Service
public class WxWithDriversServiceImpl implements WxWithDriversService{

    @Resource
    private WxWithDriversMapper wxWithDriversMapper;

    @Override
    public int insert(WxWithDrivers record) {
        return wxWithDriversMapper.insert(record);
    }

    @Override
    public int insertSelective(WxWithDrivers record) {
        return wxWithDriversMapper.insertSelective(record);
    }

    @Override
    public List<String> selectOpenId() {
        return wxWithDriversMapper.selectOpenId();
    }

    @Override
    public List<String> selectOpenIdByPushTimesGreaterThan(@Param("minPushTimes")Integer minPushTimes) {
        return wxWithDriversMapper.selectOpenIdByPushTimesGreaterThan(minPushTimes);
    }

    @Override
    public List<WxWithDrivers> selectAllByPhoneNumberLikeAndDriverNameLikeAndOpenIdAndPushTimesGreaterThan(WxWithDrivers wxWithDrivers) {
        return wxWithDriversMapper.selectAllByPhoneNumberLikeAndDriverNameLikeAndOpenIdAndPushTimesGreaterThan(wxWithDrivers);
    }

    @Override
    public WxWithDrivers selectAllByOpenId(String openId) {
        return wxWithDriversMapper.selectAllByOpenId(openId);
    }

    @Override
    public int updateByOpenId(WxWithDrivers updated) {
        return wxWithDriversMapper.updateByOpenId(updated);
    }
}
