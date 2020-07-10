package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TypeImageDTO implements Serializable {
    private int imageId;
    private int typeId;
    private String resourceUrl;
}
