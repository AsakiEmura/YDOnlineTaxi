package com.ruoyi.YDOnlineTaxi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.YDOnlineTaxi.mapper.DriverInformationMapper;
import com.ruoyi.YDOnlineTaxi.domain.DriverInformation;
import com.ruoyi.YDOnlineTaxi.service.IDriverInformationService;

/**
 * 司机线上账户信息
Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-09-08
 */
@Service
public class DriverInformationServiceImpl implements IDriverInformationService 
{
    @Autowired
    private DriverInformationMapper driverInformationMapper;

    /**
     * 查询司机线上账户信息

     * 
     * @param driverPhoneNumber 司机线上账户信息
主键
     * @return 司机线上账户信息

     */
    @Override
    public DriverInformation selectDriverInformationByDriverPhoneNumber(String driverPhoneNumber)
    {
        return driverInformationMapper.selectDriverInformationByDriverPhoneNumber(driverPhoneNumber);
    }

    /**
     * 查询司机线上账户信息
列表
     * 
     * @param driverInformation 司机线上账户信息

     * @return 司机线上账户信息

     */
    @Override
    public List<DriverInformation> selectDriverInformationList(DriverInformation driverInformation)
    {
        return driverInformationMapper.selectDriverInformationList(driverInformation);
    }

    /**
     * 新增司机线上账户信息

     * 
     * @param driverInformation 司机线上账户信息

     * @return 结果
     */
    @Override
    public int insertDriverInformation(DriverInformation driverInformation)
    {
        return driverInformationMapper.insertDriverInformation(driverInformation);
    }

    /**
     * 修改司机线上账户信息

     * 
     * @param driverInformation 司机线上账户信息

     * @return 结果
     */
    @Override
    public int updateDriverInformation(DriverInformation driverInformation)
    {
        return driverInformationMapper.updateDriverInformation(driverInformation);
    }

    /**
     * 批量删除司机线上账户信息

     * 
     * @param driverPhoneNumbers 需要删除的司机线上账户信息
主键
     * @return 结果
     */
    @Override
    public int deleteDriverInformationByDriverPhoneNumbers(String[] driverPhoneNumbers)
    {
        return driverInformationMapper.deleteDriverInformationByDriverPhoneNumbers(driverPhoneNumbers);
    }

    /**
     * 删除司机线上账户信息
信息
     * 
     * @param driverPhoneNumber 司机线上账户信息
主键
     * @return 结果
     */
    @Override
    public int deleteDriverInformationByDriverPhoneNumber(String driverPhoneNumber)
    {
        return driverInformationMapper.deleteDriverInformationByDriverPhoneNumber(driverPhoneNumber);
    }
}
