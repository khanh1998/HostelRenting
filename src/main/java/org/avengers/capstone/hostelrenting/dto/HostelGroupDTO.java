package capstone.tdhostel.dto;

import capstone.tdhostel.model.Vendor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class HostelGroupDTO implements Serializable {
    private int hostelGroupId;

    private String hostelGroupName;

    private String detailedAddress;

    private String longitude;

    private String latitude;

    private int vendorId;
}
