package com.ruoyi.YDOnlineTaxi.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ruoyi.YDOnlineTaxi.mapper.ArrivalAuditInformationMapper;
import com.ruoyi.YDOnlineTaxi.domain.ArrivalAuditInformation;
import com.ruoyi.YDOnlineTaxi.service.ArrivalAuditInformationService;
@Service
public class ArrivalAuditInformationServiceImpl implements ArrivalAuditInformationService{

    @Resource
    private ArrivalAuditInformationMapper arrivalAuditInformationMapper;

    @Override
    public int deleteByPrimaryKey(String orderId) {
        return arrivalAuditInformationMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public int insert(ArrivalAuditInformation record) {
        return arrivalAuditInformationMapper.insert(record);
    }

    @Override
    public int insertSelective(ArrivalAuditInformation record) {
        return arrivalAuditInformationMapper.insertSelective(record);
    }

    @Override
    public ArrivalAuditInformation selectByPrimaryKey(String orderId) {
        return arrivalAuditInformationMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public int updateByPrimaryKeySelective(ArrivalAuditInformation record) {
        return arrivalAuditInformationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ArrivalAuditInformation record) {
        return arrivalAuditInformationMapper.updateByPrimaryKey(record);
    }

}
