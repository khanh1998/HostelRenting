package org.avengers.capstone.hostelrenting.dto.feedback;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Contract;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author duattt on 10/27/20
 * @created 27/10/2020 - 06:57
 * @project youthhostelapp
 */
public class FeedbackDTOCreate {
    public FeedbackDTOCreate() {
        this.isDeleted = false;
        this.createdAt = System.currentTimeMillis();
    }
    @Getter @Setter
//    @JsonIgnore
    private Integer feedbackId;

    @Getter @Setter
    @NotNull(message = "Renter id is mandatory!")
    private Long renterId;

    @Getter @Setter
    @NotNull(message = "Type id is mandatory!")
    private Integer typeId;

    @Getter @Setter
    private String subject;

    @Getter @Setter
    private String comment;

    @Getter @Setter
    @NotNull(message = "Rating is mandatory!")
    private Integer rating;

    @JsonIgnore
    @Getter
    private final Boolean isDeleted;

    @JsonIgnore
    @Getter
    private final Long createdAt;
}
