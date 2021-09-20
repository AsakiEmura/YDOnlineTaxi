package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.MQProperties;
import com.ruoyi.YDOnlineTaxi.domain.DriverAccount;
import com.ruoyi.YDOnlineTaxi.domain.DriverInformation;
import com.ruoyi.YDOnlineTaxi.domain.PonitsStatistics;
import com.ruoyi.YDOnlineTaxi.domain.WxWithDrivers;
import com.ruoyi.YDOnlineTaxi.service.*;
import com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.Producer.RabbitMQProducer;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/YDOnlineTaxi/WxService")
public class YDOnlineTaxiWxService extends BaseController {
    @Autowired
    private IDriverAccountService driverAccountService;

    @Autowired
    private ServerConfig serverConfig;


    @Autowired
    private IPonitsStatisticsService ponitsStatisticsService;

    @Autowired
    private IDriverInformationService driverInformationService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired(required = false)
    private MQProperties mqProperties;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;


    @PostMapping("/register")
    public AjaxResult register(@RequestBody DriverAccount driverAccount) {
        try {
            if (UserConstants.NOT_UNIQUE.equals(driverAccountService.checkIdNumberUnique(driverAccount.getIdNumber()))) {
                return AjaxResult.error("exist");
            }

            if (UserConstants.NOT_UNIQUE.equals(driverAccountService.countByPhoneNumber(driverAccount.getPhoneNumber()))) {
                return AjaxResult.error("exist");
            }

            String salt = ShiroKit.getRandomSalt(5);
            String driverPassword = ShiroKit.md5(driverAccount.getDriverPassword(), salt);

            driverAccount.setDriverPassword(driverPassword);
            driverAccount.setSalt(salt);
            driverAccount.setStatus("待审核");


            driverAccountService.insertDriverAccount(driverAccount);
            rabbitTemplate.convertAndSend(mqProperties.getDefaultExchange(), mqProperties.getRouteKey(), driverAccount.getPhoneNumber() + " 待审核,请刷新界面!");

            return AjaxResult.success("200");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("400");
        }

    }

    @PostMapping("/register/uploadPhotos")
    public AjaxResult uploadImage(@RequestParam("files") MultipartFile file, @RequestParam("type") String type, @RequestParam("idCard") String idCard) throws Exception {
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
    public AjaxResult login(@RequestBody Map<String, Object> data) {
        String phoneNumber;
        try {
            phoneNumber = data.get("phoneNumber").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("jsonError");
        }

        if (!UserConstants.NOT_UNIQUE.equals(driverAccountService.countByPhoneNumber(phoneNumber))) {
            return AjaxResult.error("notExist");
        }
        DriverAccount driverAccount = driverAccountService.selectDriverPassWordByPhoneNumber(phoneNumber);
        if (!driverAccount.getStatus().equals("审核通过")) {
            return AjaxResult.error("waitAudit");
        }


        String driverPassword;
        try {
            driverPassword = data.get("driverPassword").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("jsonError");
        }

        String salt = driverAccount.getSalt();
        String password = driverAccount.getDriverPassword();

        String newPassword = ShiroKit.md5(driverPassword, salt);

        if (!newPassword.equals(password)) {
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
}
