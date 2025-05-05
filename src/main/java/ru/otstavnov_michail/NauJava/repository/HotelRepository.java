package ru.otstavnov_michail.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otstavnov_michail.NauJava.modeldb.Hotel;
import java.util.List;

@RepositoryRestResource(path = "hotels")
public interface HotelRepository extends CrudRepository<Hotel, Integer> {
    // Метод поиска отелей по местоположению и рейтингу
    List<Hotel> findByLocationAndStarRatingGreaterThanEqual(String location, Integer minRating);

    // Метод поиска отелей по названию, содержащему подстроку (используем Containing)
    List<Hotel> findByNameContaining(String namePart);
}
