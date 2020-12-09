package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Group;
import org.avengers.capstone.hostelrenting.model.HostelRequest;
import org.avengers.capstone.hostelrenting.repository.HostelRequestRepository;
import org.avengers.capstone.hostelrenting.service.HostelRequestService;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * @author duattt on 11/16/20
 * @created 16/11/2020 - 17:21
 * @project youthhostelapp
 */
@Service
public class HostelRequestServiceImpl implements HostelRequestService {
    private HostelRequestRepository hostelRequestRepository;
    private RenterService renterService;

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

    @Autowired
    public void setHostelRequestRepository(HostelRequestRepository hostelRequestRepository) {
        this.hostelRequestRepository = hostelRequestRepository;
    }

    @Override
    public void checkExist(Integer requestId) {
        Optional<HostelRequest> model = hostelRequestRepository.findById(requestId);
        if (model.isEmpty())
            throw new EntityNotFoundException(HostelRequest.class, "id", requestId.toString());
    }

    @Override
    public HostelRequest createNew(HostelRequest request) {
        //TODO: check duplicate request
        return hostelRequestRepository.save(request);
    }

    @Override
    public HostelRequest findById(Integer requestId) {
        checkExist((requestId));

        return hostelRequestRepository.getOne(requestId);
    }

    @Override
    public Collection<HostelRequest> findByRenterId(UUID renterId, int page, int size) {
        renterService.findById(renterId);
        Sort sort = Sort.by("dueTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        return hostelRequestRepository.findByRenter_UserId(renterId, pageable);
    }
}
