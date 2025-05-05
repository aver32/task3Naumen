package ru.otstavnov_michail.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otstavnov_michail.NauJava.modeldb.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}