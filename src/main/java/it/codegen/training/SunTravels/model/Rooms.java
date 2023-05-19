/*
 * Copyright (c) 2023. CodeGen International (Pvt) Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of CodeGen
 * International (Pvt) Ltd. ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with CodeGen International.
 *
 */
package it.codegen.training.SunTravels.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 17 Apr 2023
 */

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table( name = "JPA_ROOM" )

public class Rooms
{

    @Id
    @SequenceGenerator( name = "jpa_room_sequence", sequenceName = "jpa_room_sequence", allocationSize = 1

    )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "jpa_room_sequence"

    )
    private int roomId;
    private String roomType;
    private int noOfRooms;
    private int maxPeople;
    private int cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotelId")
    @JsonIgnore
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "contractId")
    private Contract contract;

}





