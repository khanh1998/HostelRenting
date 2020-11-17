package org.avengers.capstone.hostelrenting.dto.hostelRequest;

import lombok.Getter;
import lombok.Setter;

/**
 * @author duattt on 11/16/20
 * @created 16/11/2020 - 17:14
 * @project youthhostelapp
 */
@Getter
@Setter
public class HostelRequestDTO {
    private int requestId;
    private Float price;
    private Float superficiality;
    private Double latitude;
    private Double longitude;
    private Integer maxDistance;
    private Long dueDate;
    private long renterId;
    private long createdAt;
    private Long updatedAt;
}
