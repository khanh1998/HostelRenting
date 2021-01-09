package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.TypeFacility;

import java.util.List;

public interface TypeFacilityService{
    TypeFacility create(TypeFacility input);
    void deleteById(List<Integer> indexes);
    TypeFacility update(TypeFacility input);
}
