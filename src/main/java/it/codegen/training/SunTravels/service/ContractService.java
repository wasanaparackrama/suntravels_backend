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
import it.codegen.training.SunTravels.dto.ContractDTO;

import it.codegen.training.SunTravels.dto.DeleteResponse;
import it.codegen.training.SunTravels.exception.DbException;
import it.codegen.training.SunTravels.mapper.ContractMapper;
import it.codegen.training.SunTravels.model.Contract;
import it.codegen.training.SunTravels.model.Hotel;

import it.codegen.training.SunTravels.repository.ContractRepository;
import it.codegen.training.SunTravels.repository.HotelRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 20 Apr 2023
 */


@Service
@Transactional
public class ContractService
{

    @Autowired
    private ContractRepository contractRepository;
    ;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ContractMapper contractMapper;

    private Logger logger = LoggerFactory.getLogger( ContractService.class );



    public List<ContractDTO> findAllContracts() {
        try {
            logger.info("Fetching all contracts");

            List<Contract> contractList = contractRepository.findAll();
            List<ContractDTO> contractDTOList = new ArrayList<>();
            for (Contract contract : contractList) {
                contractDTOList.add(contractMapper.mapOut(contract));
            }

            logger.info("Retrieved {} contracts", contractDTOList.size());

            return contractDTOList;
        } catch (Exception e) {
            logger.error("Failed to retrieve all contracts", e);
            throw e;
        }
    }

    public List<ContractDTO> getContractByHotelId(Long hotelId) {
        try {
            logger.info("Fetching contracts for hotelId: {}", hotelId);

            List<Contract> contractListByHotelId = contractRepository.findByHotelHotelId(hotelId);
            List<ContractDTO> contractDTOList = new ArrayList<>();
            for (Contract contract : contractListByHotelId) {
                contractDTOList.add(contractMapper.mapOut(contract));
            }

            logger.info("Retrieved {} contracts for hotelId: {}", contractDTOList.size(), hotelId);

            return contractDTOList;
        } catch (Exception e) {
            logger.error("Failed to retrieve contracts for hotelId: {}", hotelId, e);
            throw e;
        }
    }

    public ContractDTO findContractById(Long contractId) {
        try {
            logger.info("Fetching contract by ID: {}", contractId);

            Contract contract = contractRepository.findById(contractId)
                                                  .orElseThrow(() -> new IllegalArgumentException("Contract not found for the given ID " + contractId));

            logger.info("Retrieved contract with ID: {}", contractId);

            return contractMapper.mapOut(contract);
        } catch (Exception e) {
            logger.error("Failed to retrieve contract with id: {}", contractId, e);
            throw e;
        }
    }

