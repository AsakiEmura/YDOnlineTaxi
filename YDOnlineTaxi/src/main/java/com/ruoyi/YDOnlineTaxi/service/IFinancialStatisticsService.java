package com.ruoyi.YDOnlineTaxi.service;

import java.util.List;
import com.ruoyi.YDOnlineTaxi.domain.FinancialStatistics;
import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import org.apache.ibatis.annotations.Param;

/**
 * 财务统计Service接口
 * 
 * @author Asaki
 * @date 2021-11-03
 */
public interface IFinancialStatisticsService 
{
    /**
     * 查询财务统计
     * 
     * @param orderId 财务统计主键
     * @return 财务统计
     */
    public FinancialStatistics selectFinancialStatisticsByOrderId(String orderId);

    /**
     * 查询财务统计列表
     * 
     * @param financialStatistics 财务统计
     * @return 财务统计集合
     */
    public List<FinancialStatistics> selectFinancialStatisticsList(FinancialStatistics financialStatistics);

    /**
     * 新增财务统计
     * 
     * @param financialStatistics 财务统计
     * @return 结果
     */
    public int insertFinancialStatistics(FinancialStatistics financialStatistics);

    /**
     * 修改财务统计
     * 
     * @param financialStatistics 财务统计
     * @return 结果
     */
    public int updateFinancialStatistics(FinancialStatistics financialStatistics);

    /**
     * 批量删除财务统计
     * 
     * @param orderIds 需要删除的财务统计主键集合
     * @return 结果
     */
    public int deleteFinancialStatisticsByOrderIds(String[] orderIds);

    /**
     * 删除财务统计信息
     * 
     * @param orderId 财务统计主键
     * @return 结果
     */
    public int deleteFinancialStatisticsByOrderId(String orderId);

    /**
     * 导入用户数据
     *
     * @param orderList       用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importOrder(List<FinancialStatistics> orderList, Boolean isUpdateSupport, String operName);

    public List<FinancialStatistics> selectAllByConditions(String driverPhoneNumber, String minTime, String maxTime);
}
