package com.ruoyi.YDOnlineTaxi.mapper;

import java.util.List;
import com.ruoyi.YDOnlineTaxi.domain.DriverInformation;

/**
 * 司机账户信息Mapper接口
 * 
 * @author ruoyi
 * @date 2021-08-30
 */
public interface DriverInformationMapper 
{
    /**
     * 查询司机账户信息
     * 
     * @param driverPhoneNumber 司机账户信息主键
     * @return 司机账户信息
     */
    public DriverInformation selectDriverInformationByDriverPhoneNumber(String driverPhoneNumber);

    /**
     * 查询司机账户信息列表
     * 
     * @param driverInformation 司机账户信息
     * @return 司机账户信息集合
     */
    public List<DriverInformation> selectDriverInformationList(DriverInformation driverInformation);

    /**
     * 新增司机账户信息
     * 
     * @param driverInformation 司机账户信息
     * @return 结果
     */
    public int insertDriverInformation(DriverInformation driverInformation);

    /**
     * 修改司机账户信息
     * 
     * @param driverInformation 司机账户信息
     * @return 结果
     */
    public int updateDriverInformation(DriverInformation driverInformation);

    /**
     * 删除司机账户信息
     * 
     * @param driverPhoneNumber 司机账户信息主键
     * @return 结果
     */
    public int deleteDriverInformationByDriverPhoneNumber(String driverPhoneNumber);

    /**
     * 批量删除司机账户信息
     * 
     * @param driverPhoneNumbers 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDriverInformationByDriverPhoneNumbers(String[] driverPhoneNumbers);
}
