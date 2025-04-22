package ru.otstavnov_michail.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.otstavnov_michail.NauJava.modeldb.Reservation;
import ru.otstavnov_michail.NauJava.modeldb.Payment;
import ru.otstavnov_michail.NauJava.repository.ReservationRepository;
import ru.otstavnov_michail.NauJava.repository.PaymentRepository;

import java.time.LocalDate;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  PaymentRepository paymentRepository) {
        this.reservationRepository = reservationRepository;
        this.paymentRepository = paymentRepository;
    }

    // Вариант 1: Декларативное управление транзакциями через аннотацию
//    @Override
//    @Transactional
//    public Reservation createReservationWithPayment(Reservation reservation, Double initialPaymentAmount) {
//        // Сохраняем бронирование
//        Reservation savedReservation = reservationRepository.save(reservation);
//
//        // Создаем и сохраняем платеж
//        Payment payment = new Payment();
//        payment.setReservation(savedReservation);
//        payment.setAmount(initialPaymentAmount);
//        payment.setPaymentDate(LocalDate.now());
//
//        paymentRepository.save(payment);
//
//        return savedReservation;
//    }

    // Вариант 2: Программное управление транзакциями (альтернативный вариант)\
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public Reservation createReservationWithPayment(Reservation reservation, Double initialPaymentAmount) {
        // Валидация входных данных
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }
        if (reservation.getGuest() == null) {
            throw new IllegalArgumentException("Guest must be specified");
        }
        if (reservation.getRoom() == null) {
            throw new IllegalArgumentException("Room must be specified");
        }
        if (initialPaymentAmount == null || initialPaymentAmount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            // Сохраняем бронирование
            Reservation savedReservation = reservationRepository.save(reservation);

            // Создаем и сохраняем платеж
            Payment payment = new Payment();
            payment.setReservation(savedReservation);
            payment.setAmount(initialPaymentAmount);
            payment.setPaymentDate(LocalDate.now());

            paymentRepository.save(payment);

            transactionManager.commit(status);
            return savedReservation;
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw new RuntimeException("Transaction failed", ex);
        }
    }
}
