package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.deal.DealDTOCreate;
import org.avengers.capstone.hostelrenting.model.Deal;

import java.util.List;

public interface DealService {
    void checkActive(Integer id);
    Deal findById(Integer id);
    Deal create(DealDTOCreate reqDTO);
    Deal update(DealDTOCreate reqDTO);
    List<Deal> findByRenterId(Long renterId);
    List<Deal> findByVendorId(Long vendorId);

    Deal changeStatus(Integer id, Deal.STATUS status);
}
