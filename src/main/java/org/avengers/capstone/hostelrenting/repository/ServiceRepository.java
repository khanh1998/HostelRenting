package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    Collection<Service> getByServiceNameContains(String name);

    Service getByServiceName(String name);
}
