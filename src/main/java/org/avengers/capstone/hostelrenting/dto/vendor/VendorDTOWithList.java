package org.avengers.capstone.hostelrenting.dto.vendor;

        import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
        import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOShort;
        import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOShort;
        import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;

        import java.io.Serializable;
        import java.util.List;

public class VendorDTOWithList implements Serializable {
    private int vendorId;
    private List<ContractDTOShort> contracts;
    private List<HostelGroupDTO> hostelGroups;
    private List<DealDTOShort> deals;
    private List<BookingDTOShort> bookings;
}
