package com.ruoyi.YDOnlineTaxi.service;

import java.util.List;
import com.ruoyi.YDOnlineTaxi.domain.DriverFeedback;

/**
 * 用户反馈Service接口
 * 
 * @author ruoyi
 * @date 2021-10-13
 */
public interface IDriverFeedbackService 
{
    /**
     * 查询用户反馈
     * 
     * @param id 用户反馈主键
     * @return 用户反馈
     */
    public DriverFeedback selectDriverFeedbackById(Long id);

    /**
     * 查询用户反馈列表
     * 
     * @param driverFeedback 用户反馈
     * @return 用户反馈集合
     */
    public List<DriverFeedback> selectDriverFeedbackList(DriverFeedback driverFeedback);

    /**
     * 新增用户反馈
     * 
     * @param driverFeedback 用户反馈
     * @return 结果
     */
    public int insertDriverFeedback(DriverFeedback driverFeedback);

    /**
     * 修改用户反馈
     * 
     * @param driverFeedback 用户反馈
     * @return 结果
     */
    public int updateDriverFeedback(DriverFeedback driverFeedback);

    /**
     * 批量删除用户反馈
     * 
     * @param ids 需要删除的用户反馈主键集合
     * @return 结果
     */
    public int deleteDriverFeedbackByIds(Long[] ids);

    /**
     * 删除用户反馈信息
     * 
     * @param id 用户反馈主键
     * @return 结果
     */
    public int deleteDriverFeedbackById(Long id);
}
