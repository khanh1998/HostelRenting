package org.avengers.capstone.hostelrenting.dto.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.image.ImageDTOCreate;
import org.avengers.capstone.hostelrenting.exception.GenericException;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Contract;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class ContractDTOCreate {

    public ContractDTOCreate() {
        this.qrCode = UUID.randomUUID();
        this.status = Contract.STATUS.INACTIVE;
        this.isPaid = false;
        this.createdAt = System.currentTimeMillis();
    }

    @Getter
    @Setter
    @JsonIgnore
    private Integer contractId;

    @Getter
    @Setter
    private String appendixContract;

    @Getter
    @Setter
    @NotNull(message = "Room id is mandatory!")
    private Integer roomId;

    @Getter
    @Setter
    @NotNull(message = "Vendor id is mandatory!")
    private Long vendorId;

    @Getter
    @Setter
    @NotNull(message = "Renter id is mandatory!")
    private Long renterId;

    @Getter
    @Setter
    private Integer dealId;

    @Getter
    @Setter
    private Integer bookingId;

    @Getter
    @Setter
    @NotNull(message = "Start time is mandatory!")
    private Long startTime;

    @Getter
    @Setter
    @NotNull(message = "Contract duration is mandatory!")
    private Integer duration;

    @JsonIgnore
    @Getter
    private final Long createdAt;

    @Getter
    @JsonIgnore
    private final Contract.STATUS status;

    @Getter
    @JsonIgnore
    private final UUID qrCode;

    @Getter
    @Setter
    @NotNull(message = "List of agreement services is mandatory!")
    @JsonProperty(value = "groupServiceIds")
    private Collection<GroupServiceDTOForContract> groupServices;

    @Getter
    @Setter
    @JsonProperty(value = "images")
    private Collection<ImageDTOCreate> contractImages;

    @Getter
    @Setter
    private boolean isReserved;

    @Getter
    @Setter
    @JsonIgnore
    private boolean isPaid;

    @Getter
    @Setter
    private Float downPayment;
}
