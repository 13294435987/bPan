package onem.baymax.pan.server.module.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import onem.baymax.pan.core.response.R;
import onem.baymax.pan.core.util.IdUtil;
import onem.baymax.pan.server.common.annotation.LoginIgnore;
import onem.baymax.pan.server.common.util.UserIdUtil;
import onem.baymax.pan.server.module.user.context.*;
import onem.baymax.pan.server.module.user.converter.UserConverter;
import onem.baymax.pan.server.module.user.po.*;
import onem.baymax.pan.server.module.user.service.IUserService;
import onem.baymax.pan.server.module.user.vo.UserInfoVo;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * UserController
 *
 * @author hujiabin wrote in 2024/3/16 22:27
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserController {

    @Resource
    private UserConverter userConverter;

    @Resource
    private IUserService userService;

    /**
     * 用户注册接口
     *
     * @param userRegisterPo 用户注册参数
     * @return userId (加密)
     */
    @ApiOperation(
            value = "用户注册接口",
            notes = "该接口提供了用户注册的功能，实现了冥等性注册的逻辑，可以放心多并发调用",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @LoginIgnore
    @PostMapping("/register")
    public R<String> register(@Validated @RequestBody UserRegisterPo userRegisterPo) {
        UserRegisterContext userRegisterContext = userConverter.userRegisterPo2UserRegisterContext(userRegisterPo);
        Long userId = userService.register(userRegisterContext);
        return R.data(IdUtil.encrypt(userId));
    }

    /**
     * 登录接口
     *
     * @param userLoginPo 用户登录参数
     * @return token
     */
    @ApiOperation(
            value = "用户登录接口",
            notes = "该接口提供了用户登录的功能，成功登陆之后，会返回有时效性的accessToken供后续服务使用",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @LoginIgnore
    @PostMapping("/login")
    public R<String> login(@Validated @RequestBody UserLoginPo userLoginPo) {
        UserLoginContext userLoginContext = userConverter.userLoginPo2UserLoginContext(userLoginPo);
        String accessToken = userService.login(userLoginContext);
        return R.data(accessToken);
    }

    /**
     * 用户登出接口
     *
     * @return R
     */
    @ApiOperation(
            value = "用户登出接口",
            notes = "该接口提供了用户登出的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PostMapping("/exit")
    public R<?> exit() {
        userService.exit(UserIdUtil.get());
        return R.success();
    }

    /**
     * 用户忘记密码-校验用户名
     *
     * @param checkUsernamePo 校验用户名参数
     * @return question
     */
    @ApiOperation(
            value = "用户忘记密码-校验用户名",
            notes = "该接口提供了用户忘记密码-校验用户名的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @LoginIgnore
    @PostMapping("/username/check")
    public R<String> checkUsername(@Validated @RequestBody CheckUsernamePo checkUsernamePo) {
        CheckUsernameContext checkUsernameContext = userConverter.checkUsernamePo2CheckUsernameContext(checkUsernamePo);
        String question = userService.checkUsername(checkUsernameContext);
        return R.data(question);
    }


    /**
     * 用户忘记密码-校验密保答案
     *
     * @param checkAnswerPo 参数
     * @return token
     */
    @ApiOperation(
            value = "用户忘记密码-校验密保答案",
            notes = "该接口提供了用户忘记密码-校验密保答案的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @LoginIgnore
    @PostMapping("/answer/check")
    public R<String> checkAnswer(@Validated @RequestBody CheckAnswerPo checkAnswerPo) {
        CheckAnswerContext checkAnswerContext = userConverter.checkAnswerPo2CheckAnswerContext(checkAnswerPo);
        String token = userService.checkAnswer(checkAnswerContext);
        return R.data(token);
    }

    /**
     * 用户忘记密码-重置新密码
     *
     * @param resetPasswordPo 重置用户密码参数
     * @return R
     */
    @ApiOperation(
            value = "用户忘记密码-重置新密码",
            notes = "该接口提供了用户忘记密码-重置新密码的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PostMapping("/password/reset")
    @LoginIgnore
    public R<?> resetPassword(@Validated @RequestBody ResetPasswordPo resetPasswordPo) {
        ResetPasswordContext resetPasswordContext = userConverter.resetPasswordPo2ResetPasswordContext(resetPasswordPo);
        userService.resetPassword(resetPasswordContext);
        return R.success();
    }

    /**
     * 用户在线修改密码
     *
     * @param changePasswordPo 用户在线修改密码参数
     * @return R
     */
    @ApiOperation(
            value = "用户在线修改密码",
            notes = "该接口提供了用户在线修改密码的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PostMapping("/password/change")
    public R<?> changePassword(@Validated @RequestBody ChangePasswordPo changePasswordPo) {
        ChangePasswordContext changePasswordContext = userConverter.changePasswordPo2ChangePasswordContext(changePasswordPo);
        changePasswordContext.setUserId(UserIdUtil.get());
        userService.changePassword(changePasswordContext);
        return R.success();
    }

    /**
     * 查询登录用户的基本信息
     *
     * @return vo
     */
    @ApiOperation(
            value = "查询登录用户的基本信息",
            notes = "该接口提供了查询登录用户的基本信息的功能",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @GetMapping("/")
    public R<UserInfoVo> info() {
        UserInfoVo userInfoVo = userService.info(UserIdUtil.get());
        return R.data(userInfoVo);
    }

}

