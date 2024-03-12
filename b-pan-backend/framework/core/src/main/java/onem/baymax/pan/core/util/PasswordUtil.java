package onem.baymax.pan.core.util;

import lombok.experimental.UtilityClass;

/**
 * 密码工具类
 *
 * @author hujiabin wrote in 2024/3/12 21:31
 */
@UtilityClass
@SuppressWarnings("all")
public class PasswordUtil {
    /**
     * 随机生成盐值
     *
     * @return
     */
    public static String getSalt() {
        return MessageDigestUtil.md5(UUIDUtil.getUuid());
    }

    /**
     * 密码加密
     *
     * @param salt
     * @param inputPassword
     * @return
     */
    public static String encryptPassword(String salt, String inputPassword) {
        return MessageDigestUtil.sha256(MessageDigestUtil.sha1(inputPassword) + salt);
    }
}
