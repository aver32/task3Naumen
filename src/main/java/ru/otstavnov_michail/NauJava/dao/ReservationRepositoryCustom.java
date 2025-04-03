package ru.otstavnov_michail.NauJava.dao;

import ru.otstavnov_michail.NauJava.modeldb.Reservation;
import java.time.LocalDate;
import java.util.List;

public interface ReservationRepositoryCustom {
    /**
     * Находит бронирования по диапазону дат заезда и типу комнаты
     * @param startDate начальная дата
     * @param endDate конечная дата
     * @param roomType тип комнаты
     */
    List<Reservation> findByCheckInDateBetweenAndRoomType(LocalDate startDate, LocalDate endDate, String roomType);

    /**
     * Находит бронирования по имени гостя
     * @param guestName имя гостя
     */
    List<Reservation> findByGuestName(String guestName);
}
