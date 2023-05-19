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

import it.codegen.training.SunTravels.model.Contract;
import it.codegen.training.SunTravels.model.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
public interface ContractRepository extends JpaRepository<Contract,Long>
{
public List<Contract> findByHotelHotelId( Long hotelId);

}
