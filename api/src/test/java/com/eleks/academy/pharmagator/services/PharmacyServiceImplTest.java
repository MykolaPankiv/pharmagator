package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.input.PharmacyDto;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PharmacyServiceImplTest {

    @Mock
    private PharmacyRepository pharmacyRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PharmacyServiceImpl pharmacyService;

    private Pharmacy pharmacy;
    private PharmacyDto pharmacyDto;

    @BeforeEach
    void setUp() {
        pharmacy = new Pharmacy(1L, "Test Pharmacy", "https://example.com/medicine/{id}");

        pharmacyDto = new PharmacyDto();
        pharmacyDto.setName("Test Pharmacy");
        pharmacyDto.setMedicineLinkTemplate("https://example.com/medicine/{id}");
    }

    @Test
    void findAll_ShouldReturnAllPharmacies() {
        // Given
        List<Pharmacy> pharmacies = Arrays.asList(pharmacy);
        when(pharmacyRepository.findAll()).thenReturn(pharmacies);

        // When
        List<Pharmacy> result = pharmacyService.findAll();

        // Then
        assertThat(result).isEqualTo(pharmacies);
        verify(pharmacyRepository).findAll();
    }

    @Test
    void findById_WhenPharmacyExists_ShouldReturnPharmacy() {
        // Given
        Long pharmacyId = 1L;
        when(pharmacyRepository.findById(pharmacyId)).thenReturn(Optional.of(pharmacy));

        // When
        Optional<Pharmacy> result = pharmacyService.findById(pharmacyId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(pharmacy);
        verify(pharmacyRepository).findById(pharmacyId);
    }

    @Test
    void findById_WhenPharmacyDoesNotExist_ShouldReturnEmpty() {
        // Given
        Long pharmacyId = 999L;
        when(pharmacyRepository.findById(pharmacyId)).thenReturn(Optional.empty());

        // When
        Optional<Pharmacy> result = pharmacyService.findById(pharmacyId);

        // Then
        assertThat(result).isEmpty();
        verify(pharmacyRepository).findById(pharmacyId);
    }

    @Test
    void save_ShouldMapDtoAndSavePharmacy() {
        // Given
        when(modelMapper.map(pharmacyDto, Pharmacy.class)).thenReturn(pharmacy);
        when(pharmacyRepository.save(pharmacy)).thenReturn(pharmacy);

        // When
        Pharmacy result = pharmacyService.save(pharmacyDto);

        // Then
        assertThat(result).isEqualTo(pharmacy);
        verify(modelMapper).map(pharmacyDto, Pharmacy.class);
        verify(pharmacyRepository).save(pharmacy);
    }

    @Test
    void update_WhenPharmacyExists_ShouldUpdateAndReturnPharmacy() {
        // Given
        Long pharmacyId = 1L;
        Pharmacy updatedPharmacy = new Pharmacy(pharmacyId, "Updated Pharmacy", "https://updated.com/medicine/{id}");

        when(pharmacyRepository.findById(pharmacyId)).thenReturn(Optional.of(pharmacy));
        when(modelMapper.map(pharmacyDto, Pharmacy.class)).thenReturn(updatedPharmacy);
        when(pharmacyRepository.save(any(Pharmacy.class))).thenReturn(updatedPharmacy);

        // When
        Optional<Pharmacy> result = pharmacyService.update(pharmacyId, pharmacyDto);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(pharmacyId);
        verify(pharmacyRepository).findById(pharmacyId);
        verify(modelMapper).map(pharmacyDto, Pharmacy.class);
        verify(pharmacyRepository).save(any(Pharmacy.class));
    }

    @Test
    void update_WhenPharmacyDoesNotExist_ShouldReturnEmpty() {
        // Given
        Long pharmacyId = 999L;
        when(pharmacyRepository.findById(pharmacyId)).thenReturn(Optional.empty());

        // When
        Optional<Pharmacy> result = pharmacyService.update(pharmacyId, pharmacyDto);

        // Then
        assertThat(result).isEmpty();
        verify(pharmacyRepository).findById(pharmacyId);
        verify(modelMapper, never()).map(any(), any());
        verify(pharmacyRepository, never()).save(any());
    }

    @Test
    void deleteById_ShouldCallRepositoryDeleteById() {
        // Given
        Long pharmacyId = 1L;

        // When
        pharmacyService.deleteById(pharmacyId);

        // Then
        verify(pharmacyRepository).deleteById(pharmacyId);
    }
}