package capstone.tdhostel.service;

import capstone.tdhostel.dto.BookingDTO;
import capstone.tdhostel.dto.HostelGroupDTO;
import capstone.tdhostel.model.Booking;
import capstone.tdhostel.model.HostelGroup;

import java.util.List;
import java.util.Optional;

public interface HostelGroupService {
    Optional<HostelGroup> findAllHostelGroupById(Integer id);
    HostelGroup findHostelGroupById(Integer hostelGroupId);
    List<HostelGroupDTO> getAllHostelGroup();
    List<HostelGroup> findAllHostelGroupByVendorId(Integer vendorId);
    HostelGroup saveHostelGroup(HostelGroup hostelGroup);
    void removeHostelGroup(HostelGroup hostelGroup);
}
