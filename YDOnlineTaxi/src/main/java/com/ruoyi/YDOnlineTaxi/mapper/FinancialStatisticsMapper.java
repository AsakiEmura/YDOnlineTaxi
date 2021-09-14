package com.ruoyi.YDOnlineTaxi.mapper;

import java.util.List;
import com.ruoyi.YDOnlineTaxi.domain.VO.FinancialStatistics;

/**
 * 财务统计Mapper接口
 * 
 * @author ruoyi
 * @date 2021-08-30
 */
public interface FinancialStatisticsMapper 
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
     * 删除财务统计
     * 
     * @param driverPhoneNumber 财务统计主键
     * @return 结果
     */
    public int deleteFinancialStatisticsByDriverPhoneNumber(String driverPhoneNumber);

    /**
     * 批量删除财务统计
     * 
     * @param driverPhoneNumbers 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinancialStatisticsByDriverPhoneNumbers(String[] driverPhoneNumbers);
    
}
