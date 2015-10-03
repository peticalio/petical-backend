package org.majimena.petz.domain.clinic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.majimena.petz.domain.common.defs.ID;
import org.majimena.petz.domain.common.defs.MailAddress;
import org.majimena.petz.domain.common.defs.Name;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * クリニックペットクライテリア.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClinicPetCriteria implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 117457330723087691L;

    /**
     * クリニックID.
     */
    @Size(max = ID.MAX_LENGTH)
    private String clinicId;

    /**
     * ログインメールアドレス.
     */
    @Email
    @Size(max = MailAddress.MAX_LENGTH)
    private String login;

    /**
     * 名前.
     */
    @Size(max = Name.MAX_LENGTH)
    private String name;
}
