package ru.otstavnov_michail.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otstavnov_michail.NauJava.modeldb.Payment;
import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource(path = "payments")
public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    // Метод поиска платежей по диапазону дат (используем Between)
    List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    List<Payment> findByReservationId(Integer reservationId);

    long count();
}
