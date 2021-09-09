package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.DriverAccount;
import com.ruoyi.YDOnlineTaxi.service.IDriverAccountService;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ShiroKit;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/YDOnlineTaxi/WxService")
public class YDOnlineTaxiWxService extends BaseController {
    @Autowired
    private IDriverAccountService driverAccountService;

    @Autowired
    private ServerConfig serverConfig;


    @Autowired
    private IOrderInformationService orderInformationService;


    @PostMapping("/register")
    public AjaxResult register(@RequestBody DriverAccount driverAccount) {
        try
            {
                if (UserConstants.NOT_UNIQUE.equals(driverAccountService.checkIdNumberUnique(driverAccount.getIdNumber()))) {
                    return AjaxResult.error("exist");
                }

                if (UserConstants.NOT_UNIQUE.equals(driverAccountService.countByPhoneNumber(driverAccount.getPhoneNumber()))) {
                    return AjaxResult.error("exist");
                }

                driverAccountService.insertDriverAccount(driverAccount);
                return AjaxResult.success("200");
            }
        catch (Exception e)
        {
            e.printStackTrace();
            return AjaxResult.error("400");
        }

    }

    @PostMapping("/register/uploadPhotos")
    public AjaxResult uploadImage(@RequestParam("files") MultipartFile file, @RequestParam("type") String type, @RequestParam("idCard") String idCard) throws Exception {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;

            DriverAccount driverAccount = driverAccountService.selectDriverAccountByIdNumber(idCard);

            switch (type)
            {
                case "front_id":
                    driverAccount.setIdPhotoFront(fileName);
                    break;
                case "observe_id":
                    driverAccount.setIdPhotoBack(fileName);
                    break;
                case "car_id":
                    driverAccount.setVehicleLicensePhoto(fileName);
                    break;
                case "driver_id":
                    driverAccount.setDriverLicencePhoto(fileName);
                    break;
            }

            driverAccountService.updateDriverAccount(driverAccount);

            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            ajax.put("status", "ok");
            ajax.put("msg", "上传图片成功");
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }


    /**
     * 登录验证手机号
     */
    @PostMapping("/testPhone")
    public AjaxResult testPhone(@RequestBody Map<String, Object> data) {
        String phoneNumber;
        int check;
        try
        {
            phoneNumber = data.get("phoneNumber").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("40000");
        }
        check = driverAccountService.isDriverAccountByPhoneNumber(phoneNumber);
        if (check == 1)
        {
            //有这个号
            return AjaxResult.success("40001");
        }
        else
        {
            //没这个号
            return AjaxResult.success("40002");
        }
    }

    @PostMapping("/login")
    public AjaxResult login(@RequestBody Map<String, Object> data)
    {
        String phoneNumber;
        try
        {
            phoneNumber = data.get("phoneNumber").toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return AjaxResult.error("jsonError");
        }

        if(!UserConstants.NOT_UNIQUE.equals(driverAccountService.countByPhoneNumber(phoneNumber)))
        {
            return AjaxResult.error("notExist");
        }
        DriverAccount driverAccount = driverAccountService.selectDriverPassWordByPhoneNumber(phoneNumber);
        if (!driverAccount.getStatus().equals("审核通过")) {
            return AjaxResult.error("waitAudit");
        }


        String driverPassword;
        try
        {
            driverPassword = data.get("driverPassword").toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return AjaxResult.error("jsonError");
        }

        String salt = driverAccount.getSalt();
        String password = driverAccount.getDriverPassword();

        String newPassword = ShiroKit.md5(salt,driverPassword);

        if(newPassword != password){
            return AjaxResult.error("passwordError");
        }

        return AjaxResult.success("success");
    }

    /**
     * 登录验证密码
     */
    @PostMapping("/testPwd")
    public AjaxResult testPwd(@RequestBody Map<String, Object> data) {
        String phoneNumber;
        String driverPassword;
        try {
            phoneNumber = data.get("phoneNumber").toString();
            driverPassword = data.get("driverPassword").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("20002");
        }
        DriverAccount driverAccount = driverAccountService.selectDriverPassWordByPhoneNumber(phoneNumber);

        String salt = driverAccount.getSalt();
        String oldPassWord = driverAccount.getDriverPassword();
        String newPassWord = ShiroKit.md5(driverPassword, salt);
        String status = driverAccount.getStatus();

        if (oldPassWord.equals(newPassWord) && "审核通过".equals(status)) {
            return AjaxResult.success("20000");
        } else {
            return AjaxResult.error("20001");
        }
    }


    @PostMapping("/resetPwd/{phoneNumber}")
    public AjaxResult resetPwd(@RequestBody Map<String, Object> password,@PathVariable("phoneNumber") String phoneNumber) {
        String newPassword;
        try
        {
            newPassword = password.get("password").toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return AjaxResult.error("304");
        }

        DriverAccount driverAccount = driverAccountService.selectAllByPhoneNumber(phoneNumber);
        driverAccount.setDriverPassword(newPassword);
        String salt = ShiroKit.getRandomSalt(5);
        String driverPassword = ShiroKit.md5(driverAccount.getDriverPassword(), salt);

        driverAccount.setDriverPassword(driverPassword);
        driverAccount.setSalt(salt);

        try
        {
            driverAccountService.resetPwd(driverAccount);
            return AjaxResult.success("200");
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("400");
        }

    }
}
