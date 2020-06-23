package capstone.tdhostel.dto;

import capstone.tdhostel.model.Renter;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class BookingDTO implements Serializable {
    private int bookingId;

    private int dealId;

    private String startTime;

    private String endTime;

    private String status;

    private int renterId;

    private int vendorId;

    private int hostelTypeId;
}
