package com.hotel.broker.service;

import com.hotel.broker.controller.dto.CategoryDto;
import com.hotel.broker.controller.dto.HotelDto;
import com.hotel.broker.controller.dto.PriceDto;
import com.hotel.broker.controller.dto.RoomDto;
import com.hotel.broker.exception.CustomInvalidDate;
import com.hotel.broker.model.Hotel;
import com.hotel.broker.model.Price;
import com.hotel.broker.model.Room;
import com.hotel.broker.service.broker.HotelBrokerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @InjectMocks
    private TripService tripService;

    @Mock
    private HotelBrokerService hotelBrokerService;

    @Test
    void getTripHotelsDetailsWithNullHotelId() {
        List<HotelDto> hotelDtos = createListOfHotelDto();
        List<Hotel> hotels = createListOfHotels();
        Long hotelId = 4L;
        Long cityId = 7110L;
        String checkInDate = "08/05/2022";
        String checkOutDate = "10/05/2022";
        int numberOfAdults = 2;
        int numberOfChildren = 1;

        when(hotelBrokerService.getAllHotelsByCity(cityId)).thenReturn(hotels);

        List<HotelDto> tripHotelsDetails = tripService.getTripHotelsDetails(null, cityId, checkInDate, checkOutDate, numberOfAdults, numberOfChildren);

        verify(hotelBrokerService, times(1)).getAllHotelsByCity(cityId);
    }

    @Test
    void getTripHotelsDetailsWithNullCityId() {
        List<HotelDto> hotelDtos = createListOfHotelDto();
        List<Hotel> hotels = createListOfHotels();
        Long hotelId = 4L;
        Long cityId = 7110L;
        String checkInDate = "08/05/2022";
        String checkOutDate = "10/05/2022";
        int numberOfAdults = 2;
        int numberOfChildren = 1;

        when(hotelBrokerService.getHotelById(hotelId)).thenReturn(hotels);

        List<HotelDto> tripHotelsDetails = tripService.getTripHotelsDetails(hotelId, null, checkInDate, checkOutDate, numberOfAdults, numberOfChildren);

        verify(hotelBrokerService, times(1)).getHotelById(hotelId);
    }

    @Test
    void getTripHotelsDetailsWithInvalidDate() {
        List<HotelDto> hotelDtos = createListOfHotelDto();
        List<Hotel> hotels = createListOfHotels();
        Long hotelId = 4L;
        Long cityId = 7110L;
        String checkInDate = "as/05/2022";
        String checkOutDate = "10/25/2022";
        int numberOfAdults = 2;
        int numberOfChildren = 1;

        assertThatThrownBy(() ->
                tripService.getTripHotelsDetails(hotelId, null, checkInDate, checkOutDate, numberOfAdults, numberOfChildren))
                .isInstanceOf(CustomInvalidDate.class)
                .hasMessageContaining("Date must be in the format: ");
    }

    private List<Hotel> createListOfHotels() {
        Room room1 = new Room(500, "Teste name1", new Price(400, 100));
        Room room2 = new Room(800, "Teste name2", new Price(500, 200));
        Room room3 = new Room(500, "Teste name3", new Price(600, 250));
        Room room4 = new Room(800, "Teste name4", new Price(700, 150));
        Hotel hotel1 = new Hotel(1, "hotel name", 9626, "São Paulo", Arrays.asList(room1, room2, room3));
        Hotel hotel2 = new Hotel(2, "hotel name2", 7110, "Rio de Janeiro", Arrays.asList(room1, room4));
        return Arrays.asList(hotel1, hotel2);
    }

    private List<HotelDto> createListOfHotelDto() {
        RoomDto roomDto1 = new RoomDto(500, new CategoryDto("Teste name1"), new PriceDto(400, 100));
        RoomDto roomDto2 = new RoomDto(800, new CategoryDto("Teste name2"), new PriceDto(500, 200));
        RoomDto roomDto3 = new RoomDto(500, new CategoryDto("Teste name3"), new PriceDto(600, 250));
        RoomDto roomDto4 = new RoomDto(800, new CategoryDto("Teste name4"), new PriceDto(700, 150));
        HotelDto hotelDto1 = new HotelDto(1, "São Paulo", "hotel name", Arrays.asList(roomDto1, roomDto2, roomDto3));
        HotelDto hotelDto2 = new HotelDto(2, "Porto Seguro", "hotel name2", Arrays.asList(roomDto1, roomDto4));
        return Arrays.asList(hotelDto1, hotelDto2);
    }
}