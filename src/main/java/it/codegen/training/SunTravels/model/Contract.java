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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 17 Apr 2023
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "JPA_CONTRACT"
)
public class Contract implements Serializable
{

    @Id
    @SequenceGenerator(
            name = "jpa_contract_sequence",
            sequenceName = "jpa_contract_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "jpa_contract_sequence"

    )
    @Column( nullable = false, updatable = false )
    private long contractId;
    @JsonFormat( pattern = "yyyy-MM-dd" )
    private Date startDate;
    @JsonFormat( pattern = "yyyy-MM-dd" )
    private Date endDate;
    private Float markUp;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotelId")
    @JsonIgnore
    private Hotel hotel;

    @OneToMany( mappedBy = "contract", cascade = CascadeType.ALL )
    private List<Rooms> rooms;

}











//    public Contract( long contractId, Date startDate, Date endDate, Float markUp, boolean isValid )
//    {
//        this.contractId = contractId;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.markUp = markUp;
//        this.isValid = isValid;
//
//    }


//
//    public Contract()
//    {
//    }


//    public long getContractId()
//    {
//        return contractId;
//    }
//
//    public void setContractId( long contractId )
//    {
//        this.contractId = contractId;
//    }
//
//    public Date getStartDate()
//    {
//        return startDate;
//    }
//
//    public void setStartDate( Date startDate )
//    {
//        this.startDate = startDate;
//    }
//
//    public Date getEndDate()
//    {
//        return endDate;
//    }
//
//    public void setEndDate( Date endDate )
//    {
//        this.endDate = endDate;
//    }
//
//    public Float getMarkUp()
//    {
//        return markUp;
//    }
//
//    public Hotel getHotel()
//    {
//        return hotel;
//    }
//
//    public void setHotel( Hotel hotel )
//    {
//        this.hotel = hotel;
//    }
//
//    public void setMarkUp( Float markUp )
//    {
//        this.markUp = markUp;
//    }
//
//    public boolean isValid()
//    {
//        return isValid;
//    }
//
//    public void setValid( boolean valid )
//    {
//        isValid = valid;
//    }



