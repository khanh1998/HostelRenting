package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;

/**
 * @author duattt on 11/10/20
 * @created 10/11/2020 - 14:32
 * @project youthhostelapp
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "contract_image")
public class ContractImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    @Column(nullable = false)
    private String resourceUrl;

    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isReserved;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isDeleted;
}
