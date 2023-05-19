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
import it.codegen.training.SunTravels.exception.DbException;
import it.codegen.training.SunTravels.exception.UserNotFoundException;
import it.codegen.training.SunTravels.mapper.HotelMapper;
import it.codegen.training.SunTravels.model.Hotel;
import it.codegen.training.SunTravels.repository.HotelRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 18 Apr 2023
 */

@Service
@Transactional
public class HotelService
{
    private final HotelRepository hotelRepository;

    @Autowired
    private HotelMapper hotelMapper;

    private Logger logger = LoggerFactory.getLogger( HotelService.class );

    @Autowired
    public HotelService( HotelRepository hotelRepository )
    {
        this.hotelRepository = hotelRepository;
    }

    public HotelDTO addHotel( HotelDTO hotelDTO )
    {
        try
        {
            logger.info( "Adding hotel: {}", hotelDTO );

            if( Objects.isNull( hotelDTO.getHotelName() ) || Objects.isNull( hotelDTO.getCity() ) )
            {
                String errorMessage = "Invalid request: hotelName and city must not be null";
                logger.error( errorMessage );
                throw new IllegalArgumentException( errorMessage );
            }

            Hotel entity = hotelMapper.mapIn( hotelDTO );
            Hotel savedEntity = hotelRepository.save( entity );
            HotelDTO savedHotelDTO = hotelMapper.mapOut( savedEntity );

            logger.info( "Hotel added successfully: {}", savedHotelDTO );

            return savedHotelDTO;
        }
        catch( Exception e )
        {
            // Logging statement
            logger.error( "Failed to add hotel: {}", hotelDTO, e );
            throw e;
        }
    }

    public Page<HotelDTO> findAllHotel(Pageable pageable) {
        try {
            logger.info("Fetching all hotels");

            Page<Hotel> hotelPage = hotelRepository.findAllHotels(pageable);
            logger.debug("Retrieved {} hotels", hotelPage.getNumberOfElements());

            List<HotelDTO> hotelDTOList = hotelPage.getContent()
                    .stream()
                    .map(hotelMapper::mapOut)
                    .collect(Collectors.toList());

            return new PageImpl<>(hotelDTOList, pageable, hotelPage.getTotalElements());
        } catch (Exception e) {
            logger.error("Failed to retrieve all hotels", e);
            throw e;
        }
    }



    public List<HotelDTO> findAllHotels()
    {
        try
        {
            logger.info( "Fetching all hotels" );

            List<Hotel> hotelList = hotelRepository.findAll();
            logger.debug( "Retrieved {} hotels", hotelList.size() );

            List<HotelDTO> hotelDTOList = new ArrayList<>();
            for( Hotel hotel : hotelList )
            {
                HotelDTO hotelDTO = hotelMapper.mapOut( hotel );
                hotelDTOList.add( hotelDTO );
            }

            logger.debug( "Mapped {} hotels to DTOs", hotelDTOList.size() );

            return hotelDTOList;
        }
        catch( Exception e )
        {
            logger.error( "Failed to retrieve all hotels", e );
            throw e;
        }
    }

    public HotelDTO updateHotel( Long id, HotelDTO hotelDTO )
    {
        try
        {
            logger.info( "Updating hotel with ID {}: {}", id, hotelDTO );

            if( Objects.isNull( hotelDTO.getHotelName() ) || Objects.isNull( hotelDTO.getCity() ) )
            {
                String errorMessage = "Invalid request: hotelName and city must not be null";
                logger.error( errorMessage );
                throw new IllegalArgumentException( errorMessage );
            }

            Hotel existingHotel = hotelRepository.findById( id )
                                                 .orElseThrow( () ->
                                                 {
                                                     String errorMessage = "Hotel not found for the given ID: " + id;
                                                     logger.error( errorMessage );
                                                     return new IllegalArgumentException( errorMessage );
                                                 } );

            existingHotel.setHotelName( hotelDTO.getHotelName() );
            existingHotel.setCity( hotelDTO.getCity() );
            existingHotel.setPhoneNumber( hotelDTO.getPhoneNumber() );

            Hotel savedEntity = hotelRepository.save( existingHotel );
            HotelDTO updatedHotelDTO = hotelMapper.mapOut( savedEntity );

            logger.info( "Hotel updated successfully: {}", updatedHotelDTO );

            return updatedHotelDTO;
        }
        catch( Exception e )
        {
            // Logging statement
            logger.error( "Failed to update contract with id {}: {}", id, hotelDTO, e );
            throw e;
        }
    }

