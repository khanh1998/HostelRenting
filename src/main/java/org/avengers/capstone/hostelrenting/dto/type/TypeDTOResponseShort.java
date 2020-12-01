package org.avengers.capstone.hostelrenting.dto.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.category.CategoryDTO;
import org.avengers.capstone.hostelrenting.dto.TypeStatusDTO;
import org.avengers.capstone.hostelrenting.dto.facility.FacilityDTOResponse;
import org.avengers.capstone.hostelrenting.dto.image.ImageDTOResponse;
import org.avengers.capstone.hostelrenting.dto.room.RoomDTOCreate;
import org.avengers.capstone.hostelrenting.dto.room.RoomDTOResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author duattt on 10/27/20
 * @created 27/10/2020 - 07:30
 * @project youthhostelapp
 */
@Setter
@Getter
public class TypeDTOResponseShort extends TypeDTOResponse {

    @Override
    public Collection<ImageDTOResponse> getTypeImages() {
        return super.getTypeImages().stream().limit(1).collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public String getPriceUnit() {
        return "triệu";
    }

    @Override
    @JsonIgnore
    public Collection<FacilityDTOResponse> getFacilities() {
        return super.getFacilities();
    }

    @Override
    @JsonIgnore
    public Collection<RoomDTOResponse> getRooms() {
        return super.getRooms();
    }

    @Override
    @JsonIgnore
    public void setFacilities(Collection<FacilityDTOResponse> facilities) {
        super.setFacilities(facilities);
    }

    @Override
    @JsonIgnore
    public void setRooms(Collection<RoomDTOResponse> rooms) {
        super.setRooms(rooms);
    }

    @Override
    @JsonIgnore
    public Float getScore() {
        return super.getScore();
    }

    @Override
    @JsonIgnore
    public Float getSuperficiality() {
        return super.getSuperficiality();
    }

    @Override
    @JsonIgnore
    public Integer getCapacity() {
        return super.getCapacity();
    }

    @Override
    @JsonIgnore
    public Integer getView() {
        return super.getView();
    }

    @Override
    @JsonIgnore
    public Integer getDeposit() {
        return super.getDeposit();
    }

    @Override
    @JsonIgnore
    public boolean isDeleted() {
        return super.isDeleted();
    }

    @Override
    @JsonIgnore
    public int getSchoolmate() {
        return super.getSchoolmate();
    }

    @Override
    @JsonIgnore
    public int getCompatriot() {
        return super.getCompatriot();
    }

    @Override
    @JsonIgnore
    public int getAvailableRoom() {
        return super.getAvailableRoom();
    }

    @Override
    @JsonIgnore
    public int getCurrentBooking() {
        return super.getCurrentBooking();
    }

    @Override
    @JsonIgnore
    public Long getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    @JsonIgnore
    public Long getUpdatedAt() {
        return super.getUpdatedAt();
    }
}
