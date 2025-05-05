package ru.otstavnov_michail.NauJava.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otstavnov_michail.NauJava.modeldb.User;
import ru.otstavnov_michail.NauJava.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String message = String.format("Пользователь с именем: %s не найден", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(message));
    }
}