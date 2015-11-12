package org.majimena.petz.repository.spec;

import org.majimena.petz.domain.Examination;
import org.majimena.petz.domain.ticket.ExaminationCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.Optional;

/**
 * 診察検索スペック.
 */
public class ExaminationSpecs {

    public static Specification<Examination> of(ExaminationCriteria criteria) {
        return Specifications
                .where(Optional.ofNullable(criteria.getClinicId()).map(ExaminationSpecs::equalClinicId).orElse(null))
                .and(Optional.ofNullable(criteria.getTicketId()).map(ExaminationSpecs::equalTicketId).orElse(null));
    }

    private static Specification equalClinicId(String clinicId) {
        return (root, query, cb) -> cb.equal(root.get("ticket").get("clinic").get("id"), clinicId);
    }

    private static Specification equalTicketId(String ticketId) {
        return (root, query, cb) -> cb.equal(root.get("ticket").get("id"), ticketId);
    }
}
