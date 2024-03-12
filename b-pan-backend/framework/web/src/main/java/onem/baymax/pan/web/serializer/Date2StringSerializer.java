package onem.baymax.pan.web.serializer;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * Date转String的JSON序列化器
 * 用于返回实体Date类型字段的自动序列化
 *
 * @author hujiabin wrote in 2024/3/12 22:06
 */
public class Date2StringSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(date)) {
            jsonGenerator.writeString(StringUtils.EMPTY);
        } else {
            jsonGenerator.writeString(DateUtil.formatDateTime(date));
        }
    }
}
