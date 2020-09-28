package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOShort;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOShort;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Contract;
import org.avengers.capstone.hostelrenting.model.District;

import java.util.List;

public interface ContractService {
    void checkActive(Integer id);
    Contract findById(Integer id);
    Contract create(ContractDTOShort reqDTO);
    Contract update(ContractDTOShort reqDTO);
    void delete(Integer id);
    List<Contract> findByRenterId(Long renterId);
    List<Contract> findByVendorId(Long vendorId);

}
