package onem.baymax.pan.server.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import onem.baymax.pan.cache.core.constant.CacheConstant;
import onem.baymax.pan.core.exception.BPanBusinessException;
import onem.baymax.pan.core.response.ResponseCode;
import onem.baymax.pan.core.util.IdUtil;
import onem.baymax.pan.core.util.JwtUtil;
import onem.baymax.pan.core.util.PasswordUtil;
import onem.baymax.pan.server.module.file.constant.FileConstant;
import onem.baymax.pan.server.module.file.context.CreateFolderContext;
import onem.baymax.pan.server.module.file.entity.BPanUserFile;
import onem.baymax.pan.server.module.file.service.IUserFileService;
import onem.baymax.pan.server.module.user.constant.UserConstant;
import onem.baymax.pan.server.module.user.context.*;
import onem.baymax.pan.server.module.user.converter.UserConverter;
import onem.baymax.pan.server.module.user.entity.BPanUser;
import onem.baymax.pan.server.module.user.service.IUserService;
import onem.baymax.pan.server.module.user.mapper.BPanUserMapper;
import onem.baymax.pan.server.module.user.vo.UserInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author hujiabin
 * @description 针对表【b_pan_user(用户信息表)】的数据库操作Service实现
 * @createDate 2024-03-14 11:30:08
 */
