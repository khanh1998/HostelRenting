package capstone.tdhostel.dto;

import capstone.tdhostel.model.Booking;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class HostelTypeDTO implements Serializable {
    private int hostelTypeId;

    private String hostelName;

    private String price;

    private String unit;

    private float superficiality;

    private int capacity;

    private int bookingId;

    private int hostelGroupId;
}
