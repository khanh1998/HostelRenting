package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.HostelRequest;

import java.util.Collection;
import java.util.Optional;

/**
 * @author duattt on 11/16/20
 * @created 16/11/2020 - 17:21
 * @project youthhostelapp
 */
public interface HostelRequestService {
    void checkExist(Integer requestId);
    HostelRequest createNew(HostelRequest request);
    HostelRequest findById(Integer requestId);
    Collection<HostelRequest> findByRenterId(Long renterId, int page, int size);
}
