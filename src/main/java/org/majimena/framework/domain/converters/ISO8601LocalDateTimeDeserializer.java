package org.majimena.framework.domain.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ISO8601形式の日時をデシリアライズするJacksonのDeserializer.
 */
public class ISO8601LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    /**
     * ログ.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ISO8601LocalDateTimeDeserializer.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonToken token = parser.getCurrentToken();
        if (token == JsonToken.VALUE_STRING) {
            String value = parser.getText().trim();
            LOG.debug("deserialize value is {}.", value);

            try {
                ZonedDateTime dateTime = ZonedDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                return LocalDateTime.from(dateTime);
            } catch (Exception e) {
                LOG.warn("ZonedDateTime cannot convert deserialize.", e);
            }
        }
        throw context.mappingException(handledType());
    }
}
