package org.avengers.capstone.hostelrenting.util;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.service.impl.ContractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author duattt on 9/16/20
 * @created 16/09/2020 - 12:00
 * @project youthhostelapp
 */
public class Utilities {
    private static final Logger logger = LoggerFactory.getLogger(Utilities.class);
    /**
     * Calculate Haversine Distance Algorithm between two places
     *
     * R = earth’s radius (mean radius = 6,371km)
     * Δlat = lat2− lat1
     * Δlong = long2− long1
     * a = sin²(Δlat/2) + cos(lat1).cos(lat2).sin²(Δlong/2)
     * c = 2.atan2(√a, √(1−a))
     * d = R.c
     */
    public static double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        Double latDistance = toRad(lat2 - lat1);
        Double lonDistance = toRad(lng2 - lng1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distance = Constant.EARTH_RADIUS * c;
        return Math.round(distance * 100.0) / 100.0;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    public static float roundFloatNumber(Float number){
        return (float) (Math.round(number * 100.0)/100.0);
    }

    public static MultipartFile pathToMultipartFile(String pathFile, String fileName, String originalFileName, String contentType){
        Path path = Paths.get(pathFile);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        logger.error(e.getMessage(), e);
        }
        MultipartFile multipartFile = new MockMultipartFile(fileName,
                originalFileName, contentType, content);
        return multipartFile;
    }
}
