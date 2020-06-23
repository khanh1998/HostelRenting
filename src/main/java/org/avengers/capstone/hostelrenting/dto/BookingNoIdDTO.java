package capstone.tdhostel.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookingNoIdDTO implements Serializable {
    private int dealId;

    private String startTime;

    private String endTime;

    private String status;

    private int renterId;

    private int vendorId;

    private int hostelTypeId;

}
