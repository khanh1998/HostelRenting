package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.model.Deal;

import java.util.List;

public interface DealService {
    void checkExist(Integer id);
    Deal findById(Integer id);
    Deal create(DealDTOShort reqDTO);
    Deal update(DealDTOShort reqDTO);
    List<Deal> findByRenterId(Integer renterId);
    List<Deal> findByVendorId(Integer vendorId);

    Deal changeStatus(Integer id, Deal.Status status);
}
