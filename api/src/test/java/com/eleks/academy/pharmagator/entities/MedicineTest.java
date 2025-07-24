package com.eleks.academy.pharmagator.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MedicineTest {

    @Test
    void builder_ShouldCreateMedicineWithAllFields() {
        // When
        Medicine medicine = Medicine.builder()
                .id(1L)
                .title("Aspirin")
                .build();

        // Then
        assertThat(medicine.getId()).isEqualTo(1L);
        assertThat(medicine.getTitle()).isEqualTo("Aspirin");
    }

    @Test
    void constructor_ShouldCreateEmptyMedicine() {
        // When
        Medicine medicine = new Medicine();

        // Then
        assertThat(medicine.getId()).isNull();
        assertThat(medicine.getTitle()).isNull();
    }

    @Test
    void allArgsConstructor_ShouldCreateMedicineWithAllArgs() {
        // When
        Medicine medicine = new Medicine(1L, "Aspirin");

        // Then
        assertThat(medicine.getId()).isEqualTo(1L);
        assertThat(medicine.getTitle()).isEqualTo("Aspirin");
    }

    @Test
    void setters_ShouldUpdateMedicineFields() {
        // Given
        Medicine medicine = new Medicine();

        // When
        medicine.setId(2L);
        medicine.setTitle("Ibuprofen");

        // Then
        assertThat(medicine.getId()).isEqualTo(2L);
        assertThat(medicine.getTitle()).isEqualTo("Ibuprofen");
    }

    @Test
    void equals_ShouldReturnTrueForSameMedicines() {
        // Given
        Medicine medicine1 = Medicine.builder().id(1L).title("Aspirin").build();
        Medicine medicine2 = Medicine.builder().id(1L).title("Aspirin").build();

        // When/Then
        assertThat(medicine1).isEqualTo(medicine2);
    }

    @Test
    void hashCode_ShouldReturnSameValueForSameMedicines() {
        // Given
        Medicine medicine1 = Medicine.builder().id(1L).title("Aspirin").build();
        Medicine medicine2 = Medicine.builder().id(1L).title("Aspirin").build();

        // When/Then
        assertThat(medicine1.hashCode()).isEqualTo(medicine2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Given
        Medicine medicine = Medicine.builder().id(1L).title("Aspirin").build();

        // When
        String toString = medicine.toString();

        // Then
        assertThat(toString).contains("id=1", "title=Aspirin");
    }
}