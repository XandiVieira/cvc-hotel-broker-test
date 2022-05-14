package com.hotel.broker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Hotel implements Serializable {

    private int id;
    private String name;
    private int cityCode;
    private String cityName;
    private List<Room> rooms;
}
