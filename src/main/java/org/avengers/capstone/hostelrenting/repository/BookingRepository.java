package capstone.tdhostel.repository;

import capstone.tdhostel.model.Booking;
import capstone.tdhostel.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("SELECT b from Booking b where b.vendor in (SELECT v from Vendor v where v.vendorId = ?1)")
    List<Booking> findAllBookingByVendorId(Integer vendorId);

    @Query("SELECT b from Booking b where b.renter in (SELECT r from Renter r where r.renterId = ?1)")
    List<Booking> findAllBookingByRenterId(Integer renterId);

    @Query("SELECT b from Booking b where b.hostelType in (SELECT h from HostelType h where h.hostelTypeId = ?1)")
    List<Booking> findAllBookingByHostelTypeId(Integer hostelTypeId);
}
