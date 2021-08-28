package com.ruoyi.web.controller.YDOnlineTaxi;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.YDOnlineTaxi.domain.DriverInformation;
import com.ruoyi.YDOnlineTaxi.service.IDriverInformationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 司机线上账户信息
Controller
 * 
 * @author ruoyi
 * @date 2021-08-28
 */
@RestController
@RequestMapping("/YDOnlineTaxi/DriverInformation")
public class DriverInformationController extends BaseController
{
    @Autowired
    private IDriverInformationService driverInformationService;

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
     * 导出司机线上账户信息
列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverInformation:export')")
    @Log(title = "司机线上账户信息 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DriverInformation driverInformation)
    {
        List<DriverInformation> list = driverInformationService.selectDriverInformationList(driverInformation);
        ExcelUtil<DriverInformation> util = new ExcelUtil<DriverInformation>(DriverInformation.class);
        return util.exportExcel(list, "司机线上账户信息 数据");
    }

    /**
     * 获取司机线上账户信息
详细信息
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
     * 删除司机线上账户信息

     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:DriverInformation:remove')")
    @Log(title = "司机线上账户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{driverPhoneNumbers}")
    public AjaxResult remove(@PathVariable String[] driverPhoneNumbers)
    {
        return toAjax(driverInformationService.deleteDriverInformationByDriverPhoneNumbers(driverPhoneNumbers));
    }
}
