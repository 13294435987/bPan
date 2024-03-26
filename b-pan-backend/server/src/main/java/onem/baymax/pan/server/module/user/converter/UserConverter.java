package onem.baymax.pan.server.module.user.converter;

import onem.baymax.pan.server.module.file.entity.BPanUserFile;
import onem.baymax.pan.server.module.user.context.*;
import onem.baymax.pan.server.module.user.entity.BPanUser;
import onem.baymax.pan.server.module.user.po.*;
import onem.baymax.pan.server.module.user.vo.UserInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 用户模块实体转化工具类
 *
 * @author hujiabin wrote in 2024/3/17 18:32
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    /**
     * UserRegisterPo转化成UserRegisterContext
     *
     * @param userRegisterPo UserRegisterPo对象
     * @return UserRegisterContext对象
     */
    UserRegisterContext userRegisterPo2UserRegisterContext(UserRegisterPo userRegisterPo);

    /**
     * UserRegisterContext转BPanUser
     *
     * @param userRegisterContext UserRegisterContext对象
     * @return BPanUser对象
     */
    @Mapping(target = "password", ignore = true)
    BPanUser userRegisterContext2bPanUser(UserRegisterContext userRegisterContext);

    /**
     * UserLoginPo转UserLoginContext
     *
     * @param userLoginPo UserLoginPo参数
     * @return UserLoginContext参数
     */
    UserLoginContext userLoginPo2UserLoginContext(UserLoginPo userLoginPo);

    /**
     * CheckUsernamePo转CheckUsernameContext
     *
     * @param checkUsernamePo CheckUsernamePo对象
     * @return CheckUsernameContext对象
     */
    CheckUsernameContext checkUsernamePo2CheckUsernameContext(CheckUsernamePo checkUsernamePo);

    /**
     * CheckAnswerPo转CheckAnswerContext
     *
     * @param checkAnswerPo CheckAnswerPo对象
     * @return CheckAnswerContext对象
     */
    CheckAnswerContext checkAnswerPo2CheckAnswerContext(CheckAnswerPo checkAnswerPo);

    /**
     * ResetPasswordPo转ResetPasswordContext
     *
     * @param resetPasswordPo ResetPasswordPo对象
     * @return ResetPasswordContext对象
     */
    ResetPasswordContext resetPasswordPo2ResetPasswordContext(ResetPasswordPo resetPasswordPo);

    /**
     * CChangePasswordPo转ChangePasswordContext
     *
     * @param changePasswordPo ChangePasswordPo对象
     * @return ChangePasswordContext对象
     */
    ChangePasswordContext changePasswordPo2ChangePasswordContext(ChangePasswordPo changePasswordPo);

    /**
     * 拼装用户基本信息返回实体
     *
     * @param bPanUser     bPanUser
     * @param bPanUserFile bPanUserFile
     * @return vo
     */
    @Mapping(source = "bPanUser.username", target = "username")
    @Mapping(source = "bPanUserFile.fileId", target = "rootFileId")
    @Mapping(source = "bPanUserFile.filename", target = "rootFilename")
    UserInfoVo assembleUserInfoVo(BPanUser bPanUser, BPanUserFile bPanUserFile);

}

