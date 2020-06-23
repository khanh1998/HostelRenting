package capstone.tdhostel.service.impl;

import capstone.tdhostel.dto.VendorDTO;
import capstone.tdhostel.model.Vendor;
import capstone.tdhostel.repository.VendorRepository;
import capstone.tdhostel.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorServiceImpl implements VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public List<Vendor> findAllVendor() {
        return vendorRepository.findAll();
    }

    @Override
    public Optional<Vendor> findVendorById(Integer vendorId) {
        return vendorRepository.findById(vendorId);
    }

    @Override
    public Vendor findVendorByVendorId(Integer vendorId) {
        return vendorRepository.findById(vendorId).get();
    }

    @Override
    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Override
    public void removeVendor(Vendor vendor) {
        vendorRepository.delete(vendor);
    }
}
