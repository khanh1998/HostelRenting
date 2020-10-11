package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.model.UCategory;
import org.avengers.capstone.hostelrenting.model.UType;
import org.avengers.capstone.hostelrenting.model.Utility;
import org.avengers.capstone.hostelrenting.repository.UtilityRepository;
import org.avengers.capstone.hostelrenting.service.UtilityService;
import org.avengers.capstone.hostelrenting.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UtilityServiceImpl implements UtilityService {
    @Autowired
    private UtilityRepository utilityRepository;

//    @Autowired
//    public void setUtilityRepository(UtilityRepository utilityRepository) {
//        this.utilityRepository = utilityRepository;
//    }

    @Override
    public Set<UCategory> getNearbyUtilities(Double latitude, Double longitude, Double distance) {
        Set<UCategory> resCategories = new HashSet<>();
        Set<UType> resTypes = new HashSet<>();
        List<Utility> resUtilities = utilityRepository.getNearbyUtilities(latitude, longitude, distance);

        resUtilities.forEach(utility -> {
            utility.setDistance(Utilities.calculateDistance(latitude, longitude, utility.getLatitude(), utility.getLongitude()));
        });

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
            uType.setUtilities(uType.getUtilities().stream().sorted(Comparator.comparingDouble(Utility::getDistance)).collect(Collectors.toList()));
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
