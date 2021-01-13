package org.avengers.capstone.hostelrenting.dto.hostelRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.avengers.capstone.hostelrenting.model.HostelRequest;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author duattt on 11/16/20
 * @created 16/11/2020 - 17:17
 * @project youthhostelapp
 */
public class HostelRequestDTOCreate extends HostelRequestDTO{
    public HostelRequestDTOCreate() {
        this.setCreatedAt(System.currentTimeMillis());
        this.setStatus(HostelRequest.STATUS.CREATED);
        if (getMaxDistance() == null){
            setMaxDistance(5);
        }
    }

    @Override
    @JsonIgnore
    public void setRequestId(int requestId) {
        super.setRequestId(requestId);
    }


    @Override
    @NotNull(message = "Longitude is mandatory")
    public Double getLongitude() {
        return super.getLongitude();
    }

    @Override
    @NotNull(message = "Latitude is mandatory")
    public Double getLatitude() {
        return super.getLatitude();
    }

    @Override
    @NotNull(message = "Due time is mandatory")
    public Long getDueTime() {
        return super.getDueTime();
    }

    @Override
    @JsonIgnore
    public void setCreatedAt(long createdAt) {
        super.setCreatedAt(createdAt);
    }

    @Override
    @JsonIgnore
    public void setUpdatedAt(Long updatedAt) {
        super.setUpdatedAt(updatedAt);
    }

    @Override
    @JsonIgnore
    public UUID getRenterId() {
        return super.getRenterId();
    }

    @Override
    @JsonIgnore
    public HostelRequest.STATUS getStatus() {
        return super.getStatus();
    }
}
