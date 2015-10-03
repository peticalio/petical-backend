package org.majimena.framework.domain.converters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.majimena.framework.domain.EnumDataType;

import java.io.IOException;

/**
 * Created by todoken on 2015/07/26.
 */
public class EnumDataTypeSerializer extends JsonSerializer<EnumDataType> {
    @Override
    public void serialize(EnumDataType value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(value.getValue());
    }
}
