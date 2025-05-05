package ru.otstavnov_michail.NauJava.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otstavnov_michail.NauJava.modeldb.User;
import ru.otstavnov_michail.NauJava.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        if (findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }

        if (findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ADMIN");
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User addUser(User user) {
        registerUser(user);
        return user;
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }
}