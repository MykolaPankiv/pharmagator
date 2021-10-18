package com.eleks.academy.pharmagator.dataproviders.dto.helsi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class HelsiMedicineDto {
    @JsonProperty("ItemId")
    private Long id;
    @JsonProperty("GoodsName")
    private String name;
    @JsonProperty("Price_min")
    private BigDecimal price;
    @JsonProperty("GoodsProducer")
    private String manufacturer;
}
