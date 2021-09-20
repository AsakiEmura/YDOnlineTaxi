package com.ruoyi.YDOnlineTaxi.mapper;

import com.ruoyi.YDOnlineTaxi.domain.ArrivalAuditInformation;

import java.util.List;

/**
 * 到达审核信息Mapper接口
 *
 * @author ruoyi
 * @date 2021-09-19
 */
public interface ArrivalAuditInformationMapper {
    /**
     * 查询到达审核信息
     *
     * @param orderid 到达审核信息主键
     * @return 到达审核信息
     */
    public ArrivalAuditInformation selectArrivalAuditInformationByOrderid(String orderid);

    /**
     * 查询到达审核信息列表
     *
     * @param arrivalAuditInformation 到达审核信息
     * @return 到达审核信息集合
     */
    public List<ArrivalAuditInformation> selectArrivalAuditInformationList(ArrivalAuditInformation arrivalAuditInformation);

    /**
     * 新增到达审核信息
     *
     * @param arrivalAuditInformation 到达审核信息
     * @return 结果
     */
    public int insertArrivalAuditInformation(ArrivalAuditInformation arrivalAuditInformation);

    /**
     * 修改到达审核信息
     *
     * @param arrivalAuditInformation 到达审核信息
     * @return 结果
     */
    public int updateArrivalAuditInformation(ArrivalAuditInformation arrivalAuditInformation);

    /**
     * 删除到达审核信息
     *
     * @param orderid 到达审核信息主键
     * @return 结果
     */
    public int deleteArrivalAuditInformationByOrderid(String orderid);

    /**
     * 批量删除到达审核信息
     *
     * @param orderids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteArrivalAuditInformationByOrderids(String[] orderids);
}
