package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingStatusRepository extends JpaRepository<BookingStatus, Integer> {
    BookingStatus getByStatusName(String name);
}
