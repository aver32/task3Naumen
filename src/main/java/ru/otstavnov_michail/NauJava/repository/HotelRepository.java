package ru.otstavnov_michail.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otstavnov_michail.NauJava.modeldb.Hotel;
import java.util.List;

public interface HotelRepository extends CrudRepository<Hotel, Integer> {
    // Метод поиска отелей по местоположению и рейтингу
    List<Hotel> findByLocationAndStarRatingGreaterThanEqual(String location, Integer minRating);

    // Метод поиска отелей по названию, содержащему подстроку (используем Containing)
    List<Hotel> findByNameContaining(String namePart);
}
