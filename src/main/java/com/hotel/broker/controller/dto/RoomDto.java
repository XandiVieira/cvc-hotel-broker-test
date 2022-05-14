package com.hotel.broker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomDto {

    private float totalPrice;
    private CategoryDto category;
    private PriceDto priceDetail;
}
