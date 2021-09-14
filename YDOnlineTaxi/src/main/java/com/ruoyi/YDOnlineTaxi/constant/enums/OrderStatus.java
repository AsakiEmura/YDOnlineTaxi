package com.ruoyi.YDOnlineTaxi.constant.enums;

public enum OrderStatus {
    WAIT_DISPATCHED, WAIT_SET_OUT,WAIT_ARRIVED,WAIT_AUDIT, WAIT_PAYMENT , FINISH
    ;

    @Override
    public String toString() {
        switch (this){
            case WAIT_DISPATCHED:
                return "待派单";
            case WAIT_AUDIT:
                return "待审核";
            case WAIT_PAYMENT:
                return "待支付";
            case FINISH:
                return "结单";
            default:
                return "未知";
        }
    }
}
