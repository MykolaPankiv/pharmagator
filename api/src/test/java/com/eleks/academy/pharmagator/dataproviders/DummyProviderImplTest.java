package com.eleks.academy.pharmagator.dataproviders;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DummyProviderImplTest {

    private DummyProviderImpl dummyProvider;

    @BeforeEach
    void setUp() {
        dummyProvider = new DummyProviderImpl();
    }

    @Test
    void loadData_ShouldReturn100Medicines() {
        // When
        List<MedicineDto> medicines = dummyProvider.loadData().collect(Collectors.toList());

        // Then
        assertThat(medicines).hasSize(100);
    }

    @Test
    void loadData_ShouldGenerateMedicinesWithCorrectPattern() {
        // When
        List<MedicineDto> medicines = dummyProvider.loadData().collect(Collectors.toList());

        // Then
        for (int i = 0; i < medicines.size(); i++) {
            MedicineDto medicine = medicines.get(i);
            int expectedIndex = i + 1; // since IntStream.rangeClosed(1, 100)
            
            assertThat(medicine.getTitle()).isEqualTo("title" + expectedIndex);
            assertThat(medicine.getExternalId()).isEqualTo(String.valueOf(expectedIndex));
            assertThat(medicine.getPrice()).isNotNull();
            assertThat(medicine.getPrice()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
            assertThat(medicine.getPrice()).isLessThan(BigDecimal.ONE);
        }
    }

    @Test
    void loadData_ShouldGenerateRandomPrices() {
        // When
        List<MedicineDto> medicines = dummyProvider.loadData().collect(Collectors.toList());

        // Then
        // Verify that prices are random (not all the same)
        boolean hasVariation = medicines.stream()
                .map(MedicineDto::getPrice)
                .distinct()
                .count() > 1;
        
        assertThat(hasVariation).isTrue();
    }

    @Test
    void loadData_ShouldGenerateUniqueExternalIds() {
        // When
        List<MedicineDto> medicines = dummyProvider.loadData().collect(Collectors.toList());

        // Then
        long uniqueExternalIds = medicines.stream()
                .map(MedicineDto::getExternalId)
                .distinct()
                .count();
        
        assertThat(uniqueExternalIds).isEqualTo(100);
    }

    @Test
    void loadData_ShouldGenerateUniqueTitles() {
        // When
        List<MedicineDto> medicines = dummyProvider.loadData().collect(Collectors.toList());

        // Then
        long uniqueTitles = medicines.stream()
                .map(MedicineDto::getTitle)
                .distinct()
                .count();
        
        assertThat(uniqueTitles).isEqualTo(100);
    }
}