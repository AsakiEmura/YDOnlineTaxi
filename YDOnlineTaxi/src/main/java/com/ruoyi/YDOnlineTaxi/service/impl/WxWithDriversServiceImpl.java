package com.ruoyi.YDOnlineTaxi.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ruoyi.YDOnlineTaxi.mapper.WxWithDriversMapper;
import com.ruoyi.YDOnlineTaxi.domain.WxWithDrivers;
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
    public List<String> selectMachineId() {
        return wxWithDriversMapper.selectMachineId();
    }

    @Override
    public WxWithDrivers selectAllByMachineId(String machineId) {
        return wxWithDriversMapper.selectAllByMachineId(machineId);
    }

    @Override
    public int updateByMachineId(WxWithDrivers updated) {
        return wxWithDriversMapper.updateByMachineId(updated);
    }

    @Override
    public List<String> selectMachineIdByDriverLevel(String driverLevel) {
        return wxWithDriversMapper.selectMachineIdByDriverLevel(driverLevel);
    }

    @Override
    public String selectMachineIdByPhoneNumber(String phoneNumber) {
        return wxWithDriversMapper.selectMachineIdByPhoneNumber(phoneNumber);
    }

    @Override
    public int countByPhoneNumber(String phoneNumber) {
        return wxWithDriversMapper.countByPhoneNumber(phoneNumber);
    }
}
