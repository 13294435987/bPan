package onem.baymax.pan.server.module.file.mapper;

import onem.baymax.pan.server.module.file.context.QueryFileListContext;
import onem.baymax.pan.server.module.file.entity.BPanUserFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import onem.baymax.pan.server.module.file.vo.BPanUserFileVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hujiabin
 */
public interface BPanUserFileMapper extends BaseMapper<BPanUserFile> {

    /**
     * 查询用户的文件列表
     *
     * @param context context
     * @return list
     */
    List<BPanUserFileVo> selectFileList(@Param("param") QueryFileListContext context);

}




