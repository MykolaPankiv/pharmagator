package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.input.MedicineDto;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineServiceImplTest {

    @Mock
    private MedicineRepository medicineRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MedicineServiceImpl medicineService;

    private Medicine medicine;
    private MedicineDto medicineDto;

    @BeforeEach
    void setUp() {
        medicine = Medicine.builder()
                .id(1L)
                .title("Aspirin")
                .build();

        medicineDto = new MedicineDto();
        medicineDto.setTitle("Aspirin");
    }

    @Test
    void findAll_ShouldReturnAllMedicines() {
        // Given
        List<Medicine> medicines = Arrays.asList(medicine);
        when(medicineRepository.findAll()).thenReturn(medicines);

        // When
        List<Medicine> result = medicineService.findAll();

        // Then
        assertThat(result).isEqualTo(medicines);
        verify(medicineRepository).findAll();
    }

    @Test
    void findById_WhenMedicineExists_ShouldReturnMedicine() {
        // Given
        Long medicineId = 1L;
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(medicine));

        // When
        Optional<Medicine> result = medicineService.findById(medicineId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(medicine);
        verify(medicineRepository).findById(medicineId);
    }

    @Test
    void findById_WhenMedicineDoesNotExist_ShouldReturnEmpty() {
        // Given
        Long medicineId = 999L;
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.empty());

        // When
        Optional<Medicine> result = medicineService.findById(medicineId);

        // Then
        assertThat(result).isEmpty();
        verify(medicineRepository).findById(medicineId);
    }

    @Test
    void save_ShouldMapDtoAndSaveMedicine() {
        // Given
        when(modelMapper.map(medicineDto, Medicine.class)).thenReturn(medicine);
        when(medicineRepository.save(medicine)).thenReturn(medicine);

        // When
        Medicine result = medicineService.save(medicineDto);

        // Then
        assertThat(result).isEqualTo(medicine);
        verify(modelMapper).map(medicineDto, Medicine.class);
        verify(medicineRepository).save(medicine);
    }

    @Test
    void update_WhenMedicineExists_ShouldUpdateAndReturnMedicine() {
        // Given
        Long medicineId = 1L;
        Medicine updatedMedicine = Medicine.builder()
                .id(medicineId)
                .title("Updated Aspirin")
                .build();

        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(medicine));
        when(modelMapper.map(medicineDto, Medicine.class)).thenReturn(updatedMedicine);
        when(medicineRepository.save(any(Medicine.class))).thenReturn(updatedMedicine);

        // When
        Optional<Medicine> result = medicineService.update(medicineId, medicineDto);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(medicineId);
        verify(medicineRepository).findById(medicineId);
        verify(modelMapper).map(medicineDto, Medicine.class);
        verify(medicineRepository).save(any(Medicine.class));
    }

    @Test
    void update_WhenMedicineDoesNotExist_ShouldReturnEmpty() {
        // Given
        Long medicineId = 999L;
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.empty());

        // When
        Optional<Medicine> result = medicineService.update(medicineId, medicineDto);

        // Then
        assertThat(result).isEmpty();
        verify(medicineRepository).findById(medicineId);
        verify(modelMapper, never()).map(any(), any());
        verify(medicineRepository, never()).save(any());
    }

    @Test
    void delete_ShouldCallRepositoryDeleteById() {
        // Given
        Long medicineId = 1L;

        // When
        medicineService.delete(medicineId);

        // Then
        verify(medicineRepository).deleteById(medicineId);
    }
}