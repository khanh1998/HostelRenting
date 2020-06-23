package capstone.tdhostel.service.impl;

import capstone.tdhostel.model.HostelType;
import capstone.tdhostel.repository.HostelTypeRepository;
import capstone.tdhostel.service.HostelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelTypeServiceImpl implements HostelTypeService {
    @Autowired
    private HostelTypeRepository hostelTypeRepository;

    @Override
    public List<HostelType> findAllHostelType() {
        return hostelTypeRepository.findAll();
    }

    @Override
    public Optional<HostelType> findHostelTypeById(Integer id) {
        return hostelTypeRepository.findById(id);
    }

    @Override
    public HostelType findHostelTypeByHostelTypeId(Integer hostelTypeId) {
        return hostelTypeRepository.findById(hostelTypeId).get();
    }

    @Override
    public List<HostelType> findAllHostelTypeByHostelGroupId(Integer hostelGroupId) {
        return hostelTypeRepository.findAllHostelTypeByHostelGroupId(hostelGroupId);
    }

    @Override
    public HostelType saveHostelType(HostelType hostelType) {
        return hostelTypeRepository.save(hostelType);
    }

    @Override
    public void removeHostelType(HostelType hostelType) {
        hostelTypeRepository.delete(hostelType);
    }
}
