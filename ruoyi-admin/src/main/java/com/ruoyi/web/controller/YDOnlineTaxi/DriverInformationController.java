package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.DriverAccount;
import com.ruoyi.YDOnlineTaxi.domain.DriverInformation;
import com.ruoyi.YDOnlineTaxi.service.IDriverAccountService;
import com.ruoyi.YDOnlineTaxi.service.IDriverInformationService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 司机线上账户信息
Controller
 * 
 * @author ruoyi
 * @date 2021-09-08
 */
@RestController
@RequestMapping("/YDOnlineTaxi/DriverInformation")
public class DriverInformationController extends BaseController
{
    @Autowired
    private IDriverInformationService driverInformationService;

    @Autowired
    private IDriverAccountService driverAccountService;

    /**
     * 查询司机线上账户信息列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverInformation:list')")
    @GetMapping("/list")
    public TableDataInfo list(DriverInformation driverInformation)
    {
        startPage();
        List<DriverInformation> list = driverInformationService.selectDriverInformationList(driverInformation);
        return getDataTable(list);
    }

    /**
     * 导出司机线上账户信息列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverInformation:export')")
    @Log(title = "司机线上账户信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DriverInformation driverInformation)
    {
        List<DriverInformation> list = driverInformationService.selectDriverInformationList(driverInformation);
        ExcelUtil<DriverInformation> util = new ExcelUtil<DriverInformation>(DriverInformation.class);
        return util.exportExcel(list, "司机线上账户信息数据");
    }

    /**
     * 获取司机线上账户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverInformation:query')")
    @GetMapping(value = "/{driverPhoneNumber}")
    public AjaxResult getInfo(@PathVariable("driverPhoneNumber") String driverPhoneNumber)
    {
        return AjaxResult.success(driverInformationService.selectDriverInformationByDriverPhoneNumber(driverPhoneNumber));
    }

    /**
     * 新增司机线上账户信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverInformation:add')")
    @Log(title = "司机线上账户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DriverInformation driverInformation)
    {
        return toAjax(driverInformationService.insertDriverInformation(driverInformation));
    }

    /**
     * 修改司机线上账户信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverInformation:edit')")
    @Log(title = "司机线上账户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DriverInformation driverInformation)
    {
        return toAjax(driverInformationService.updateDriverInformation(driverInformation));
    }

    /**
     * 移到黑名单并删除该司机
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverInformation:remove')")
    @Log(title = "司机线上账户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{driverPhoneNumbers}")
    public AjaxResult remove(@PathVariable String[] driverPhoneNumbers)
    {
        for (String driverPhoneNumber : driverPhoneNumbers) {
            DriverAccount driverAccount = driverAccountService.selectAllByPhoneNumber(driverPhoneNumber);
            driverAccount.setStatus("黑名单");
            driverAccountService.updateDriverAccount(driverAccount);
        }
        return toAjax(driverInformationService.deleteDriverInformationByDriverPhoneNumbers(driverPhoneNumbers));
    }

}
