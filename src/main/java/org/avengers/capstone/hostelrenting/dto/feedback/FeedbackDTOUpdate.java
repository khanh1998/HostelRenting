package org.avengers.capstone.hostelrenting.dto.feedback;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Contract;

import java.io.Serializable;

/**
 * @author duattt on 10/26/20
 * @created 26/10/2020 - 13:34
 * @project youthhostelapp
 */
public class FeedbackDTOUpdate implements Serializable {

    public FeedbackDTOUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }

    @Getter @Setter
    private String comment;

    @Getter @Setter
    private Integer rating;

    @Getter
    @JsonIgnore
    private final Long updatedAt;
}
