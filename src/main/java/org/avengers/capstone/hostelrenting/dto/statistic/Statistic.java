package org.avengers.capstone.hostelrenting.dto.statistic;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

//@Getter
//@Setter
//@AllArgsConstructor
@JsonPropertyOrder
public interface Statistic {
     @Value("#{target.province_name}")
     String getProvinceName();
     @Value("#{target.province_id}")
     int getProvinceId();
     @Value("#{target.district_id}")
     int getDistrictId();
     @Value("#{target.district_name}")
     String getDistrictName();
     @Value("#{target.ward_id}")
     int getWardId();
     @Value("#{target.ward_name}")
     String getWardName();
     @Value("#{target.avg_price}")
     float getAvgPrice();
     @Value("#{target.avg_superficiality}")
     float getAvgSuperficiality();
     @Value("#{target.count}")
     long getCount();
     @Value("#{target.street_id}")
     int getStreetId();
     @Value("#{target.street_name}")
     String getStreetName();
}
