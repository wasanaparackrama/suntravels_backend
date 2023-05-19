/*
 * Copyright (c) 2023. CodeGen International (Pvt) Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of CodeGen
 * International (Pvt) Ltd. ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with CodeGen International.
 *
 */
package it.codegen.training.SunTravels.mapper;

import it.codegen.training.SunTravels.dto.RoomDTO;
import it.codegen.training.SunTravels.model.Rooms;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 24 Apr 2023
 */

@Component
public class RoomMapper
{
    public Rooms mapIn( RoomDTO dto )
    {
        Rooms entity = new Rooms();
        entity.setRoomId( dto.getRoomId() );
        entity.setRoomType( dto.getRoomType() );
        entity.setNoOfRooms( dto.getNoOfRooms() );
        entity.setMaxPeople( dto.getMaxPeople() );
        entity.setCost( dto.getCost() );
        return entity;
    }

    public List<Rooms> mapInList( List<RoomDTO> dtoList){
        List<Rooms> roomDTOList = new ArrayList<>();

        for(RoomDTO dto: dtoList ){
            roomDTOList.add( mapIn( dto ) );
        }

        return roomDTOList;
    }

    public RoomDTO mapOut( Rooms entity )
    {
        RoomDTO dto = new RoomDTO();
        dto.setRoomId( entity.getRoomId() );
        dto.setRoomType( entity.getRoomType() );
        dto.setNoOfRooms(entity.getNoOfRooms());
        dto.setMaxPeople( entity.getMaxPeople() );
        dto.setCost( entity.getCost() );
        dto.setHotelId( entity.getHotel().getHotelId() );
        dto.setHotelName( entity.getHotel().getHotelName() );
        dto.setContractId( entity.getContract().getContractId() );
        return dto;
    }

    public List<RoomDTO> mapOutList(List<Rooms> entityList){
        List<RoomDTO> roomDTOList = new ArrayList<>();

        for(Rooms entity: entityList ){
            roomDTOList.add( mapOut( entity ) );
        }

        return roomDTOList;
    }

}
