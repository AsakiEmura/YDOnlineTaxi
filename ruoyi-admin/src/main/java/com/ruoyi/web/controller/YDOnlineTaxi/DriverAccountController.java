package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.DriverAccount;
import com.ruoyi.YDOnlineTaxi.domain.DriverInformation;
import com.ruoyi.YDOnlineTaxi.service.impl.DriverAccountServiceImpl;
import com.ruoyi.YDOnlineTaxi.service.impl.DriverInformationServiceImpl;
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
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 查询司机详细信息列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverAccount:list')")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<DriverAccount> list = driverAccountService.selectAllByStatus("待审核");
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

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverAccount:resetPwd')")
    @Log(title = "司机密码修改", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody DriverAccount driverAccount) {
        String salt = ShiroKit.getRandomSalt(5);
        String driverPassword = ShiroKit.md5(driverAccount.getDriverPassword(), salt);

        driverAccount.setDriverPassword(driverPassword);
        driverAccount.setSalt(salt);
        driverAccount.setUpdateBy(getUsername());
        return toAjax(driverAccountService.resetPwd(driverAccount));
    }
}
