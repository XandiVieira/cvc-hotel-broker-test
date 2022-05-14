package com.hotel.broker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PriceDto {

    private float pricePerDayAdult;
    private float pricePerDayChild;
}