package com.ruoyi.YDOnlineTaxi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.YDOnlineTaxi.mapper.DriverFeedbackMapper;
import com.ruoyi.YDOnlineTaxi.domain.DriverFeedback;
import com.ruoyi.YDOnlineTaxi.service.IDriverFeedbackService;

/**
 * 用户反馈Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-10-13
 */
@Service
public class DriverFeedbackServiceImpl implements IDriverFeedbackService 
{
    @Autowired
    private DriverFeedbackMapper driverFeedbackMapper;

    /**
     * 查询用户反馈
     * 
     * @param id 用户反馈主键
     * @return 用户反馈
     */
    @Override
    public DriverFeedback selectDriverFeedbackById(Long id)
    {
        return driverFeedbackMapper.selectDriverFeedbackById(id);
    }

    /**
     * 查询用户反馈列表
     * 
     * @param driverFeedback 用户反馈
     * @return 用户反馈
     */
    @Override
    public List<DriverFeedback> selectDriverFeedbackList(DriverFeedback driverFeedback)
    {
        return driverFeedbackMapper.selectDriverFeedbackList(driverFeedback);
    }

    /**
     * 新增用户反馈
     * 
     * @param driverFeedback 用户反馈
     * @return 结果
     */
    @Override
    public int insertDriverFeedback(DriverFeedback driverFeedback)
    {
        return driverFeedbackMapper.insertDriverFeedback(driverFeedback);
    }

    /**
     * 修改用户反馈
     * 
     * @param driverFeedback 用户反馈
     * @return 结果
     */
    @Override
    public int updateDriverFeedback(DriverFeedback driverFeedback)
    {
        return driverFeedbackMapper.updateDriverFeedback(driverFeedback);
    }

    /**
     * 批量删除用户反馈
     * 
     * @param ids 需要删除的用户反馈主键
     * @return 结果
     */
    @Override
    public int deleteDriverFeedbackByIds(Long[] ids)
    {
        return driverFeedbackMapper.deleteDriverFeedbackByIds(ids);
    }

    /**
     * 删除用户反馈信息
     * 
     * @param id 用户反馈主键
     * @return 结果
     */
    @Override
    public int deleteDriverFeedbackById(Long id)
    {
        return driverFeedbackMapper.deleteDriverFeedbackById(id);
    }
}
