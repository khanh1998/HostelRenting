package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Deal;
import org.avengers.capstone.hostelrenting.model.HostelType;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.repository.DealRepository;
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
    private DealRepository dealRepository;
    private VendorService vendorService;
    private RenterService renterService;
    private HostelTypeService hostelTypeService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

    @Autowired
    public void setHostelTypeService(HostelTypeService hostelTypeService) {
        this.hostelTypeService = hostelTypeService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setDealRepository(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<Deal> model = dealRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Deal.class, "id", id.toString());
    }

    @Override
    public Deal findById(Integer id) {
        checkExist(id);

        return dealRepository.getOne(id);
    }

    /**
     * Change status of deal from CREATED to DONE
     * @param id a deal id to identify
     * @return Deal object with status has been updated
     */
    @Override
    public Deal changeStatus(Integer id, Deal.Status status) {
        Optional<Deal> existed = dealRepository.findById(id);
        if (existed.isPresent() && existed.get().getStatus().equals(Deal.Status.CREATED))
            existed.get().setStatus(status);
        return existed.orElse(null);
    }

    @Override
    public Deal create(DealDTOShort reqDTO) {
        Vendor existedVendor = vendorService.findById(reqDTO.getVendorId());
        Renter existedRenter = renterService.findById(reqDTO.getRenterId());
        HostelType existedType = hostelTypeService.findById(reqDTO.getTypeId());

        Deal reqModel = modelMapper.map(reqDTO, Deal.class);
        reqModel.setVendor(existedVendor);
        reqModel.setRenter(existedRenter);
        reqModel.setHostelType(existedType);
        reqModel.setStatus(Deal.Status.CREATED);

        return dealRepository.save(reqModel);
    }

    @Override
    public Deal update(DealDTOShort reqDTO) {
        checkExist(reqDTO.getDealId());


        //Update status
        Deal exModel = dealRepository.getOne(reqDTO.getDealId());
        if (!exModel.getStatus().equals(reqDTO.getStatus())){
            exModel.setStatus(reqDTO.getStatus());
            return dealRepository.save(exModel);
        }

        return null;
    }

    @Override
    public List<Deal> findByRenterId(Integer renterId) {
        renterService.checkExist(renterId);

        return renterService.findById(renterId).getDeals();
    }

    @Override
    public List<Deal> findByVendorId(Integer vendorId) {
        vendorService.checkExist(vendorId);

        return vendorService.findById(vendorId).getDeals();
    }
}
