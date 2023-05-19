/*
 * Copyright (c) 2023. CodeGen International (Pvt) Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of CodeGen
 * International (Pvt) Ltd. ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with CodeGen International.
 *
 */
package it.codegen.training.SunTravels.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.codegen.training.SunTravels.model.Contract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;
import java.util.List;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 21 Apr 2023
 */

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContractDTO
{
    private long contractId;
    @JsonFormat( pattern = "yyyy-MM-dd" )
    private Date startDate;
    @JsonFormat( pattern = "yyyy-MM-dd" )
    private Date endDate;
    private Float markUp;

    private long hotelId;
    private String hotelName;
    private int roomId;
    private List<RoomDTO> assignedRoomData;
}
