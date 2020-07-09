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

@Service
public class TypeStatusServiceImpl implements TypeStatusService {

    private TypeStatusRepository typeStatusRepository;

    @Autowired
    public void setTypeStatusRepository(TypeStatusRepository typeStatusRepository) {
        this.typeStatusRepository = typeStatusRepository;
    }

    @Override
    public TypeStatus save(TypeStatus typeStatus) {
        if (typeStatusRepository.getByTypeStatusName(typeStatus.getTypeStatusName()) != null)
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "type_status_name", typeStatus.getTypeStatusName()));

        return typeStatusRepository.save(typeStatus);
    }

    @Override
    public TypeStatus findById(Integer id) {
        if(isNotFound(id))
            throw new EntityNotFoundException(TypeStatus.class, "id", id.toString());

        return typeStatusRepository.getOne(id);
    }

    @Override
    public List<TypeStatus> findAll() {
        return typeStatusRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(TypeStatus.class, "id", id.toString());
        }
        typeStatusRepository.deleteById(id);
    }


    private boolean isNotFound(Integer id) {
        Optional<TypeStatus> typeStatus = typeStatusRepository.findById(id);
        return typeStatus.isEmpty();
    }
}
