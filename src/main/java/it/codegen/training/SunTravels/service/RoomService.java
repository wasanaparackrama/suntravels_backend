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

import it.codegen.training.SunTravels.controller.ContractController;
import it.codegen.training.SunTravels.dto.DeleteResponse;
import it.codegen.training.SunTravels.dto.HotelDTO;
import it.codegen.training.SunTravels.dto.RoomDTO;
import it.codegen.training.SunTravels.exception.DbException;
import it.codegen.training.SunTravels.mapper.RoomMapper;
import it.codegen.training.SunTravels.model.Contract;
import it.codegen.training.SunTravels.model.Hotel;
import it.codegen.training.SunTravels.model.Rooms;
import it.codegen.training.SunTravels.repository.ContractRepository;
import it.codegen.training.SunTravels.repository.HotelRepository;
import it.codegen.training.SunTravels.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 20 Apr 2023
 */


@Service
@Transactional
public class RoomService
{
    @Autowired
    private  RoomRepository roomRepository;
    @Autowired
    private  HotelRepository hotelRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private RoomMapper roomMapper;

    private Logger logger = LoggerFactory.getLogger( RoomService.class );
    public RoomDTO addRoom(RoomDTO roomDTO) {
        try {
            logger.info("Adding room: {}", roomDTO);

            Rooms roomTypeEntity = roomMapper.mapIn(roomDTO);
            Hotel hotel = hotelRepository.findById(roomDTO.getHotelId()).orElseThrow(() -> new IllegalArgumentException("Hotel not found for the given hotelId " + roomDTO.getHotelId()));
            Contract contract = contractRepository.findById(roomDTO.getContractId()).orElseThrow(() -> new IllegalArgumentException("Contract not found for the given contractId " + roomDTO.getContractId()));

            roomTypeEntity.setHotel(hotel);
            roomTypeEntity.setContract(contract);
            Rooms savedEntity = roomRepository.save(roomTypeEntity);

//            logger.info("Room added successfully: {}", savedEntity);

            return roomMapper.mapOut(savedEntity);
        } catch (Exception e) {
            logger.error("Failed to add room: {}", roomDTO, e);
            throw e;
        }
    }

    public List<RoomDTO> findAllRooms() {
        try {
            logger.info("Fetching all rooms");

            List<Rooms> roomList = roomRepository.findAll();
            List<RoomDTO> roomDTOList = new ArrayList<>();
            for (Rooms room : roomList) {
                roomDTOList.add(roomMapper.mapOut(room));
            }

            logger.info("Retrieved {} rooms", roomDTOList.size());

            return roomDTOList;
        } catch (Exception e) {
            logger.error("Failed to retrieve all rooms", e);
            throw e;
        }
    }

    public List<RoomDTO> getRoomsByHotelId(Long hotelId) {
        try {
            logger.info("Fetching rooms for hotelId: {}", hotelId);

            List<Rooms> roomTypeListByHotelId = roomRepository.findByHotelHotelId(hotelId);
            List<RoomDTO> roomTypeDTOList = new ArrayList<>();
            for (Rooms roomType : roomTypeListByHotelId) {
                roomTypeDTOList.add(roomMapper.mapOut(roomType));
            }

            logger.info("Retrieved {} rooms for hotelId: {}", roomTypeDTOList.size(), hotelId);

            return roomTypeDTOList;
        } catch (Exception e) {
            logger.error("Failed to retrieve rooms for hotelId: {}", hotelId, e);
            throw e;
        }
    }
        public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        try {
            logger.info("Updating room with id {}: {}", id, roomDTO);

            Rooms existingRooms = roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found for the given id " + id));
            existingRooms.setRoomType(roomDTO.getRoomType());
            existingRooms.setNoOfRooms(roomDTO.getNoOfRooms());
            existingRooms.setMaxPeople(roomDTO.getMaxPeople());
            existingRooms.setCost(roomDTO.getCost());
            Rooms savedEntity = roomRepository.save(existingRooms);

//            logger.info("Room updated successfully: {}", savedEntity);

            return roomMapper.mapOut(savedEntity);
        } catch (Exception e) {
            logger.error("Failed to update room with id {}: {}", id, roomDTO, e);
            throw e;
        }
    }

    public RoomDTO findRoomById(Long roomId) {
        try {
            logger.info("Fetching room with id: {}", roomId);

            Rooms rooms = roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found for the given id " + roomId));

            logger.info("Retrieved room with id {}: {}", roomId, rooms);

            return roomMapper.mapOut(rooms);
        } catch (Exception e) {
            logger.error("Failed to retrieve room with id: {}", roomId, e);
            throw e;
        }
    }

    public DeleteResponse deleteRoom(Long id) throws DbException {
        try {
            logger.info("Deleting room with id: {}", id);

            if (id == null || id == 0) {
                throw new IllegalArgumentException("You must provide a valid id");
            }

            if (!roomRepository.existsById(id)) {
                throw new IllegalArgumentException("Room not found for the given id => " + id);
            }

            roomRepository.deleteById(id);

            logger.info("Room deleted successfully with id: {}", id);

            DeleteResponse response = new DeleteResponse();
            response.setEntity("Room");
            response.setId(id);
            response.setMessage("Room " + id + " removed successfully!!");
            return response;
        } catch (Exception e) {
            logger.error("Failed to delete room with id: {}", id, e);
            throw new DbException("Can't delete the Room with Id: " + id, e);
        }
    }



}


