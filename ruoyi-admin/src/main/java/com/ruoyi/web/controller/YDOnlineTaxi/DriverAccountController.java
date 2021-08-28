package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.DriverAccount;
import com.ruoyi.YDOnlineTaxi.service.impl.DriverAccountServiceImpl;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroKit;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 司机详细信息Controller
 *
 * @author Asaki
 * @date 2021-08-25
 */
@RestController
@RequestMapping("/YDOnlineTaxi/DriverAccount")
public class DriverAccountController extends BaseController {
    @Autowired
    private DriverAccountServiceImpl driverAccountService;

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询司机详细信息列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverAccount:list')")
    @GetMapping("/list")
    public TableDataInfo list(DriverAccount driverAccount) {
        startPage();
        List<DriverAccount> list = driverAccountService.selectDriverAccountList(driverAccount);
        return getDataTable(list);
    }

    /**
     * 导出司机详细信息列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverAccount:export')")
    @Log(title = "司机详细信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DriverAccount driverAccount) {
        List<DriverAccount> list = driverAccountService.selectDriverAccountList(driverAccount);
        ExcelUtil<DriverAccount> util = new ExcelUtil<DriverAccount>(DriverAccount.class);
        return util.exportExcel(list, "司机详细信息数据");
    }

    /**
     * 获取司机详细信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverAccount:query')")
    @GetMapping(value = "/{idNumber}")
    public AjaxResult getInfo(@PathVariable("idNumber") String idNumber) {
        return AjaxResult.success(driverAccountService.selectDriverAccountByIdNumber(idNumber));
    }

    /**
     * 新增司机详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverAccount:add')")
    @Log(title = "司机详细信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DriverAccount driverAccount) {
        if (UserConstants.NOT_UNIQUE.equals(driverAccountService.checkIdNumberUnique(driverAccount.getIdNumber()))) {
            return AjaxResult.error("账号已存在!");
        }
        String salt = ShiroKit.getRandomSalt(5);
        String driverPassword = ShiroKit.md5(driverAccount.getDriverPassword(), salt);

        driverAccount.setDriverPassword(driverPassword);
        driverAccount.setSalt(salt);
        driverAccount.setStatus("待审核");
        return toAjax(driverAccountService.insertDriverAccount(driverAccount));
    }

    /**
     * 修改司机详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverAccount:edit')")
    @Log(title = "司机详细信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DriverAccount driverAccount) {
        return toAjax(driverAccountService.updateDriverAccount(driverAccount));
    }

    /**
     * 删除司机详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverAccount:remove')")
    @Log(title = "司机详细信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{idNumbers}")
    public AjaxResult remove(@PathVariable String[] idNumbers) {
        return toAjax(driverAccountService.deleteDriverAccountByIdNumbers(idNumbers));
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody DriverAccount driverAccount) {
        if (UserConstants.NOT_UNIQUE.equals(driverAccountService.checkIdNumberUnique(driverAccount.getIdNumber()))) {
            return AjaxResult.error("账号已存在!");
        }
        String salt = ShiroKit.getRandomSalt(5);
        String driverPassword = ShiroKit.md5(driverAccount.getDriverPassword(), salt);

        driverAccount.setDriverPassword(driverPassword);
        driverAccount.setSalt(salt);
        return AjaxResult.success("注册成功");
    }

    //    @RequestMapping("/register/uploadImg")
//    public AjaxResult uploadImg(@RequestBody DriverAccount driverAccount) {
//
//    }
    public String changeImgToBase64(MultipartFile file) {
        //创建base64对象
        BASE64Encoder encoder = new BASE64Encoder();
        //获取文件后缀
        String filename = file.getOriginalFilename();
        String fileType = "";
        String image = "";
        if (filename != null)
            fileType = filename.substring(filename.lastIndexOf("."));
        try {
            //图片前缀
            if (".jpg".equals(fileType))
                fileType = "jpeg";
            else
                fileType = "png";
            //base64结果
            image = "data:image/" + fileType + ";base64," + encoder.encode(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
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
                    driverAccount.setIdPhotoFront(url);
                case "observe_id":
                    driverAccount.setIdPhotoFront(url);
                case "car_id":
                    driverAccount.setIdPhotoFront(url);
                case "driver_id":
                    driverAccount.setIdPhotoFront(url);
            }

            driverAccountService.insertDriverAccount(driverAccount);

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
}
