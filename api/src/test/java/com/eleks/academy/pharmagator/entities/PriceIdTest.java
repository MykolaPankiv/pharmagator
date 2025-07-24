package com.eleks.academy.pharmagator.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceIdTest {

    @Test
    void constructor_ShouldCreatePriceIdWithAllArgs() {
        // When
        PriceId priceId = new PriceId(1L, 2L);

        // Then
        assertThat(priceId.getPharmacyId()).isEqualTo(1L);
        assertThat(priceId.getMedicineId()).isEqualTo(2L);
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyPriceId() {
        // When
        PriceId priceId = new PriceId();

        // Then
        assertThat(priceId.getPharmacyId()).isNull();
        assertThat(priceId.getMedicineId()).isNull();
    }

    @Test
    void setters_ShouldUpdatePriceIdFields() {
        // Given
        PriceId priceId = new PriceId();

        // When
        priceId.setPharmacyId(3L);
        priceId.setMedicineId(4L);

        // Then
        assertThat(priceId.getPharmacyId()).isEqualTo(3L);
        assertThat(priceId.getMedicineId()).isEqualTo(4L);
    }

    @Test
    void equals_ShouldReturnTrueForSamePriceIds() {
        // Given
        PriceId priceId1 = new PriceId(1L, 2L);
        PriceId priceId2 = new PriceId(1L, 2L);

        // When/Then
        assertThat(priceId1).isEqualTo(priceId2);
    }

    @Test
    void equals_ShouldReturnFalseForDifferentPriceIds() {
        // Given
        PriceId priceId1 = new PriceId(1L, 2L);
        PriceId priceId2 = new PriceId(1L, 3L);

        // When/Then
        assertThat(priceId1).isNotEqualTo(priceId2);
    }

    @Test
    void hashCode_ShouldReturnSameValueForSamePriceIds() {
        // Given
        PriceId priceId1 = new PriceId(1L, 2L);
        PriceId priceId2 = new PriceId(1L, 2L);

        // When/Then
        assertThat(priceId1.hashCode()).isEqualTo(priceId2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Given
        PriceId priceId = new PriceId(1L, 2L);

        // When
        String toString = priceId.toString();

        // Then
        assertThat(toString).contains("pharmacyId=1", "medicineId=2");
    }
}