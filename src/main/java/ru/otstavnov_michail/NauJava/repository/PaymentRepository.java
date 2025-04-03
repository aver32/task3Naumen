package ru.otstavnov_michail.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otstavnov_michail.NauJava.modeldb.Payment;
import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    // Метод поиска платежей по диапазону дат (используем Between)
    List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    List<Payment> findByReservationId(Integer reservationId);

    long count();
}
