package it.codegen.training.SunTravels.repository;

import it.codegen.training.SunTravels.model.Hotel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 17 Apr 2023
 */

@DataJpaTest
class HotelRepositoryTest
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    public void testFindAllWhenRepositoryIsEmpty() {
        List<Hotel> hotels = hotelRepository.findAll();
        assertThat(hotels).isEmpty();
    }

    @Test
    public void testFindAllHotels() {
        // Create two new Hotel objects
        Hotel hotel1 = new Hotel();
        hotel1.setHotelName( "Hotel 1" );
        hotel1.setCity( "City 1" );
        entityManager.persistAndFlush(hotel1);

        Hotel hotel2 = new Hotel();
        hotel2.setHotelName( "Hotel 2" );
        hotel2.setCity( "City 2" );
        entityManager.persistAndFlush(hotel2);

        // Retrieve all contracts from the database and assert that there are two of them
        List<Hotel> hotels = hotelRepository.findAll();
        assertThat( hotels ).hasSize( 2 );
    }

    @Test
    public void testSaveHotel(){
        // Create a new Hotel object
        Hotel hotel = new Hotel();
        hotel.setHotelName( "Hotel 1" );
        hotel.setCity( "City 1" );

        // Save the Hotel to the in-memory H2-database
        Hotel savedHotel = entityManager.persistAndFlush( hotel );

        // Retrieve the saved Hotel from the database and assert its properties
        Hotel retrievedHotel = hotelRepository.findById(savedHotel.getHotelId()).orElse(null);
        assertThat(retrievedHotel).isNotNull();
        assertThat(retrievedHotel.getHotelName()).isEqualTo(hotel.getHotelName());
        assertThat(retrievedHotel.getCity()).isEqualTo(hotel.getCity());
    }

    @Test
    public void testUpdateHotel(){
        // Create a new Hotel object
        Hotel hotel = new Hotel();
        hotel.setHotelName( "Hotel 1" );
        hotel.setCity( "City 1" );

        // Save the Hotel to the in-memory H2-database
        Hotel savedHotel = entityManager.persistAndFlush( hotel );

        // Update the Hotel and save it to the database
        savedHotel.setHotelName( "Updated Hotel" );
        savedHotel.setCity( "Updated City" );

        entityManager.persistAndFlush( savedHotel );

        // Retrieve the updated contract from the database and assert its properties
        Hotel retrievedHotel = hotelRepository.findById( savedHotel.getHotelId() ).orElse( null );
        assertThat(retrievedHotel).isNotNull();
        assertThat(retrievedHotel.getHotelName()).isEqualTo(savedHotel.getHotelName());
        assertThat(retrievedHotel.getCity()).isEqualTo(savedHotel.getCity());
    }

    @Test
    public void testDeleteHotel(){

        // Create a new Hotel object
        Hotel hotel = new Hotel();
        hotel.setHotelName( "Hotel 1" );
        hotel.setCity( "City 1" );

        // Save the Hotel to the in-memory H2-database
        Hotel savedHotel = entityManager.persistAndFlush( hotel );

        // Delete the contract from the database
        hotelRepository.delete(savedHotel);

        // Try to retrieve the deleted contract from the database and assert that it is null
        Hotel retrievedHotel = hotelRepository.findById(savedHotel.getHotelId()).orElse(null);
        assertThat(retrievedHotel).isNull();
    }
}