package onem.baymax.pan.server.module.user.mapper;

import onem.baymax.pan.server.module.user.entity.BPanUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author hujiabin
 * @description 针对表【b_pan_user(用户信息表)】的数据库操作Mapper
 * @createDate 2024-03-14 11:30:08
 * @Entity onem.baymax.pan.server.module.user.entity.BPanUser
 */
public interface BPanUserMapper extends BaseMapper<BPanUser> {

    /**
     * 通过用户名称查询用户设置的密保问题
     *
     * @param username 名称
     * @return 密保问题
     */
    String selectQuestionByUsername(@Param("username") String username);
}




