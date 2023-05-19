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
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 26 Apr 2023
 */
@Data
public class SearchRoomRequest
{
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date checkInDate;
    private int noOfNights;
    private List<RoomRequestCriteria> roomRequests;



}
