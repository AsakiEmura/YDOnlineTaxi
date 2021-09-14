package com.ruoyi.YDOnlineTaxi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.YDOnlineTaxi.mapper.PonitsStatisticsMapper;
import com.ruoyi.YDOnlineTaxi.domain.VO.PonitsStatistics;
import com.ruoyi.YDOnlineTaxi.service.IPonitsStatisticsService;

/**
 * 积分统计Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-09-08
 */
@Service
public class PonitsStatisticsServiceImpl implements IPonitsStatisticsService 
{
    @Autowired
    private PonitsStatisticsMapper ponitsStatisticsMapper;

    /**
     * 查询积分统计
     * 
     * @param driverPhoneNumber 积分统计主键
     * @return 积分统计
     */
    @Override
    public PonitsStatistics selectPonitsStatisticsByDriverPhoneNumber(String driverPhoneNumber)
    {
        return ponitsStatisticsMapper.selectPonitsStatisticsByDriverPhoneNumber(driverPhoneNumber);
    }

    /**
     * 查询积分统计列表
     * 
     * @param ponitsStatistics 积分统计
     * @return 积分统计
     */
    @Override
    public List<PonitsStatistics> selectPonitsStatisticsList(PonitsStatistics ponitsStatistics)
    {
        return ponitsStatisticsMapper.selectPonitsStatisticsList(ponitsStatistics);
    }

    /**
     * 新增积分统计
     * 
     * @param ponitsStatistics 积分统计
     * @return 结果
     */
    @Override
    public int insertPonitsStatistics(PonitsStatistics ponitsStatistics)
    {
        return ponitsStatisticsMapper.insertPonitsStatistics(ponitsStatistics);
    }

    /**
     * 修改积分统计
     * 
     * @param ponitsStatistics 积分统计
     * @return 结果
     */
    @Override
    public int updatePonitsStatistics(PonitsStatistics ponitsStatistics)
    {
        return ponitsStatisticsMapper.updatePonitsStatistics(ponitsStatistics);
    }

    /**
     * 批量删除积分统计
     * 
     * @param driverPhoneNumbers 需要删除的积分统计主键
     * @return 结果
     */
    @Override
    public int deletePonitsStatisticsByDriverPhoneNumbers(String[] driverPhoneNumbers)
    {
        return ponitsStatisticsMapper.deletePonitsStatisticsByDriverPhoneNumbers(driverPhoneNumbers);
    }

    /**
     * 删除积分统计信息
     * 
     * @param driverPhoneNumber 积分统计主键
     * @return 结果
     */
    @Override
    public int deletePonitsStatisticsByDriverPhoneNumber(String driverPhoneNumber)
    {
        return ponitsStatisticsMapper.deletePonitsStatisticsByDriverPhoneNumber(driverPhoneNumber);
    }
}
