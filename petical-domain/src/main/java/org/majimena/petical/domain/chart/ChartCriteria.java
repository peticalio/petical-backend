package org.majimena.petical.domain.chart;

import lombok.*;
import org.majimena.petical.datatype.defs.ID;
import org.majimena.petical.datatype.defs.Name;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * カルテクライテリア.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ChartCriteria implements Serializable {

    private static final long serialVersionUID = -4222423315140530208L;

    @Size(max = ID.MAX_LENGTH)
    private String clinicId;

    @Size(max = ID.MAX_LENGTH)
    private String customerId;

    @Size(max = ID.MAX_LENGTH)
    private String petId;

    @Size(max = Name.MAX_LENGTH)
    private String customerName;
}
