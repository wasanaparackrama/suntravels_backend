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


import it.codegen.training.SunTravels.exception.DbException;
import it.codegen.training.SunTravels.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/hotel")

public class HotelController {
    @Autowired
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    private Logger logger = LoggerFactory.getLogger(HotelController.class);


    @GetMapping("/all")
    public ResponseEntity<List<HotelDTO>> getHotels() {
        try {
            logger.info("Fetching all hotels");
            List<HotelDTO> hotels = hotelService.findAllHotels();
            return new ResponseEntity<>(hotels, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to fetch hotels", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @GetMapping("/all/pages")
    public ResponseEntity<Page<HotelDTO>> getHotel(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        try {
            logger.info("Fetching all hotels");
            Pageable pageable = PageRequest.of(page, size);
            Page<HotelDTO> hotels = hotelService.findAllHotel(pageable);
            return new ResponseEntity<>(hotels, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to fetch hotels", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/find/{hotelId}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable("hotelId") Long hotelId) {

        try {
            logger.info("Fetching hotel by hotelId: {}", hotelId);
            HotelDTO hotel = hotelService.findHotelById(hotelId);
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to fetch hotel by hotelId: {}", hotelId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/add")
    public ResponseEntity<HotelDTO> addHotels(@RequestBody HotelDTO hotelRequest) {

        try {
            logger.info("Adding hotel: {}", hotelRequest);

            return new ResponseEntity<>(hotelService.addHotel(hotelRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to add hotel", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        HotelDTO newHotel = hotelService.addHotel( hotelRequest );
//        return new ResponseEntity<>( newHotel, HttpStatus.CREATED );

    }

    @PutMapping("/update/{hotelId}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable("hotelId") Long hotelId, @RequestBody HotelDTO hotelDTO) {

        try {
            logger.info("Updating hotel: {}", hotelId);
            HotelDTO updateHotel = hotelService.updateHotel(hotelId, hotelDTO);
            return new ResponseEntity<>(updateHotel, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to update hotel: {}", hotelId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        HotelDTO updateHotel = hotelService.updateHotel( hotelId, hotelDTO );
//        return new ResponseEntity<>( updateHotel, HttpStatus.OK );

    }

    @DeleteMapping("/delete/{hotelId}")
    public ResponseEntity<?> deleteHotel(@PathVariable("hotelId") Long hotelId) throws DbException {
        try {
            hotelService.deleteHotel(hotelId);
            logger.info("Hotel with ID {} deleted successfully", hotelId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DbException e) {
            logger.error("Error occurred while deleting hotel with ID {}", hotelId, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error occurred while deleting hotel with ID {}", hotelId, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}


//    @GetMapping( "/find/{hotelId}" )
//    public ResponseEntity<HotelDTO> getHotelById( @PathVariable( "hotelId" ) Long hotelId )
//    {
//        HotelDTO hotel = hotelService.findHotelById( hotelId );
//        return new ResponseEntity<>( hotel, HttpStatus.OK );
//
//    }

//    @GetMapping( "/all" )
//    public ResponseEntity<List<HotelDTO>> getHotels()
//    {
//
//        return new ResponseEntity<>( hotelService.findAllHotel(), HttpStatus.OK );
//
//    }

//    @PostMapping( "/add" )
//    public ResponseEntity<HotelDTO> addHotels( @RequestBody HotelDTO hotelRequest )
//    {
//        HotelDTO newHotel = hotelService.addHotel( hotelRequest );
//        return new ResponseEntity<>( newHotel, HttpStatus.CREATED );
//
//    }

//    @PutMapping( "/update/{hotelId}" )
//    public ResponseEntity<HotelDTO> updateHotel( @PathVariable( "hotelId" ) Long hotelId, @RequestBody HotelDTO hotelDTO )
//    {
//        HotelDTO updateHotel = hotelService.updateHotel( hotelId, hotelDTO );
//        return new ResponseEntity<>( updateHotel, HttpStatus.OK );
//
//    }
//
//    @DeleteMapping( "/delete/{hotelId}" )
//    public ResponseEntity<?> deleteHotel( @PathVariable( "hotelId" ) Long hotelId ) throws DbException
//    {
//        hotelService.deleteHotel( hotelId );
//        return new ResponseEntity<>( HttpStatus.OK );
//
//    }