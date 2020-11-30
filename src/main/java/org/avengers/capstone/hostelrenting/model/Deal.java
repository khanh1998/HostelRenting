package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "deal")
public class Deal {
    public enum STATUS {CREATED, DONE, CANCELLED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dealId;

    @Column(columnDefinition = "varchar(10) default 'CREATED'", nullable = false)
    @Enumerated(EnumType.STRING)
    private STATUS status;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;
    @Transient
    private Group group;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private Renter renter;

    @Column
    @NotNull(message = "Offered price is mandatory")
    private Float offeredPrice;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    public Group getGroup() {
        if (type != null)
            return type.getGroup();
        return null;
    }
}
