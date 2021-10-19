package com.ruoyi.YDOnlineTaxi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.YDOnlineTaxi.mapper.AuditExtraOrderMapper;
import com.ruoyi.YDOnlineTaxi.domain.AuditExtraOrder;
import com.ruoyi.YDOnlineTaxi.service.IAuditExtraOrderService;

/**
 * 额外积分申请Service业务层处理
 * 
 * @author PenPen
 * @date 2021-10-13
 */
@Service
public class AuditExtraOrderServiceImpl implements IAuditExtraOrderService 
{
    @Autowired
    private AuditExtraOrderMapper auditExtraOrderMapper;

    /**
     * 查询额外积分申请
     * 
     * @param orderId 额外积分申请主键
     * @return 额外积分申请
     */
    @Override
    public List<AuditExtraOrder> selectAuditExtraOrderByOrderId(String orderId)
    {
        return auditExtraOrderMapper.selectAuditExtraOrderByOrderId(orderId);
    }

    /**
     * 查询额外积分申请列表
     * 
     * @param auditExtraOrder 额外积分申请
     * @return 额外积分申请
     */
    @Override
    public List<AuditExtraOrder> selectAuditExtraOrderList(AuditExtraOrder auditExtraOrder)
    {
        return auditExtraOrderMapper.selectAuditExtraOrderList(auditExtraOrder);
    }

    /**
     * 新增额外积分申请
     * 
     * @param auditExtraOrder 额外积分申请
     * @return 结果
     */
    @Override
    public int insertAuditExtraOrder(AuditExtraOrder auditExtraOrder)
    {
        return auditExtraOrderMapper.insertAuditExtraOrder(auditExtraOrder);
    }

    /**
     * 修改额外积分申请
     * 
     * @param auditExtraOrder 额外积分申请
     * @return 结果
     */
    @Override
    public int updateAuditExtraOrder(AuditExtraOrder auditExtraOrder)
    {
        return auditExtraOrderMapper.updateAuditExtraOrder(auditExtraOrder);
    }

    /**
     * 批量删除额外积分申请
     * 
     * @param orderIds 需要删除的额外积分申请主键
     * @return 结果
     */
    @Override
    public int deleteAuditExtraOrderByOrderIds(String[] orderIds)
    {
        return auditExtraOrderMapper.deleteAuditExtraOrderByOrderIds(orderIds);
    }

    /**
     * 删除额外积分申请信息
     * 
     * @param orderId 额外积分申请主键
     * @return 结果
     */
    @Override
    public int deleteAuditExtraOrderByOrderId(String orderId)
    {
        return auditExtraOrderMapper.deleteAuditExtraOrderByOrderId(orderId);
    }
}
