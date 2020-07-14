package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Contract;
import org.avengers.capstone.hostelrenting.model.District;

import java.util.List;

public interface ContractService {
    Contract findById(Integer id);
    List<Contract> findAll();
    Contract save(Contract contract);
    void deleteById(Integer id);

}
