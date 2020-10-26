package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.dto.statistic.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<org.avengers.capstone.hostelrenting.model.Statistic, Integer> {

    @Query(value = "SELECT sta.avg_price,sta.avg_superficiality,sta.count,s.street_id,s.street_name,w.ward_id,w.ward_name,d.district_id,d.district_name,p.province_id,p.province_name " +
            "FROM statistic AS sta,street_ward AS sw,street AS s,ward AS w,district AS d,province AS p " +
            "WHERE sw.street_ward_id=sta.street_ward_id " +
            "AND p.province_id=d.province_id " +
            "AND sw.street_id=s.street_id " +
            "AND w.ward_id=sw.ward_id " +
            "AND w.district_id=d.district_id " +
            "AND d.district_id IN :ids", nativeQuery = true)
    List<Statistic> findByDistrictId(Integer[] ids);
}