//    public  RoomDTO updateRoom(Long id,  RoomDTO  roomDTO )
//        {
//
//        Rooms exsitingRooms = roomRepository.findById( id ).orElseThrow(() -> new Error("Hotel not found for the given id " + id));
//        exsitingRooms.setRoomType( roomDTO.getRoomType() );
//        exsitingRooms.setNoOfRooms( roomDTO.getNoOfRooms() );
//        exsitingRooms.setMaxPeople( roomDTO.getMaxPeople() );
//        exsitingRooms.setCost( roomDTO.getCost() );
//        Rooms savedEntity = roomRepository.save( exsitingRooms );
//        return roomMapper.mapOut( savedEntity );
//    }

//    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
//        try {
//            logger.info("Updating room with id {}: {}", id, roomDTO);
//
//            Rooms existingRooms = roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found for the given id " + id));
//            existingRooms.setRoomType(roomDTO.getRoomType());
//            existingRooms.setNoOfRooms(roomDTO.getNoOfRooms());
//            existingRooms.setMaxPeople(roomDTO.getMaxPeople());
//            existingRooms.setCost(roomDTO.getCost());
//            Rooms savedEntity = roomRepository.save(existingRooms);
//
//            logger.info("Room updated successfully: {}", savedEntity);
//
//            return roomMapper.mapOut(savedEntity);
//        } catch (Exception e) {
//            logger.error("Failed to update room with id {}: {}", id, roomDTO, e);
//            throw e;
//        }
//    }



//
//    public RoomDTO addRoom( RoomDTO roomDTO){
//
//        Rooms roomTypeEntity = roomMapper.mapIn( roomDTO );
//        Hotel hotel = hotelRepository.findById(roomDTO.getHotelId() ).get();
//        Contract contract=contractRepository.findById( roomDTO.getContractId() ).get();
//        roomTypeEntity.setHotel( hotel );
//        roomTypeEntity.setContract( contract );
//        Rooms savedEntity = roomRepository.save( roomTypeEntity );
//        return roomMapper.mapOut( savedEntity );
//    }
//
//
//    public List<RoomDTO> findAllRooms(){
//        List<Rooms> roomList = roomRepository.findAll();
//        List<RoomDTO> roomDTOList = new ArrayList<>();
//        for( Rooms room : roomList )
//        {
//            roomDTOList.add( roomMapper.mapOut( room ) );
//        }
//        return roomDTOList;
//    }
//
//    public List<RoomDTO> getRoomsByHotelId( Long hotelId )
//    {
//        List<Rooms> roomTypeListByHotelId = roomRepository.findByHotelHotelId(hotelId);
//        List<RoomDTO> roomTypeDTOList = new ArrayList<>();
//        for( Rooms roomType : roomTypeListByHotelId )
//        {
//            roomTypeDTOList.add( roomMapper.mapOut( roomType ) );
//        }
//        return roomTypeDTOList;
////        return roomTypeMapper.mapOut( roomType );
//    }
//
//    public  RoomDTO updateRoom(Long id,  RoomDTO  roomDTO )
//    {
//
//        Rooms exsitingRooms = roomRepository.findById( id ).orElseThrow(() -> new Error("Hotel not found for the given id " + id));
//        exsitingRooms.setRoomType( roomDTO.getRoomType() );
//        exsitingRooms.setNoOfRooms( roomDTO.getNoOfRooms() );
//        exsitingRooms.setMaxPeople( roomDTO.getMaxPeople() );
//        exsitingRooms.setCost( roomDTO.getCost() );
//        Rooms savedEntity = roomRepository.save( exsitingRooms );
//        return roomMapper.mapOut( savedEntity );
//    }
//    public RoomDTO findRoomById( Long roomId )
//    {
//        Rooms rooms = roomRepository.findById( roomId )
//                                    .orElseThrow(() -> new Error("Hotel not found for the given id " + roomId));
//        return roomMapper.mapOut( rooms );
//    }
//
//    public DeleteResponse deleteRoom( Long id ) throws DbException
//    {
//
//        // Check for an invalid id
//        if (id == null || id == 0) {
//            throw new IllegalArgumentException("You must provide valid id");
//        }
//
//        // Check for the existence of the id in the DB
//        if (!roomRepository.existsById( id )){
//            throw new Error("RoomType not found for the given id => " + id);
//        }
//
//        try{
//            roomRepository.deleteById( id );
//        }
//        catch( Exception e ){
//            throw new DbException("Can't delete the RoomType with Id: "+ id , e);
//        }
//
//        DeleteResponse response = new DeleteResponse();
//        response.setEntity( "RoomType" );
//        response.setId( id );
//        response.setMessage( "RoomType "+id+" removed successfully!!" );
//        return response;
//
////        roomRepository.deleteById( id );
//    }



