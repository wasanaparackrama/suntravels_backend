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

import it.codegen.training.SunTravels.dto.ContractDTO;
import it.codegen.training.SunTravels.dto.HotelDTO;
import it.codegen.training.SunTravels.dto.RoomDTO;
import it.codegen.training.SunTravels.exception.DbException;
import it.codegen.training.SunTravels.model.Hotel;

import it.codegen.training.SunTravels.repository.HotelRepository;
import it.codegen.training.SunTravels.repository.RoomRepository;
import it.codegen.training.SunTravels.service.HotelService;
import it.codegen.training.SunTravels.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 17 Apr 2023
 */

@RestController
@RequestMapping( "/room" )

public class RoomController
{

    @Autowired
    private RoomService roomService;
    private Logger logger = LoggerFactory.getLogger( RoomController.class );

    @PostMapping( "/add" )
    public ResponseEntity<RoomDTO> addRooms( @RequestBody RoomDTO roomDTO )
    {
        try
        {
            logger.info( "Adding room: {}", roomDTO );

            return new ResponseEntity<>( roomService.addRoom( roomDTO ), HttpStatus.CREATED );
        }
        catch( Exception e )
        {
            logger.error( "Failed to add room", e );
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
        }

    }

    @GetMapping( "/all" )
    public ResponseEntity<List<RoomDTO>> getRooms()
    {
        try
        {
            logger.info( "Fetching all rooms" );
            List<RoomDTO> rooms = roomService.findAllRooms();
            return new ResponseEntity<>( rooms, HttpStatus.OK );
        }
        catch( Exception e )
        {
            logger.error( "Failed to fetch rooms", e );
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
        }


    }

    @GetMapping( "/find/{hotelId}" )
    public ResponseEntity<List<RoomDTO>> getRoomsByHotelId( @PathVariable( "hotelId" ) Long hotelId )
    {

        try
        {
            logger.info( "Fetching rooms by hotelId: {}", hotelId );
            List<RoomDTO> rooms = roomService.getRoomsByHotelId( hotelId );
            return new ResponseEntity<>( rooms, HttpStatus.OK );
        }
        catch( Exception e )
        {
            logger.error( "Failed to fetch rooms by hotelId: {}", hotelId, e );
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
        }

    }

    @GetMapping( "/get/{roomId}" )
    public ResponseEntity<RoomDTO> getRoomsByRoomId( @PathVariable( "roomId" ) Long roomId )
    {
        try
        {
            logger.info( "Fetching contract by contractId: {}", roomId );
            RoomDTO room = roomService.findRoomById( roomId );
            return new ResponseEntity<>( room, HttpStatus.OK );
        }
        catch( Exception e )
        {
            logger.error( "Failed to fetch contract by contractId: {}", roomId, e );
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @PutMapping( "/update/{roomId}" )
    public ResponseEntity<RoomDTO> updateRoom( @PathVariable( "roomId" ) Long roomId, @RequestBody RoomDTO roomDTO )
    {
        try
        {
            logger.info( "Updating room: {}", roomId );
            RoomDTO updateRoom = roomService.updateRoom( roomId, roomDTO );
            return new ResponseEntity<>( updateRoom, HttpStatus.OK );
        }
        catch( Exception e )
        {
            logger.error( "Failed to update room: {}", roomId, e );
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
        }

    }

    @DeleteMapping( "/delete/{roomId}" )
    public ResponseEntity<?> deleteHotel( @PathVariable( "roomId" ) Long roomId ) throws DbException
    {
        try {
            roomService.deleteRoom( roomId );
            logger.info("Room with ID {} deleted successfully", roomId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DbException e) {
            logger.error("Error occurred while deleting room with ID {}", roomId, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error occurred while deleting room with ID {}", roomId, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}



//
//    @PostMapping( "/add" )
//    public ResponseEntity<RoomDTO> addRooms( @RequestBody RoomDTO roomDTO )
//    {
//
//        return new ResponseEntity<>( roomService.addRoom( roomDTO ), HttpStatus.CREATED );
//    }
//
//    @GetMapping( "/all" )
//    public ResponseEntity<List<RoomDTO>> getRooms()
//    {
//
//        return new ResponseEntity<>( roomService.findAllRooms(), HttpStatus.OK );
//
//    }
//
//    @GetMapping( "/find/{hotelId}" )
//    public ResponseEntity<List<RoomDTO>> getRoomsByHotelId( @PathVariable( "hotelId" ) Long hotelId )
//    {
//        return new ResponseEntity<>( roomService.getRoomsByHotelId( hotelId ), HttpStatus.OK );
//    }
//
//
//    @GetMapping( "/get/{roomId}" )
//    public ResponseEntity<RoomDTO> getRoomsByRoomId( @PathVariable( "roomId" ) Long roomId )
//    {
//        RoomDTO room = roomService.findRoomById( roomId );
//        return new ResponseEntity<>( room, HttpStatus.OK );
//
//    }
//
//    @PutMapping( "/update/{roomId}" )
//    public ResponseEntity<RoomDTO> updateRoom( @PathVariable( "roomId" ) Long roomId, @RequestBody RoomDTO roomDTO )
//    {
//        RoomDTO updateRoom = roomService.updateRoom( roomId, roomDTO );
//        return new ResponseEntity<>( updateRoom, HttpStatus.OK );
//
//    }
//
//    @DeleteMapping( "/delete/{roomId}" )
//    public ResponseEntity<?> deleteHotel( @PathVariable( "roomId" ) Long roomId ) throws DbException
//    {
//        roomService.deleteRoom( roomId );
//        return new ResponseEntity<>( HttpStatus.OK );
//
//    }