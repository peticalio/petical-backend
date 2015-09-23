package org.majimena.petz.domain.pet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.majimena.petz.domain.common.defs.ID;
import org.majimena.petz.domain.common.defs.Name;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * ペットのクライテリア.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetCriteria implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 696392781769207410L;

    /**
     * ユーザーID.
     */
    @Size(max = ID.MAX_LENGTH)
    private String userId;

    /**
     * 名前.
     */
    @Size(max = Name.MAX_LENGTH)
    private String name;
}
