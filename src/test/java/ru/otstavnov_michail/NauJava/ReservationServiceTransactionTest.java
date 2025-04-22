package ru.otstavnov_michail.NauJava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otstavnov_michail.NauJava.modeldb.*;
import ru.otstavnov_michail.NauJava.repository.*;
import ru.otstavnov_michail.NauJava.service.ReservationService;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTransactionTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void testCreateReservationWithPayment_Success() {
        Hotel hotel = new Hotel();
        hotel.setName("Transaction Hotel");
        hotelRepository.save(hotel);

        Room room = new Room();
        room.setRoomNumber("201");
        room.setRoomType("Standard");
        room.setPrice(100.0);
        room.setHotel(hotel);
        roomRepository.save(room);

        Guest guest = new Guest();
        guest.setName("Transaction Guest");
        guestRepository.save(guest);

        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setRoom(room);
        reservation.setCheckInDate(LocalDate.now().plusDays(2));
        reservation.setCheckOutDate(LocalDate.now().plusDays(4));

        Reservation result = reservationService.createReservationWithPayment(reservation, 50.0);

        assertNotNull(result.getId());

        List<Payment> payments = paymentRepository.findByReservationId(result.getId());
        assertEquals(1, payments.size());
        assertEquals(50.0, payments.get(0).getAmount());
    }

    @Test
    void testCreateReservationWithPayment_Rollback() {
        Guest guest = new Guest();
        guest.setName("Rollback Guest");
        guestRepository.save(guest);

        Reservation invalidReservation = new Reservation();
        invalidReservation.setGuest(guest);

        assertThrows(Exception.class, () -> {
            reservationService.createReservationWithPayment(invalidReservation, 50.0);
        });
    }
}
