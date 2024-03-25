package onem.baymax.pan.server.module.file.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件夹表示枚举类
 *
 * @author hujiabin wrote in 2024/3/19 20:46
 */
@AllArgsConstructor
@Getter
public enum FolderFlagEnum {

    /**
     * 非文件夹
     */
    NO(0),
    /**
     * 是文件夹
     */
    YES(1);

    private final Integer code;

}
