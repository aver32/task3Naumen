package ru.otstavnov_michail.NauJava.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import ru.otstavnov_michail.NauJava.modeldb.Reservation;
import ru.otstavnov_michail.NauJava.modeldb.Room;
import ru.otstavnov_michail.NauJava.modeldb.Guest;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public ReservationRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Reservation> findByCheckInDateBetweenAndRoomType(LocalDate startDate, LocalDate endDate, String roomType) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);

        Root<Reservation> reservation = cq.from(Reservation.class);
        Join<Reservation, Room> room = reservation.join("room", JoinType.INNER);

        Predicate datePredicate = cb.between(reservation.get("checkInDate"), startDate, endDate);
        Predicate roomTypePredicate = cb.equal(room.get("roomType"), roomType);

        cq.select(reservation)
                .where(cb.and(datePredicate, roomTypePredicate));

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Reservation> findByGuestName(String guestName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);

        Root<Reservation> reservation = cq.from(Reservation.class);
        Join<Reservation, Guest> guest = reservation.join("guest", JoinType.INNER);

        Predicate namePredicate = cb.equal(guest.get("name"), guestName);

        cq.select(reservation)
                .where(namePredicate);

        return entityManager.createQuery(cq).getResultList();
    }
}
