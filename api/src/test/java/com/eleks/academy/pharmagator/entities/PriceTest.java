package com.eleks.academy.pharmagator.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {

    @Test
    void builder_ShouldCreatePriceWithAllFields() {
        // When
        Price price = Price.builder()
                .pharmacyId(1L)
                .medicineId(2L)
                .price(BigDecimal.valueOf(15.99))
                .externalId("EXT123")
                .build();

        // Then
        assertThat(price.getPharmacyId()).isEqualTo(1L);
        assertThat(price.getMedicineId()).isEqualTo(2L);
        assertThat(price.getPrice()).isEqualTo(BigDecimal.valueOf(15.99));
        assertThat(price.getExternalId()).isEqualTo("EXT123");
    }

    @Test
    void constructor_ShouldCreateEmptyPrice() {
        // When
        Price price = new Price();

        // Then
        assertThat(price.getPharmacyId()).isNull();
        assertThat(price.getMedicineId()).isNull();
        assertThat(price.getPrice()).isNull();
        assertThat(price.getExternalId()).isNull();
    }

    @Test
    void allArgsConstructor_ShouldCreatePriceWithAllArgs() {
        // When
        Price price = new Price(1L, 2L, BigDecimal.valueOf(15.99), "EXT123", null);

        // Then
        assertThat(price.getPharmacyId()).isEqualTo(1L);
        assertThat(price.getMedicineId()).isEqualTo(2L);
        assertThat(price.getPrice()).isEqualTo(BigDecimal.valueOf(15.99));
        assertThat(price.getExternalId()).isEqualTo("EXT123");
    }

    @Test
    void setters_ShouldUpdatePriceFields() {
        // Given
        Price price = new Price();

        // When
        price.setPharmacyId(3L);
        price.setMedicineId(4L);
        price.setPrice(BigDecimal.valueOf(25.50));
        price.setExternalId("EXT456");

        // Then
        assertThat(price.getPharmacyId()).isEqualTo(3L);
        assertThat(price.getMedicineId()).isEqualTo(4L);
        assertThat(price.getPrice()).isEqualTo(BigDecimal.valueOf(25.50));
        assertThat(price.getExternalId()).isEqualTo("EXT456");
    }

    @Test
    void equals_ShouldReturnTrueForSamePrices() {
        // Given
        Price price1 = Price.builder()
                .pharmacyId(1L)
                .medicineId(2L)
                .price(BigDecimal.valueOf(15.99))
                .externalId("EXT123")
                .build();
        
        Price price2 = Price.builder()
                .pharmacyId(1L)
                .medicineId(2L)
                .price(BigDecimal.valueOf(15.99))
                .externalId("EXT123")
                .build();

        // When/Then
        assertThat(price1).isEqualTo(price2);
    }

    @Test
    void hashCode_ShouldReturnSameValueForSamePrices() {
        // Given
        Price price1 = Price.builder()
                .pharmacyId(1L)
                .medicineId(2L)
                .price(BigDecimal.valueOf(15.99))
                .externalId("EXT123")
                .build();
        
        Price price2 = Price.builder()
                .pharmacyId(1L)
                .medicineId(2L)
                .price(BigDecimal.valueOf(15.99))
                .externalId("EXT123")
                .build();

        // When/Then
        assertThat(price1.hashCode()).isEqualTo(price2.hashCode());
    }
}