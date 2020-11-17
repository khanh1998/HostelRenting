package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Type;

import java.util.Collection;
import java.util.List;

public interface TypeService {

    void checkExist(Integer id);
    Type findById(Integer id);
    Type create(Type reqModel);
    Type update(Type reqModel);
    void deleteById(Integer id);
    List<Type> findByHostelGroupId(Integer hostelGroupId);

    Collection<Type> handleAfterRetrieve(Collection<Type> types);
    Type countAvailableRoomAndCurrentBooking(Type type);


    /**
     * Get hostel types based on input factors
     * In case all factor are present, descending priority factors are: long, lat, distance; school; hometown
     *
     * @param latitude of the input location
     * @param longitude of the input location
     * @param distance radius from the input location
     * @param sortBy attribute to sort the result
     * @param asc true for ascending order
     * @param size size of a result
     * @param page page of the result
     * @return corresponding hostel types with factors
     */
    Collection<Type> searchWithMainFactors(Double latitude, Double longitude,
                                           Double distance, Integer schoolId,
                                           Integer provinceId, Integer requestId,
                                           String sortBy, Boolean asc, int size,
                                           int page);

    Collection<Type> filtering(Collection<Type> types, Integer schoolId, Integer provinceId,
                               Integer categoryId, Float minPrice,
                               Float maxPrice, Float minSuperficiality,
                               Float maxSuperficiality, Integer minCapacity,
                               Integer maxCapacity, Integer[] facilityIds,
                               Integer[] serviceIds, Integer[] regulationIds);
}
