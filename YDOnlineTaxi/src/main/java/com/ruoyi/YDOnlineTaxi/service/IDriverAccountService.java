package com.ruoyi.YDOnlineTaxi.service;

import com.ruoyi.YDOnlineTaxi.domain.DriverAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 司机详细信息Service接口
 * 
 * @author ruoyi
 * @date 2021-08-25
 */
public interface IDriverAccountService 
{

    /**
     * 验证密码是否正确
     *
     * @param phoneNumber 司机手机号
     * @return 司机详细信息
     */
    public DriverAccount selectDriverPassWordByPhoneNumber(String phoneNumber);

    /**
     * 查询司机手机号是否存在
     * @param phoneNumber 司机详细信息主键
     * @return 司机详细信息
     */
    public int isDriverAccountByPhoneNumber(String phoneNumber);

    /**
     * 查询司机详细信息
     * 
     * @param idNumber 司机详细信息主键
     * @return 司机详细信息
     */
    public DriverAccount selectDriverAccountByIdNumber(String idNumber);

    /**
     * 查询司机详细信息列表
     * 
     * @param driverAccount 司机详细信息
     * @return 司机详细信息集合
     */
    public List<DriverAccount> selectDriverAccountList(DriverAccount driverAccount);

    /**
     * 新增司机详细信息
     * 
     * @param driverAccount 司机详细信息
     * @return 结果
     */
    public int insertDriverAccount(DriverAccount driverAccount);

    /**
     * 修改司机详细信息
     * 
     * @param driverAccount 司机详细信息
     * @return 结果
     */
    public int updateDriverAccount(DriverAccount driverAccount);

    /**
     * 批量删除司机详细信息
     * 
     * @param idNumbers 需要删除的司机详细信息主键集合
     * @return 结果
     */
    public int deleteDriverAccountByIdNumbers(String[] idNumbers);

    /**
     * 删除司机详细信息信息
     * 
     * @param idNumber 司机详细信息主键
     * @return 结果
     */
    public int deleteDriverAccountByIdNumber(String idNumber);

    public String checkIdNumberUnique(String idNumber);

    /**
     * 重置用户密码
     *
     * @param driverAccount 用户信息
     * @return 结果
     */
    public int resetPwd(DriverAccount driverAccount);

    /**
     * 重置用户密码
     *
     * @param idNumber 用户名
     * @param driverPassword 密码
     * @return 结果
     */
    public int resetUserPwd(String idNumber, String driverPassword);

    /**
     *
     * @param status 账号审核状态
     * @return
     */
    public List<DriverAccount> selectAllByStatus(String status);

    public DriverAccount selectAllByPhoneNumber(String phoneNumber);

    public String countByPhoneNumber(String phoneNumber);

    public String selectOpenIdByPhoneNumber(String phoneNumber);
}
