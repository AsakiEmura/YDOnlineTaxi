package com.ruoyi.YDOnlineTaxi.domain.VO;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 司机详细信息对象 driver_account
 * 
 * @author ruoyi
 * @date 2021-08-25
 */
public class DriverAccount extends BaseEntity
{

    /** 司机姓名 */
    @Excel(name = "司机姓名")
    private String driverName;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idNumber;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phoneNumber;

    /** 紧急联系电话 */
    @Excel(name = "紧急联系电话")
    private String emergencyContactNumber;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 车型 */
    @Excel(name = "车型")
    private String motorcycleType;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String licensePlateNumber;

    /** 身份证照片人像面 */
    private String idPhotoFront;

    /** 身份证照片国旗面 */
    private String idPhotoBack;

    /** 驾驶证照片 */
    private String vehicleLicensePhoto;

    /** 行驶证照片 */
    private String driverLicencePhoto;

    /** 司机审核状态 */
    @Excel(name = "司机审核状态")
    private String status;

    /** 司机账户密码 */
    private String driverPassword;

    /** md5密码盐 */
    private String salt;

    /** 用户微信openid*/
    private String openId;


    public void setDriverName(String driverName) 
    {
        this.driverName = driverName;
    }

    public String getDriverName() 
    {
        return driverName;
    }
    public void setIdNumber(String idNumber) 
    {
        this.idNumber = idNumber;
    }

    public String getIdNumber() 
    {
        return idNumber;
    }
    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }
    public void setEmergencyContactNumber(String emergencyContactNumber) 
    {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getEmergencyContactNumber() 
    {
        return emergencyContactNumber;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setMotorcycleType(String motorcycleType) 
    {
        this.motorcycleType = motorcycleType;
    }

    public String getMotorcycleType() 
    {
        return motorcycleType;
    }
    public void setLicensePlateNumber(String licensePlateNumber) 
    {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getLicensePlateNumber() 
    {
        return licensePlateNumber;
    }
    public void setIdPhotoFront(String idPhotoFront) 
    {
        this.idPhotoFront = idPhotoFront;
    }

    public String getIdPhotoFront() 
    {
        return idPhotoFront;
    }
    public void setIdPhotoBack(String idPhotoBack) 
    {
        this.idPhotoBack = idPhotoBack;
    }

    public String getIdPhotoBack()
    {
        return idPhotoBack;
    }
    public void setVehicleLicensePhoto(String vehicleLicensePhoto) 
    {
        this.vehicleLicensePhoto = vehicleLicensePhoto;
    }

    public String getVehicleLicensePhoto() 
    {
        return vehicleLicensePhoto;
    }
    public void setDriverLicencePhoto(String driverLicencePhoto) 
    {
        this.driverLicencePhoto = driverLicencePhoto;
    }

    public String getDriverLicencePhoto() 
    {
        return driverLicencePhoto;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDriverPassword(String driverPassword) 
    {
        this.driverPassword = driverPassword;
    }

    public String getDriverPassword() 
    {
        return driverPassword;
    }
    public void setSalt(String salt) 
    {
        this.salt = salt;
    }

    public String getSalt() 
    {
        return salt;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("driverName", getDriverName())
            .append("idNumber", getIdNumber())
            .append("phoneNumber", getPhoneNumber())
            .append("emergencyContactNumber", getEmergencyContactNumber())
            .append("address", getAddress())
            .append("motorcycleType", getMotorcycleType())
            .append("licensePlateNumber", getLicensePlateNumber())
            .append("idPhotoFront", getIdPhotoFront())
            .append("idPhotoBack", getIdPhotoBack())
            .append("vehicleLicensePhoto", getVehicleLicensePhoto())
            .append("driverLicencePhoto", getDriverLicencePhoto())
            .append("status", getStatus())
            .append("driverPassword", getDriverPassword())
            .append("salt", getSalt())
            .append("openId",getOpenId())
            .toString();
    }
}
