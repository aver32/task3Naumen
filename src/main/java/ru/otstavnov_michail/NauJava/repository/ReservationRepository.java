package ru.otstavnov_michail.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otstavnov_michail.NauJava.dao.ReservationRepositoryCustom;
import ru.otstavnov_michail.NauJava.modeldb.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Integer>, ReservationRepositoryCustom {
    // Метод с JPQL запросом для поиска бронирований по имени гостя
    @Query("SELECT r FROM Reservation r JOIN r.guest g WHERE g.name = :guestName")
    List<Reservation> findByGuestName(String guestName);

    // Метод поиска бронирований по диапазону дат заезда
    List<Reservation> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate);
}
