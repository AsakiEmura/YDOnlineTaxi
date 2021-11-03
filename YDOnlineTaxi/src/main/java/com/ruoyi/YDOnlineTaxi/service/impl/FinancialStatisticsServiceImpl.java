package com.ruoyi.YDOnlineTaxi.service.impl;

import com.ruoyi.YDOnlineTaxi.domain.FinancialStatistics;
import com.ruoyi.YDOnlineTaxi.mapper.FinancialStatisticsMapper;
import com.ruoyi.YDOnlineTaxi.service.IFinancialStatisticsService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 财务统计Service业务层处理
 *
 * @author Asaki
 * @date 2021-11-03
 */
@Service
public class FinancialStatisticsServiceImpl implements IFinancialStatisticsService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private IFinancialStatisticsService financialStatisticsService;

    @Autowired(required = false)
    private FinancialStatisticsMapper financialStatisticsMapper;

    /**
     * 查询财务统计
     *
     * @param orderId 财务统计主键
     * @return 财务统计
     */
    @Override
    public FinancialStatistics selectFinancialStatisticsByOrderId(String orderId) {
        return financialStatisticsMapper.selectFinancialStatisticsByOrderId(orderId);
    }

    /**
     * 查询财务统计列表
     *
     * @param financialStatistics 财务统计
     * @return 财务统计
     */
    @Override
    public List<FinancialStatistics> selectFinancialStatisticsList(FinancialStatistics financialStatistics) {
        return financialStatisticsMapper.selectFinancialStatisticsList(financialStatistics);
    }

    /**
     * 新增财务统计
     *
     * @param financialStatistics 财务统计
     * @return 结果
     */
    @Override
    public int insertFinancialStatistics(FinancialStatistics financialStatistics) {
        String TelNumber = getTelNumber(financialStatistics.getDriverInformation());
        financialStatistics.setDriverPhoneNumber(TelNumber);
        return financialStatisticsMapper.insertFinancialStatistics(financialStatistics);
    }

    /**
     * 修改财务统计
     *
     * @param financialStatistics 财务统计
     * @return 结果
     */
    @Override
    public int updateFinancialStatistics(FinancialStatistics financialStatistics) {
        return financialStatisticsMapper.updateFinancialStatistics(financialStatistics);
    }

    /**
     * 批量删除财务统计
     *
     * @param orderIds 需要删除的财务统计主键
     * @return 结果
     */
    @Override
    public int deleteFinancialStatisticsByOrderIds(String[] orderIds) {
        return financialStatisticsMapper.deleteFinancialStatisticsByOrderIds(orderIds);
    }

    /**
     * 删除财务统计信息
     *
     * @param orderId 财务统计主键
     * @return 结果
     */
    @Override
    public int deleteFinancialStatisticsByOrderId(String orderId) {
        return financialStatisticsMapper.deleteFinancialStatisticsByOrderId(orderId);
    }

    @Override
    public String importOrder(List<FinancialStatistics> orderList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(orderList) || orderList.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (FinancialStatistics order : orderList) {
            try {
                if (order.getOrderId() != "") {
                    // 验证是否存在这个订单数据
                    FinancialStatistics o = financialStatisticsService.selectFinancialStatisticsByOrderId(order.getOrderId());
                    if (StringUtils.isNull(o)) {
                        order.setCreateBy(operName);

                        SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
                        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time1 = hms.format(order.getDateOf());
                        String time2 = ymd.format(order.getCreationDate());
                        Date time3 = df.parse(time2 + " " + time1);
                        order.setDateOf(time3);
                        order.setCreationDate(time3);

                        String TelNumber = getTelNumber(order.getDriverInformation());
                        order.setDriverPhoneNumber(TelNumber);
                        if (TelNumber.length() != 11 || TelNumber == null)
                            failureMsg.append("<br/>" + failureNum + "、预约号为 " + order.getOrderId() + " 的司机手机号长度非正常,请手动查询!");
                        this.insertFinancialStatistics(order);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、预约号为 " + order.getOrderId() + " 的订单信息导入成功");
                    } else if (isUpdateSupport) {
                        order.setCreateBy(operName);

                        SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
                        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time1 = hms.format(order.getDateOf());
                        String time2 = ymd.format(order.getCreationDate());
                        Date time3 = df.parse(time2 + " " + time1);
                        order.setDateOf(time3);
                        order.setCreationDate(time3);

                        String TelNumber = getTelNumber(order.getDriverInformation());
                        order.setDriverPhoneNumber(TelNumber);
                        if (TelNumber.length() != 11 || TelNumber == null)
                            failureMsg.append("<br/>" + failureNum + "、预约号为 " + order.getOrderId() + " 的司机手机号长度非正常,请手动查询!");

                        order.setUpdateBy(operName);
                        this.updateFinancialStatistics(order);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、预约号为 " + order.getOrderId() + " 的订单信息导更新成功");
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、预约号为 " + order.getOrderId() + " 的订单信息已存在");
                    }
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、订单预约号不能为空!");
                }

            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、预约号为 " + order.getOrderId() + " 的订单信息导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            return failureMsg.toString();
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    public static String getTelNumber(String sParam) {
        if (sParam == null || sParam.length() <= 0)
            return null;
        Pattern pattern = Pattern.compile("(1|861)([0-9])\\d{9}$*");
        Matcher matcher = pattern.matcher(sParam);
        StringBuilder bf = new StringBuilder();
        while (matcher.find()) {
            bf.append(matcher.group()).append(",");
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
        }
        return bf.toString();
    }

    @Override
    public List<FinancialStatistics> selectAllByConditions(String driverPhoneNumber, String minTime, String maxTime) {
        return financialStatisticsMapper.selectAllByConditions(driverPhoneNumber,minTime,maxTime);
    }
}
