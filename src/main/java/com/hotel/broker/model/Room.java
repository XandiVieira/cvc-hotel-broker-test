package com.hotel.broker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Room implements Serializable {

    private int roomId;
    private String categoryName;
    private Price price;
}
