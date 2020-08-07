package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.ServiceDTO;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Service;
import org.avengers.capstone.hostelrenting.repository.ServiceRepository;
import org.avengers.capstone.hostelrenting.service.ServiceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("dev")
@Tag("Service")
class ServiceServiceImplTest {
    /**
     * Autowired the service need to test
     */
    @Autowired
    private ServiceService service;

    @MockBean
    private ServiceRepository mockRepository;

    @Test
    @DisplayName("Test get all services")
    void testGetAll(){
        // Set up mock
        Service mockModel1 = Mockito.mock(Service.class);
        Service mockModel2 = Mockito.mock(Service.class);
        when(mockRepository.findAll()).thenReturn(Arrays.asList(mockModel1, mockModel2));

        // Execute the service call
        List<ServiceDTO> serviceDTOs = service.getAll();

        // Assert the response
        Assertions.assertEquals(2, serviceDTOs.size());
    }

    @Test
    @DisplayName("Test found")
    void testFound(){
        //Set up mock
        Service mockModel = Mockito.mock(Service.class);
        when(mockRepository.findById(mockModel.getServiceId())).thenReturn(Optional.of(mockModel));

        // Assert the response
        Assertions.assertDoesNotThrow(() -> service.checkNotFound(mockModel.getServiceId()));
    }

    @Test
    @DisplayName("Test findById success")
    void testFindByIdSuccess(){
        // Set up mock
        Service mockModel = Mockito.mock(Service.class);
        when(mockRepository.findById(mockModel.getServiceId())).thenReturn(Optional.of(mockModel));
        when(mockRepository.getOne(mockModel.getServiceId())).thenReturn(mockModel);

        // Assert the response
        Assertions.assertEquals(service.findById(mockModel.getServiceId()), mockModel);

    }

    @Test
    @DisplayName("Test findById fail")
    void testFindByIdFail(){
        // Set up mock
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Assert the response
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, ()->service.checkNotFound(5));
        Assertions.assertNotNull(exception);

    }

    @Test
    @DisplayName("Test not found")
    void testNotFound(){
        //Set up mock
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Execute the service call
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> service.checkNotFound(5));

        // Assert the response
        Assertions.assertNotNull(exception);
    }
}