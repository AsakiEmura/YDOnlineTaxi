package com.ruoyi.YDOnlineTaxi.utils;

public enum OrderStatus {
    WAIT_DISPATCH {
        public String getStatus() {
            return "待派单";
        }
    },
    WAIT_AUDIT {
        public String getStatus() {
            return "待派单";
        }
    },
    WAIT_PAYMENT {
        public String getStatus() {
            return "待支付";
        }
    },
    FINISH {
        public String getStatus() {
            return "结单";
        }
    },
    ;

    @Override
    public String toString() {
        switch (this){
            case WAIT_DISPATCH:
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
