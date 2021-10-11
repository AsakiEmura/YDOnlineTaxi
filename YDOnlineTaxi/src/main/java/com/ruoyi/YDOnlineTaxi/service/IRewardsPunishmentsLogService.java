package com.ruoyi.YDOnlineTaxi.service;

import com.ruoyi.YDOnlineTaxi.domain.RewardsPunishmentsLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 积分奖惩日志Service接口
 *
 * @author ruoyi
 * @date 2021-09-16
 */
public interface IRewardsPunishmentsLogService {
    /**
     * 查询积分奖惩日志
     *
     * @param phoneNumber 积分奖惩日志主键
     * @return 积分奖惩日志
     */
    public RewardsPunishmentsLog selectRewardsPunishmentsLogByPhoneNumber(String phoneNumber);

    /**
     * 查询积分奖惩日志列表
     *
     * @param rewardsPunishmentsLog 积分奖惩日志
     * @return 积分奖惩日志集合
     */
    public List<RewardsPunishmentsLog> selectRewardsPunishmentsLogList(RewardsPunishmentsLog rewardsPunishmentsLog);

    /**
     * 新增积分奖惩日志
     *
     * @param rewardsPunishmentsLog 积分奖惩日志
     * @return 结果
     */
    public int insertRewardsPunishmentsLog(RewardsPunishmentsLog rewardsPunishmentsLog);

    /**
     * 修改积分奖惩日志
     *
     * @param rewardsPunishmentsLog 积分奖惩日志
     * @return 结果
     */
    public int updateRewardsPunishmentsLog(RewardsPunishmentsLog rewardsPunishmentsLog);

    /**
     * 批量删除积分奖惩日志
     *
     * @param phoneNumbers 需要删除的积分奖惩日志主键集合
     * @return 结果
     */
    public int deleteRewardsPunishmentsLogByPhoneNumbers(String[] phoneNumbers);

    /**
     * 删除积分奖惩日志信息
     *
     * @param phoneNumber 积分奖惩日志主键
     * @return 结果
     */
    public int deleteRewardsPunishmentsLogByPhoneNumber(String phoneNumber);

    public List<RewardsPunishmentsLog> selectAllByOperatingTimeBetweenAndPhoneNumber(String minOperatingTime,String maxOperatingTime,String phoneNumber);

    public List<RewardsPunishmentsLog> selectAllByPhoneNumber(String phoneNumber);

}
