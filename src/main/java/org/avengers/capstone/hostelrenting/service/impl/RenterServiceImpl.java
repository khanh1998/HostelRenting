package capstone.tdhostel.service.impl;

import capstone.tdhostel.model.Renter;
import capstone.tdhostel.repository.RenterRepository;
import capstone.tdhostel.service.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RenterServiceImpl implements RenterService {
    @Autowired
    private RenterRepository renterRepository;

    @Override
    public List<Renter> findAllRenter() {
        return renterRepository.findAll();
    }

    @Override
    public Optional<Renter> findRenterById(Integer id) {
        return renterRepository.findById(id);
    }

    @Override
    public Renter findRenterByRenterId(Integer id) {
        return renterRepository.findById(id).get();
    }

    @Override
    public Renter saveRenter(Renter renter) {
        return renterRepository.save(renter);
    }

    @Override
    public void removeRenter(Renter renter) {
        renterRepository.delete(renter);
    }
}
