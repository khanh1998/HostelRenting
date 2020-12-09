package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.deal.DealDTOCreate;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOUpdate;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Deal;
import org.avengers.capstone.hostelrenting.model.Type;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.repository.DealRepository;
import org.avengers.capstone.hostelrenting.service.DealService;
import org.avengers.capstone.hostelrenting.service.TypeService;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DealServiceImpl implements DealService {
    private DealRepository dealRepository;
    private VendorService vendorService;
    private RenterService renterService;
    private TypeService typeService;
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
    public void setHostelTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setDealRepository(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    /**
     * Check that object with given id is active or not
     *
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
        if (existed.isPresent() && existed.get().getStatus().equals(Deal.STATUS.CREATED)) {
            existed.get().setStatus(status);
            setUpdatedTime(existed.get());
        }
        return existed.orElse(null);
    }

    @Override
    public Deal create(DealDTOCreate reqDTO) {
        Optional<Deal> exDeal = dealRepository.findByRenter_UserIdAndType_TypeIdAndStatusIs(reqDTO.getRenterId(), reqDTO.getTypeId(), Deal.STATUS.CREATED);
        if (exDeal.isPresent()) {
            DealDTOUpdate updateDTO = modelMapper.map(reqDTO, DealDTOUpdate.class);
            updateDTO.setDealId(exDeal.get().getDealId());
            modelMapper.map(updateDTO, exDeal.get());
//            exDeal.get().setUpdatedAt(System.currentTimeMillis());
            dealRepository.save(exDeal.get());
        } else {
            Vendor existedVendor = vendorService.findById(reqDTO.getVendorId());
            Renter existedRenter = renterService.findById(reqDTO.getRenterId());
            Type existedType = typeService.findById(reqDTO.getTypeId());

            Deal reqModel = modelMapper.map(reqDTO, Deal.class);
            reqModel.setVendor(existedVendor);
            reqModel.setRenter(existedRenter);
            reqModel.setType(existedType);
            reqModel.setStatus(Deal.STATUS.CREATED);
            reqModel.setCreatedAt(System.currentTimeMillis());

            return dealRepository.save(reqModel);
        }
        return exDeal.get();
    }

    @Override
    public Deal update(DealDTOCreate reqDTO) {
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
    public List<Deal> findByRenterId(UUID renterId) {
        renterService.checkExist(renterId);

        return renterService.findById(renterId).getDeals();
    }

    @Override
    public List<Deal> findByVendorId(UUID vendorId) {
        vendorService.checkExist(vendorId);

        return vendorService.findById(vendorId).getDeals();
    }

    private void setUpdatedTime(Deal exModel) {
        exModel.setUpdatedAt(System.currentTimeMillis());
    }
}
