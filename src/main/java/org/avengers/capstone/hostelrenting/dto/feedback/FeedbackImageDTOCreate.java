package org.avengers.capstone.hostelrenting.dto.feedback;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author duattt on 11/3/20
 * @created 03/11/2020 - 12:42
 * @project youthhostelapp
 */

public class FeedbackImageDTOCreate implements Serializable {

    public FeedbackImageDTOCreate() {
        this.isDeleted = false;
        this.createdAt = System.currentTimeMillis();
    }
    @Getter
    @Setter
    @JsonIgnore
    private Integer imageId;

    @Getter
    @Setter
    private String resourceUrl;

    @JsonIgnore
    @Getter
    private final Boolean isDeleted;

    @JsonIgnore
    @Getter
    private final Long createdAt;
}
