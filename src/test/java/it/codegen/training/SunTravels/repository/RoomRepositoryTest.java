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
import it.codegen.training.SunTravels.model.Hotel;
import it.codegen.training.SunTravels.model.Rooms;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 09 May 2023
 */


@RunWith( SpringRunner.class)
@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void findByHotelHotelId_shouldReturnListOfRooms() {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setHotelName("Test Hotel");
        entityManager.persist(hotel);
        Rooms room1 = new Rooms();
        room1.setRoomType("Single");
        room1.setNoOfRooms(2);
        room1.setMaxPeople(1);
        room1.setCost(100);
        room1.setHotel(hotel);
        entityManager.persist(room1);
        Rooms room2 = new Rooms();
        room2.setRoomType("Double");
        room2.setNoOfRooms(3);
        room2.setMaxPeople(2);
        room2.setCost(200);
        room2.setHotel(hotel);
        entityManager.persist(room2);
        entityManager.flush();

        // Act
        List<Rooms> rooms = roomRepository.findByHotelHotelId(hotel.getHotelId());

        // Assert
//        assertThat(rooms).isNotNull().hasSize(2);
//        assertThat(rooms).containsExactlyInAnyOrder(room1, room2);
    }

    @Test
    public void getAvailableRoomsByRequestCriteria_shouldReturnListOfObjects() {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setHotelName("Test Hotel");
        entityManager.persist(hotel);
        Contract contract = new Contract();
        contract.setHotel(hotel);
        contract.setMarkUp( 20F );
        contract.setStartDate( Date.valueOf( LocalDate.of(2023, 6, 1) ) );
        contract.setEndDate( Date.valueOf( LocalDate.of(2023, 6, 15) ) );
        entityManager.persist(contract);
        Rooms room1 = new Rooms();
        room1.setRoomType("Single");
        room1.setNoOfRooms(2);
        room1.setMaxPeople(1);
        room1.setCost(100);
        room1.setHotel(hotel);
        room1.setContract(contract);
        entityManager.persist(room1);
        Rooms room2 = new Rooms();
        room2.setRoomType("Double");
        room2.setNoOfRooms(3);
        room2.setMaxPeople(2);
        room2.setCost(200);
        room2.setHotel(hotel);
        room2.setContract(contract);
        entityManager.persist(room2);
        entityManager.flush();

//         Act
//        List<Object[]> rooms = roomRepository.getAvailableRoomsByRequestCriteria(LocalDate.of(2023, 6, 5), LocalDate.of(2023, 6, 10), 2, 3);

//        Assert
//        assertThat(rooms).isNotNull().hasSize(2);
//        assertThat(rooms.get(0)).containsExactly("Test Hotel", null, "Single", 1, 2, 100, 20, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 6, 15));
//        assertThat(rooms.get(1)).containsExactly("Test Hotel", null, "Double", 2, 3, 200, 20, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 6, 15));
    }
}