package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.statistic.*;
import org.avengers.capstone.hostelrenting.repository.StatisticRepository;
import org.avengers.capstone.hostelrenting.service.*;
import org.avengers.capstone.hostelrenting.util.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService {
    private StatisticRepository repository;
    private StreetService streetService;
    private WardService wardService;
    private ProvinceService provinceService;
    private DistrictService districtService;
    private ModelMapper modelMapper;

    @Autowired
    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }

    @Autowired
    public void setProvinceService(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @Autowired
    public void setWardService(WardService wardService) {
        this.wardService = wardService;
    }

    @Autowired
    public void setRepository(StatisticRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setStreetService(StreetService streetService) {
        this.streetService = streetService;
    }

    @Override
    public StatisticDTOResponse getStatisticByDistrictId(Integer[] ids) {
        List<Statistic> statistics = repository.findByDistrictId(ids);
        StatisticDTOResponse response = getStatistic(statistics);

//        List<StatisticProvince> provinces = statistics.stream().map(statistic -> {
//            return StatisticProvince.builder().
//        });

        return response;
    }

    private StatisticDTOResponse getStatistic(List<Statistic> inputStatistic) {
        long count = inputStatistic.stream().map(Statistic::getCount).reduce(0L, Long::sum);
        Float totalPrice = inputStatistic.stream().map(Statistic::getAvgPrice).reduce((float) 0, Float::sum);
        Float totalSuperficiality = inputStatistic.stream().map(Statistic::getAvgSuperficiality).reduce((float) 0, (a, b) -> a + b);
        StatisticDTOResponse response = StatisticDTOResponse.builder()
                .avgPrice(Utilities.roundFloatNumber(totalPrice / count))
                .avgSuperficality(Utilities.roundFloatNumber(totalSuperficiality / count))
                .count(count)
                .provinces(getProvinceStatistic(inputStatistic)).build();
        return response;
    }

    private Set<StatisticProvince> getProvinceStatistic(List<Statistic> inputStatistic) {
        return inputStatistic.stream().collect(Collectors.groupingBy(Statistic::getProvinceId))
                .entrySet().stream()
                .map(s->{
                    long count = s.getValue().stream().map(Statistic::getCount).reduce(0L, Long::sum);
                    float totalPrice = s.getValue().stream().map(province -> {
                        if (province.getCount() > 1)
                            return province.getAvgPrice() * province.getCount();
                        return province.getAvgPrice();
                    }).reduce((float) 0, Float::sum);
                    float totalSuperficiality = s.getValue().stream().map(province -> {
                        if (province.getCount() > 1)
                            return province.getAvgSuperficiality() * province.getCount();
                        return province.getAvgSuperficiality();
                    }).reduce((float) 0, Float::sum);
                    return StatisticProvince.builder().count(count)
                            .provinceName(provinceService.findById(s.getKey()).getProvinceName())
                            .provinceId(s.getKey())
                            .avgPrice(Utilities.roundFloatNumber(totalPrice / count))
                            .avgSuperficiality(Utilities.roundFloatNumber(totalSuperficiality / count))
                            .districts(getDistrictStatistic(inputStatistic, s.getKey()))
                            .build();
                }).collect(Collectors.toSet());
    }

    private Set<StatisticDistrict> getDistrictStatistic(List<Statistic> inputStatistic, Integer provinceId){
        inputStatistic = inputStatistic.stream().filter(statistic -> statistic.getProvinceId() == provinceId).collect(Collectors.toList());
        List<Statistic> finalInputStatistic = inputStatistic;
        return inputStatistic.stream().collect(Collectors.groupingBy(Statistic::getDistrictId))
                .entrySet().stream()
                .map(s->{
                    long count = s.getValue().stream().map(Statistic::getCount).reduce(0L, Long::sum);
                    float totalPrice = s.getValue().stream().map(district -> {
                        if (district.getCount() > 1)
                            return district.getAvgPrice() * district.getCount();
                        return district.getAvgPrice();
                    }).reduce((float) 0, Float::sum);
                    float totalSuperficiality = s.getValue().stream().map(district -> {
                        if (district.getCount() > 1)
                            return district.getAvgSuperficiality() * district.getCount();
                        return district.getAvgSuperficiality();
                    }).reduce((float) 0, Float::sum);
                    return StatisticDistrict.builder().count(count)
                            .districtName(districtService.findById(s.getKey()).getDistrictName())
                            .districtId(s.getKey())
                            .avgPrice(Utilities.roundFloatNumber(totalPrice / count))
                            .avgSuperficiality(Utilities.roundFloatNumber(totalSuperficiality / count))
                            .wards(getWardStatistic(finalInputStatistic, s.getKey()))
                            .build();
                }).collect(Collectors.toSet());
    }

    private Set<StatisticWard> getWardStatistic(List<Statistic> inputStatistic, Integer districtId){
        inputStatistic = inputStatistic.stream().filter(statistic -> statistic.getDistrictId() == districtId).collect(Collectors.toList());
        List<Statistic> finalInputStatistic = inputStatistic;
        return inputStatistic.stream().collect(Collectors.groupingBy(Statistic::getWardId))
                .entrySet().stream()
                .map(s->{
                    long count = s.getValue().stream().map(Statistic::getCount).reduce(0L, Long::sum);
                    float totalPrice = s.getValue().stream().map(ward -> {
                        if (ward.getCount() > 1)
                            return ward.getAvgPrice() * ward.getCount();
                        return ward.getAvgPrice();
                    }).reduce((float) 0, Float::sum);
                    float totalSuperficiality = s.getValue().stream().map(ward -> {
                        if (ward.getCount() > 1)
                            return ward.getAvgSuperficiality() * ward.getCount();
                        return ward.getAvgSuperficiality();
                    }).reduce((float) 0, Float::sum);
                    return StatisticWard.builder().count(count)
                            .wardName(wardService.findById(s.getKey()).getWardName())
                            .wardId(s.getKey())
                            .avgPrice(Utilities.roundFloatNumber(totalPrice / count))
                            .avgSuperficality(Utilities.roundFloatNumber(totalSuperficiality / count))
                            .streets(getStreetStatistic(finalInputStatistic, s.getKey()))
                            .build();
                }).collect(Collectors.toSet());
    }

    private Set<StatisticStreet> getStreetStatistic(List<Statistic> inputStatistic, Integer wardId) {
        inputStatistic = inputStatistic.stream().filter(statistic -> statistic.getWardId() == wardId).collect(Collectors.toList());
        return inputStatistic.stream().collect(Collectors.groupingBy(Statistic::getStreetId))
                .entrySet().stream()
                .map(s -> {
                    long count = s.getValue().stream().map(Statistic::getCount).reduce(0L, Long::sum);
                    float totalPrice = s.getValue().stream().map(street -> {
                        if (street.getCount() > 1)
                            return street.getAvgPrice() * street.getCount();
                        return street.getAvgPrice();
                    }).reduce((float) 0, Float::sum);
                    float totalSuperficiality = s.getValue().stream().map(street -> {
                        if (street.getCount() > 1)
                            return street.getAvgSuperficiality() * street.getCount();
                        return street.getAvgSuperficiality();
                    }).reduce((float) 0, Float::sum);
                    return StatisticStreet.builder().count(count)
                            .streetName(streetService.findById(s.getKey()).getStreetName())
                            .streetId(s.getKey())
                            .avgPrice(Utilities.roundFloatNumber(totalPrice / count))
                            .avgSuperficality(Utilities.roundFloatNumber(totalSuperficiality / count))
                            .build();
                }).collect(Collectors.toSet());
    }

}
