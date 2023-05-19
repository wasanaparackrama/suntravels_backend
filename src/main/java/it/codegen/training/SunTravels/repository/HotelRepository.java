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


import it.codegen.training.SunTravels.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 12 Apr 2023
 */

//
//
@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long>
{
//    void deleteHotelByHotelId( Long hotelId );
        @Query("SELECT h FROM Hotel h")
        Page<Hotel> findAllHotels( Pageable pageable);
}




