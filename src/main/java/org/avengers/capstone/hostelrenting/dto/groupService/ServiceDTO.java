package org.avengers.capstone.hostelrenting.dto.groupService;

import lombok.Getter;
import lombok.Setter;

/**
 * @author duattt on 11/14/20
 * @created 14/11/2020 - 13:08
 * @project youthhostelapp
 */
@Getter
@Setter
public class ServiceDTO {
    private int serviceId;
    private String serviceName;
    private boolean isApproved;
}
