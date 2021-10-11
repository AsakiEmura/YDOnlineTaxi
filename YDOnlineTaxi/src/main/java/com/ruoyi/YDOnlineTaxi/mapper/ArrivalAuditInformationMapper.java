package com.ruoyi.YDOnlineTaxi.mapper;

import com.ruoyi.YDOnlineTaxi.domain.ArrivalAuditInformation;

public interface ArrivalAuditInformationMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(ArrivalAuditInformation record);

    int insertSelective(ArrivalAuditInformation record);

    ArrivalAuditInformation selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(ArrivalAuditInformation record);

    int updateByPrimaryKey(ArrivalAuditInformation record);
}