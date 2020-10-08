package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Integer> {

    @Query(value = "SELECT * FROM get_street_statistic(?1)", nativeQuery = true)
    List<Statistic> findByStreetWardId(String ids);
}
