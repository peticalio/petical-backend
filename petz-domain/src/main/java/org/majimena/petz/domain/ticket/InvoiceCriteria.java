package org.majimena.petz.domain.ticket;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.majimena.petz.datatype.InvoiceState;
import org.majimena.petz.datatype.deserializers.InvoiceStateDeserializer;
import org.majimena.petz.datatype.serializers.EnumDataTypeSerializer;

import java.io.Serializable;

/**
 * インヴォイスの検索条件.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InvoiceCriteria implements Serializable {

    private static final long serialVersionUID = -2915937297278890402L;

    private String clinicId;

    @JsonSerialize(using = EnumDataTypeSerializer.class)
    @JsonDeserialize(using = InvoiceStateDeserializer.class)
    private InvoiceState state;
}
