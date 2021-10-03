package com.ruoyi.YDOnlineTaxi.service.impl;

import com.ruoyi.YDOnlineTaxi.domain.DriverAccount;
import com.ruoyi.YDOnlineTaxi.mapper.DriverAccountMapper;
import com.ruoyi.YDOnlineTaxi.service.IDriverAccountService;
import com.ruoyi.common.constant.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 司机详细信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-08-25
 */
@Service
public class DriverAccountServiceImpl implements IDriverAccountService 
{
    @Autowired(required = false)
    private DriverAccountMapper driverAccountMapper;

    //TODO penpen
    /**
     * 密码验证
     *
     * @param phoneNumber 司机手机号
     * @return 司机详细信息
     */
    @Override
    public DriverAccount selectDriverPassWordByPhoneNumber(String phoneNumber) {
        return driverAccountMapper.selectDriverPassWordByPhoneNumber(phoneNumber);
    }

    /**
     * 查询司机手机号是否存在
     * TODO penpen
     * @param phoneNumber 司机详细信息主键
     * @return 司机详细信息
     */
    @Override
    public int isDriverAccountByPhoneNumber(String phoneNumber)
    {
        return driverAccountMapper.isDriverAccountByPhoneNumber(phoneNumber);
    }

    /**
     * 查询司机详细信息
     * 
     * @param idNumber 司机详细信息主键
     * @return 司机详细信息
     */
    @Override
    public DriverAccount selectDriverAccountByIdNumber(String idNumber)
    {
        return driverAccountMapper.selectDriverAccountByIdNumber(idNumber);
    }

    /**
     * 查询司机详细信息列表
     * 
     * @param driverAccount 司机详细信息
     * @return 司机详细信息
     */
    @Override
    public List<DriverAccount> selectDriverAccountList(DriverAccount driverAccount)
    {
        return driverAccountMapper.selectDriverAccountList(driverAccount);
    }

    /**
     * 新增司机详细信息
     * 
     * @param driverAccount 司机详细信息
     * @return 结果
     */
    @Override
    public int insertDriverAccount(DriverAccount driverAccount)
    {
        return driverAccountMapper.insertDriverAccount(driverAccount);
    }

    /**
     * 修改司机详细信息
     * 
     * @param driverAccount 司机详细信息
     * @return 结果
     */
    @Override
    public int updateDriverAccount(DriverAccount driverAccount)
    {
        return driverAccountMapper.updateDriverAccount(driverAccount);
    }

    /**
     * 批量删除司机详细信息
     * 
     * @param idNumbers 需要删除的司机详细信息主键
     * @return 结果
     */
    @Override
    public int deleteDriverAccountByIdNumbers(String[] idNumbers)
    {
        return driverAccountMapper.deleteDriverAccountByIdNumbers(idNumbers);
    }

    /**
     * 删除司机详细信息信息
     * 
     * @param idNumber 司机详细信息主键
     * @return 结果
     */
    @Override
    public int deleteDriverAccountByIdNumber(String idNumber)
    {
        return driverAccountMapper.deleteDriverAccountByIdNumber(idNumber);
    }

    @Override
    public String checkIdNumberUnique(String idNumber) {
        int count = driverAccountMapper.checkIdNumberUnique(idNumber);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int resetPwd(DriverAccount driverAccount) {
        return driverAccountMapper.updateDriverAccount(driverAccount);
    }

    /**
     * 重置用户密码
     *
     * @param idNumber       用户名
     * @param driverPassword 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String idNumber, String driverPassword) {
        return driverAccountMapper.resetUserPwd(idNumber, driverPassword);
    }

    /**
     *
     * @param status 账号审核状态
     * @return
     */
    @Override
    public List<DriverAccount> selectAllByStatus(String status) {
        return driverAccountMapper.selectAllByStatus(status);
    }

    @Override
    public DriverAccount selectAllByPhoneNumber(String phoneNumber) {
        return driverAccountMapper.selectAllByPhoneNumber(phoneNumber);
    }

    @Override
    public String countByPhoneNumber(String phoneNumber) {
        int count = driverAccountMapper.countByPhoneNumber(phoneNumber);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String selectMachineIdByPhoneNumber(String phoneNumber) {
        return driverAccountMapper.selectMachineIdByPhoneNumber(phoneNumber);
    }
}
