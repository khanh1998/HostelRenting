package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.model.HostelImage;

import java.util.List;

public interface HostelImageService {
    HostelImage findHostelImageById(Integer id);
    List<HostelImage> findAllHostelImage();
    HostelImage saveHostelImage(HostelImage hostelImage);
    void deleteHostelImage(Integer id);
}
