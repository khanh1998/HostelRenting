package org.avengers.capstone.hostelrenting.dto.type;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TypeImageDTO implements Serializable {
    private int imageId;
    private String resourceUrl;
}
