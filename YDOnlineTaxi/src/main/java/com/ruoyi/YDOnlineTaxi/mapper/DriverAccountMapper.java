package com.ruoyi.YDOnlineTaxi.mapper;

import com.ruoyi.YDOnlineTaxi.domain.DriverAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 司机详细信息Mapper接口
 * 
 * @author ruoyi
 * @date 2021-08-25
 */
public interface DriverAccountMapper 
{
    /**
     * 查询司机详细信息
     *
     * @param phoneNumber 司机手机号
     * @return 司机详细信息
     */
    public DriverAccount selectDriverPassWordByPhoneNumber(String phoneNumber);

    /**
     * 查询司机手机号是否存在
     *
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
    public int
    updateDriverAccount(DriverAccount driverAccount);

    /**
     * 删除司机详细信息
     * 
     * @param idNumber 司机详细信息主键
     * @return 结果
     */
    public int deleteDriverAccountByIdNumber(String idNumber);

    /**
     * 批量删除司机详细信息
     * 
     * @param idNumbers 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDriverAccountByIdNumbers(String[] idNumbers);

    public int checkIdNumberUnique(String idNumber);

    /**
     * 重置用户密码
     *
     * @param idNumber       用户名
     * @param driverPassword 密码
     * @return 结果
     */
    public int resetUserPwd(@Param("idNumber") String idNumber, @Param("driverPassword") String driverPassword);

    /**
     *
     * @param status 账号审核状态
     * @return
     */
    List<DriverAccount> selectAllByStatus(@Param("status")String status);

    public DriverAccount selectAllByPhoneNumber(@Param("phoneNumber")String phoneNumber);

    public Integer countByPhoneNumber(@Param("phoneNumber")String phoneNumber);

    String selectOpenIdByPhoneNumber(@Param("phoneNumber")String phoneNumber);


}
