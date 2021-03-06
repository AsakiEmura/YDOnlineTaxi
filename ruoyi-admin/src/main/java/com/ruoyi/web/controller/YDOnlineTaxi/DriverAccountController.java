package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.*;
import com.ruoyi.YDOnlineTaxi.service.*;
import com.ruoyi.YDOnlineTaxi.utils.JPushUtils;
import com.ruoyi.YDOnlineTaxi.utils.RSAEncrypt;
import com.ruoyi.YDOnlineTaxi.utils.WxService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroKit;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ruoyi.YDOnlineTaxi.utils.JPushUtils.sendToRegistrationId;

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

    @Autowired
    private WxWithDriversService wxWithDriversService;

    @Autowired
    private IPonitsStatisticsService ponitsStatisticsService;
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
     * 查询黑名单司机详细信息列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverAccount:list')")
    @GetMapping("/Blacklist")
    public TableDataInfo Blacklist() {
        startPage();
        List<DriverAccount> list = driverAccountService.selectAllByStatus("黑名单");
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

    /**
     * 审核司机详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverAccount:audit')")
    @Log(title = "审核司机", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody DriverAccount driverAccount) throws IOException {
        String phoneNumber =driverAccount.getPhoneNumber();
        if("审核通过".equals(driverAccount.getStatus()))
        {
            String driverName = driverAccount.getDriverName();


            driverAccountService.updateDriverAccount(driverAccount);

            DriverInformation driverInformation = new DriverInformation();
            driverInformation.setDriverName(driverAccount.getDriverName());
            driverInformation.setDriverPhoneNumber(driverAccount.getPhoneNumber());
            driverInformation.setDriverCarType(driverAccount.getMotorcycleType());
            driverInformation.setDriverCarId(driverAccount.getLicensePlateNumber());
            driverInformation.setDriverEmergencyContactPhoneNumber(driverAccount.getEmergencyContactNumber());

            WxWithDrivers wxWithDrivers = new WxWithDrivers();
            wxWithDrivers.setDriverName(driverAccount.getDriverName());
            wxWithDrivers.setMachineId(driverAccount.getMachineId());
            wxWithDrivers.setPhoneNumber(phoneNumber);

            PonitsStatistics ponitsStatistics = new PonitsStatistics();
            ponitsStatistics.setDriverName(driverName);
            ponitsStatistics.setDriverPhoneNumber(phoneNumber);

            driverInformationService.insertDriverInformation(driverInformation);
            wxWithDriversService.insert(wxWithDrivers);
            ponitsStatisticsService.insertPonitsStatistics(ponitsStatistics);

            List<String> registrationId = new ArrayList<>();
            registrationId.add(driverAccount.getMachineId());
            JPushUtils.sendToRegistrationId(registrationId,"司机审核结果","司机审核结果","审核通过,请登录app查看.");
        }
        else if("审核不通过".equals(driverAccount.getStatus()))
        {
            driverAccountService.deleteDriverAccountByIdNumber(driverAccount.getIdNumber());
        }
        return AjaxResult.success("审核操作成功");
    }



    /**
     * 移出黑名单
     */
    @Log(title = "移出黑名单", businessType = BusinessType.UPDATE)
    @PutMapping("/pushOut")
    public AjaxResult pushOut(@RequestBody DriverAccount driverAccount) {
        driverAccount.setStatus("审核通过");
        driverAccountService.updateDriverAccount(driverAccount);

        DriverInformation driverInformation = new DriverInformation();
        driverInformation.setDriverName(driverAccount.getDriverName());
        driverInformation.setDriverPhoneNumber(driverAccount.getPhoneNumber());
        driverInformation.setDriverCarType(driverAccount.getMotorcycleType());
        driverInformation.setDriverCarId(driverAccount.getLicensePlateNumber());
        driverInformation.setDriverEmergencyContactPhoneNumber(driverAccount.getEmergencyContactNumber());

        driverInformationService.insertDriverInformation(driverInformation);
        return AjaxResult.success("审核操作成功");
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
     * url：/YDOnlineTaxi/DriverAccount/refuseDriver
     * 拒绝通过司机
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverAccount:refuse')")
    @Log(title = "司机审核不通过", businessType = BusinessType.DELETE)
    @PostMapping("/refuseDriver")
    public void refuseDriver(@RequestBody Map<String, Object> data) {
        String phoneNumber = null;
        String refuseReason = null;
        try {
            phoneNumber = data.get("phoneNumber").toString();
            refuseReason = data.get("refuseReason").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DriverAccount reDriver = driverAccountService.selectAllByPhoneNumber(phoneNumber);
        String machineId = reDriver.getMachineId();
        String idNumber = reDriver.getIdNumber();
        String label = "您的申请被拒绝,拒绝理由为: ";

        List<String> registrationId = new ArrayList<>();
        registrationId.add(machineId);
        JPushUtils.sendToRegistrationId(registrationId,"司机审核结果","司机审核结果",label+refuseReason);

        driverAccountService.deleteDriverAccountByIdNumber(idNumber);
    }
}
