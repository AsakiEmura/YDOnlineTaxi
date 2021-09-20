package com.ruoyi.YDOnlineTaxi.service.impl;

import com.ruoyi.YDOnlineTaxi.domain.ArrivalAuditInformation;
import com.ruoyi.YDOnlineTaxi.mapper.ArrivalAuditInformationMapper;
import com.ruoyi.YDOnlineTaxi.service.IArrivalAuditInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 到达审核信息Service业务层处理
 *
 * @author ruoyi
 * @date 2021-09-19
 */
@Service
public class ArrivalAuditInformationServiceImpl implements IArrivalAuditInformationService {
    @Autowired(required = false)
    private ArrivalAuditInformationMapper arrivalAuditInformationMapper;

    /**
     * 查询到达审核信息
     *
     * @param orderid 到达审核信息主键
     * @return 到达审核信息
     */
    @Override
    public ArrivalAuditInformation selectArrivalAuditInformationByOrderid(String orderid) {
        return arrivalAuditInformationMapper.selectArrivalAuditInformationByOrderid(orderid);
    }

    /**
     * 查询到达审核信息列表
     *
     * @param arrivalAuditInformation 到达审核信息
     * @return 到达审核信息
     */
    @Override
    public List<ArrivalAuditInformation> selectArrivalAuditInformationList(ArrivalAuditInformation arrivalAuditInformation) {
        return arrivalAuditInformationMapper.selectArrivalAuditInformationList(arrivalAuditInformation);
    }

    /**
     * 新增到达审核信息
     *
     * @param arrivalAuditInformation 到达审核信息
     * @return 结果
     */
    @Override
    public int insertArrivalAuditInformation(ArrivalAuditInformation arrivalAuditInformation) {
        return arrivalAuditInformationMapper.insertArrivalAuditInformation(arrivalAuditInformation);
    }

    /**
     * 修改到达审核信息
     *
     * @param arrivalAuditInformation 到达审核信息
     * @return 结果
     */
    @Override
    public int updateArrivalAuditInformation(ArrivalAuditInformation arrivalAuditInformation) {
        return arrivalAuditInformationMapper.updateArrivalAuditInformation(arrivalAuditInformation);
    }

    /**
     * 批量删除到达审核信息
     *
     * @param orderids 需要删除的到达审核信息主键
     * @return 结果
     */
    @Override
    public int deleteArrivalAuditInformationByOrderids(String[] orderids) {
        return arrivalAuditInformationMapper.deleteArrivalAuditInformationByOrderids(orderids);
    }

    /**
     * 删除到达审核信息信息
     *
     * @param orderid 到达审核信息主键
     * @return 结果
     */
    @Override
    public int deleteArrivalAuditInformationByOrderid(String orderid) {
        return arrivalAuditInformationMapper.deleteArrivalAuditInformationByOrderid(orderid);
    }
}
