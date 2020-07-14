package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Contract;
import org.avengers.capstone.hostelrenting.model.Deal;
import org.avengers.capstone.hostelrenting.repository.DealRepository;
import org.avengers.capstone.hostelrenting.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<Deal> findAll() {
        return dealRepository.findAll();
    }

    //TODO: check dupplicate
    @Override
    public Deal save(Deal deal) {
        return dealRepository.save(deal);
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id))
            throw new EntityNotFoundException(Deal.class, "id", id.toString());

        dealRepository.deleteById(id);
    }

    private boolean isNotFound(Integer id) {
        Optional<Deal> deal = dealRepository.findById(id);
        return deal.isEmpty();
    }
}