@Service(value = "userService")
public class UserServiceImpl extends ServiceImpl<BPanUserMapper, BPanUser>
        implements IUserService {

    @Resource
    private UserConverter userConverter;

    @Resource
    private IUserFileService userFileService;

    @Resource
    private CacheManager cacheManager;

    @Override
    public Long register(UserRegisterContext userRegisterContext) {
        assembleUserEntity(userRegisterContext);
        doRegister(userRegisterContext);
        createUserRootFolder(userRegisterContext);
        return userRegisterContext.getEntity().getUserId();
    }

    @Override
    public String login(UserLoginContext userLoginContext) {
        // 1、用户的登录信息校验
        checkLoginInfo(userLoginContext);
        // 2、生成一个具有时效性的accessToken
        // 3、将accessToken缓存起来，去实现单机登录
        generateAndSaveAccessToken(userLoginContext);
        return userLoginContext.getAccessToken();
    }

    @Override
    public void exit(Long userId) {
        try {
            Cache cache = cacheManager.getCache(CacheConstant.B_PAN_CACHE_NAME);
            assert cache != null;
            cache.evict(UserConstant.USER_LOGIN_PREFIX + userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BPanBusinessException("用户退出登录失败");
        }
    }

    @Override
    public String checkUsername(CheckUsernameContext checkUsernameContext) {
        String question = baseMapper.selectQuestionByUsername(checkUsernameContext.getUsername());
        if (StringUtils.isBlank(question)) {
            throw new BPanBusinessException("没有此用户");
        }
        return question;
    }

    @Override
    public String checkAnswer(CheckAnswerContext checkAnswerContext) {
        QueryWrapper<BPanUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", checkAnswerContext.getUsername());
        queryWrapper.eq("question", checkAnswerContext.getQuestion());
        queryWrapper.eq("answer", checkAnswerContext.getAnswer());
        int count = count(queryWrapper);

        if (count == 0) {
            throw new BPanBusinessException("密保答案错误");
        }

        return generateCheckAnswerToken(checkAnswerContext);
    }

    @Override
    public void resetPassword(ResetPasswordContext resetPasswordContext) {
        checkForgetPasswordToken(resetPasswordContext);
        checkAndResetUserPassword(resetPasswordContext);
    }

    @Override
    public void changePassword(ChangePasswordContext changePasswordContext) {
        checkOldPassword(changePasswordContext);
        doChangePassword(changePasswordContext);
        exitLoginStatus(changePasswordContext);
    }

    @Override
    public UserInfoVo info(Long userId) {
        BPanUser entity = getById(userId);
        if (Objects.isNull(entity)) {
            throw new BPanBusinessException("用户信息查询失败");
        }

        BPanUserFile bPanUserFile = userFileService.getUserRootFile(userId);
        if (Objects.isNull(bPanUserFile)) {
            throw new BPanBusinessException("查询用户根文件夹信息失败");
        }

        return userConverter.assembleUserInfoVO(entity, null);
    }

    private void assembleUserEntity(UserRegisterContext userRegisterContext) {
        BPanUser entity = userConverter.userRegisterContext2bPanUser(userRegisterContext);
        String salt = PasswordUtil.getSalt(),
                dbPassword = PasswordUtil.encryptPassword(salt, userRegisterContext.getPassword());
        entity.setUserId(IdUtil.get());
        entity.setSalt(salt);
        entity.setPassword(dbPassword);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        userRegisterContext.setEntity(entity);
    }

    private void doRegister(UserRegisterContext userRegisterContext) {
        BPanUser entity = userRegisterContext.getEntity();
        if (Objects.nonNull(entity)) {
            try {
                if (!save(entity)) {
                    throw new BPanBusinessException("用户注册失败");
                }
            } catch (DuplicateKeyException duplicateKeyException) {
                // 需要捕获数据库的唯一索引冲突异常，来实现全局用户名称唯一
                throw new BPanBusinessException("用户名已存在");
            }
            return;
        }
        throw new BPanBusinessException(ResponseCode.ERROR);
    }

    private void createUserRootFolder(UserRegisterContext userRegisterContext) {
        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setParentId(FileConstant.TOP_PARENT_ID);
        createFolderContext.setUserId(userRegisterContext.getEntity().getUserId());
        createFolderContext.setFolderName(FileConstant.ALL_FILE_CN_STR);
        userFileService.createFolder(createFolderContext);
    }

    /**
     * 校验用户名密码
     *
     * @param userLoginContext context
     */
    private void checkLoginInfo(UserLoginContext userLoginContext) {
        String username = userLoginContext.getUsername();
        String password = userLoginContext.getPassword();

        BPanUser entity = getBpanUserByUsername(username);
        if (Objects.isNull(entity)) {
            throw new BPanBusinessException("用户名称不存在");
        }

        String salt = entity.getSalt();
        String encPassword = PasswordUtil.encryptPassword(salt, password);
        String dbPassword = entity.getPassword();
        if (!StringUtils.equals(encPassword, dbPassword)) {
            throw new BPanBusinessException("密码信息不正确");
        }

        userLoginContext.setEntity(entity);
    }


    /**
     * 生成并保存登陆之后的凭证
     *
     * @param userLoginContext context
     */
    private void generateAndSaveAccessToken(UserLoginContext userLoginContext) {
        BPanUser entity = userLoginContext.getEntity();

        String accessToken = JwtUtil.generateToken(entity.getUsername(), UserConstant.LOGIN_USER_ID, entity.getUserId(), UserConstant.ONE_DAY_LONG);

        Cache cache = cacheManager.getCache(CacheConstant.B_PAN_CACHE_NAME);
        assert cache != null;
        cache.put(UserConstant.USER_LOGIN_PREFIX + entity.getUserId(), accessToken);

        userLoginContext.setAccessToken(accessToken);
    }

    /**
     * 通过用户名称获取用户实体信息
     *
     * @param username 用户名
     * @return 用户实体信息
     */
    private BPanUser getBpanUserByUsername(String username) {
        QueryWrapper<BPanUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return getOne(queryWrapper);
    }

    /**
     * 生成用户忘记密码-校验密保答案通过的临时token
     * token的失效时间为五分钟之后
     *
     * @param checkAnswerContext context
     * @return token
     */
    private String generateCheckAnswerToken(CheckAnswerContext checkAnswerContext) {
        return JwtUtil.generateToken(checkAnswerContext.getUsername(), UserConstant.FORGET_USERNAME, checkAnswerContext.getUsername(),
                UserConstant.FIVE_MINUTES_LONG);
    }

    /**
     * 校验用户信息并重置用户密码
     *
     * @param resetPasswordContext context
     */
    private void checkAndResetUserPassword(ResetPasswordContext resetPasswordContext) {
        String username = resetPasswordContext.getUsername();
        String password = resetPasswordContext.getPassword();
        BPanUser entity = getBpanUserByUsername(username);
        if (Objects.isNull(entity)) {
            throw new BPanBusinessException("用户信息不存在");
        }

        String newDbPassword = PasswordUtil.encryptPassword(entity.getSalt(), password);
        entity.setPassword(newDbPassword);
        entity.setUpdateTime(new Date());

        if (!updateById(entity)) {
            throw new BPanBusinessException("重置用户密码失败");
        }
    }

    /**
     * 验证忘记密码的token是否有效
     *
     * @param resetPasswordContext context
     */
    private void checkForgetPasswordToken(ResetPasswordContext resetPasswordContext) {
        String token = resetPasswordContext.getToken();
        Object value = JwtUtil.analyzeToken(token, UserConstant.FORGET_USERNAME);
        if (Objects.isNull(value)) {
            throw new BPanBusinessException(ResponseCode.TOKEN_EXPIRE);
        }
        String tokenUsername = String.valueOf(value);
        if (!Objects.equals(tokenUsername, resetPasswordContext.getUsername())) {
            throw new BPanBusinessException("token错误");
        }
    }

    /**
     * 校验用户的旧密码
     * 改不周会查询并封装用户的实体信息到上下文对象中
     *
     * @param changePasswordContext context
     */
    private void checkOldPassword(ChangePasswordContext changePasswordContext) {
        Long userId = changePasswordContext.getUserId();
        String oldPassword = changePasswordContext.getOldPassword();

        BPanUser entity = getById(userId);
        if (Objects.isNull(entity)) {
            throw new BPanBusinessException("用户信息不存在");
        }
        changePasswordContext.setEntity(entity);

        String encOldPassword = PasswordUtil.encryptPassword(entity.getSalt(), oldPassword);
        String dbOldPassword = entity.getPassword();
        if (!Objects.equals(encOldPassword, dbOldPassword)) {
            throw new BPanBusinessException("旧密码不正确");
        }
    }

    /**
     * 修改新密码
     *
     * @param changePasswordContext context
     */
    private void doChangePassword(ChangePasswordContext changePasswordContext) {
        String newPassword = changePasswordContext.getNewPassword();
        BPanUser entity = changePasswordContext.getEntity();
        String salt = entity.getSalt();

        String encNewPassword = PasswordUtil.encryptPassword(salt, newPassword);

        entity.setPassword(encNewPassword);

        if (!updateById(entity)) {
            throw new BPanBusinessException("修改用户密码失败");
        }
    }

    /**
     * 退出用户的登录状态
     *
     * @param changePasswordContext context
     */
    private void exitLoginStatus(ChangePasswordContext changePasswordContext) {
        exit(changePasswordContext.getUserId());
    }

}




