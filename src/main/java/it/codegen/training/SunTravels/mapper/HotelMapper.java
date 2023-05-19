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

import it.codegen.training.SunTravels.dto.HotelDTO;
import it.codegen.training.SunTravels.model.Hotel;
import org.springframework.stereotype.Component;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 24 Apr 2023
 */
@Component
public class HotelMapper
{
    public Hotel mapIn( HotelDTO dto){
        Hotel model = new Hotel();
        model.setHotelName( dto.getHotelName() );
        model.setCity( dto.getCity() );
        model.setPhoneNumber( dto.getPhoneNumber() );
        return model;
    }
    public HotelDTO mapOut( Hotel model){
        HotelDTO dto = new HotelDTO();
        dto.setHotelId( model.getHotelId() );
        dto.setHotelName( model.getHotelName() );
        dto.setCity( model.getCity());
        dto.setPhoneNumber( model.getPhoneNumber() );
        return dto;
    }


}
