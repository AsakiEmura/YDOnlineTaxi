package com.ruoyi.web.controller.YDOnlineTaxi;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.ruoyi.YDOnlineTaxi.domain.DriverFeedback;
import com.ruoyi.YDOnlineTaxi.service.IDriverFeedbackService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户反馈Controller
 * 
 * @author ruoyi
 * @date 2021-10-13
 */
@RestController
@RequestMapping("/YDOnlineTaxi/feedback")
public class DriverFeedbackController extends BaseController
{
    @Autowired
    private IDriverFeedbackService driverFeedbackService;

    /**
     * 查询用户反馈列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(DriverFeedback driverFeedback)
    {
        startPage();
        List<DriverFeedback> list = driverFeedbackService.selectDriverFeedbackList(driverFeedback);
        return getDataTable(list);
    }

    /**
     * 导出用户反馈列表
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:feedback:export')")
    @Log(title = "用户反馈", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DriverFeedback driverFeedback)
    {
        List<DriverFeedback> list = driverFeedbackService.selectDriverFeedbackList(driverFeedback);
        ExcelUtil<DriverFeedback> util = new ExcelUtil<DriverFeedback>(DriverFeedback.class);
        return util.exportExcel(list, "用户反馈数据");
    }

    /**
     * 获取用户反馈详细信息
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:feedback:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(driverFeedbackService.selectDriverFeedbackById(id));
    }

    /**
     * 新增用户反馈
     */
    @PostMapping
    public AjaxResult add(@RequestBody DriverFeedback driverFeedback)
    {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date = new Date();
        df.format(date);
        driverFeedback.setTime(date);
        return toAjax(driverFeedbackService.insertDriverFeedback(driverFeedback));
    }

    /**
     * 修改用户反馈
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:feedback:edit')")
    @Log(title = "用户反馈", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DriverFeedback driverFeedback)
    {
        return toAjax(driverFeedbackService.updateDriverFeedback(driverFeedback));
    }

    /**
     * 删除用户反馈
     */
    @PreAuthorize("@ss.hasPermi('YDOnlineTaxi:feedback:remove')")
    @Log(title = "用户反馈", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(driverFeedbackService.deleteDriverFeedbackByIds(ids));
    }
}
