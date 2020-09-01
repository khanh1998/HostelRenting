package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTOFull;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.repository.ProvinceRepository;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("dev")
@Tag("Service")
class ProvinceServiceImplTest {
    @Autowired
    private ProvinceService service;

    @MockBean
    private ModelMapper mockMapper;

    @MockBean
    private ProvinceRepository mockRepository;

    @Test
    @DisplayName("Test get all provinces")
    void testGetAll(){
        // set up mock
        Province mockModel1 = Mockito.mock(Province.class);
        Province mockModel2 = Mockito.mock(Province.class);
        when(mockRepository.findAll()).thenReturn(Arrays.asList(mockModel1, mockModel2));

        // Execute the service call
        List<ProvinceDTOFull> resDTOs = service.getAll()
                .stream()
                .map(model -> mockMapper.map(model, ProvinceDTOFull.class))
                .collect(Collectors.toList());

        // Assert the response
        assertEquals(2, resDTOs.size());
    }

    @Test
    @DisplayName("Test not found ")
    void testCheckNotFound(){
        // Set up mock
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Execute the service call
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> service.checkNotFound(5));

        // Assert the response
        Assertions.assertNotNull(exception);
    }

    @Test
    @DisplayName("Test findById fail")
    void testFindByIdFail(){
        // Set up mock
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Execute the service call
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, ()->service.checkNotFound(5));

        Assertions.assertNotNull(exception);

    }

    @Test
    @DisplayName("Test findById success")
    void testFindByIdSuccess(){
        // Set up mock
        Province mockModel = Mockito.mock(Province.class);
        when(mockRepository.findById(mockModel.getProvinceId())).thenReturn(Optional.of(mockModel));
        when(mockRepository.getOne(mockModel.getProvinceId())).thenReturn(mockModel);

        // Assert the response
        Assertions.assertEquals(service.findById(mockModel.getProvinceId()), mockModel);

    }

    @Test
    @DisplayName("Test save success")
    void testSaveSuccess(){
        // Set up mock
        ProvinceDTOFull reqDTO = Mockito.mock(ProvinceDTOFull.class);
        ProvinceDTOFull resDTO = Mockito.mock(ProvinceDTOFull.class);
        Province resModel = Mockito.mock(Province.class);
        Province reqModel = Mockito.mock(Province.class);
        when(mockMapper.map(reqDTO, Province.class)).thenReturn(reqModel);
        when(mockRepository.save(reqModel)).thenReturn(resModel);
        when(mockMapper.map(resModel, ProvinceDTOFull.class)).thenReturn(resDTO);

        // Assert the response
        Assertions.assertEquals(resModel, service.save(reqModel));
    }


}