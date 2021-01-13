package org.avengers.capstone.hostelrenting.dto.type;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author duattt on 11/11/20
 * @created 11/11/2020 - 11:07
 * @project youthhostelapp
 */
public class ImageDTOCreateForType extends TypeImageDTO{
    @JsonIgnore
    @Override
    public void setImageId(int imageId) {
        super.setImageId(imageId);
    }
}
