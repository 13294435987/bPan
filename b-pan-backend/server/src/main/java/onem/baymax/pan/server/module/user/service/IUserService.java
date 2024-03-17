package onem.baymax.pan.server.module.user.service;

import onem.baymax.pan.server.module.user.context.*;
import onem.baymax.pan.server.module.user.entity.BPanUser;
import com.baomidou.mybatisplus.extension.service.IService;
import onem.baymax.pan.server.module.user.vo.UserInfoVo;

/**
 * @author hujiabin
 * @description 针对表【b_pan_user(用户信息表)】的数据库操作Service
 * @createDate 2024-03-14 11:30:08
 */
public interface IUserService extends IService<BPanUser> {

    /**
     * 用户注册业务
     *
     * @param userRegisterContext context
     * @return userId
     */
    Long register(UserRegisterContext userRegisterContext);

    /**
     * 用户登录业务
     *
     * @param userLoginContext context
     * @return token
     */
    String login(UserLoginContext userLoginContext);

    /**
     * 用户退出登录
     *
     * @param userId 用户ID
     */
    void exit(Long userId);

    /**
     * 用户忘记密码-校验用户名
     *
     * @param checkUsernameContext context
     * @return question
     */
    String checkUsername(CheckUsernameContext checkUsernameContext);

    /**
     * 用户忘记密码-校验密保答案
     *
     * @param checkAnswerContext context
     * @return token
     */
    String checkAnswer(CheckAnswerContext checkAnswerContext);

    /**
     * 重置用户密码
     *
     * @param resetPasswordContext context
     */
    void resetPassword(ResetPasswordContext resetPasswordContext);

    /**
     * 在线修改密码
     *
     * @param changePasswordContext context
     */
    void changePassword(ChangePasswordContext changePasswordContext);

    /**
     * 查询在线用户的基本信息
     *
     * @param userId 用户ID
     * @return vo
     */
    UserInfoVo info(Long userId);
}
