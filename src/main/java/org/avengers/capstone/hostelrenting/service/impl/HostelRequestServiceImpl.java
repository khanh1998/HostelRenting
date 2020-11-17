package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Group;
import org.avengers.capstone.hostelrenting.model.HostelRequest;
import org.avengers.capstone.hostelrenting.repository.HostelRequestRepository;
import org.avengers.capstone.hostelrenting.service.HostelRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author duattt on 11/16/20
 * @created 16/11/2020 - 17:21
 * @project youthhostelapp
 */
@Service
public class HostelRequestServiceImpl implements HostelRequestService {
    private HostelRequestRepository hostelRequestRepository;

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
        HostelRequest resModel = hostelRequestRepository.getOne(requestId);

        return resModel;
    }

}
