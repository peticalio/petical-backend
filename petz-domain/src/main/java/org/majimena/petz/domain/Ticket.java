package org.majimena.petz.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.majimena.petz.datatype.TicketState;
import org.majimena.petz.datatype.converters.LocalDateTimePersistenceConverter;
import org.majimena.petz.datatype.deserializers.ISO8601LocalDateTimeDeserializer;
import org.majimena.petz.datatype.deserializers.TicketStateDeserializer;
import org.majimena.petz.datatype.serializers.EnumDataTypeSerializer;
import org.majimena.petz.datatype.serializers.ISO8601LocalDateTimeSerializer;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * カルテドメイン.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "ticket")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Ticket extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chart_id", nullable = true)
    private Chart chart;

    @JsonSerialize(using = EnumDataTypeSerializer.class)
    @JsonDeserialize(using = TicketStateDeserializer.class)
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "state", nullable = false)
    private TicketState state;

    @Size(max = 2000)
    @Column(name = "memo", length = 2000, nullable = true)
    private String memo;

    @Column(name = "removed", nullable = false)
    private Boolean removed;

    @NotNull
    @JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateTimeDeserializer.class)
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @NotNull
    @JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateTimeDeserializer.class)
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;
}