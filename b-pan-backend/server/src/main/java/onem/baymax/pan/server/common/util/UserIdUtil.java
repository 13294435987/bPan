package onem.baymax.pan.server.common.util;

import lombok.experimental.UtilityClass;
import onem.baymax.pan.core.constant.BPanConstant;

import java.util.Objects;

/**
 * 用户ID存储工具类
 *
 * @author hujiabin wrote in 2024/3/17 21:07
 */
@UtilityClass
public class UserIdUtil {

    private static final ThreadLocal<Long> USER_ID_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置当前线程的用户ID
     *
     * @param userId 用户ID
     */
    public static void set(Long userId) {
        USER_ID_THREAD_LOCAL.set(userId);
    }

    /**
     * 获取当前线程的用户ID
     *
     * @return 用户ID
     */
    public static Long get() {
        Long userId = USER_ID_THREAD_LOCAL.get();
        if (Objects.isNull(userId)) {
            return BPanConstant.ZERO_LONG;
        }
        return userId;
    }
}
