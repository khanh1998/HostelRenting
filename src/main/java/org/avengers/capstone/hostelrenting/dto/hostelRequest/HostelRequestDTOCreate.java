package org.avengers.capstone.hostelrenting.dto.hostelRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

/**
 * @author duattt on 11/16/20
 * @created 16/11/2020 - 17:17
 * @project youthhostelapp
 */
public class HostelRequestDTOCreate extends HostelRequestDTO{
    public HostelRequestDTOCreate() {
        this.setCreatedAt(System.currentTimeMillis());
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
    @NotNull(message = "Due date is mandatory")
    public Long getDueDate() {
        return super.getDueDate();
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
    public long getRenterId() {
        return super.getRenterId();
    }
}
