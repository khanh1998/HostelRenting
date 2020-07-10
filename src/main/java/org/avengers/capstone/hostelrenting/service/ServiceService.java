package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Facility;
import org.avengers.capstone.hostelrenting.model.Service;

import java.util.List;

public interface ServiceService {
    Service save(Service service);
    Service findById(Integer id);
    List<Service> findAll();
    void deleteById(Integer id);

    long getCount();
}
