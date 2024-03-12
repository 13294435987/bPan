package onem.baymax.pan.core.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author hujiabin wrote in 2024/3/12 21:15
 */
@UtilityClass
public class UUIDUtil {
    public static final String EMPTY_STR = "";
    public static final String HYPHEN_STR = "-";

    /**
     * 获取UUID字符串
     *
     * @return UUID 大写
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replace(HYPHEN_STR, EMPTY_STR).toUpperCase();
    }
}
