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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 积分奖惩日志Controller
 *
 * @author ruoyi
 * @date 2021-09-21
 */
@RestController
@RequestMapping("/YDOnlineTaxi/RPlog")
public class RewardsPunishmentsLogController extends BaseController
{
    @Autowired
    private IRewardsPunishmentsLogService rewardsPunishmentsLogService;

    /**
     * 查询积分奖惩日志列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:RPlog:list')")
    @GetMapping("/list")
    public TableDataInfo list(RewardsPunishmentsLog rewardsPunishmentsLog)
    {
        startPage();
        List<RewardsPunishmentsLog> list = rewardsPunishmentsLogService.selectRewardsPunishmentsLogList(rewardsPunishmentsLog);
        return getDataTable(list);
    }

    /**
     * 导出积分奖惩日志列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:RPlog:export')")
    @Log(title = "积分奖惩日志", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RewardsPunishmentsLog rewardsPunishmentsLog)
    {
        List<RewardsPunishmentsLog> list = rewardsPunishmentsLogService.selectRewardsPunishmentsLogList(rewardsPunishmentsLog);
        ExcelUtil<RewardsPunishmentsLog> util = new ExcelUtil<RewardsPunishmentsLog>(RewardsPunishmentsLog.class);
        return util.exportExcel(list, "积分奖惩日志数据");
    }

    /**
     * 获取积分奖惩日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:RPlog:query')")
    @GetMapping(value = "/{phoneNumber}")
    public AjaxResult getInfo(@PathVariable("phoneNumber") String phoneNumber)
    {
        return AjaxResult.success(rewardsPunishmentsLogService.selectRewardsPunishmentsLogByPhoneNumber(phoneNumber));
    }

    /**
     * 新增积分奖惩日志
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:RPlog:add')")
    @Log(title = "积分奖惩日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RewardsPunishmentsLog rewardsPunishmentsLog)
    {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date = new Date();
        df.format(date);
        rewardsPunishmentsLog.setOperatingTime(date);
        rewardsPunishmentsLog.setOperatingPeople(getUsername());
        return toAjax(rewardsPunishmentsLogService.insertRewardsPunishmentsLog(rewardsPunishmentsLog));
    }

    /**
     * 修改积分奖惩日志
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:RPlog:edit')")
    @Log(title = "积分奖惩日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RewardsPunishmentsLog rewardsPunishmentsLog)
    {
        return toAjax(rewardsPunishmentsLogService.updateRewardsPunishmentsLog(rewardsPunishmentsLog));
    }

    /**
     * 删除积分奖惩日志
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:RPlog:remove')")
    @Log(title = "积分奖惩日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{phoneNumbers}")
    public AjaxResult remove(@PathVariable String[] phoneNumbers)
    {
        return toAjax(rewardsPunishmentsLogService.deleteRewardsPunishmentsLogByPhoneNumbers(phoneNumbers));
    }
}
