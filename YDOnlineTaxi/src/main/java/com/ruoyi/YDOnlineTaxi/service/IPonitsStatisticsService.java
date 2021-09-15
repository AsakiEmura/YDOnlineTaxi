package com.ruoyi.YDOnlineTaxi.service;

import java.util.List;
import com.ruoyi.YDOnlineTaxi.domain.PonitsStatistics;

/**
 * 积分统计Service接口
 * 
 * @author ruoyi
 * @date 2021-09-08
 */
public interface IPonitsStatisticsService 
{
    /**
     * 查询积分统计
     * 
     * @param driverPhoneNumber 积分统计主键
     * @return 积分统计
     */
    public PonitsStatistics selectPonitsStatisticsByDriverPhoneNumber(String driverPhoneNumber);

    /**
     * 查询积分统计列表
     * 
     * @param ponitsStatistics 积分统计
     * @return 积分统计集合
     */
    public List<PonitsStatistics> selectPonitsStatisticsList(PonitsStatistics ponitsStatistics);

    /**
     * 新增积分统计
     * 
     * @param ponitsStatistics 积分统计
     * @return 结果
     */
    public int insertPonitsStatistics(PonitsStatistics ponitsStatistics);

    /**
     * 修改积分统计
     * 
     * @param ponitsStatistics 积分统计
     * @return 结果
     */
    public int updatePonitsStatistics(PonitsStatistics ponitsStatistics);

    /**
     * 批量删除积分统计
     * 
     * @param driverPhoneNumbers 需要删除的积分统计主键集合
     * @return 结果
     */
    public int deletePonitsStatisticsByDriverPhoneNumbers(String[] driverPhoneNumbers);

    /**
     * 删除积分统计信息
     * 
     * @param driverPhoneNumber 积分统计主键
     * @return 结果
     */
    public int deletePonitsStatisticsByDriverPhoneNumber(String driverPhoneNumber);
}
