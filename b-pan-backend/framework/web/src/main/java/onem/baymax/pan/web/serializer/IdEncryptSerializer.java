package onem.baymax.pan.web.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import onem.baymax.pan.core.util.IdUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * Id自动加密的JSON序列化器
 * 用于返回实体Long类型ID字段的自动序列化
 *
 * @author hujiabin wrote in 2024/3/12 22:07
 */
public class IdEncryptSerializer extends JsonSerializer<Long> {
    /**
     * Method that can be called to ask implementation to serialize
     * values of type this serializer handles.
     *
     * @param value       Value to serialize; can <b>not</b> be null.
     * @param gen         Generator used to output resulting Json content
     * @param serializers Provider that can be used to get serializers for
     *                    serializing Objects value contains, if any.
     */
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.isNull(value)) {
            gen.writeString(StringUtils.EMPTY);
        } else {
            gen.writeString(IdUtil.encrypt(value));
        }
    }

}