    public HotelDTO findHotelById( Long hotelId )
    {
        try
        {
            logger.info( "Fetching hotel by ID: {}", hotelId );

            Hotel hotel = hotelRepository.findById( hotelId )
                                         .orElseThrow( () ->
                                         {
                                             String errorMessage = "Hotel not found for the given ID: " + hotelId;
                                             logger.error( errorMessage );
                                             return new IllegalArgumentException( errorMessage );
                                         } );

            HotelDTO hotelDTO = hotelMapper.mapOut( hotel );

            logger.info( "Hotel found: {}", hotelDTO );

            return hotelDTO;
        }
        catch( Exception e )
        {
            logger.error( "Failed to retrieve contract with id: {}", hotelId, e );
            throw e;
        }
    }


    public DeleteResponse deleteHotel( Long id ) throws DbException
    {

        logger.info( "Deleting hotel with ID: {}", id );

        // Check for an invalid id
        if( id == null || id == 0 )
        {
            String errorMessage = "Invalid ID: You must provide a valid ID";
            logger.error( errorMessage );
            throw new IllegalArgumentException( errorMessage );
        }

        // Check for the existence of the id in the DB
        if( !hotelRepository.existsById( id ) )
        {
            String errorMessage = "Hotel not found for the given ID: " + id;
            logger.error( errorMessage );
            throw new IllegalArgumentException( errorMessage );
        }

        try
        {
            hotelRepository.deleteById( id );
            logger.info( "Hotel deleted successfully with ID: {}", id );
        }
        catch( Exception e )
        {
            String errorMessage = "Failed to delete the hotel with ID: " + id;
            logger.error( errorMessage, e );
            throw new DbException( errorMessage, e );
        }

        DeleteResponse response = new DeleteResponse();
        response.setEntity( "Hotel" );
        response.setId( id );
        response.setMessage( "Hotel " + id + " removed successfully!!" );
        return response;
    }


}



















//
//    public HotelDTO addHotel( HotelDTO hotelDTO )
//    {
//
//        if( Objects.isNull( hotelDTO.getHotelName() ) || Objects.isNull( hotelDTO.getCity() ) )
//        {
//            throw new IllegalArgumentException( "Invalid request" );
//        }
//
//        Hotel entity = hotelMapper.mapIn( hotelDTO );
//        Hotel savedEntity = hotelRepository.save( entity );
//        return hotelMapper.mapOut( savedEntity );
//
//    }
//
//    public List<HotelDTO> findAllHotel()
//    {
//        List<Hotel> hotelList = hotelRepository.findAll();
//        List<HotelDTO> hotelDTOList = new ArrayList<>();
//        for( Hotel hotel : hotelList )
//        {
//            hotelDTOList.add( hotelMapper.mapOut( hotel ) );
//        }
//        return hotelDTOList;
//    }
//
//    public HotelDTO updateHotel( Long id, HotelDTO hotelDTO )
//    {
//
//        // Throw an exception if HotelName and HotelCity are not in the DTO.
//        if( Objects.isNull( hotelDTO.getHotelName() ) || Objects.isNull( hotelDTO.getCity() ) )
//        {
//            throw new IllegalArgumentException( "Invalid request" );
//        }
//
//        Hotel exsitingHotel = hotelRepository.findById( id ).orElseThrow( () -> new Error( "Hotel not found for the given id " + id ) );
//        exsitingHotel.setHotelName( hotelDTO.getHotelName() );
//        exsitingHotel.setCity( hotelDTO.getCity() );
//        exsitingHotel.setPhoneNumber( hotelDTO.getPhoneNumber() );
//        Hotel savedEntity = hotelRepository.save( exsitingHotel );
//        return hotelMapper.mapOut( savedEntity );
//    }
//
//    public HotelDTO findHotelById( Long hotelId )
//    {
//
//        Hotel hotel = hotelRepository.findById( hotelId )
//                                     .orElseThrow( () -> new Error( "Hotel not found for the given id " + hotelId ) );
//        return hotelMapper.mapOut( hotel );
////        return hotelRepository.findHotelById( hotelId ).orElseThrow( () -> new UserNotFoundException( "User by hotelId" + hotelId + "was not found" ) );
//    }
//
//
//    public DeleteResponse deleteHotel( Long id ) throws DbException
//    {
//        // Check for an invalid id
//        if( id == null || id == 0 )
//        {
//            throw new IllegalArgumentException( "You must provide valid id" );
//        }
//
//        // Check for the existence of the id in the DB
//        if( !hotelRepository.existsById( id ) )
//        {
//            throw new Error( "Hotel not found for the given id => " + id );
//        }
//
//        try
//        {
//            hotelRepository.deleteById( id );
//        }
//        catch( Exception e )
//        {
//            throw new DbException( "Can't delete the Hotel with Id: " + id, e );
//        }
//
//        DeleteResponse response = new DeleteResponse();
//        response.setEntity( "Hotel" );
//        response.setId( id );
//        response.setMessage( "Hotel " + id + " removed successfully!!" );
//        return response;
//    }
