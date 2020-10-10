package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Deal;
import org.avengers.capstone.hostelrenting.model.Type;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.repository.DealRepository;
import org.avengers.capstone.hostelrenting.repository.RenterRepository;
import org.avengers.capstone.hostelrenting.repository.TypeRepository;
import org.avengers.capstone.hostelrenting.repository.VendorRepository;
import org.avengers.capstone.hostelrenting.service.DealService;
import org.avengers.capstone.hostelrenting.service.HostelTypeService;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DealServiceImpl implements DealService {
    @Autowired
    private DealRepository dealRepository;
    @Autowired
    private VendorRepository vendorRepository;
//    private VendorService vendorService;
//    private RenterService renterService;
//    private HostelTypeService hostelTypeService;
    @Autowired
    private RenterRepository renterRepository;
    @Autowired
    private TypeRepository hostelTypeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

//    @Autowired
//    public void setRenterService(RenterService renterService) {
//        this.renterService = renterService;
//    }
//
//    @Autowired
//    public void setHostelTypeService(HostelTypeService hostelTypeService) {
//        this.hostelTypeService = hostelTypeService;
//    }

//    @Autowired
//    public void setVendorService(VendorService vendorService) {
//        this.vendorService = vendorService;
//    }
//
//    @Autowired
//    public void setDealRepository(DealRepository dealRepository) {
//        this.dealRepository = dealRepository;
//    }

    /**
     * Check that object with given id is active or not
     * @param id input id
     */
    @Override
    public void checkActive(Integer id) {
        Optional<Deal> model = dealRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Deal.class, "id", id.toString());
    }

    @Override
    public Deal findById(Integer id) {
        checkActive(id);
        return dealRepository.getOne(id);
    }

    /**
     * Change status of deal from CREATED to DONE
     *
     * @param id a deal id to identify
     * @return Deal object with status has been updated
     */
    @Override
    public Deal changeStatus(Integer id, Deal.STATUS status) {
        Optional<Deal> existed = dealRepository.findById(id);
        if (existed.isPresent() && existed.get().getStatus().equals(Deal.STATUS.CREATED)){
            existed.get().setStatus(status);
            setUpdatedTime(existed.get());
        }
        return existed.orElse(null);
    }

    @Override
    public Deal create(DealDTOShort reqDTO) {
        Vendor existedVendor = vendorRepository.findById(reqDTO.getVendorId()).get();
        Renter existedRenter = renterRepository.findById(reqDTO.getRenterId()).get();
        Type existedType = hostelTypeRepository.findById(reqDTO.getTypeId()).get();

        Deal reqModel = modelMapper.map(reqDTO, Deal.class);
        reqModel.setVendor(existedVendor);
        reqModel.setRenter(existedRenter);
        reqModel.setType(existedType);
        reqModel.setStatus(Deal.STATUS.CREATED);
        reqModel.setCreatedAt(System.currentTimeMillis());

        return dealRepository.save(reqModel);
    }

    @Override
    public Deal update(DealDTOShort reqDTO) {
        checkActive(reqDTO.getDealId());


        //Update status
        Deal exModel = dealRepository.getOne(reqDTO.getDealId());
        if (!exModel.getStatus().equals(reqDTO.getStatus())) {
            exModel.setStatus(reqDTO.getStatus());
            setUpdatedTime(exModel);
            return dealRepository.save(exModel);
        }

        return null;
    }

    @Override
    public List<Deal> findByRenterId(Long renterId) {
//        renterService.checkExist(renterId);
        if (renterRepository.existsById(renterId)) {
            return renterRepository.findById(renterId).get().getDeals();
        }else{
            throw new EntityNotFoundException(Renter.class, "id", renterId.toString());
        }
    }

    @Override
    public List<Deal> findByVendorId(Long vendorId) {
//        vendorService.checkExist(vendorId);

        if (vendorRepository.existsById(vendorId)) {
            return vendorRepository.findById(vendorId).get().getDeals();
        }else{
            throw new EntityNotFoundException(Vendor.class, "id", vendorId.toString());
        }
    }

    private void setUpdatedTime(Deal exModel){
        exModel.setUpdatedAt(System.currentTimeMillis());
    }
}
