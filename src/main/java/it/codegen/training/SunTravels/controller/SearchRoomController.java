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

import it.codegen.training.SunTravels.dto.SearchRoomRequest;
import it.codegen.training.SunTravels.dto.SearchRoomResponse;
import it.codegen.training.SunTravels.service.SearchRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 26 Apr 2023
 */

@RestController
@RequestMapping( "/searchRoom" )

public class SearchRoomController
{
    @Autowired
    private SearchRoomService searchRoomService;

    @PostMapping
    public ResponseEntity<SearchRoomResponse> getRoomsAvailability( @RequestBody SearchRoomRequest searchRoomRequest)
    {

        return new ResponseEntity<>( searchRoomService.searchAvailableRooms( searchRoomRequest ), HttpStatus.OK );
    }

}
