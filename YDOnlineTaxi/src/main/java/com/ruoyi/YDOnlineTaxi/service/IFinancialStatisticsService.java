package com.ruoyi.YDOnlineTaxi.service;

import java.util.List;
import com.ruoyi.YDOnlineTaxi.domain.FinancialStatistics;

/**
 * 财务统计Service接口
 * 
 * @author ruoyi
 * @date 2021-08-30
 */
public interface IFinancialStatisticsService 
{
    /**
     * 查询财务统计
     * 
     * @param driverPhoneNumber 财务统计主键
     * @return 财务统计
     */
    public FinancialStatistics selectFinancialStatisticsByDriverPhoneNumber(String driverPhoneNumber);

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
     * @param driverPhoneNumbers 需要删除的财务统计主键集合
     * @return 结果
     */
    public int deleteFinancialStatisticsByDriverPhoneNumbers(String[] driverPhoneNumbers);

    /**
     * 删除财务统计信息
     * 
     * @param driverPhoneNumber 财务统计主键
     * @return 结果
     */
    public int deleteFinancialStatisticsByDriverPhoneNumber(String driverPhoneNumber);
}
