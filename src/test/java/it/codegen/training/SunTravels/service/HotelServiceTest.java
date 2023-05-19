/*
 * Copyright (c) 2023. CodeGen International (Pvt) Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of CodeGen
 * International (Pvt) Ltd. ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with CodeGen International.
 *
 */
package it.codegen.training.SunTravels.service;

import it.codegen.training.SunTravels.dto.HotelDTO;
import it.codegen.training.SunTravels.mapper.HotelMapper;
import it.codegen.training.SunTravels.model.Hotel;
import it.codegen.training.SunTravels.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 03 May 2023
 */
@WebMvcTest( HotelService.class )
class HotelServiceTest
{
    @MockBean
    private HotelRepository hotelRepository;

    @MockBean
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testSaveHotel()
    {
        // Given
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelName("Hotel 1");
        hotelDTO.setCity("City 1");

        Hotel entity = new Hotel();
        entity.setHotelName("Hotel 1");
        entity.setCity("City 1");

        Hotel savedEntity = new Hotel();
        savedEntity.setHotelId(1L);
        savedEntity.setHotelName("Hotel 1");
        savedEntity.setCity("City 1");

        HotelDTO savedDTO = new HotelDTO();
        savedDTO.setHotelId(1L);
        savedDTO.setHotelName("Hotel 1");
        savedDTO.setCity("City 1");

        when(hotelMapper.mapIn(hotelDTO)).thenReturn(entity);
        when(hotelRepository.save(entity)).thenReturn(savedEntity);
        when(hotelMapper.mapOut(savedEntity)).thenReturn(savedDTO);

        // When
        HotelDTO result = hotelService.addHotel(hotelDTO);

        // Then
        assertNotNull(result);
        assertEquals( result.getHotelId(), 1l );
        assertEquals( result.getHotelName(), hotelDTO.getHotelName() );
        assertEquals( result.getCity(), hotelDTO.getCity() );
        assertEquals(savedDTO, result);
    }


    @Test
    public void testGetHotelById() {
        Hotel hotel = new Hotel();
        hotel.setHotelId(1L);
        hotel.setHotelName("Hotel 1");
        hotel.setCity("City 1");

        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelId( 1L );
        hotelDTO.setHotelName("Hotel 1");
        hotelDTO.setCity("City 1");

        when(hotelRepository.findById(1L)).thenReturn( Optional.of(hotel));
        when(hotelMapper.mapOut(hotel)).thenReturn(hotelDTO);

        HotelDTO result = hotelService.findHotelById(1L);

        assertNotNull(result);
        assertEquals( hotel.getHotelId(), result.getHotelId() );
        assertEquals(hotel.getHotelName(), result.getHotelName());
        assertEquals(hotel.getCity(), result.getCity());
    }

    @Test
    void testGetAllHotels()
    {
        Hotel hotel1 = new Hotel();
        hotel1.setHotelId( 1L);
        hotel1.setHotelName("Test Hotel 1");
        hotel1.setCity("Test City 1");

        Hotel hotel2 = new Hotel();
        hotel2.setHotelId(2L);
        hotel2.setHotelName("Test Hotel 2");
        hotel2.setCity("Test City 2");

        List<Hotel> hotelList = Arrays.asList(hotel1, hotel2);

        when(hotelRepository.findAll()).thenReturn( hotelList );
        when( hotelMapper.mapOut( hotel1 ) ).thenReturn( new HotelDTO( hotel1.getHotelId(), hotel1.getHotelName(), hotel1.getCity(),hotel1.getPhoneNumber()) );
        when( hotelMapper.mapOut( hotel2 ) ).thenReturn( new HotelDTO( hotel2.getHotelId(), hotel2.getHotelName(), hotel2.getCity(),hotel2.getPhoneNumber()) );

        List<HotelDTO> resultList = hotelService.findAllHotel();

        assertThat( resultList ).hasSize( 2 );
    }


}
