package com.xxw.platform.module.common.constants;

public class Constants {

    public static class CommonConstant {

        /**
         * onlineTopic
         */
        public static final String ONLINE_TOPIC = "sds/onlineTopic";

        /**
         * onlineTopic
         */
        public static final String WILL_TOPIC = "sds/willTopic";

        /**
         * errorTopic
         */
        public static final String ERROR_TOPIC = "sds/errorTopic";

    }

    public static enum CommonIntegerConstant {

        ENABLE(1, "启用"),
        DISABLE(2, "禁用"),
        SUCCESS(1, "成功"),
        FAILURE(2, "失败"),
        YES(1, "是"),
        NO(2, "否"),
        PORTION_SUCCESS(3, "部分成功"),
        ARRIVALS(1, "进港"),
        DEPARTURE(2, "出港"),
        PUSH(1, "推送"),
        PULL(2, "拉取"),
        FILL_PUSH(3, "补推"),
        FILLPUSH_TYPE_NETWORK(1, "补推类型-网点"),
        FILLPUSH_TYPE_ADDRESS(2, "补推类型-地址"),
        FILLPUSH_TYPE_ALL(3, "补推类型-全网"),
        STATUS_AWAIT_INTERCEPT(1, "待拦截"),
        STATUS_ALREADY_INTERCEPT(2, "已拦截"),
        STATUS_UNDONE(3, "已撤销"),
        ORDER_TYPE_ORDER(1, "正常"),
        ORDER_TYPE_RETURN(2, "退回件"),
        ORDER_TYPE_TRANSFER(3, "转寄件"),
        ORDER_TYPE_CANCELLATION(4, "订单取消"),
        PUSH_ORDER(1, "订单"),
        PUSH_RETURN(2, "退回件"),
        PUSH_TRANSFER(3, "转寄件"),
        PUSH_INTERCEPT(4, "拦截件"),
        PUSH_ELECTRONIC_PACKAGE(5, "一段码包牌"),
        PUSH_SECOND_PACKAGE(6, "二段码包牌"),
        PUSH_THIRD_PACKAGE(8, "三段码包牌"),
        PUSH_CANCELLATION(7, "订单取消");

        private CommonIntegerConstant(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        private final Integer value;
        private final String name;

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    public static enum SortingAbnormalityConstant {

        ABNORMALITY_111("111", "正常读码"),
        ABNORMALITY_992("992", "综合异常口"),
        ABNORMALITY_993("993", "未识别条码"),
        ABNORMALITY_994("994", "多条码"),
        ABNORMALITY_995("995", "未获取三段码信息"),
        ABNORMALITY_996("996", "未配置三段码格口"),
        ABNORMALITY_997("997", "超最大循环"),
        ABNORMALITY_998("998", "取消件"),
        ABNORMALITY_999("999", "拦截件");

        private SortingAbnormalityConstant(String value, String name) {
            this.value = value;
            this.name = name;
        }

        private final String value;
        private final String name;

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

}
