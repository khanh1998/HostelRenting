package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HostelImageDTO implements Serializable {
    private int imageId;
    private String resourceUrl;
    private int hostelTypeId;
}
