package com.zzxx.travel.util;

import java.util.UUID;

/**
 * 产生UUID随机字符串工具类
 */
public final class UuidUtil {
    private UuidUtil() {
    }

    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        System.out.println(UuidUtil.getUuid());
        System.out.println(UuidUtil.getUuid());
        System.out.println(UuidUtil.getUuid());
        System.out.println(UuidUtil.getUuid());
    }
}
