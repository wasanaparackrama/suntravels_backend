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

import it.codegen.training.SunTravels.dto.AvailableRoom;
import it.codegen.training.SunTravels.dto.RoomRequestCriteria;
import it.codegen.training.SunTravels.dto.SearchRoomRequest;
import it.codegen.training.SunTravels.dto.SearchRoomResponse;
import it.codegen.training.SunTravels.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 26 Apr 2023
 */
@Service
@Transactional
public class SearchRoomService
{
    @Autowired
    private RoomRepository roomRepository;

    public SearchRoomResponse searchAvailableRooms( SearchRoomRequest searchRoomRequest )
    {

        Date checkInDate = searchRoomRequest.getCheckInDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime( checkInDate );

        calendar.add( Calendar.DAY_OF_MONTH, searchRoomRequest.getNoOfNights() );

        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        String checkOutDate = dateFormat.format( new Date( calendar.getTimeInMillis() ) );

        int maxOfAdultsInRequest = 0, totalRoomsRequested = 0;

//finds the maximum number of adults specified in RoomRequest criteria, and calculates the total number of rooms requested by summing up the number of rooms specified in each criteria.

        for( RoomRequestCriteria criteria: searchRoomRequest.getRoomRequests()){
            if (maxOfAdultsInRequest < criteria.getNoOfAdults()){
                maxOfAdultsInRequest = criteria.getNoOfAdults();
            }
            totalRoomsRequested += criteria.getNoOfRooms();
        }

        List<Object[]> searchQueryResponse = roomRepository.getAvailableRoomsByRequestCriteria( checkInDate, Date.valueOf( checkOutDate ), maxOfAdultsInRequest, totalRoomsRequested);

        SearchRoomResponse response = new SearchRoomResponse();
        response.setAvailable( ( searchQueryResponse.size() >= 1 ) );

        List<AvailableRoom> availableRooms = new ArrayList<>();


        for( Object[] array : searchQueryResponse )
        {


            AvailableRoom availableRoom = new AvailableRoom();

            availableRoom.setHotelName( array[0].toString() + " " + array[1].toString() );
            availableRoom.setRoomType( array[2].toString() );
            availableRoom.setMaxPeople( Integer.parseInt( array[3].toString() ) );
            availableRoom.setNoOfRooms( Integer.parseInt( array[4].toString() ) );

            float pricePerPerson = Float.parseFloat( array[5].toString() );
            float markup = Float.parseFloat( array[6].toString() ) + 100 ;
            int noOfNights = searchRoomRequest.getNoOfNights();

            Float markedUpPrice =  (pricePerPerson * markup * noOfNights )/ 100;

            availableRoom.setMarkUpPrice(markedUpPrice);
            availableRooms.add( availableRoom );
        }

        response.setAvailableRoomList( availableRooms );

        return response;
    }


}

//
//    int minAdults = searchRoomRequest.getRoomRequests()
//                                     .stream()
//                                     .mapToInt( RoomRequestCriteria::getNoOfAdults )
//                                     .min()
//                                     .orElse( 0 );
//
//    int maxAdults = searchRoomRequest.getRoomRequests()
//                                     .stream()
//                                     .mapToInt( RoomRequestCriteria::getNoOfAdults )
//                                     .max()
//                                     .orElse( 0 );
////        System.out.println(minAdults);
//
//
//    int maxOfAdultsInRequest = 0, totalRoomsRequested = 0;
//
//        for( RoomRequestCriteria criteria : searchRoomRequest.getRoomRequests() )
//                {
////            System.out.println(criteria.getNoOfAdults());
////            if (maxOfAdultsInRequest <= criteria.getNoOfAdults()){
////                maxOfAdultsInRequest = criteria.getNoOfAdults();
////            }
//                totalRoomsRequested += criteria.getNoOfRooms();
//                }