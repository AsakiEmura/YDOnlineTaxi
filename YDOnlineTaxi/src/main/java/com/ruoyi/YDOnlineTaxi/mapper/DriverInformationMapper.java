package com.ruoyi.YDOnlineTaxi.mapper;

import java.util.List;
import com.ruoyi.YDOnlineTaxi.domain.VO.DriverInformation;

/**
 * 司机线上账户信息
Mapper接口
 * 
 * @author ruoyi
 * @date 2021-09-08
 */
public interface DriverInformationMapper 
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
     * 删除司机线上账户信息

     * 
     * @param driverPhoneNumber 司机线上账户信息
主键
     * @return 结果
     */
    public int deleteDriverInformationByDriverPhoneNumber(String driverPhoneNumber);

    /**
     * 批量删除司机线上账户信息

     * 
     * @param driverPhoneNumbers 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDriverInformationByDriverPhoneNumbers(String[] driverPhoneNumbers);
}
