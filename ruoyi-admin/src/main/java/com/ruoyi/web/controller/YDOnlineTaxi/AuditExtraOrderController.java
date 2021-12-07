package com.ruoyi.web.controller.YDOnlineTaxi;

import java.util.*;

import com.ruoyi.YDOnlineTaxi.domain.*;
import com.ruoyi.YDOnlineTaxi.service.*;
import com.ruoyi.YDOnlineTaxi.utils.RSAEncrypt;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import static com.ruoyi.YDOnlineTaxi.utils.JPushUtils.sendToRegistrationId;

/**
 * 额外积分申请Controller
 * 
 * @author PenPen
 * @date 2021-10-13
 */
@RestController
@RequestMapping("/YDOnlineTaxi/order")
public class AuditExtraOrderController extends BaseController
{
    @Autowired
    private IAuditExtraOrderService auditExtraOrderService;

    @Autowired
    private IOrderInformationService orderInformationService;

    @Autowired
    private IDriverAccountService driverAccountService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private ArrivalAuditInformationService arrivalAuditInformationService;

    /**
     * 查询额外积分申请列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderInformation orderInformation)
    {
        startPage();
        List<OrderInformation> list = orderInformationService.selectOrderInformationList(orderInformation);
        return getDataTable(list);
    }

    /**
     * 导出额外积分申请列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:order:export')")
    @Log(title = "额外积分申请", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AuditExtraOrder auditExtraOrder)
    {
        List<AuditExtraOrder> list = auditExtraOrderService.selectAuditExtraOrderList(auditExtraOrder);
        ExcelUtil<AuditExtraOrder> util = new ExcelUtil<AuditExtraOrder>(AuditExtraOrder.class);
        return util.exportExcel(list, "额外积分申请数据");
    }

    /**
     * 获取额外积分申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:order:query')")
    @GetMapping(value = "/{orderId}")
    public List<AuditExtraOrder> getInfo(@PathVariable("orderId") String orderId)
    {
        return auditExtraOrderService.selectAuditExtraOrderByOrderId(orderId);
    }

    /**
     * 新增额外积分申请
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:order:add')")
    @Log(title = "额外积分申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AuditExtraOrder auditExtraOrder)
    {
        return toAjax(auditExtraOrderService.insertAuditExtraOrder(auditExtraOrder));
    }

    /**
     * 删除额外积分申请
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:order:remove')")
    @Log(title = "额外积分申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable String[] orderIds)
    {
        return toAjax(auditExtraOrderService.deleteAuditExtraOrderByOrderIds(orderIds));
    }

    /**
     * 审核结果消息发送和更新
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:audit_information:edit')")
    @Log(title = " 到达审核信息 ", businessType = BusinessType.UPDATE)
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
                        orderInformation.setParkingFees(orderInformation.getParkingFees().add(auditInformation.getExtraOrderPoints()));
                        orderInformation.setDriverBase(orderInformation.getParkingFees().add(orderInformation.getDriverBase()));
                    } else if ("高速积分".equals(auditInformation.getNotes())) {
                        orderInformation.setTollFees(orderInformation.getTollFees().add(auditInformation.getExtraOrderPoints()));
                        orderInformation.setDriverBase(orderInformation.getTollFees().add(orderInformation.getDriverBase()));
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
}
