package com.mmall.common;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;

/**
 * @Author lcy
 * @Date 2020/2/24
 * @Description 常量类
 */
public class Const {
    public static final String CURRENT_USER = "currentUser";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String NULL = "null";
    public static final String TOKEN_PREFIX = "Token_";


    public interface RedisCacheExtime {
        int REDIS_SESSION_EXTIME = 60 * 30;
    }


    /**
     * 角色状态信息
     */
    public interface Role {
        int ROLE_CUSTOMER = 0;//普通用户
        int ROLE_ADMIN = 1;//管理员
    }

    /**
     * 购物车的选中状态 以及 数量限制状态
     */
    public interface Cart {
        int CHECKED = 1;//购物车选中状态
        int UN_CHECKED = 0;//购物车未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";

    }

    /**
     * 产品的状态信息
     */
    @Getter
    public enum ProductStatusEnum {
        /**
         * 在线
         */
        ON_SALE(1, "在线");
        /**
         * 状态码
         */
        private int code;
        /**
         * 状态值
         */
        private String value;

        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }
    }


    /**
     * 订单状态
     */
    @Getter
    public enum OrderStatusEnum {
        /**
         * 已取消
         */
        CANCELED(0, "已取消"),
        /**
         * 未支付
         */
        NO_PAY(10, "未支付"),
        /**
         * 已付款
         */
        PAID(20, "已付款"),
        /**
         * 已发货
         */
        SHIPPED(30, "已发货"),
        /**
         * 订单完成
         */
        ORDER_SUCCESS(40, "订单完成"),
        /**
         * 订单关闭
         */
        ORDER_CLOSE(50, "订单关闭"),
        ;

        /**
         * 订单状态码
         */
        private int code;

        /**
         * 订单状态
         */
        private String value;

        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的订单状态");
        }

    }

    /**
     * 交易状态说明
     */
    public interface AlipayCallback {
        /**
         * 交易创建，等待买家付款
         */
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        /**
         * 交易支付成功
         */
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    @Getter
    public enum PayPlatformEnum {
        /**
         * 支付宝支付
         */
        ALIPAY(1, "支付宝");
        /**
         * 支付平台码
         */
        private int code;

        /**
         * 支付平台
         */
        private String value;

        PayPlatformEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }
    }

    @Getter
    public enum PaymentTypeEnum {
        /**
         * 在线支付
         */
        ONLINE_PAY(1, "在线支付");
        /**
         * 支付方式码
         */
        private int code;

        /**
         * 支付方式
         */
        private String value;

        PaymentTypeEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public static PaymentTypeEnum codeOf(int code) {
            for (PaymentTypeEnum paymentTypeEnum : values()) {
                if (paymentTypeEnum.getCode() == code) {
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应的支付方式");
        }
    }


    /**
     * 排序
     */
    public interface ProductListOrderBy {
        //为什么用Set 因为Set的Contain的复杂度是O(n) List的Contain的复杂度是O(n^2)
        //HashSet 底层的去重 也是通过HashMap实现的
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }
}
