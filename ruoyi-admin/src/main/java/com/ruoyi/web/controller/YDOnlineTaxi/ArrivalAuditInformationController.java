package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.ArrivalAuditInformation;
import com.ruoyi.YDOnlineTaxi.service.IArrivalAuditInformationService;
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
 * 到达审核信息Controller
 *
 * @author ruoyi
 * @date 2021-09-19
 */
@RestController
@RequestMapping("/YDOnlineTaxi/audit_information")
public class ArrivalAuditInformationController extends BaseController {
    @Autowired
    private IArrivalAuditInformationService arrivalAuditInformationService;

    /**
     * 查询到达审核信息列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:audit_information:list')")
    @GetMapping("/list")
    public TableDataInfo list(ArrivalAuditInformation arrivalAuditInformation) {
        startPage();
        List<ArrivalAuditInformation> list = arrivalAuditInformationService.selectArrivalAuditInformationList(arrivalAuditInformation);
        return getDataTable(list);
    }

    /**
     * 导出到达审核信息列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:audit_information:export')")
    @Log(title = "到达审核信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ArrivalAuditInformation arrivalAuditInformation) {
        List<ArrivalAuditInformation> list = arrivalAuditInformationService.selectArrivalAuditInformationList(arrivalAuditInformation);
        ExcelUtil<ArrivalAuditInformation> util = new ExcelUtil<ArrivalAuditInformation>(ArrivalAuditInformation.class);
        return util.exportExcel(list, "到达审核信息数据");
    }

    /**
     * 获取到达审核信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:audit_information:query')")
    @GetMapping(value = "/{orderid}")
    public AjaxResult getInfo(@PathVariable("orderid") String orderid) {
        return AjaxResult.success(arrivalAuditInformationService.selectArrivalAuditInformationByOrderid(orderid));
    }

    /**
     * 新增到达审核信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:audit_information:add')")
    @Log(title = "到达审核信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ArrivalAuditInformation arrivalAuditInformation) {
        return toAjax(arrivalAuditInformationService.insertArrivalAuditInformation(arrivalAuditInformation));
    }

    /**
     * 修改到达审核信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:audit_information:edit')")
    @Log(title = "到达审核信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ArrivalAuditInformation arrivalAuditInformation) {
        return toAjax(arrivalAuditInformationService.updateArrivalAuditInformation(arrivalAuditInformation));
    }

    /**
     * 删除到达审核信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:audit_information:remove')")
    @Log(title = "到达审核信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderids}")
    public AjaxResult remove(@PathVariable String[] orderids) {
        return toAjax(arrivalAuditInformationService.deleteArrivalAuditInformationByOrderids(orderids));
    }
}
