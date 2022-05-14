package com.hotel.broker.service.broker;

import com.hotel.broker.exception.CustomNotFoundException;
import com.hotel.broker.model.Hotel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static javax.management.timer.Timer.ONE_MINUTE;

@Service
@Slf4j
public class HotelBrokerService {

    @Autowired
    private RestTemplate restTemplate;

    @Cacheable("getAllHotelsByCity")
    public List<Hotel> getAllHotelsByCity(Long cityId) {
        Hotel[] hotels = restTemplate.getForObject("https://cvcbackendhotel.herokuapp.com/hotels/avail/" + cityId, Hotel[].class);
        if (hotels != null) {
            return Arrays.stream(hotels).toList();
        } else {
            throw new CustomNotFoundException("City with id " + cityId + " was not found.");
        }
    }

    @Cacheable("getHotelById")
    public List<Hotel> getHotelById(Long id) {
        Hotel[] hotel = restTemplate.getForObject("https://cvcbackendhotel.herokuapp.com/hotels/" + id, Hotel[].class);
        if (hotel != null) {
            return Arrays.stream(hotel).toList();
        } else {
            throw new CustomNotFoundException("Hotel with id " + id + " was not found.");
        }
    }

    @Scheduled(fixedRate = ONE_MINUTE)
    @CacheEvict(value = {"getAllHotelsByCity", "getHotelById"}, allEntries = true)
    public void clearCache() {
        log.info("Cache clear.");
    }
}
