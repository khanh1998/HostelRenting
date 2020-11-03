package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author duattt on 11/3/20
 * @created 03/11/2020 - 12:30
 * @project youthhostelapp
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "feedback_image")
public class FeedbackImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    @Column(nullable = false)
    private String resourceUrl;

    @ManyToOne
    @JoinColumn(name = "feedback_id", nullable = false)
    private Feedback feedback;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(columnDefinition = "bool default false")
    private boolean isDeleted;
}
