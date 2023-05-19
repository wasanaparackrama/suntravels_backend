/*
 * Copyright (c) 2023. CodeGen International (Pvt) Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of CodeGen
 * International (Pvt) Ltd. ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with CodeGen International.
 *
 */
package it.codegen.training.SunTravels.repository;


import it.codegen.training.SunTravels.model.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 20 Apr 2023
 */
@Repository
public interface RoomRepository extends JpaRepository<Rooms,Long>
{

    public List<Rooms> findByHotelHotelId( Long hotelId);


    @Query( value = "SELECT  h.hotel_name,h.city ,rt.room_type,rt.max_people, rt.no_of_rooms, rt.cost,c.mark_up, c.start_date, c.end_date " +

                            "FROM jpa_contract c " +
                            "JOIN jpa_room rt ON c.contract_id= rt.contract_id " +
                            "JOIN jpa_hotel h ON h.hotel_id = c.hotel_id " +
                            "WHERE c.start_date <= :checkInDate AND c.end_date >=:checkOutDate AND rt.max_people >=:maxOfAdultsInRequest AND rt.no_of_rooms >=:totalRoomsRequested",
            nativeQuery = true
    )
    public List<Object[]> getAvailableRoomsByRequestCriteria(
            @Param( "checkInDate" ) Date checkInDate,
            @Param( "checkOutDate" ) Date checkOutDate,
            @Param( "maxOfAdultsInRequest" ) int maxOfAdultsInRequest,
            @Param( "totalRoomsRequested" ) int totalRoomsRequested);
}

//    SELECT  h.hotel_name,h.city ,rt.room_type,rt.max_people, rt.no_of_rooms, rt.cost,c.mark_up, c.start_date, c.end_date
//                     FROM jpa_contract c
//                     JOIN jpa_hotel h ON h.hotel_id = c.hotel_id
//                     JOIN jpa_room rt ON rt.room_id = c.room_id


//"SELECT  h.hotel_name,h.city ,rt.room_type,rt.max_people, rt.no_of_rooms, rt.cost,c.mark_up, c.start_date, c.end_date " +
//
//        " FROM jpa_room rt " +
//        "JOIN jpa_contract c ON c.hotel_id= rt.hotel_id " +
//        "JOIN jpa_hotel h ON h.hotel_id = rt.hotel_id " +