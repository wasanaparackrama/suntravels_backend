package it.codegen.training.SunTravels.controller;///*
// * Copyright (c) 2023. CodeGen International (Pvt) Ltd. All Rights Reserved.
// *
// * This software is the confidential and proprietary information of CodeGen
// * International (Pvt) Ltd. ("Confidential Information"). You shall not disclose
// * such Confidential Information and shall use it only in accordance with the
// * terms of the license agreement you entered into with CodeGen International.
// *
// */

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 09 May 2023
 */


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.codegen.training.SunTravels.controller.RoomController;
import it.codegen.training.SunTravels.exception.DbException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.codegen.training.SunTravels.dto.RoomDTO;
import it.codegen.training.SunTravels.service.RoomService;

@ExtendWith(MockitoExtension.class)
public class RoomControllerTest {

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    @Test
    public void testGetRooms() {
        // Arrange
        List<RoomDTO> rooms = new ArrayList<>();
        RoomDTO room1 = new RoomDTO();
        room1.setRoomType("Double");
        room1.setNoOfRooms(5);
        room1.setMaxPeople(2);
        room1.setCost(100);
        RoomDTO room2 = new RoomDTO();
        room2.setRoomType("Single");
        room2.setNoOfRooms(3);
        room2.setMaxPeople(1);
        room2.setCost(50);
        rooms.add(room1);
        rooms.add(room2);
        when(roomService.findAllRooms()).thenReturn(rooms);

        // Act
        ResponseEntity<List<RoomDTO>> responseEntity = roomController.getRooms();

        // Assert
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody().size() == 2);
        assert(responseEntity.getBody().get(0).getRoomType().equals("Double"));
        assert(responseEntity.getBody().get(0).getNoOfRooms() == 5);
        assert(responseEntity.getBody().get(0).getMaxPeople() == 2);
        assert(responseEntity.getBody().get(0).getCost() == 100);
        assert(responseEntity.getBody().get(1).getRoomType().equals("Single"));
        assert(responseEntity.getBody().get(1).getNoOfRooms() == 3);
        assert(responseEntity.getBody().get(1).getMaxPeople() == 1);
        assert(responseEntity.getBody().get(1).getCost() == 50);
    }



    @Test
    public void testAddRoom() {
        // Arrange
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomType("Double");
        roomDTO.setNoOfRooms(5);
        roomDTO.setMaxPeople(2);
        roomDTO.setCost(100);
        when(roomService.addRoom(roomDTO)).thenReturn(roomDTO);

        // Act
        ResponseEntity<RoomDTO> responseEntity = roomController.addRooms(roomDTO);

        // Assert
        assert(responseEntity.getStatusCode() == HttpStatus.CREATED);
        assert(responseEntity.getBody().getRoomType().equals("Double"));
        assert(responseEntity.getBody().getNoOfRooms() == 5);
        assert(responseEntity.getBody().getMaxPeople() == 2);
        assert(responseEntity.getBody().getCost() == 100);
    }


    @Test
    public void testUpdateRoom() {
        // Arrange
        Long roomId = 1L;
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomType("Double");
        roomDTO.setNoOfRooms(5);
        roomDTO.setMaxPeople(2);
        roomDTO.setCost(100);
        when(roomService.updateRoom(roomId, roomDTO)).thenReturn(roomDTO);

        // Act
        ResponseEntity<RoomDTO> responseEntity = roomController.updateRoom(roomId, roomDTO);

        // Assert
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody().getRoomType().equals("Double"));
        assert(responseEntity.getBody().getNoOfRooms() == 5);
        assert(responseEntity.getBody().getMaxPeople() == 2);
        assert(responseEntity.getBody().getCost() == 100);
    }

    @Test
    public void testDeleteRoom() throws DbException {
        // Arrange
        Long roomId = 1L;
        doNothing().when(roomService).deleteRoom(roomId);

        // Act
        ResponseEntity<?> responseEntity = roomController.deleteHotel(roomId);

        // Assert
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == null);
        System.out.println("Room with ID " + roomId + " was successfully deleted.");
    }


}
