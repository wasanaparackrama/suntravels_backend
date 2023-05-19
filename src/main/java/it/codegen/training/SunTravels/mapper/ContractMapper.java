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

import it.codegen.training.SunTravels.dto.ContractDTO;
import it.codegen.training.SunTravels.dto.RoomDTO;
import it.codegen.training.SunTravels.model.Contract;
import it.codegen.training.SunTravels.model.Rooms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 24 Apr 2023
 */
@Component
public class ContractMapper

{
    @Autowired
    private RoomMapper roomMapper;
    public Contract mapIn( ContractDTO dto )
    {
        Contract entity = new  Contract();
        entity.setContractId( dto.getContractId() );
        entity.setStartDate( dto.getStartDate() );
        entity.setEndDate( dto.getEndDate() );
        entity.setMarkUp( dto.getMarkUp() );

        if (dto.getAssignedRoomData() != null){
            entity.setRooms(roomMapper.mapInList(dto.getAssignedRoomData()) );
        }

        return entity;
    }

    public ContractDTO mapOut( Contract entity )
    {
        ContractDTO dto = new ContractDTO();
        dto.setContractId( entity.getContractId() );
        dto.setStartDate( entity.getStartDate() );
        dto.setEndDate(entity.getEndDate());
        dto.setMarkUp( entity.getMarkUp() );

        dto.setHotelId( entity.getHotel().getHotelId() );
        dto.setHotelName( entity.getHotel().getHotelName() );
        if (entity.getRooms()!=null){
           dto.setAssignedRoomData( roomMapper.mapOutList( entity.getRooms()));
        }
        return dto;
    }
}
