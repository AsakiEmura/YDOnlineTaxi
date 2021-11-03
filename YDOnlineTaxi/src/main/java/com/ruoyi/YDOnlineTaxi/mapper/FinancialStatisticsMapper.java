package com.ruoyi.YDOnlineTaxi.mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.ruoyi.YDOnlineTaxi.domain.FinancialStatistics;

/**
 * 财务统计Mapper接口
 * 
 * @author Asaki
 * @date 2021-11-03
 */
public interface FinancialStatisticsMapper 
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
     * 删除财务统计
     * 
     * @param orderId 财务统计主键
     * @return 结果
     */
    public int deleteFinancialStatisticsByOrderId(String orderId);

    /**
     * 批量删除财务统计
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinancialStatisticsByOrderIds(String[] orderIds);

    public List<FinancialStatistics> selectAllByConditions(@Param("driverPhoneNumber")String driverPhoneNumber,@Param("minTime") String minTime, @Param("maxTime") String maxTime);


}
