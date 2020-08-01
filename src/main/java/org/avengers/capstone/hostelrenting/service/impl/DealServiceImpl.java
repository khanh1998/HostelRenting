package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Deal;
import org.avengers.capstone.hostelrenting.repository.DealRepository;
import org.avengers.capstone.hostelrenting.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DealServiceImpl implements DealService {
    private DealRepository dealRepository;


    @Autowired
    public void setDealRepository(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @Override
    public Deal findById(Integer id) {
        if (isNotFound(id))
            throw new EntityNotFoundException(Deal.class, "id", id.toString());

        return dealRepository.getOne(id);
    }

    /**
     * Change status of deal from CREATED to DONE
     * @param id a deal id to identify
     * @return Deal object with status has been updated
     */
    @Override
    public Deal changeStatus(Integer id, Deal.Status status) {
        Optional<Deal> existed = dealRepository.findById(id);
        if (existed.isPresent() &&existed.get().getStatus().equals(Deal.Status.CREATED))
            existed.get().setStatus(status);
        return existed.orElse(null);
    }

    @Override
    public Deal save(Deal deal) {
        return null;
    }

    private boolean isNotFound(Integer id) {
        Optional<Deal> deal = dealRepository.findById(id);
        return deal.isEmpty();
    }
}
