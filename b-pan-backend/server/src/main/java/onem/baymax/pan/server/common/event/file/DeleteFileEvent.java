package onem.baymax.pan.server.common.event.file;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * 文件删除时间
 *
 * @author hujiabin wrote in 2024/3/27 23:43
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class DeleteFileEvent extends ApplicationEvent {

    private List<Long> fileIdList;

    public DeleteFileEvent(Object source, List<Long> fileIdList) {
        super(source);
        this.fileIdList = fileIdList;
    }

}
