package com.hotel.broker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDto {

    private int id;
    private String city;
    private String name;
    private List<RoomDto> rooms;
}
