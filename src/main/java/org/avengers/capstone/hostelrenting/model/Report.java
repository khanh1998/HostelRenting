package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author duattt on 04/12/2020
 * @created 04/12/2020 - 11:15
 * @project youthhostelapp
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    private UUID renterId;

    private UUID vendorId;
}
