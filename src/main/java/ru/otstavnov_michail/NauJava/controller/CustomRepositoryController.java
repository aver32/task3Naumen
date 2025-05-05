package ru.otstavnov_michail.NauJava.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.otstavnov_michail.NauJava.modeldb.*;
import ru.otstavnov_michail.NauJava.repository.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/custom")
public class CustomRepositoryController {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public CustomRepositoryController(
            GuestRepository guestRepository,
            PaymentRepository paymentRepository,
            ReservationRepository reservationRepository,
            RoomRepository roomRepository,
            HotelRepository hotelRepository) {
        this.guestRepository = guestRepository;
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    @GetMapping("/guests/{id}")
    public Guest getGuestById(@PathVariable Integer id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + id));
    }

    // Методы для работы с GuestRepository
//    @GetMapping("/guests/byNameAndEmail")
//    public List<Guest> findGuestsByNameAndEmail(
//            @RequestParam String name,
//            @RequestParam String email) {
//        return guestRepository.findByNameAndEmail(name, email);
//    }

    @Operation(summary = "Поиск гостей по имени и email")
    @ApiResponse(responseCode = "200", description = "Успешный поиск гостей")
    @GetMapping("/guests/byNameAndEmail")
    public List<Guest> findGuestsByNameAndEmail(
            @Parameter(description = "Имя гостя") @RequestParam String name,
            @Parameter(description = "Email гостя") @RequestParam String email) {
        List<Guest> result = guestRepository.findByNameAndEmail(name, email);
        return result != null ? result : Collections.emptyList();
    }

    @GetMapping("/guests/byNameOrPhone")
    public List<Guest> findGuestsByNameOrPhone(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone) {
        return guestRepository.findByNameOrPhoneNumber(name, phone);
    }

    // Методы для работы с PaymentRepository
    @GetMapping("/payments/byDateRange")
    public List<Payment> findPaymentsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return paymentRepository.findByPaymentDateBetween(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }

    // Методы для работы с ReservationRepository
    @GetMapping("/reservations/byGuestName")
    public List<Reservation> findReservationsByGuestName(
            @RequestParam String guestName) {
        return reservationRepository.findByGuestName(guestName);
    }

    @GetMapping("/reservations/byCheckInDateRange")
    public List<Reservation> findReservationsByCheckInDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return reservationRepository.findByCheckInDateBetween(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }

    // Методы для работы с RoomRepository
    @GetMapping("/rooms/byTypeAndMaxPrice")
    public List<Room> findRoomsByTypeAndMaxPrice(
            @RequestParam String type,
            @RequestParam Double maxPrice) {
        return roomRepository.findByRoomTypeAndPriceLessThanEqual(type, maxPrice);
    }

    @GetMapping("/rooms/byHotelName")
    public List<Room> findRoomsByHotelName(
            @RequestParam String hotelName) {
        return roomRepository.findByHotelName(hotelName);
    }

    // Методы для работы с HotelRepository
    @GetMapping("/hotels/byLocationAndRating")
    public List<Hotel> findHotelsByLocationAndRating(
            @RequestParam String location,
            @RequestParam Integer minRating) {
        return hotelRepository.findByLocationAndStarRatingGreaterThanEqual(location, minRating);
    }

    @GetMapping("/hotels/searchByName")
    public List<Hotel> searchHotelsByName(
            @RequestParam String namePart) {
        return hotelRepository.findByNameContaining(namePart);
    }

}
