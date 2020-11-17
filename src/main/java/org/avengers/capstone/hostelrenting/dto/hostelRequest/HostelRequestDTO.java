package org.avengers.capstone.hostelrenting.dto.hostelRequest;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.HostelRequest;

/**
 * @author duattt on 11/16/20
 * @created 16/11/2020 - 17:14
 * @project youthhostelapp
 */
@Getter
@Setter
public class HostelRequestDTO {
    private int requestId;
    private Float maxPrice;
    private Float minSuperficiality;
    private Double latitude;
    private Double longitude;
    private Integer maxDistance;
    private Long dueTime;
    private long renterId;
    private HostelRequest.STATUS status;
    private long createdAt;
    private Long updatedAt;
}
