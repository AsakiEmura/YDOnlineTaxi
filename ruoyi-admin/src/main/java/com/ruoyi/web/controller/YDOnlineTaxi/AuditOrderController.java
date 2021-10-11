package com.ruoyi.web.controller.YDOnlineTaxi;


import com.ruoyi.YDOnlineTaxi.domain.ArrivalAuditInformation;
import com.ruoyi.YDOnlineTaxi.service.ArrivalAuditInformationService;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/YDOnlineTaxi/AuditOrder")
public class AuditOrderController extends BaseController {
    @Autowired
    private ArrivalAuditInformationService arrivalAuditInformationService;

    @Autowired
    private ServerConfig serverConfig;

    @PostMapping("/UploadEvidence")
    public AjaxResult UploadEvidence(ArrivalAuditInformation arrivalAuditInformation, MultipartFile photoOne, MultipartFile photoTwo) throws IOException {
        if(photoTwo != null){
            // 上传文件路径
            String photoOnePath = RuoYiConfig.getUploadPath();

            String photoTwoPath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String photoOneName = FileUploadUtils.upload(photoOnePath, photoOne);
            String urlOne = serverConfig.getUrl() + photoOneName;

            String photoTwoName = FileUploadUtils.upload(photoTwoPath, photoTwo);
            String urlTwo = serverConfig.getUrl() + photoTwoName;

            arrivalAuditInformation.setProofPhoto1(urlOne);
            arrivalAuditInformation.setProofPhoto2(urlTwo);
            return toAjax(arrivalAuditInformationService.insert(arrivalAuditInformation));
        }
        else{
            // 上传文件路径
            String photoOnePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String photoOneName = FileUploadUtils.upload(photoOnePath, photoOne);
            String urlOne = serverConfig.getUrl() + photoOneName;
            return toAjax(arrivalAuditInformationService.insert(arrivalAuditInformation));
        }
    }
}
