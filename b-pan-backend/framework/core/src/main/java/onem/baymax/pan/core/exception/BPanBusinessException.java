package onem.baymax.pan.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import onem.baymax.pan.core.response.ResponseCode;

/**
 * 自定义全局业务异常类
 *
 * @author hujiabin wrote in 2024/3/12 21:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BPanBusinessException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    public BPanBusinessException(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getDesc();
    }

    public BPanBusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BPanBusinessException(String message) {
        this.code = ResponseCode.ERROR_PARAM.getCode();
        this.message = message;
    }

    public BPanBusinessException() {
        this.code = ResponseCode.ERROR_PARAM.getCode();
        this.message = ResponseCode.ERROR_PARAM.getDesc();
    }

}

