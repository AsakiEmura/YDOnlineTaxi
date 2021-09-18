package com.ruoyi.YDOnlineTaxi.service.impl;

import com.ruoyi.YDOnlineTaxi.domain.RewardsPunishmentsLog;
import com.ruoyi.YDOnlineTaxi.mapper.RewardsPunishmentsLogMapper;
import com.ruoyi.YDOnlineTaxi.service.IRewardsPunishmentsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 积分奖惩日志Service业务层处理
 *
 * @author ruoyi
 * @date 2021-09-16
 */
@Service
public class RewardsPunishmentsLogServiceImpl implements IRewardsPunishmentsLogService {
    @Autowired(required = false)
    private RewardsPunishmentsLogMapper rewardsPunishmentsLogMapper;

    /**
     * 查询积分奖惩日志
     *
     * @param phoneNumber 积分奖惩日志主键
     * @return 积分奖惩日志
     */
    @Override
    public RewardsPunishmentsLog selectRewardsPunishmentsLogByPhoneNumber(String phoneNumber) {
        return rewardsPunishmentsLogMapper.selectRewardsPunishmentsLogByPhoneNumber(phoneNumber);
    }

    /**
     * 查询积分奖惩日志列表
     *
     * @param rewardsPunishmentsLog 积分奖惩日志
     * @return 积分奖惩日志
     */
    @Override
    public List<RewardsPunishmentsLog> selectRewardsPunishmentsLogList(RewardsPunishmentsLog rewardsPunishmentsLog) {
        return rewardsPunishmentsLogMapper.selectRewardsPunishmentsLogList(rewardsPunishmentsLog);
    }

    /**
     * 新增积分奖惩日志
     *
     * @param rewardsPunishmentsLog 积分奖惩日志
     * @return 结果
     */
    @Override
    public int insertRewardsPunishmentsLog(RewardsPunishmentsLog rewardsPunishmentsLog) {
        return rewardsPunishmentsLogMapper.insertRewardsPunishmentsLog(rewardsPunishmentsLog);
    }

    /**
     * 修改积分奖惩日志
     *
     * @param rewardsPunishmentsLog 积分奖惩日志
     * @return 结果
     */
    @Override
    public int updateRewardsPunishmentsLog(RewardsPunishmentsLog rewardsPunishmentsLog) {
        return rewardsPunishmentsLogMapper.updateRewardsPunishmentsLog(rewardsPunishmentsLog);
    }

    /**
     * 批量删除积分奖惩日志
     *
     * @param phoneNumbers 需要删除的积分奖惩日志主键
     * @return 结果
     */
    @Override
    public int deleteRewardsPunishmentsLogByPhoneNumbers(String[] phoneNumbers) {
        return rewardsPunishmentsLogMapper.deleteRewardsPunishmentsLogByPhoneNumbers(phoneNumbers);
    }

    /**
     * 删除积分奖惩日志信息
     *
     * @param phoneNumber 积分奖惩日志主键
     * @return 结果
     */
    @Override
    public int deleteRewardsPunishmentsLogByPhoneNumber(String phoneNumber) {
        return rewardsPunishmentsLogMapper.deleteRewardsPunishmentsLogByPhoneNumber(phoneNumber);
    }
}
