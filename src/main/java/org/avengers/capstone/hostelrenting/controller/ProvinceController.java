package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.ProvinceDTO;
import org.avengers.capstone.hostelrenting.dto.response.Response;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/provinces")
public class ProvinceController {
    private ProvinceService provinceService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setProvinceService(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping
    public Response<?> getAllProvinces(){
        List<ProvinceDTO> results = provinceService.findAll()
                .stream()
                .map(province -> modelMapper.map(province, ProvinceDTO.class))
                .collect(Collectors.toList());
        return Response.ok(results, "Get all successfully");
    }

    @GetMapping("/{provinceId}")
    public Province getProvinceById(@PathVariable int provinceId) throws EntityNotFoundException{
        Province province = provinceService.findById(provinceId);
        System.out.println("Object returned: " + province);
//        if (province != null) {
//            ProvinceDTO provinceDTO = modelMapper.map(province, ProvinceDTO.class);
//            return Response.ok(provinceDTO, "Get successfully");
//        }
        return province;
    }

    @PostMapping
    public Response<?> createProvince(@Valid @RequestBody ProvinceDTO provinceDTO){
        Province province = modelMapper.map(provinceDTO, Province.class);
        Province createdProvince = provinceService.save(province);
        provinceDTO = modelMapper.map(province, ProvinceDTO.class);
        return Response.ok(provinceDTO, "Add successfully");
    }



}
