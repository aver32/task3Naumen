package ru.otstavnov_michail.NauJava.service;

import ru.otstavnov_michail.NauJava.modeldb.Reservation;
import ru.otstavnov_michail.NauJava.modeldb.Payment;

public interface ReservationService {
    /**
     * Создает бронирование и первоначальный платеж в одной транзакции
     * @param reservation данные бронирования
     * @param initialPaymentAmount сумма первоначального платежа
     * @return созданное бронирование
     */
    Reservation createReservationWithPayment(Reservation reservation, Double initialPaymentAmount);
}
