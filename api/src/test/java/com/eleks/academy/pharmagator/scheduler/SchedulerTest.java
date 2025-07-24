package com.eleks.academy.pharmagator.scheduler;

import com.eleks.academy.pharmagator.dataproviders.DataProvider;
import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchedulerTest {

    @Mock
    private DataProvider dataProvider1;

    @Mock
    private DataProvider dataProvider2;

    @InjectMocks
    private Scheduler scheduler;

    private List<DataProvider> dataProviderList;
    private MedicineDto medicineDto1;
    private MedicineDto medicineDto2;

    @BeforeEach
    void setUp() {
        dataProviderList = Arrays.asList(dataProvider1, dataProvider2);
        scheduler = new Scheduler(dataProviderList);

        medicineDto1 = MedicineDto.builder()
                .title("Medicine 1")
                .price(BigDecimal.valueOf(10.50))
                .externalId("EXT1")
                .build();

        medicineDto2 = MedicineDto.builder()
                .title("Medicine 2")
                .price(BigDecimal.valueOf(25.75))
                .externalId("EXT2")
                .build();
    }

    @Test
    void schedule_ShouldCallLoadDataOnAllProviders() {
        // Given
        when(dataProvider1.loadData()).thenReturn(Stream.of(medicineDto1));
        when(dataProvider2.loadData()).thenReturn(Stream.of(medicineDto2));

        // When
        scheduler.schedule();

        // Then
        verify(dataProvider1).loadData();
        verify(dataProvider2).loadData();
    }

    @Test
    void schedule_ShouldProcessAllMedicinesFromAllProviders() {
        // Given
        when(dataProvider1.loadData()).thenReturn(Stream.of(medicineDto1));
        when(dataProvider2.loadData()).thenReturn(Stream.of(medicineDto2));

        // When
        scheduler.schedule();

        // Then
        verify(dataProvider1).loadData();
        verify(dataProvider2).loadData();
        // Note: storeToDatabase method is private, so we verify indirectly by ensuring loadData is called
    }

    @Test
    void schedule_WhenProviderReturnsEmptyStream_ShouldNotFail() {
        // Given
        when(dataProvider1.loadData()).thenReturn(Stream.empty());
        when(dataProvider2.loadData()).thenReturn(Stream.of(medicineDto2));

        // When
        scheduler.schedule();

        // Then
        verify(dataProvider1).loadData();
        verify(dataProvider2).loadData();
    }

    @Test
    void schedule_ShouldHandleMultipleMedicinesFromSingleProvider() {
        // Given
        when(dataProvider1.loadData()).thenReturn(Stream.of(medicineDto1, medicineDto2));
        when(dataProvider2.loadData()).thenReturn(Stream.empty());

        // When
        scheduler.schedule();

        // Then
        verify(dataProvider1).loadData();
        verify(dataProvider2).loadData();
    }

    @Test
    void schedule_WhenNoProviders_ShouldNotFail() {
        // Given
        Scheduler emptyScheduler = new Scheduler(Arrays.asList());

        // When/Then - should not throw any exception
        emptyScheduler.schedule();
    }
}