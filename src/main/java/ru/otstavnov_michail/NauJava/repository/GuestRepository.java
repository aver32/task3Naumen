package ru.otstavnov_michail.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otstavnov_michail.NauJava.modeldb.Guest;

import java.util.List;

// Репозиторий для работы с сущностью Guest
@RepositoryRestResource(path = "guests")
public interface GuestRepository extends CrudRepository<Guest, Integer> {
    // Метод поиска по имени и email (используем ключевые слова And)
    List<Guest> findByNameAndEmail(String name, String email);

    // Метод поиска по имени или номеру телефона (используем ключевые слова Or)
    List<Guest> findByNameOrPhoneNumber(String name, String phoneNumber);
}
