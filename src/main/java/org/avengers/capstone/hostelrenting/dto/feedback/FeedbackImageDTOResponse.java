package org.avengers.capstone.hostelrenting.dto.feedback;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author duattt on 11/3/20
 * @created 03/11/2020 - 16:25
 * @project youthhostelapp
 */
@Getter
@Setter
public class FeedbackImageDTOResponse implements Serializable {
    private int imageId;
    private String resourceUrl;
    private long createdAt;
    private Long updatedAt;
}
