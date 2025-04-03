package ru.otstavnov_michail.NauJava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otstavnov_michail.NauJava.modeldb.*;
import ru.otstavnov_michail.NauJava.repository.*;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationCriteriaTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void testFindByCheckInDateBetweenAndRoomType() {
        Hotel hotel = new Hotel();
        hotel.setName("Test Hotel");
        hotel.setLocation("Test Location");
        hotel.setStarRating(5);
        hotelRepository.save(hotel);

        Room room = new Room();
        room.setRoomNumber("101");
        room.setRoomType("Deluxe");
        room.setPrice(200.0);
        room.setHotel(hotel);
        roomRepository.save(room);

        Guest guest = new Guest();
        guest.setName("Criteria Guest");
        guest.setEmail("criteria@test.com");
        guest.setPhoneNumber("+1111111111");
        guestRepository.save(guest);

        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setRoom(room);
        reservation.setCheckInDate(LocalDate.now().plusDays(1));
        reservation.setCheckOutDate(LocalDate.now().plusDays(3));
        reservationRepository.save(reservation);

        List<Reservation> result = reservationRepository.findByCheckInDateBetweenAndRoomType(
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "Deluxe");

        assertFalse(result.isEmpty());
        assertEquals(reservation.getId(), result.get(0).getId());
    }
}
