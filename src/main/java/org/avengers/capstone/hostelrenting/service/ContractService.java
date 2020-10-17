package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOCreate;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOUpdate;
import org.avengers.capstone.hostelrenting.model.Contract;

import java.util.List;

public interface ContractService {
    void checkExist(Integer id);
    Contract findById(Integer id);
    Contract create(Contract reqModel);
    Contract confirm(Contract exModel, ContractDTOUpdate reqDTO);
    List<Contract> findByRenterId(Long renterId);
    List<Contract> findByVendorId(Long vendorId);
}
