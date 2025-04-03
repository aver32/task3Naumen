package ru.otstavnov_michail.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otstavnov_michail.NauJava.modeldb.Room;
import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    // Метод поиска комнат по типу и цене меньше или равной указанной
    List<Room> findByRoomTypeAndPriceLessThanEqual(String roomType, Double maxPrice);

    // Метод с JPQL запросом для поиска комнат по названию отеля
    @Query("SELECT r FROM Room r JOIN r.hotel h WHERE h.name = :hotelName")
    List<Room> findByHotelName(String hotelName);
}
