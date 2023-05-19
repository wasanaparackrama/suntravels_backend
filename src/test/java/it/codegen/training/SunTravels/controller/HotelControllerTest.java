/*
 * Copyright (c) 2023. CodeGen International (Pvt) Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of CodeGen
 * International (Pvt) Ltd. ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with CodeGen International.
 *
 */
package it.codegen.training.SunTravels.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import it.codegen.training.SunTravels.dto.DeleteResponse;
import it.codegen.training.SunTravels.dto.HotelDTO;
import it.codegen.training.SunTravels.service.HotelService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 03 May 2023
 */
@WebMvcTest( HotelController.class )
class HotelControllerTest
{
    @MockBean
    private HotelService hotelService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private HotelController hotelController;

    @Test
    public void testGetAll() throws Exception
    {
        // Arrange
        List<HotelDTO> hotels = new ArrayList<>( Arrays.asList(
                new HotelDTO(1L, "Hotel 1", "City 1","0980878908"),
                new HotelDTO(2L, "Hotel 2", "City 2","0876546780")
        ));
        when(hotelService.findAllHotel()).thenReturn(hotels);

        // Act
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/hotels")).andReturn();

        // Assert
        int expectedSize = hotels.size();
        int actualSize = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.length()");
        assertEquals(expectedSize, actualSize);
        assertEquals( HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

    }

    @Test
    public void testGetById() throws Exception
    {
        // Arrange
        Long id = 1L;
        HotelDTO hotel = new HotelDTO(id, "Hotel 1", "City 1","0980878908");

        when(hotelService.findHotelById(id)).thenReturn(hotel);

        // Act
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/hotels/{id}",id)).andReturn();

        // Assert
        String expectedHotelName = "Hotel 1";
        String actualHotelName = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.hotelName");

        String expectedHotelCity = "City 1";
        String actualHotelCity = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.hotelCity");

        assertEquals(expectedHotelName, actualHotelName);
        assertEquals(expectedHotelCity, actualHotelCity);
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

    }

    @Test
    public void testSave() throws Exception
    {
        // Arrange
        HotelDTO hotel = new HotelDTO( 1L, "Hotel 1", "City 1","0980878908");
        when(hotelService.addHotel(hotel)).thenReturn(hotel);

        // Act
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/hotels").contentType( MediaType.APPLICATION_JSON)
                                                                    .content(objectMapper.writeValueAsString(hotel))).andReturn();


        // Assert
        Long expectedHotelId = hotel.getHotelId();
        int actualHotelId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.hotelId");

        String expectedHotelName = hotel.getHotelName();
        String actualHotelName = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.hotelName");

        String expectedHotelCity = hotel.getCity();
        String actualHotelCity = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.hotelCity");

        assertEquals(expectedHotelId, actualHotelId);
        assertEquals(expectedHotelName, actualHotelName);
        assertEquals(expectedHotelCity, actualHotelCity);
        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testUpdate() throws Exception
    {
        // Arrange
        Long id = 1L;
        HotelDTO hotel = new HotelDTO(id, "Hotel 1", "City 1","0980878908");
        HotelDTO updatedHotel = new HotelDTO(id,"Updated Hotel 1", "Updated City 1","0980878908");

        when(hotelService.updateHotel(id, hotel)).thenReturn(updatedHotel);

        // Act
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/hotels/{id}", id).contentType( MediaType.APPLICATION_JSON)
                                                                            .content(objectMapper.writeValueAsString(hotel))).andReturn();

        // Assert
        Long expectedHotelId = updatedHotel.getHotelId();
        int actualHotelId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.hotelId");

        String expectedHotelName = updatedHotel.getHotelName();
        String actualHotelName = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.hotelName");

        String expectedHotelCity = updatedHotel.getCity();
        String actualHotelCity = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.hotelCity");

        assertEquals(expectedHotelId, actualHotelId);
        assertEquals(expectedHotelName, actualHotelName);
        assertEquals(expectedHotelCity, actualHotelCity);
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testDelete() throws Exception
    {
        // Arrange
        Long id = 186L;
        DeleteResponse deleteResponse = new DeleteResponse("Hotel",id, "Hotel 1 removed successfully!!");
        when(hotelService.deleteHotel(id)).thenReturn(deleteResponse);

        // Act
        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/hotels/{id}", id)).andReturn();

        // Assert
        String expectedEntity = "Hotel";
        String actualEntity = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.entity");

        int actualId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.id");

        String expectedMessage = "Hotel 1 removed successfully!!";
        String actualMessage = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.message");

        assertEquals(expectedEntity, actualEntity);
        assertEquals(id, actualId);
        assertEquals( expectedMessage, actualMessage );

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

    }
}



