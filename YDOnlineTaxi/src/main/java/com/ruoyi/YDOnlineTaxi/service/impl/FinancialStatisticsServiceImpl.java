package com.ruoyi.YDOnlineTaxi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.YDOnlineTaxi.mapper.FinancialStatisticsMapper;
import com.ruoyi.YDOnlineTaxi.domain.FinancialStatistics;
import com.ruoyi.YDOnlineTaxi.service.IFinancialStatisticsService;

/**
 * 财务统计Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-08-30
 */
@Service
public class FinancialStatisticsServiceImpl implements IFinancialStatisticsService 
{
    @Autowired
    private FinancialStatisticsMapper financialStatisticsMapper;

    /**
     * 查询财务统计
     * 
     * @param driverPhoneNumber 财务统计主键
     * @return 财务统计
     */
    @Override
    public FinancialStatistics selectFinancialStatisticsByDriverPhoneNumber(String driverPhoneNumber)
    {
        return financialStatisticsMapper.selectFinancialStatisticsByDriverPhoneNumber(driverPhoneNumber);
    }

    /**
     * 查询财务统计列表
     * 
     * @param financialStatistics 财务统计
     * @return 财务统计
     */
    @Override
    public List<FinancialStatistics> selectFinancialStatisticsList(FinancialStatistics financialStatistics)
    {
        return financialStatisticsMapper.selectFinancialStatisticsList(financialStatistics);
    }

    /**
     * 新增财务统计
     * 
     * @param financialStatistics 财务统计
     * @return 结果
     */
    @Override
    public int insertFinancialStatistics(FinancialStatistics financialStatistics)
    {
        return financialStatisticsMapper.insertFinancialStatistics(financialStatistics);
    }

    /**
     * 修改财务统计
     * 
     * @param financialStatistics 财务统计
     * @return 结果
     */
    @Override
    public int updateFinancialStatistics(FinancialStatistics financialStatistics)
    {
        return financialStatisticsMapper.updateFinancialStatistics(financialStatistics);
    }

    /**
     * 批量删除财务统计
     * 
     * @param driverPhoneNumbers 需要删除的财务统计主键
     * @return 结果
     */
    @Override
    public int deleteFinancialStatisticsByDriverPhoneNumbers(String[] driverPhoneNumbers)
    {
        return financialStatisticsMapper.deleteFinancialStatisticsByDriverPhoneNumbers(driverPhoneNumbers);
    }

    /**
     * 删除财务统计信息
     * 
     * @param driverPhoneNumber 财务统计主键
     * @return 结果
     */
    @Override
    public int deleteFinancialStatisticsByDriverPhoneNumber(String driverPhoneNumber)
    {
        return financialStatisticsMapper.deleteFinancialStatisticsByDriverPhoneNumber(driverPhoneNumber);
    }
}
