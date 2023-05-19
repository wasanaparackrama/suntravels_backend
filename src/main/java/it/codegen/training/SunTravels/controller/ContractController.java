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
import it.codegen.training.SunTravels.dto.RoomDTO;
import it.codegen.training.SunTravels.exception.DbException;
import it.codegen.training.SunTravels.model.Contract;
import it.codegen.training.SunTravels.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping( "/contract" )

public class ContractController
{

    @Autowired
    private ContractService contractService;

    public ContractController( ContractService contractService )
    {
        this.contractService = contractService;
    }

    private Logger logger = LoggerFactory.getLogger( ContractController.class );

    @PostMapping( "/add" )
    public ResponseEntity<ContractDTO> addContracts( @RequestBody ContractDTO contractDTO )
    {
        try
        {
            logger.info( "Adding contract: {}", contractDTO );

            return new ResponseEntity<>( contractService.addContract( contractDTO ), HttpStatus.CREATED );
        }
        catch( Exception e )
        {
            logger.error( "Failed to add contract", e );
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @GetMapping( "/all" )
    public ResponseEntity<List<ContractDTO>> getContract()
    {
        try
        {
            logger.info( "Fetching all contracts" );
            List<ContractDTO> contracts = contractService.findAllContracts();
            return new ResponseEntity<>( contracts, HttpStatus.OK );
        }
        catch( Exception e )
        {
            logger.error( "Failed to fetch contracts", e );
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @DeleteMapping("/delete/{contractId}")
    public ResponseEntity<?> deleteHotel(@PathVariable("contractId") Long contractId) {
        try {
            contractService.deleteContract(contractId);
            logger.info("Contract with ID {} deleted successfully", contractId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DbException e) {
            logger.error("Error occurred while deleting contract with ID {}", contractId, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error occurred while deleting contract with ID {}", contractId, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping( "/get/{hotelId}" )
    public ResponseEntity<List<ContractDTO>> getContractByHotelId( @PathVariable( "hotelId" ) Long hotelId )
    {
        try
        {
            logger.info( "Fetching contracts by hotelId: {}", hotelId );
            List<ContractDTO> contracts = contractService.getContractByHotelId( hotelId );
            return new ResponseEntity<>( contracts, HttpStatus.OK );
        }
        catch( Exception e )
        {
            logger.error( "Failed to fetch contracts by hotelId: {}", hotelId, e );
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @GetMapping( "/find/{contractId}" )
    public ResponseEntity<ContractDTO> getContractById( @PathVariable( "contractId" ) Long contractId )
    {
        try
        {
            logger.info( "Fetching contract by contractId: {}", contractId );
            ContractDTO contract = contractService.findContractById( contractId );
            return new ResponseEntity<>( contract, HttpStatus.OK );
        }
        catch( Exception e )
        {
            logger.error( "Failed to fetch contract by contractId: {}", contractId, e );
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @PutMapping( "/update/{contractId}" )
    public ResponseEntity<ContractDTO> updateContract( @PathVariable( "contractId" ) Long contractId, @RequestBody ContractDTO contractDTO )
    {
        try
        {
            logger.info( "Updating contract: {}", contractId );
            ContractDTO updatedContract = contractService.updateContract( contractId, contractDTO );
            return new ResponseEntity<>( updatedContract, HttpStatus.OK );
        }
        catch( Exception e )
        {
            logger.error( "Failed to update contract: {}", contractId, e );
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}






//    @PostMapping( "/add" )
//    public ResponseEntity<ContractDTO> addContracts( @RequestBody ContractDTO contractDTO )
//    {
//        System.out.println( contractDTO );
//        return new ResponseEntity<>( contractService.addContract( contractDTO ), HttpStatus.CREATED );
//    }

//    @GetMapping( "/all" )
//    public ResponseEntity<List<ContractDTO>> getContract()
//    {
//
//        return new ResponseEntity<>( contractService.findAllContracts(), HttpStatus.OK );
//
//    }


//    @DeleteMapping( "/delete/{contractId}" )
//    public ResponseEntity<?> deleteHotel( @PathVariable( "contractId" ) Long contractId ) throws DbException
//    {
//        contractService.deleteContract( contractId );
//        return new ResponseEntity<>( HttpStatus.OK );
//
//    }


//    @GetMapping( "/get/{hotelId}" )
//    public ResponseEntity<List<ContractDTO>> getContractByHotelId( @PathVariable( "hotelId" ) Long hotelId )
//    {
//        return new ResponseEntity<>( contractService.getContractByHotelId( hotelId ), HttpStatus.OK );
//    }
//
//    @GetMapping( "/find/{contractId}" )
//    public ResponseEntity<ContractDTO> getContractById( @PathVariable( "contractId" ) Long contractId )
//    {
//        ContractDTO contract = contractService.findContractById( contractId );
//        return new ResponseEntity<>( contract, HttpStatus.OK );
//
//    }
//
//    @PutMapping( "/update/{contractId}" )
//    public ResponseEntity<ContractDTO> updateContract( @PathVariable( "contractId" ) Long contractId, @RequestBody ContractDTO contractDTO )
//    {
//        ContractDTO updateContract = contractService.updateContract( contractId, contractDTO );
//        return new ResponseEntity<>( updateContract, HttpStatus.OK );
//
//    }



