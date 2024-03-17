package onem.baymax.pan.server.module.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import onem.baymax.pan.server.module.user.entity.BPanUserSearchHistory;
import onem.baymax.pan.server.module.user.service.IUserSearchHistoryService;
import onem.baymax.pan.server.module.user.mapper.BPanUserSearchHistoryMapper;
import org.springframework.stereotype.Service;

/**
 * @author hujiabin
 * @description 针对表【b_pan_user_search_history(用户搜索历史表)】的数据库操作Service实现
 * @createDate 2024-03-14 11:30:08
 */
@Service
public class IUserSearchHistoryServiceImpl extends ServiceImpl<BPanUserSearchHistoryMapper, BPanUserSearchHistory>
        implements IUserSearchHistoryService {

}




