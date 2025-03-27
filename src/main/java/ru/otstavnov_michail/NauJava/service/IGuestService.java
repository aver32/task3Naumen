package ru.otstavnov_michail.NauJava.service;

import ru.otstavnov_michail.NauJava.entity.Guest;

import java.util.List;

public interface IGuestService {

    void createGuest(Long id, String name, String roomNumber, String email);

    Guest getGuestById(Long id);

    void updateGuest(Long id, String newName, int newRoomNumber);

    void deleteGuestById(Long id);
}
