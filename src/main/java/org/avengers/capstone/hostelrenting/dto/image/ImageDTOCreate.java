package org.avengers.capstone.hostelrenting.dto.image;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author duattt on 11/10/20
 * @created 10/11/2020 - 14:37
 * @project youthhostelapp
 */
public class ImageDTOCreate extends ImageDTO {
    @Override
    @JsonIgnore
    public void setImageId(int imageId) {
        super.setImageId(imageId);
    }

    @Override
    public void setDeleted(boolean isDeleted) {
        super.setDeleted(false);
    }
}
