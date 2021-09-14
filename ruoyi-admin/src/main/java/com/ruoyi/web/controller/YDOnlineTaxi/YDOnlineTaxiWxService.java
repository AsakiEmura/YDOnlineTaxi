package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.Properties.MQProperties;
import com.ruoyi.YDOnlineTaxi.domain.VO.DriverAccount;
import com.ruoyi.YDOnlineTaxi.domain.VO.WxWithDrivers;
import com.ruoyi.YDOnlineTaxi.service.IDriverAccountService;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;
import com.ruoyi.YDOnlineTaxi.utils.RabbitMQService;
import com.ruoyi.YDOnlineTaxi.service.WxWithDriversService;
import com.ruoyi.YDOnlineTaxi.utils.RSAEncrypt;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.ShiroKit;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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
    private WxWithDriversService wxWithDriversService;

    @Autowired
    private RedisCache redisUtils;

    @Autowired
    private RabbitMQService rabbitMQService;

    @Autowired
    private IOrderInformationService orderInformationService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired(required = false)
    private MQProperties mqProperties;

    @Autowired(required = false)
    private RSAEncrypt rsaEncrypt;

    private RestTemplate restTemplate;


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

                String salt = ShiroKit.getRandomSalt(5);
                String driverPassword = ShiroKit.md5(driverAccount.getDriverPassword(), salt);

                driverAccount.setDriverPassword(driverPassword);
                driverAccount.setSalt(salt);
                driverAccount.setStatus("待审核");


                driverAccountService.insertDriverAccount(driverAccount);
                rabbitTemplate.convertAndSend(mqProperties.getDefaultExchange(), mqProperties.getRouteKey(), driverAccount.getPhoneNumber() + " 待审核,请刷新界面!");

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

        String newPassword = ShiroKit.md5(driverPassword,salt);

        if(!newPassword.equals(password)){
            return AjaxResult.error("passwordError");
        }

        return AjaxResult.success("success");
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


    @PostMapping("/getCarTypeArray")
    public String[] getCarTypeArray(@RequestBody Map<String, Object> data) {
        String phoneNumber = null;
        try {
            phoneNumber = data.get("phoneNumber").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String carType = driverAccountService.selectDriverPassWordByPhoneNumber(phoneNumber).getMotorcycleType();
        String[] carTypeArray = {"舒适型", "商务型", "豪华型", "豪华商务型"};
        if (carTypeArray[0].equals(carType)) {
            //舒适型的司机只能看舒适型的订单
            return new String[]{"舒适型"};
        }else if (carTypeArray[1].equals(carType)) {
            //商务型的司机能看舒适型和商务型的订单
            return new String[]{"舒适型", "商务型"};
        }else if(carTypeArray[2].equals(carType)) {
            //豪华型的司机能看舒适型和豪华型的订单
            return new String[]{"舒适型", "豪华型"};
        }else {
            //豪华商务型的司机能看所有的订单
            return new String[]{"舒适型", "商务型", "豪华型", "豪华商务型"};
        }
    }

    @GetMapping("/getUserOpenId")
    public List<String> getOpenIdList(){
        List<String> openIdList = wxWithDriversService.selectOpenIdByPushTimesGreaterThan(0);
        try{
            for (String openId : openIdList) {
                WxWithDrivers wxWithDrivers = wxWithDriversService.selectAllByOpenId(openId);
                int times = wxWithDrivers.getPushTimes() - 1;
                wxWithDrivers.setPushTimes(times);
                wxWithDriversService.updateByOpenId(wxWithDrivers);
            }
        }catch (Exception e){
            return null;
        }
        return openIdList;
    }

}
