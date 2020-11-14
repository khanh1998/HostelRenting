package org.avengers.capstone.hostelrenting.dto.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.image.ImageDTOCreate;
import org.avengers.capstone.hostelrenting.dto.room.RoomDTOCreate;
import org.avengers.capstone.hostelrenting.model.Type;

import java.util.Collection;

public class TypeDTOCreate extends TypeDTO {
    public TypeDTOCreate() {
        if (getStatus() == null) {
            setStatus(Type.STATUS.AVAILABLE);
        }

        setDeleted(false);
        setCreatedAt(System.currentTimeMillis());
    }

    @Getter
    @Setter
    private Collection<RoomDTOCreate> rooms;

    @Getter
    @Setter
//    private Collection<FacilityDTOCreateForType> facilities;
    private Collection<TypeFacilityDTOCreate> facilities;

    @Getter
    @Setter
    @JsonProperty(value = "imageUrls")
    private Collection<ImageDTOCreate> typeImages;

    /* Disable unnecessary attributes */
    @Override
    @JsonIgnore
    public void setTypeId(Integer typeId) {
        super.setTypeId(typeId);
    }

    @Override
    @JsonIgnore
    public void setScore(Float score) {
        super.setScore(score);
    }

    @Override
    @JsonIgnore
    public void setView(Integer view) {
        super.setView(view);
    }

    @Override
    @JsonIgnore
    public void setSchoolmate(int schoolmate) {
        super.setSchoolmate(schoolmate);
    }

    @Override
    @JsonIgnore
    public void setCompatriot(int compatriot) {
        super.setCompatriot(compatriot);
    }

    @Override
    @JsonIgnore
    public void setAvailableRoom(int availableRoom) {
        super.setAvailableRoom(availableRoom);
    }

    @Override
    @JsonIgnore
    public void setCurrentBooking(int currentBooking) {
        super.setCurrentBooking(currentBooking);
    }

    @Override
    @JsonIgnore
    public void setUpdatedAt(Long updatedAt) {
        super.setUpdatedAt(updatedAt);
    }

    @Override
    @JsonIgnore
    public void setCreatedAt(Long createdAt) {
        super.setCreatedAt(createdAt);
    }

    @Override
    @JsonIgnore
    public void setGroupId(int groupId) {
        super.setGroupId(groupId);
    }
}
