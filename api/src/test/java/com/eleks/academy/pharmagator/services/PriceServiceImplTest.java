package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.input.PriceDto;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private MedicineRepository medicineRepository;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PriceServiceImpl priceService;

    private Price price;
    private PriceDto priceDto;
    private PriceId priceId;

    @BeforeEach
    void setUp() {
        Long pharmacyId = 1L;
        Long medicineId = 2L;
        
        priceId = new PriceId(pharmacyId, medicineId);
        
        price = Price.builder()
                .pharmacyId(pharmacyId)
                .medicineId(medicineId)
                .price(BigDecimal.valueOf(15.99))
                .externalId("EXT123")
                .build();

        priceDto = new PriceDto();
        priceDto.setPrice(BigDecimal.valueOf(15.99));
        priceDto.setExternalId("EXT123");
    }

    @Test
    void findAll_ShouldReturnAllPrices() {
        // Given
        List<Price> prices = Arrays.asList(price);
        when(priceRepository.findAll()).thenReturn(prices);

        // When
        List<Price> result = priceService.findAll();

        // Then
        assertThat(result).isEqualTo(prices);
        verify(priceRepository).findAll();
    }

    @Test
    void findById_WhenPriceExists_ShouldReturnPrice() {
        // Given
        Long pharmacyId = 1L;
        Long medicineId = 2L;
        when(priceRepository.findById(any(PriceId.class))).thenReturn(Optional.of(price));

        // When
        Optional<Price> result = priceService.findById(pharmacyId, medicineId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(price);
        verify(priceRepository).findById(any(PriceId.class));
    }

    @Test
    void findById_WhenPriceDoesNotExist_ShouldReturnEmpty() {
        // Given
        Long pharmacyId = 999L;
        Long medicineId = 888L;
        when(priceRepository.findById(any(PriceId.class))).thenReturn(Optional.empty());

        // When
        Optional<Price> result = priceService.findById(pharmacyId, medicineId);

        // Then
        assertThat(result).isEmpty();
        verify(priceRepository).findById(any(PriceId.class));
    }

    @Test
    void save_ShouldMapDtoAndSavePrice() {
        // Given
        when(modelMapper.map(priceDto, Price.class)).thenReturn(price);
        when(priceRepository.save(price)).thenReturn(price);

        // When
        Price result = priceService.save(priceDto);

        // Then
        assertThat(result).isEqualTo(price);
        verify(modelMapper).map(priceDto, Price.class);
        verify(priceRepository).save(price);
    }

    @Test
    void update_WhenPriceExists_ShouldUpdateAndReturnPrice() {
        // Given
        Long pharmacyId = 1L;
        Long medicineId = 2L;
        Price updatedPrice = Price.builder()
                .pharmacyId(pharmacyId)
                .medicineId(medicineId)
                .price(BigDecimal.valueOf(19.99))
                .externalId("EXT456")
                .build();

        when(priceRepository.findById(any(PriceId.class))).thenReturn(Optional.of(price));
        when(modelMapper.map(priceDto, Price.class)).thenReturn(updatedPrice);
        when(priceRepository.save(any(Price.class))).thenReturn(updatedPrice);

        // When
        Optional<Price> result = priceService.update(pharmacyId, medicineId, priceDto);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getPharmacyId()).isEqualTo(pharmacyId);
        assertThat(result.get().getMedicineId()).isEqualTo(medicineId);
        verify(priceRepository).findById(any(PriceId.class));
        verify(modelMapper).map(priceDto, Price.class);
        verify(priceRepository).save(any(Price.class));
    }

    @Test
    void update_WhenPriceDoesNotExist_ShouldReturnEmpty() {
        // Given
        Long pharmacyId = 999L;
        Long medicineId = 888L;
        when(priceRepository.findById(any(PriceId.class))).thenReturn(Optional.empty());

        // When
        Optional<Price> result = priceService.update(pharmacyId, medicineId, priceDto);

        // Then
        assertThat(result).isEmpty();
        verify(priceRepository).findById(any(PriceId.class));
        verify(modelMapper, never()).map(any(), any());
        verify(priceRepository, never()).save(any());
    }

    @Test
    void deleteById_ShouldCallRepositoryDeleteById() {
        // Given
        Long pharmacyId = 1L;
        Long medicineId = 2L;

        // When
        priceService.deleteById(pharmacyId, medicineId);

        // Then
        verify(priceRepository).deleteById(any(PriceId.class));
    }
}