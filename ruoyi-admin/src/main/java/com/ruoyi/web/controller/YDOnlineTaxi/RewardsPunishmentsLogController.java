package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.domain.RewardsPunishmentsLog;
import com.ruoyi.YDOnlineTaxi.service.IRewardsPunishmentsLogService;
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
 * 积分奖惩日志Controller
 *
 * @author ruoyi
 * @date 2021-09-16
 */
@RestController
@RequestMapping("/YDOnlineTaxi/log")
public class RewardsPunishmentsLogController extends BaseController {
    @Autowired
    private IRewardsPunishmentsLogService rewardsPunishmentsLogService;

    /**
     * 查询积分奖惩日志列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(RewardsPunishmentsLog rewardsPunishmentsLog) {
        startPage();
        List<RewardsPunishmentsLog> list = rewardsPunishmentsLogService.selectRewardsPunishmentsLogList(rewardsPunishmentsLog);
        return getDataTable(list);
    }

    /**
     * 导出积分奖惩日志列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:log:export')")
    @Log(title = "积分奖惩日志", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RewardsPunishmentsLog rewardsPunishmentsLog) {
        List<RewardsPunishmentsLog> list = rewardsPunishmentsLogService.selectRewardsPunishmentsLogList(rewardsPunishmentsLog);
        ExcelUtil<RewardsPunishmentsLog> util = new ExcelUtil<RewardsPunishmentsLog>(RewardsPunishmentsLog.class);
        return util.exportExcel(list, "积分奖惩日志数据");
    }

    /**
     * 获取积分奖惩日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:log:query')")
    @GetMapping(value = "/{phoneNumber}")
    public AjaxResult getInfo(@PathVariable("phoneNumber") String phoneNumber) {
        return AjaxResult.success(rewardsPunishmentsLogService.selectRewardsPunishmentsLogByPhoneNumber(phoneNumber));
    }

    /**
     * 新增积分奖惩日志
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:log:add')")
    @Log(title = "积分奖惩日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody RewardsPunishmentsLog rewardsPunishmentsLog) {
        return toAjax(rewardsPunishmentsLogService.insertRewardsPunishmentsLog(rewardsPunishmentsLog));
    }

    /**
     * 修改积分奖惩日志
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:log:edit')")
    @Log(title = "积分奖惩日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RewardsPunishmentsLog rewardsPunishmentsLog) {
        return toAjax(rewardsPunishmentsLogService.updateRewardsPunishmentsLog(rewardsPunishmentsLog));
    }

    /**
     * 删除积分奖惩日志
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:log:remove')")
    @Log(title = "积分奖惩日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{phoneNumbers}")
    public AjaxResult remove(@PathVariable String[] phoneNumbers) {
        return toAjax(rewardsPunishmentsLogService.deleteRewardsPunishmentsLogByPhoneNumbers(phoneNumbers));
    }
}
