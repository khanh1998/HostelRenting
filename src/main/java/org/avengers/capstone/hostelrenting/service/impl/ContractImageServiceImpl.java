package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.ContractImage;
import org.avengers.capstone.hostelrenting.repository.ContractImageRepository;
import org.avengers.capstone.hostelrenting.service.ContractImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author duattt on 23/11/2020
 * @created 23/11/2020 - 10:44
 * @project youthhostelapp
 */
@Service
public class ContractImageServiceImpl implements ContractImageService {

    private ContractImageRepository contractImageRepository;

    @Autowired
    public void setContractImageRepository(ContractImageRepository contractImageRepository) {
        this.contractImageRepository = contractImageRepository;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<ContractImage> model = contractImageRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(ContractImage.class, "id", id.toString());
    }

    @Override
    public ContractImage findById(Integer id) {
        checkExist(id);
        return contractImageRepository.getOne(id);
    }

    @Override
    public ContractImage create(ContractImage reqModel) {
        return contractImageRepository.save(reqModel);
    }

    @Override
    public ContractImage update(ContractImage reqModel) {
        return contractImageRepository.save(reqModel);
    }
}
