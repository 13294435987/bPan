package onem.baymax.pan.server.module.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import onem.baymax.pan.server.module.file.entity.BPanFileChunk;
import onem.baymax.pan.server.module.file.service.IFileChunkService;
import onem.baymax.pan.server.module.file.mapper.BPanFileChunkMapper;
import org.springframework.stereotype.Service;

/**
 * @author hujiabin
 * @description 针对表【b_pan_file_chunk(文件分片信息表)】的数据库操作Service实现
 * @createDate 2024-03-14 11:22:44
 */
@Service
public class FileChunkServiceImpl extends ServiceImpl<BPanFileChunkMapper, BPanFileChunk>
        implements IFileChunkService {

}




