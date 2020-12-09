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

    public enum TYPE{PAPER, RESERVED_BILL, REST_BILL}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    @Column(nullable = false)
    private String resourceUrl;

    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TYPE type;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isDeleted;
}
