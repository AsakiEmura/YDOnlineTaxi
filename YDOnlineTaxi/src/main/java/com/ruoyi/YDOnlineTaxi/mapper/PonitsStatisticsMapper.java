package com.ruoyi.YDOnlineTaxi.mapper;

import java.util.List;
import com.ruoyi.YDOnlineTaxi.domain.VO.PonitsStatistics;

/**
 * 积分统计Mapper接口
 * 
 * @author ruoyi
 * @date 2021-09-08
 */
public interface PonitsStatisticsMapper 
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
     * 删除积分统计
     * 
     * @param driverPhoneNumber 积分统计主键
     * @return 结果
     */
    public int deletePonitsStatisticsByDriverPhoneNumber(String driverPhoneNumber);

    /**
     * 批量删除积分统计
     * 
     * @param driverPhoneNumbers 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePonitsStatisticsByDriverPhoneNumbers(String[] driverPhoneNumbers);
}
