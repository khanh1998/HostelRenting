package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface RenterRepository extends JpaRepository<Renter, UUID> {
    Optional<Renter> findByPhone(String phone);
    int countByPhone(String phone);
    Collection<Renter> findByUserIdIn(Collection<UUID> ids);
}
