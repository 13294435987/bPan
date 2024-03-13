package onem.baymax.pan.web.exception;

import onem.baymax.pan.core.exception.BPanBusinessException;
import onem.baymax.pan.core.exception.BPanFrameworkException;
import onem.baymax.pan.core.response.R;
import onem.baymax.pan.core.response.ResponseCode;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理器
 *
 * @author hujiabin wrote in 2024/3/13 20:58
 */
@RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(value = BPanBusinessException.class)
    public R<String> rPanBusinessExceptionHandler(BPanBusinessException e) {
        return R.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().stream().findFirst()
                .orElseThrow(() -> new NullPointerException("no ObjectError"));
        return R.fail(ResponseCode.ERROR_PARAM.getCode(), objectError.getDefaultMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public R<String> constraintDeclarationExceptionHandler(ConstraintViolationException e) {
        ConstraintViolation<?> constraintViolation = e.getConstraintViolations().stream().findFirst()
                .orElseThrow(() -> new NullPointerException("no ConstraintViolation"));
        return R.fail(ResponseCode.ERROR_PARAM.getCode(), constraintViolation.getMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public R<String> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        return R.fail(ResponseCode.ERROR_PARAM);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public R<String> illegalStateExceptionHandler(IllegalStateException e) {
        return R.fail(ResponseCode.ERROR_PARAM);
    }

    @ExceptionHandler(value = BindException.class)
    public R<String> bindExceptionHandler(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().stream().findFirst()
                .orElseThrow(() -> new NullPointerException("no FieldError"));
        return R.fail(ResponseCode.ERROR_PARAM.getCode(), fieldError.getDefaultMessage());
    }

    @ExceptionHandler(value = BPanFrameworkException.class)
    public R<String> bPanFrameworkExceptionHandler(BPanFrameworkException e) {
        return R.fail(ResponseCode.ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public R<String> runtimeExceptionHandler(RuntimeException e) {
        return R.fail(ResponseCode.ERROR.getCode(), e.getMessage());
    }

}

