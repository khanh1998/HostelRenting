package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.deal.DealDTOCreate;
import org.avengers.capstone.hostelrenting.model.Deal;

import java.util.List;
import java.util.UUID;

public interface DealService {
    void checkActive(Integer id);
    Deal findById(Integer id);
    Deal create(DealDTOCreate reqDTO);
    Deal update(DealDTOCreate reqDTO);
    List<Deal> findByRenterId(UUID renterId);
    List<Deal> findByVendorId(UUID vendorId);

    Deal changeStatus(Integer id, Deal.STATUS status);
}
