package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.UCategoryDTO;
import org.avengers.capstone.hostelrenting.model.UCategory;

import java.util.List;

public interface UCategoryService {
    List<UCategoryDTO> getAll();
}
