package com.ruoyi.YDOnlineTaxi.service;

import java.util.List;
import com.ruoyi.YDOnlineTaxi.domain.DriverInformation;

/**
 * 司机线上账户信息
Service接口
 * 
 * @author ruoyi
 * @date 2021-08-28
 */
public interface IDriverInformationService 
{
    /**
     * 查询司机线上账户信息

     * 
     * @param driverPhoneNumber 司机线上账户信息
主键
     * @return 司机线上账户信息

     */
    public DriverInformation selectDriverInformationByDriverPhoneNumber(String driverPhoneNumber);

    /**
     * 查询司机线上账户信息
列表
     * 
     * @param driverInformation 司机线上账户信息

     * @return 司机线上账户信息
集合
     */
    public List<DriverInformation> selectDriverInformationList(DriverInformation driverInformation);

    /**
     * 新增司机线上账户信息

     * 
     * @param driverInformation 司机线上账户信息

     * @return 结果
     */
    public int insertDriverInformation(DriverInformation driverInformation);

    /**
     * 修改司机线上账户信息

     * 
     * @param driverInformation 司机线上账户信息

     * @return 结果
     */
    public int updateDriverInformation(DriverInformation driverInformation);

    /**
     * 批量删除司机线上账户信息

     * 
     * @param driverPhoneNumbers 需要删除的司机线上账户信息
主键集合
     * @return 结果
     */
    public int deleteDriverInformationByDriverPhoneNumbers(String[] driverPhoneNumbers);

    /**
     * 删除司机线上账户信息
信息
     * 
     * @param driverPhoneNumber 司机线上账户信息
主键
     * @return 结果
     */
    public int deleteDriverInformationByDriverPhoneNumber(String driverPhoneNumber);
}
