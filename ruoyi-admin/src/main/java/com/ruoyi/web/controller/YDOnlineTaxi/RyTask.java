package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.service.IPonitsStatisticsService;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("ryTaskTest")
public class RyTask {
    @Autowired
    private IPonitsStatisticsService ponitsStatisticsService;

    public void clearPoints(){
        ponitsStatisticsService.updateMonthPoints();
    }

    public void testTask(){
        ponitsStatisticsService.updateMonthPoints();
        System.out.println("现在时间为"+ new Date());
    }
}
