package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<Booking> findFirstByRenter_UserIdAndType_TypeIdAndStatusOrderByCreatedAtDesc(Long renterId, Integer typeId, Booking.STATUS status);
    Collection<Booking> findByType_TypeIdAndStatusIs(Integer typeId, Booking.STATUS status);
}

