package com.ruoyi.YDOnlineTaxi.service;

import com.ruoyi.YDOnlineTaxi.domain.ArrivalAuditInformation;

import java.util.List;

public interface ArrivalAuditInformationService{
    int deleteByPrimaryKey(String orderId);

    int insert(ArrivalAuditInformation record);

    int insertSelective(ArrivalAuditInformation record);

    List<ArrivalAuditInformation> selectByPrimaryKey(String orderId);

    List<ArrivalAuditInformation> selectByPrimaryKeyHaveExtraNumber(String orderId);

    int updateByPrimaryKeySelective(ArrivalAuditInformation record);

    int updateByPrimaryKey(ArrivalAuditInformation record);

}
