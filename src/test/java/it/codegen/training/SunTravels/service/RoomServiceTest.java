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


import it.codegen.training.SunTravels.dto.RoomDTO;
import it.codegen.training.SunTravels.model.Contract;
import it.codegen.training.SunTravels.model.Hotel;
import it.codegen.training.SunTravels.model.Rooms;
import it.codegen.training.SunTravels.repository.ContractRepository;
import it.codegen.training.SunTravels.repository.HotelRepository;
import it.codegen.training.SunTravels.repository.RoomRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 09 May 2023
 */

@RunWith( SpringRunner.class)
@SpringBootTest
public class RoomServiceTest
{

    @Autowired
    private RoomService roomService;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private HotelRepository hotelRepository;

    @MockBean
    private ContractRepository contractRepository;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks( this );
    }

    @Test
    public void testAddRoom()
    {
        // arrange
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomId( 1 );
        roomDTO.setRoomType( "single" );
        roomDTO.setNoOfRooms( 10 );
        roomDTO.setMaxPeople( 1 );
        roomDTO.setCost( 100 );
        roomDTO.setHotelId( 1L );
        roomDTO.setHotelName( "Test Hotel" );
        roomDTO.setContractId( 1L );

        Rooms roomEntity = new Rooms();
        roomEntity.setRoomId( 1 );
        roomEntity.setRoomType( "single" );
        roomEntity.setNoOfRooms( 10 );
        roomEntity.setMaxPeople( 1 );
        roomEntity.setCost( 100 );
        Hotel hotelEntity = new Hotel();
        hotelEntity.setHotelId( 1L );
        hotelEntity.setHotelName( "Test Hotel" );
        roomEntity.setHotel( hotelEntity );
        Contract contractEntity = new Contract();
        contractEntity.setContractId( 1L );
        roomEntity.setContract( contractEntity );

        Mockito.when( hotelRepository.findById( 1L ) ).thenReturn( Optional.of( hotelEntity ) );
        Mockito.when( contractRepository.findById( 1L ) ).thenReturn( Optional.of( contractEntity ) );
        Mockito.when( roomRepository.save( Mockito.any( Rooms.class ) ) ).thenReturn( roomEntity );

        // act
        RoomDTO result = roomService.addRoom( roomDTO );

        // assert
        Assert.assertNotNull( result );
        Assert.assertEquals( result.getRoomId(), roomDTO.getRoomId() );
        Assert.assertEquals( result.getRoomType(), roomDTO.getRoomType() );
        Assert.assertEquals( result.getNoOfRooms(), roomDTO.getNoOfRooms() );
        Assert.assertEquals( result.getMaxPeople(), roomDTO.getMaxPeople() );
        Assert.assertEquals( result.getCost(), roomDTO.getCost() );
        Assert.assertEquals( result.getHotelId(), roomDTO.getHotelId() );
        Assert.assertEquals( result.getHotelName(), roomDTO.getHotelName() );
        Assert.assertEquals( result.getContractId(), roomDTO.getContractId() );

        verify( hotelRepository, times( 1 ) ).findById( 1L );
        verify( contractRepository, times( 1 ) ).findById( 1L );
        verify( roomRepository, times( 1 ) ).save( Mockito.any( Rooms.class ) );

        System.out.println(roomDTO);
        System.out.println(roomEntity);

    }

    @Test
    public void testGetRoomsByHotelId()
    {
        // arrange
        List<Rooms> roomEntityList = new ArrayList<>();
        Rooms roomEntity = new Rooms();
        roomEntity.setRoomId( 1 );
        roomEntity.setRoomType( "single" );
        roomEntity.setNoOfRooms( 10 );
        roomEntity.setMaxPeople( 1 );
        roomEntity.setCost( 100 );
        Hotel hotelEntity = new Hotel();
        hotelEntity.setHotelId( 1L );
        hotelEntity.setHotelName( "Test Hotel" );
        roomEntity.setHotel( hotelEntity );
        Contract contractEntity = new Contract();

    }
@Test
    public void testGetAllRooms() {
        // Create some test data
        List<Rooms> expectedRooms = new ArrayList<>();
        Rooms roomEntity = new Rooms();
        roomEntity.setRoomId( 1 );
        roomEntity.setRoomType( "single" );
        roomEntity.setNoOfRooms( 10 );
        roomEntity.setMaxPeople( 1 );
        roomEntity.setCost( 100 );
        Hotel hotelEntity = new Hotel();
        hotelEntity.setHotelId( 1L );
        hotelEntity.setHotelName( "Test Hotel" );
        roomEntity.setHotel( hotelEntity );
        Contract contractEntity = new Contract();

        // Mock the repository to return the test data
        when(roomRepository.findAll()).thenReturn(expectedRooms);

        // Call the method being tested
        List<RoomDTO> actualRooms = roomService.findAllRooms();

        // Assert that the method returns the expected data
        assertEquals(expectedRooms.size(), actualRooms.size());
        for (int i = 0; i < expectedRooms.size(); i++) {
            assertEquals(expectedRooms.get(i), actualRooms.get(i));
        }

        // Verify that the repository method was called once
        verify(roomRepository, times(1)).findAll();
    }
}