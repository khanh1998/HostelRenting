package org.avengers.capstone.hostelrenting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author duattt on 10/6/20
 * @created 06/10/2020 - 09:32
 * @project youthhostelapp
 */
@Getter
@Setter
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

    /**
     * fine payment when the renter violate
     */
    @Column(columnDefinition = "float(4) default 0")
    private float finePayment;

    /**
     * define the regulation is allowed or not
     */
    @Column(columnDefinition = "bool default true")
    private boolean isAllowed;

    /**
     * define the regulation is active or not
     */
    @Column(columnDefinition = "bool default true")
    private boolean isActive;
}