    public DeleteResponse deleteContract(Long id) throws DbException {
        try {
            logger.info("Deleting contract with ID: {}", id);

            if (id == null || id == 0) {
                String errorMessage = "Invalid ID: You must provide a valid ID";
                logger.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            if (!contractRepository.existsById(id)) {
                String errorMessage = "Contract not found for the given ID: " + id;
                logger.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            contractRepository.deleteById(id);
            logger.info("Contract deleted successfully with ID: {}", id);

            DeleteResponse response = new DeleteResponse();
            response.setEntity("Contract");
            response.setId(id);
            response.setMessage("Contract " + id + " removed successfully!!");
            return response;
        } catch (Exception e) {
            logger.error("Failed to delete contract with ID: {}", id, e);
            throw new DbException("Can't delete the Contract with ID: " + id, e);
        }
    }

    public ContractDTO addContract(ContractDTO contractDTO) {
        try {
            // Logging statement
            logger.info("Adding contract: {}", contractDTO);

            if (Objects.isNull(contractDTO.getStartDate()) || Objects.isNull(contractDTO.getEndDate()) || Objects.isNull(contractDTO.getMarkUp())) {
                throw new IllegalArgumentException("Invalid request");
            }

            Contract contract = contractMapper.mapIn(contractDTO);
            Hotel hotel = hotelRepository.findById(contractDTO.getHotelId()).orElseThrow(() -> new IllegalArgumentException("Hotel not found for the given hotelId " + contractDTO.getHotelId()));
            contract.setHotel(hotel);
            Contract savedEntity = contractRepository.save(contract);

            // Logging statement
//            logger.info("Contract added successfully: {}", savedEntity);

            return contractMapper.mapOut(savedEntity);
        } catch (Exception e) {
            // Logging statement
            logger.error("Failed to add contract: {}", contractDTO, e);
            throw e;
        }
    }
    public ContractDTO updateContract(Long id, ContractDTO contractDTO) {
        try {
            // Logging statement
            logger.info("Updating contract with id {}: {}", id, contractDTO);

            Contract existingContract = contractRepository.findById(id)
                                                          .orElseThrow(() -> new IllegalArgumentException("Contract not found for the given id " + id));

            existingContract.setStartDate(contractDTO.getStartDate());
            existingContract.setEndDate(contractDTO.getEndDate());
            existingContract.setMarkUp(contractDTO.getMarkUp());
            Contract savedEntity = contractRepository.save(existingContract);

            // Logging statement
//            logger.info("Contract updated successfully: {}", savedEntity);

            return contractMapper.mapOut(savedEntity);
        } catch (Exception e) {
            // Logging statement
            logger.error("Failed to update contract with id {}: {}", id, contractDTO, e);
            throw e;
        }
    }

}




//    public ContractDTO updateContract( Long id, ContractDTO contractDTO )
//    {
//
//        Contract existingContracts = contractRepository.findById( id ).orElseThrow( () -> new Error( "Contract not found for the given id " + id ) );
//        existingContracts.setStartDate( contractDTO.getStartDate() );
//        existingContracts.setEndDate( contractDTO.getEndDate() );
//        existingContracts.setMarkUp( contractDTO.getMarkUp() );
//        Contract savedEntity = contractRepository.save( existingContracts );
//        return contractMapper.mapOut( savedEntity );
//    }


//    public ContractDTO addContract( ContractDTO contractDTO )
//    {
//        if( Objects.isNull( contractDTO.getStartDate() ) || Objects.isNull( contractDTO.getEndDate() ) || Objects.isNull( contractDTO.getMarkUp() ) )
//        {
//            throw new IllegalArgumentException( "Invalid request" );
//        }
//
//        Contract contract = contractMapper.mapIn( contractDTO );
//        Hotel hotel = hotelRepository.findById( contractDTO.getHotelId() ).get();
//        contract.setHotel( hotel );
//        Contract savedEntity = contractRepository.save( contract );
//        return contractMapper.mapOut( savedEntity );
//    }
//
//    public ContractDTO updateContract( Long id, ContractDTO contractDTO )
//    {
//
//        Contract existingContracts = contractRepository.findById( id ).orElseThrow( () -> new Error( "Contract not found for the given id " + id ) );
//        existingContracts.setStartDate( contractDTO.getStartDate() );
//        existingContracts.setEndDate( contractDTO.getEndDate() );
//        existingContracts.setMarkUp( contractDTO.getMarkUp() );
//        Contract savedEntity = contractRepository.save( existingContracts );
//        return contractMapper.mapOut( savedEntity );
//    }


//
//    public Contract updateContract( Contract contract )
//    {
//        return contractRepository.save( contract );
//    }
//
//    public Contract findContractById( long contractId )
//    {
//        return ( Contract ) contractRepository.findContractBycontractId( contractId ).orElseThrow( () -> new UserNotFoundException( "User by id" + contractId + "was not found" ) );
//    }

//    public  void deleteContract( long contractId )
//    {
//        contractRepository.deleteById( contractId );
//    }
//    public List<ContractDTO> findAllContracts()
//    {
//        List<Contract> contractList = contractRepository.findAll();
//        List<ContractDTO> contractDTOList = new ArrayList<>();
//        for( Contract contract : contractList )
//        {
//            contractDTOList.add( contractMapper.mapOut( contract ) );
//        }
//        return contractDTOList;
//    }
//
//
//    public List<ContractDTO> getContractByHotelId( Long hotelId )
//    {
//        List<Contract> contractListByHotelId = contractRepository.findByHotelHotelId( hotelId );
//        List<ContractDTO> contractDTOList = new ArrayList<>();
//        for( Contract roomType : contractListByHotelId )
//        {
//            contractDTOList.add( contractMapper.mapOut( roomType ) );
//        }
//        return contractDTOList;
////        return roomTypeMapper.mapOut( roomType );
//    }
//
//    public ContractDTO findContractById( Long contractId )
//    {
//        Contract contract = contractRepository.findById( contractId )
//                                              .orElseThrow( () -> new Error( "Hotel not found for the given id " + contractId ) );
//        return contractMapper.mapOut( contract );
//    }