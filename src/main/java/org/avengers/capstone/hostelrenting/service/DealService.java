package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.model.Deal;

import java.util.List;

public interface DealService {
    void checkActive(Integer id);
    Deal findById(Integer id);
    Deal create(DealDTOShort reqDTO);
    Deal update(DealDTOShort reqDTO);
    void delete(Integer id);
    List<Deal> findByRenterId(Long renterId);
    List<Deal> findByVendorId(Long vendorId);

    Deal changeStatus(Integer id, Deal.STATUS status);
}
