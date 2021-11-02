package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.DriverAccount;
import com.ruoyi.YDOnlineTaxi.domain.DriverInformation;
import com.ruoyi.YDOnlineTaxi.domain.PonitsStatistics;
import com.ruoyi.YDOnlineTaxi.service.IDriverAccountService;
import com.ruoyi.YDOnlineTaxi.service.IDriverInformationService;
import com.ruoyi.YDOnlineTaxi.service.IPonitsStatisticsService;
import com.ruoyi.YDOnlineTaxi.service.WxWithDriversService;
import com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.RabbitMQConfig;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ShiroKit;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/YDOnlineTaxi/WxService")
public class YDOnlineTaxiWxService extends BaseController {
    @Autowired
    private IDriverAccountService driverAccountService;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IPonitsStatisticsService ponitsStatisticsService;

    @Autowired
    private IDriverInformationService driverInformationService;

    @Autowired
    private WxWithDriversService wxWithDriversService;

    @PostMapping("/getMachineId")
    public AjaxResult getMachineId(String phoneNumber){
        String machineId = "";
        try{
            machineId = driverAccountService.selectMachineIdByPhoneNumber(phoneNumber);
            return AjaxResult.success(machineId);
        }
        catch (NullPointerException e){
            return AjaxResult.error();
        }
    }

    @PutMapping(value = "/{phoneNumber}")
    public AjaxResult changeMachineId(@PathVariable("phoneNumber") String phoneNumber,String machineId){
        if(wxWithDriversService.updateMachineIdByPhoneNumber(machineId,phoneNumber) && driverAccountService.updateMachineIdByPhoneNumber(machineId,phoneNumber)){
            return  AjaxResult.success("success");
        }
        else{
            return  AjaxResult.error("error");
        }
    }

