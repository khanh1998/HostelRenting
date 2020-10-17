package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOCreate;
import org.avengers.capstone.hostelrenting.model.Contract;

import java.util.List;

public interface ContractService {
    void checkActive(Integer id);
    Contract findById(Integer id);
    Contract create(Contract reqModel);
    Contract update(Contract reqModel);
    List<Contract> findByRenterId(Long renterId);
    List<Contract> findByVendorId(Long vendorId);
}
