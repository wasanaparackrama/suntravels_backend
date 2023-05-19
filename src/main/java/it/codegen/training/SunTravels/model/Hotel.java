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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 12 Apr 2023
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "JPA_HOTEL" )
public class Hotel implements Serializable
{
    @Id
    @SequenceGenerator( name = "jpa_hotel_sequence", sequenceName = "jpa_hotel_sequence", allocationSize = 1

    )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "jpa_hotel_sequence"

    )
    @Column( nullable = false, updatable = false, name = "hotelId" )
    private long hotelId;
    private String hotelName;
    private String city;
    private String phoneNumber;

    //    @JsonIgnore
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "hotel" )
    private List<Contract> contract;


    @OneToMany( cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Rooms> rooms;
}
























//    public Hotel( Long id, String hotelName, String city )
//    {
//        this.id = id;
//        this.hotelName = hotelName;
//        this.city = city;
//    }
//
//    public Hotel()
//    {
//    }
//
//
//    public Long getHotelId()
//    {
//        return id;
//    }
//
//    public void setHotelId( Long id )
//    {
//        this.id = id;
//    }
//
//    public String getHotelName()
//    {
//        return hotelName;
//    }
//
//    public void setHotelName( String hotelName )
//    {
//        this.hotelName = hotelName;
//    }
//
//    public String getCity()
//    {
//        return city;
//    }
//
//    public void setCity( String city )
//    {
//        this.city = city;
//    }
//
////    @Override
////    public String toString(){
////        return "Hotel"{
////    }
//
//
//    public List<Contract> getContract()
//    {
//        return contract;
//    }
//
//    public void setContract( List<Contract> contract )
//    {
//        this.contract = contract;
//    }
//
//    public List<Rooms> getRooms()
//    {
//        return rooms;
//    }
//
//    public void setRooms( List<Rooms> rooms )
//    {
//        this.rooms = rooms;
//    }

