package capstone.tdhostel.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HostelTypeListDTO implements Serializable {
    private int hostelTypeId;

    private String hostelName;

    private String price;

    private String unit;

    private float superficiality;

    private int capacity;

    private List<Integer> bookingId;

    private int hostelGroupId;
}
