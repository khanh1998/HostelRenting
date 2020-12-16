package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.UCategory;
import org.avengers.capstone.hostelrenting.model.Utility;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UtilityService {
    Set<UCategory> getNearbyUtilities(Double latitude, Double longitude, Double distance);
    Collection<Utility> getUtilitiesByCategoryId(Integer uCategoryId);
}
