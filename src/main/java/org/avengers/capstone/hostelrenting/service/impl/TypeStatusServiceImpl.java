package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.TypeStatus;
import org.avengers.capstone.hostelrenting.repository.TypeStatusRepository;
import org.avengers.capstone.hostelrenting.service.TypeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TypeStatusServiceImpl implements TypeStatusService {
    TypeStatusRepository typeStatusRepository;

    @Autowired
    public void setTypeStatusRepository(TypeStatusRepository typeStatusRepository) {
        this.typeStatusRepository = typeStatusRepository;
    }

    @Override
    public TypeStatus findTypeStatusById(Integer id) {
        Optional<TypeStatus> typeStatus = typeStatusRepository.findById(id);
        if (typeStatus.isEmpty()){
            throw new EntityNotFoundException(TypeStatus.class, "id", id.toString());
        }else{
            return typeStatus.get();
        }
    }

    @Override
    public List<TypeStatus> findAllTypeStatus() {
        return typeStatusRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public TypeStatus save(TypeStatus typeStatus) {
        if (typeStatusRepository.getByStatusName(typeStatus.getStatusName()) != null) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "status_name", typeStatus.getStatusName()));
        }
        return typeStatusRepository.save(typeStatus);
    }

    @Override
    public void deleteTypeStatus(Integer id) {
        typeStatusRepository.deleteById(id);
    }
}
