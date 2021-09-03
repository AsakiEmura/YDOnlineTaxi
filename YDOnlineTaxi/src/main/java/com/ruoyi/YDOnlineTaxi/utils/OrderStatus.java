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

}
