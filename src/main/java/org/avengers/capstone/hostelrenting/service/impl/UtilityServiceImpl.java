package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.model.UCategory;
import org.avengers.capstone.hostelrenting.model.UType;
import org.avengers.capstone.hostelrenting.model.Utility;
import org.avengers.capstone.hostelrenting.repository.UtilityRepository;
import org.avengers.capstone.hostelrenting.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UtilityServiceImpl implements UtilityService {
    private UtilityRepository utilityRepository;

    @Autowired
    public void setUtilityRepository(UtilityRepository utilityRepository) {
        this.utilityRepository = utilityRepository;
    }

    @Override
    public Set<UCategory> getNearbyUtilities(Double latitude, Double longitude, Double distance) {
        Set<UCategory> resCategories = new HashSet<>();
        Set<UType> resTypes = new HashSet<>();
        List<Utility> resUtilities = utilityRepository.getNearbyUtilities(latitude, longitude, distance);

        resUtilities.forEach(utility -> resTypes.add(utility.getUType()));

        resTypes.forEach(type -> resCategories.add(type.getUCategory()));

        resTypes.forEach(uType -> {
            uType.setUtilities(null);
            resUtilities.forEach(utility -> {
                if (utility.getUType().getUTypeId()==uType.getUTypeId()){
                    List<Utility> tempUtility = uType.getUtilities();
                    if (tempUtility == null)
                        tempUtility = new ArrayList<>();
                    tempUtility.add(utility);
                    uType.setUtilities(tempUtility);
                }
            });
        });

        resCategories.forEach(uCategory -> {
            uCategory.setUTypes(null);
            resTypes.forEach(uType -> {
                if (uType.getUCategory().getUCategoryId()==uCategory.getUCategoryId()){
                    List<UType> tempUType = uCategory.getUTypes();
                    if (tempUType == null)
                        tempUType = new ArrayList<>();
                    tempUType.add(uType);
                    uCategory.setUTypes(tempUType);
                }
            });
        });

        return resCategories;
    }
}
