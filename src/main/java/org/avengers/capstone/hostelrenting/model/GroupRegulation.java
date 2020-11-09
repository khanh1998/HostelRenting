package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;

/**
 * @author duattt on 10/6/20
 * @created 06/10/2020 - 09:32
 * @project youthhostelapp
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "group_regulation")
public class GroupRegulation {
    /**
     * group_regulation id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * hostelGroup object
     */
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    /**
     * regulation object
     */
    @ManyToOne
    @JoinColumn(name = "regulation_id", nullable = false)
    private Regulation regulation;
}
