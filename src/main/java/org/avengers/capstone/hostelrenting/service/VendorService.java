package capstone.tdhostel.service;

import capstone.tdhostel.dto.VendorDTO;
import capstone.tdhostel.model.Vendor;

import java.util.List;
import java.util.Optional;

public interface VendorService {
    List<Vendor> findAllVendor();
    Optional<Vendor> findVendorById(Integer vendorId);
    Vendor findVendorByVendorId(Integer vendorId);
    Vendor saveVendor(Vendor vendor);
    void removeVendor(Vendor vendor);
}
