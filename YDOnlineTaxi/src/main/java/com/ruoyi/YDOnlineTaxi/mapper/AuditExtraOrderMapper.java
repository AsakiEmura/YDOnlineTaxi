package com.ruoyi.YDOnlineTaxi.mapper;

import java.util.List;
import com.ruoyi.YDOnlineTaxi.domain.AuditExtraOrder;

/**
 * 额外积分申请Mapper接口
 * 
 * @author PenPen
 * @date 2021-10-13
 */
public interface AuditExtraOrderMapper 
{
    /**
     * 查询额外积分申请
     * 
     * @param orderId 额外积分申请主键
     * @return 额外积分申请
     */
    public List<AuditExtraOrder> selectAuditExtraOrderByOrderId(String orderId);

    /**
     * 查询额外积分申请列表
     * 
     * @param auditExtraOrder 额外积分申请
     * @return 额外积分申请集合
     */
    public List<AuditExtraOrder> selectAuditExtraOrderList(AuditExtraOrder auditExtraOrder);

    /**
     * 新增额外积分申请
     * 
     * @param auditExtraOrder 额外积分申请
     * @return 结果
     */
    public int insertAuditExtraOrder(AuditExtraOrder auditExtraOrder);

    /**
     * 修改额外积分申请
     * 
     * @param auditExtraOrder 额外积分申请
     * @return 结果
     */
    public int updateAuditExtraOrder(AuditExtraOrder auditExtraOrder);

    /**
     * 删除额外积分申请
     * 
     * @param orderId 额外积分申请主键
     * @return 结果
     */
    public int deleteAuditExtraOrderByOrderId(String orderId);

    /**
     * 批量删除额外积分申请
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAuditExtraOrderByOrderIds(String[] orderIds);
}
