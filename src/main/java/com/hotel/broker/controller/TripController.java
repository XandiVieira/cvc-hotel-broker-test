package com.hotel.broker.controller;

import com.hotel.broker.controller.dto.HotelDto;
import com.hotel.broker.service.TripService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/trip")
@Api(value = "API Trip simulation")
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping(value = "/hotels/{cityId}")
    @ApiOperation(value = "Get trip details of each hotel in a city")
    public List<HotelDto> getTripDetailOfHotelsByCityId(@PathVariable("cityId") final Long cityId,
                                                        @RequestParam("checkInDate") final String checkInDate,
                                                        @RequestParam("checkOutDate") final String checkOutDate,
                                                        @RequestParam("numberOfAdults") final int numberOfAdults,
                                                        @RequestParam("numberOfChildren") final int numberOfChildren) {
        return tripService.getTripHotelsDetails(null, cityId, checkInDate, checkOutDate, numberOfAdults, numberOfChildren);
    }

    @GetMapping(value = "/hotel/{id}")
    @ApiOperation(value = "Get trip details of specific hotel")
    public List<HotelDto> getTripDetailOfHotelById(@PathVariable("id") final Long id,
                                                   @RequestParam("checkInDate") final String checkInDate,
                                                   @RequestParam("checkOutDate") final String checkOutDate,
                                                   @RequestParam("numberOfAdults") final int numberOfAdults,
                                                   @RequestParam("numberOfChildren") final int numberOfChildren) {
        return tripService.getTripHotelsDetails(id, null, checkInDate, checkOutDate, numberOfAdults, numberOfChildren);
    }
}