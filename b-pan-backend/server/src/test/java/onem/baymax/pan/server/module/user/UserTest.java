package onem.baymax.pan.server.module.user;

import junit.framework.TestCase;
import onem.baymax.pan.core.exception.BPanBusinessException;
import onem.baymax.pan.core.util.JwtUtil;
import onem.baymax.pan.server.BPanServerLauncher;
import onem.baymax.pan.server.module.user.constant.UserConstant;
import onem.baymax.pan.server.module.user.context.*;
import onem.baymax.pan.server.module.user.service.IUserService;
import onem.baymax.pan.server.module.user.vo.UserInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户模块单元测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BPanServerLauncher.class)
@Transactional
public class UserTest extends TestCase {

    @Resource
    private IUserService userService;

    /**
     * 测试成功注册用户信息
     */
    @Test
    public void testRegisterUser() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;
    }

    /**
     * 测试重复用户名称注册幂等
     */
    @Test(expected = BPanBusinessException.class)
    public void testRegisterDuplicateUsername() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;
        userService.register(context);
    }

    /**
     * 测试登陆成功
     */
    @Test
    public void loginSuccess() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;

        UserLoginContext userLoginContext = createUserLoginContext();
        String accessToken = userService.login(userLoginContext);

        assert StringUtils.isNotBlank(accessToken);
    }

    /**
     * 测试登录失败：用户名不正确
     */
    @Test(expected = BPanBusinessException.class)
    public void wrongUsername() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;

        UserLoginContext userLoginContext = createUserLoginContext();
        userLoginContext.setUsername(userLoginContext.getUsername() + "_change");
        userService.login(userLoginContext);
    }

    /**
     * 测试登录失败：密码不正确
     */
    @Test(expected = BPanBusinessException.class)
    public void wrongPassword() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;

        UserLoginContext userLoginContext = createUserLoginContext();
        userLoginContext.setPassword(userLoginContext.getPassword() + "_change");
        userService.login(userLoginContext);
    }

    /**
     * 用户成功登出
     */
    @Test
    public void exitSuccess() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;

        UserLoginContext userLoginContext = createUserLoginContext();
        String accessToken = userService.login(userLoginContext);

        assert StringUtils.isNotBlank(accessToken);

        Long userId = (Long) JwtUtil.analyzeToken(accessToken, UserConstant.LOGIN_USER_ID);

        userService.exit(userId);
    }

    /**
     * 校验用户名称通过
     */
    @Test
    public void checkUsernameSuccess() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;

        CheckUsernameContext checkUsernameContext = new CheckUsernameContext();
        checkUsernameContext.setUsername(USERNAME);
        String question = userService.checkUsername(checkUsernameContext);
        assert StringUtils.isNotBlank(question);
    }

    /**
     * 校验用户名称失败-没有查询到该用户
     */
    @Test(expected = BPanBusinessException.class)
    public void checkUsernameNotExist() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;

        CheckUsernameContext checkUsernameContext = new CheckUsernameContext();
        checkUsernameContext.setUsername(USERNAME + "_change");
        userService.checkUsername(checkUsernameContext);
    }

    /**
     * 校验用户密保问题答案通过
     */
    @Test
    public void checkAnswerSuccess() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;

        CheckAnswerContext checkAnswerContext = new CheckAnswerContext();
        checkAnswerContext.setUsername(USERNAME);
        checkAnswerContext.setQuestion(QUESTION);
        checkAnswerContext.setAnswer(ANSWER);

        String token = userService.checkAnswer(checkAnswerContext);

        assert StringUtils.isNotBlank(token);
    }

    /**
     * 校验用户密保问题答案失败
     */
    @Test(expected = BPanBusinessException.class)
    public void checkAnswerFail() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;

        CheckAnswerContext checkAnswerContext = new CheckAnswerContext();
        checkAnswerContext.setUsername(USERNAME);
        checkAnswerContext.setQuestion(QUESTION);
        checkAnswerContext.setAnswer(ANSWER + "_change");

        userService.checkAnswer(checkAnswerContext);
    }


    /**
     * 正常重置用户密码
     */
    @Test
    public void resetPasswordSuccess() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;

        CheckAnswerContext checkAnswerContext = new CheckAnswerContext();
        checkAnswerContext.setUsername(USERNAME);
        checkAnswerContext.setQuestion(QUESTION);
        checkAnswerContext.setAnswer(ANSWER);

        String token = userService.checkAnswer(checkAnswerContext);

        assert StringUtils.isNotBlank(token);

        ResetPasswordContext resetPasswordContext = new ResetPasswordContext();
        resetPasswordContext.setUsername(USERNAME);
        resetPasswordContext.setPassword(PASSWORD + "_change");
        resetPasswordContext.setToken(token);

        userService.resetPassword(resetPasswordContext);
    }

    /**
     * 用户重置密码失败-token异常
     */
    @Test(expected = BPanBusinessException.class)
    public void resetPasswordTokenError() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;

        CheckAnswerContext checkAnswerContext = new CheckAnswerContext();
        checkAnswerContext.setUsername(USERNAME);
        checkAnswerContext.setQuestion(QUESTION);
        checkAnswerContext.setAnswer(ANSWER);

        String token = userService.checkAnswer(checkAnswerContext);

        assert StringUtils.isNotBlank(token);

        ResetPasswordContext resetPasswordContext = new ResetPasswordContext();
        resetPasswordContext.setUsername(USERNAME);
        resetPasswordContext.setPassword(PASSWORD + "_change");
        resetPasswordContext.setToken(token + "_change");

        userService.resetPassword(resetPasswordContext);
    }

    /**
     * 正常在线修改密码
     */
    @Test
    public void changePasswordSuccess() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;

        ChangePasswordContext changePasswordContext = new ChangePasswordContext();

        changePasswordContext.setUserId(register);
        changePasswordContext.setOldPassword(PASSWORD);
        changePasswordContext.setNewPassword(PASSWORD + "_change");

        userService.changePassword(changePasswordContext);
    }

    /**
     * 修改密码失败-旧密码错误
     */
    @Test(expected = BPanBusinessException.class)
    public void changePasswordFailByWrongOldPassword() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;

        ChangePasswordContext changePasswordContext = new ChangePasswordContext();

        changePasswordContext.setUserId(register);
        changePasswordContext.setOldPassword(PASSWORD + "_change");
        changePasswordContext.setNewPassword(PASSWORD + "_change");

        userService.changePassword(changePasswordContext);
    }

    @Test
    public void testQueryUserInfo() {

        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        assert register > 0L;

        UserInfoVo userInfoVo = userService.info(register);
        assert userInfoVo != null;
    }


    private final static String USERNAME = "baymax";
    private final static String PASSWORD = "123456789";
    private final static String QUESTION = "question";
    private final static String ANSWER = "answer";

    /**
     * 构建注册用户上下文信息
     *
     * @return UserRegisterContext对象
     */
    private UserRegisterContext createUserRegisterContext() {
        UserRegisterContext context = new UserRegisterContext();
        context.setUsername(USERNAME);
        context.setPassword(PASSWORD);
        context.setQuestion(QUESTION);
        context.setAnswer(ANSWER);
        return context;
    }

    /**
     * 构建用户登录上下文实体
     *
     * @return UserLoginContext对象
     */
    private UserLoginContext createUserLoginContext() {
        UserLoginContext userLoginContext = new UserLoginContext();
        userLoginContext.setUsername(USERNAME);
        userLoginContext.setPassword(PASSWORD);
        return userLoginContext;
    }

}