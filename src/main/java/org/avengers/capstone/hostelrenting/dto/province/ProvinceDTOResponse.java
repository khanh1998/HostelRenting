package org.avengers.capstone.hostelrenting.dto.province;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOResponse;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProvinceDTOResponse extends ProvinceDTO implements Serializable {
    private List<DistrictDTOResponse> districts;
}
