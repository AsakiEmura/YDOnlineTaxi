package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.VO.DriverAccount;
import com.ruoyi.YDOnlineTaxi.domain.VO.DriverInformation;
import com.ruoyi.YDOnlineTaxi.domain.VO.WxWithDrivers;
import com.ruoyi.YDOnlineTaxi.service.IDriverAccountService;
import com.ruoyi.YDOnlineTaxi.service.IDriverInformationService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroKit;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private IDriverAccountService driverAccountService;

    @Autowired
    private IDriverInformationService driverInformationService;
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

    @Log(title = "司机密码修改", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody DriverAccount driverAccount) {
        if(driverAccount.getStatus().equals("审核通过"))
        {
            DriverInformation driverInformation = new DriverInformation();
            driverInformation.setDriverName(driverAccount.getDriverName());
            driverInformation.setDriverPhoneNumber(driverAccount.getPhoneNumber());
            driverInformation.setDriverCarType(driverAccount.getMotorcycleType());
            driverInformation.setDriverCarId(driverAccount.getLicensePlateNumber());
            driverInformation.setDriverEmergencyContactPhoneNumber(driverAccount.getEmergencyContactNumber());

            WxWithDrivers wxWithDrivers = new WxWithDrivers();
            wxWithDrivers.setDriverName(driverAccount.getDriverName());
            wxWithDrivers.setOpenId(driverAccount.getOpenId());
            wxWithDrivers.setPhoneNumber(driverAccount.getDriverPassword());
            wxWithDrivers.setPushTimes(5);



            driverInformationService.insertDriverInformation(driverInformation);
        }
        return toAjax(driverAccountService.resetPwd(driverAccount));
    }
}
