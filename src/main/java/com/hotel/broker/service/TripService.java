package com.hotel.broker.service;

import com.hotel.broker.controller.dto.CategoryDto;
import com.hotel.broker.controller.dto.HotelDto;
import com.hotel.broker.controller.dto.PriceDto;
import com.hotel.broker.controller.dto.RoomDto;
import com.hotel.broker.exception.CustomInvalidDate;
import com.hotel.broker.model.Hotel;
import com.hotel.broker.model.Room;
import com.hotel.broker.service.broker.HotelBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TripService {

    private static final Float COMMISSION = 0.7F;

    private final HotelBrokerService hotelBrokerService;

    @Autowired
    public TripService(HotelBrokerService hotelBrokerService) {
        this.hotelBrokerService = hotelBrokerService;
    }

    public List<HotelDto> getTripHotelsDetails(Long hotelId, Long cityId, String checkInDate, String checkOutDate, int numberOfAdults, int numberOfChildren) {
        Date checkIn = convertStringToDate(checkInDate);
        Date checkOut = convertStringToDate(checkOutDate);
        long totalDays = calculateDifferenceInDaysBetweenDates(checkIn, checkOut);
        List<HotelDto> hotelDtoList = new ArrayList<>();
        List<Hotel> hotels;
        if (hotelId != null) {
            hotels = hotelBrokerService.getHotelById(hotelId);
        } else {
            hotels = hotelBrokerService.getAllHotelsByCity(cityId);
        }

        hotels.forEach(hotel -> {
            HotelDto hotelDto = getTripHotelDetails(hotel, totalDays, numberOfAdults, numberOfChildren);
            hotelDtoList.add(hotelDto);
        });
        return hotelDtoList;
    }

    private HotelDto getTripHotelDetails(Hotel hotel, long totalDays, int numberOfAdults, int numberOfChildren) {
        List<RoomDto> roomDtoList = new ArrayList<>();
        hotel.getRooms().forEach(room -> {
            float totalPrice = calculateTotalPrice(room, totalDays, numberOfAdults, numberOfChildren);
            PriceDto priceDto = new PriceDto(room.getPrice().getAdult(), room.getPrice().getChild());
            roomDtoList.add(new RoomDto(totalPrice, new CategoryDto(room.getCategoryName()), priceDto));
        });
        return new HotelDto(hotel.getId(), hotel.getCityName(), hotel.getName(), roomDtoList);
    }

    private float calculateTotalPrice(Room room, long totalDays, int numberOfAdults, int numberOfChildren) {
        float priceOfAdults = (room.getPrice().getAdult() * numberOfAdults * totalDays) / COMMISSION;
        float priceOfChildren = (room.getPrice().getChild() * numberOfChildren * totalDays) / COMMISSION;
        return (priceOfAdults + priceOfChildren);
    }

    private long calculateDifferenceInDaysBetweenDates(Date checkIn, Date checkOut) {
        long dif = checkOut.getTime() - checkIn.getTime();
        return TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS);
    }

    private Date convertStringToDate(String dateString) {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            throw new CustomInvalidDate("Date must be in the format: " + pattern);
        }
    }
}