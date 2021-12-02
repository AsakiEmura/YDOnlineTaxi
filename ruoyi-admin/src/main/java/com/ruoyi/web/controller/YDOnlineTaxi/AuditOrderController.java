package com.ruoyi.web.controller.YDOnlineTaxi;


import com.ruoyi.YDOnlineTaxi.domain.*;
import com.ruoyi.YDOnlineTaxi.service.*;
import com.ruoyi.YDOnlineTaxi.utils.RSAEncrypt;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ruoyi.YDOnlineTaxi.utils.JPushUtils.sendToRegistrationId;

@RestController
@RequestMapping("/YDOnlineTaxi/AuditOrder")
public class AuditOrderController extends BaseController {
    @Autowired
    private ArrivalAuditInformationService arrivalAuditInformationService;

    @Autowired
    private IOrderInformationService orderInformationService;

    @Autowired
    private IPonitsStatisticsService ponitsStatisticsService;

    @Autowired
    private IDriverInformationService driverInformationService;

    @Autowired
    private IDriverAccountService driverAccountService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 获取额外积分申请详细信息
     */
    @GetMapping(value = "/{orderId}")
    public List<ArrivalAuditInformation> getInfo(@PathVariable("orderId") String orderId)
    {
        return arrivalAuditInformationService.selectByPrimaryKey(orderId);
    }


    /**
     * 获取这个订单待审核额外积分申请个数
     */
    @PutMapping("/getHaveExtraNumber")
    public List<ArrivalAuditInformation> getHaveExtraNumber(String orderId)
    {
        return arrivalAuditInformationService.selectByPrimaryKeyHaveExtraNumber(orderId);
    }

    /**
     * 审核结果消息发送和更新
     */
    @PutMapping("/audit")
    public AjaxResult audit(ArrivalAuditInformation arrivalAuditInformation)
    {
        try {
            OrderInformation orderInformation = orderInformationService.selectOrderInformationByOrderId(arrivalAuditInformation.getOrderId());
            String phoneNumber = orderDetailsService.selectByPrimaryKey(arrivalAuditInformation.getOrderId()).getDriverPhoneNumber();
            String openId = driverAccountService.selectAllByPhoneNumber(phoneNumber).getMachineId();
//            openId = RSAEncrypt.decrypt(openId);

            List<String> registrationIds = new ArrayList<>();
            registrationIds.add(openId);
            if("待审核".equals(arrivalAuditInformation.getExtraPointsStatus())){
                return AjaxResult.success();
            }
            else if("审核不通过".equals(arrivalAuditInformation.getExtraPointsStatus())){
                arrivalAuditInformationService.updateByPrimaryKey(arrivalAuditInformation);
                sendToRegistrationId(registrationIds,"额外积分申请","额外积分申请","您的额外积分申请被拒绝");
                arrivalAuditInformationService.deleteByPrimaryKey(arrivalAuditInformation.getOrderId());
                return AjaxResult.success("操作成功");
            }
            else if("审核通过".equals(arrivalAuditInformation.getExtraPointsStatus())){
                List<ArrivalAuditInformation> arrivalAuditList = arrivalAuditInformationService.selectByPrimaryKeyHaveExtraNumber(arrivalAuditInformation.getOrderId());
                for (ArrivalAuditInformation auditInformation : arrivalAuditList) {
                    if ("停车积分".equals(auditInformation.getNotes())) {
                        orderInformation.setParkingFees(orderInformation.getParkingFees() + auditInformation.getExtraOrderPoints());
                        orderInformation.setDriverBase(orderInformation.getParkingFees() + orderInformation.getDriverBase());
                    } else if ("高速积分".equals(auditInformation.getNotes())) {
                        orderInformation.setTollFees(orderInformation.getTollFees() + auditInformation.getExtraOrderPoints());
                        orderInformation.setDriverBase(orderInformation.getTollFees() + orderInformation.getDriverBase());
                    }
                }
                orderInformationService.updateOrderInformation(orderInformation);
                arrivalAuditInformationService.updateByPrimaryKey(arrivalAuditInformation);
                sendToRegistrationId(registrationIds,"额外积分申请","额外积分申请","您的额外积分申请已通过");
//                arrivalAuditInformationService.deleteByPrimaryKey(arrivalAuditInformation.getOrderId());
                return AjaxResult.success("操作成功");
            }else {
                return AjaxResult.error();
            }
        }catch (Exception e) {
            return AjaxResult.error(e.toString());
        }
    }

    @PostMapping("/UploadEvidence")
    public AjaxResult UploadEvidence(String orderId, int extraPoints, MultipartFile photoOne, String reason) throws IOException {
        try
        {
            ArrivalAuditInformation arrivalAuditInformation = new ArrivalAuditInformation();
            // 上传文件路径
            String photoOnePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String photoOneName = FileUploadUtils.upload(photoOnePath, photoOne);
            String url = serverConfig.getUrl() + photoOneName;

            arrivalAuditInformation.setOrderId(orderId);
            arrivalAuditInformation.setExtraOrderPoints(extraPoints);
            arrivalAuditInformation.setProofPhoto1(url);
            arrivalAuditInformation.setNotes(reason);
            arrivalAuditInformation.setExtraPointsStatus("待审核");

            OrderInformation orderInformation = orderInformationService.selectOrderInformationByOrderId(orderId);
            arrivalAuditInformationService.insert(arrivalAuditInformation);
            orderInformation.setOrderStatus("待审核");
            orderInformationService.updateOrderInformation(orderInformation);

            AjaxResult ajax = AjaxResult.success();
            ajax.put("status", "ok");
            ajax.put("msg", "上传图片成功");
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
