package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface RenterRepository extends JpaRepository<Renter, Long> {
    Optional<Renter> findByPhone(String phone);
    Collection<Renter> findByUserIdIn(Collection<Long> ids);
}
