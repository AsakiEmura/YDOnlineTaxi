package com.ruoyi.YDOnlineTaxi.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户反馈对象 driver_feedback
 * 
 * @author ruoyi
 * @date 2021-10-13
 */
public class DriverFeedback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phoneNumber;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 用户反馈 */
    @Excel(name = "用户反馈")
    private String feedback;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date time;

    /** 消息id */
    private Long id;

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setFeedback(String feedback) 
    {
        this.feedback = feedback;
    }

    public String getFeedback() 
    {
        return feedback;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("phoneNumber", getPhoneNumber())
            .append("name", getName())
            .append("feedback", getFeedback())
            .append("time", getTime())
            .append("id", getId())
            .toString();
    }
}
