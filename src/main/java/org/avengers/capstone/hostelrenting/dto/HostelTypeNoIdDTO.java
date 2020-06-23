package capstone.tdhostel.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HostelTypeNoIdDTO implements Serializable {
    private String hostelName;

    private String price;

    private String unit;

    private float superficiality;

    private int capacity;

    private int hostelGroupId;
}
