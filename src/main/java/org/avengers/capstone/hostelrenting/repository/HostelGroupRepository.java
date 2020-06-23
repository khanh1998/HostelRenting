package capstone.tdhostel.repository;

import capstone.tdhostel.model.HostelGroup;
import capstone.tdhostel.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostelGroupRepository extends JpaRepository<HostelGroup, Integer> {
    @Query("SELECT h from HostelGroup h where h.vendor in (SELECT v from Vendor v where v.vendorId = ?1)")
    List<HostelGroup> findAllHostelGroupByVendorId(Integer vendorId);
}
