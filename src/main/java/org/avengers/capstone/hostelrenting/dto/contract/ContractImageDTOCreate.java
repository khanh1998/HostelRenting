package org.avengers.capstone.hostelrenting.dto.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author duattt on 11/10/20
 * @created 10/11/2020 - 14:37
 * @project youthhostelapp
 */
@JsonIgnoreProperties(value = {"imageId", "contractId"})
public class ContractImageDTOCreate extends ContractImageDTO{
}