    @PostMapping("/checkPhoneNumberUnique")
    public AjaxResult checkPhoneNumberUnique(String phoneNumber){
        if (UserConstants.NOT_UNIQUE.equals(driverAccountService.countByPhoneNumber(phoneNumber))) {
            return AjaxResult.success("exist");
        }
        else{
            return AjaxResult.success("notExist");
        }
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody DriverAccount driverAccount) {
        try {
            if (UserConstants.NOT_UNIQUE.equals(driverAccountService.checkIdNumberUnique(driverAccount.getIdNumber()))) {
                return AjaxResult.error("exist");
            }

            if (UserConstants.NOT_UNIQUE.equals(driverAccountService.countByPhoneNumber(driverAccount.getPhoneNumber()))) {
                return AjaxResult.error("exist");
            }

            if (driverAccount.getMachineId().equals("")) {
                return AjaxResult.error("400");
            }

            String salt = ShiroKit.getRandomSalt(5);
            String driverPassword = ShiroKit.md5(driverAccount.getDriverPassword(), salt);

            driverAccount.setDriverPassword(driverPassword);
            driverAccount.setSalt(salt);
            driverAccount.setStatus("待审核");


            driverAccountService.insertDriverAccount(driverAccount);
            rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE_NAME, RabbitMQConfig.DIRECT_ROUTINGKEY_NAME, driverAccount.getPhoneNumber() + " 待审核,请刷新界面!");

            return AjaxResult.success("200");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("400");
        }

    }

    @PostMapping("/register/uploadPhotos")
    public AjaxResult uploadImage(@RequestParam("files") MultipartFile file, @RequestParam("type") String type, @RequestParam("idCard") String idCard) {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;

            DriverAccount driverAccount = driverAccountService.selectDriverAccountByIdNumber(idCard);

            switch (type) {
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
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @PostMapping("/login")
    public AjaxResult login(DriverAccount driverAccount) {
        String phoneNumber;
        phoneNumber = driverAccount.getPhoneNumber();
        if (!UserConstants.NOT_UNIQUE.equals(driverAccountService.countByPhoneNumber(phoneNumber)) || phoneNumber == null) {
            return AjaxResult.error("notExist");
        }
        DriverAccount driver = driverAccountService.selectDriverPassWordByPhoneNumber(phoneNumber);
        if (!driver.getStatus().equals("审核通过")) {
            return AjaxResult.error("waitAudit");
        }

        String driverPassword;
        driverPassword = driverAccount.getDriverPassword();

        String salt = driver.getSalt();
        String password = driver.getDriverPassword();

        String newPassword = ShiroKit.md5(driverPassword, salt);

        if (!newPassword.equals(password) || driverPassword == null) {
            return AjaxResult.error("passwordError");
        }
        return AjaxResult.success("success");
    }


    @PostMapping("/resetPwd/{phoneNumber}")
    public AjaxResult resetPwd(@RequestBody Map<String, Object> password, @PathVariable("phoneNumber") String phoneNumber) {
        String newPassword;
        try {
            newPassword = password.get("password").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("304");
        }

        DriverAccount driverAccount = driverAccountService.selectAllByPhoneNumber(phoneNumber);
        driverAccount.setDriverPassword(newPassword);
        String salt = ShiroKit.getRandomSalt(5);
        String driverPassword = ShiroKit.md5(driverAccount.getDriverPassword(), salt);

        driverAccount.setDriverPassword(driverPassword);
        driverAccount.setSalt(salt);

        try {
            driverAccountService.resetPwd(driverAccount);
            return AjaxResult.success("200");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("400");
        }

    }


    /**
     * url：/YDOnlineTaxi/WxService/getOrderNumber
     * 获取完成的总订单数，本月订单数，司机积分
     * method: 'post'
     * 输入：【{“phoneNumber”}】
     * 输出：【{“msg”，“allOrderNumber”，“monthOrderNumber”，“allPoint”， "driverLevel"}】
     */
    @PostMapping("/getOrderNumber")
    public Map<String, Object> getOrderNumber(@RequestBody Map<String, Object> data) {
        Map<String, Object> orderNumber = new HashMap<>();
        String phoneNumber;
        try {
            phoneNumber = data.get("phoneNumber").toString();
        } catch (Exception e) {
            e.printStackTrace();
            //输入数据错误
            orderNumber.put("msg", "40020");
            return orderNumber;
        }

        try {
            orderNumber.put("msg", "成功");
            DriverInformation driverInformation = driverInformationService.selectDriverInformationByDriverPhoneNumber(phoneNumber);
            PonitsStatistics ponitsStatistics = ponitsStatisticsService.selectPonitsStatisticsByDriverPhoneNumber(phoneNumber);
            if (driverInformation != null) {
                orderNumber.put("allOrderNumber", driverInformation.getDriverCompleteOrderNumber());
                orderNumber.put("monthOrderNumber", driverInformation.getDriverCompleteOrderNumberMonthly());
                orderNumber.put("driverLevel", driverInformation.getDriverLevel());
            } else {
                orderNumber.put("allOrderNumber", 0);
                orderNumber.put("monthOrderNumber", 0);
                orderNumber.put("driverLevel", "青铜司机");
                orderNumber.put("msg", "未知");
            }
            if (ponitsStatistics != null) {
                orderNumber.put("allPoint", ponitsStatistics.getTotalPoints());
            } else {
                orderNumber.put("allPoint", "未知");
                orderNumber.put("msg", "未知");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //输入数据错误
            orderNumber.put("msg", "ELSE");
            return orderNumber;
        }
        return orderNumber;
    }

    @PostMapping("/getDriverInformation")
    public DriverInformation getDriverInformation(@RequestBody DriverInformation driverInformation) {
        String phoneNumber = driverInformation.getDriverPhoneNumber();
        if (phoneNumber == null) {
            return null;
        }
        DriverInformation information = driverInformationService.selectDriverInformationByDriverPhoneNumber(phoneNumber);
        return information;
    }


    @PostMapping("/updateDriverInformation")
    public AjaxResult updateDriverInformation(@RequestBody DriverInformation driverInformation) {
        return toAjax(driverInformationService.updateDriverInformation(driverInformation));
    }

    @PostMapping("/countByPhoneNumber")
    public AjaxResult countByPhoneNumber(DriverAccount driverAccount) {
        if (!UserConstants.NOT_UNIQUE.equals(driverAccountService.countByPhoneNumber(driverAccount.getPhoneNumber()))) {
            return AjaxResult.error("notExist");
        } else {
            return AjaxResult.success("exist");
        }
    }
}
