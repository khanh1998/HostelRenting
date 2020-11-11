package org.avengers.capstone.hostelrenting.dto.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Contract;

import java.io.Serializable;

/**
 * @author duattt on 11/10/20
 * @created 10/11/2020 - 14:36
 * @project youthhostelapp
 */
@Getter
@Setter
public class ImageDTO implements Serializable {
    private int imageId;
    private String resourceUrl;
}
