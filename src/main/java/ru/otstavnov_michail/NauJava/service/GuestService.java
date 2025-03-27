package ru.otstavnov_michail.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otstavnov_michail.NauJava.entity.Guest;
import ru.otstavnov_michail.NauJava.repository.GuestRepository;
import ru.otstavnov_michail.NauJava.service.IGuestService;

@Service
public class GuestService implements IGuestService {

    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Override
    public void createGuest(Long id, String name, String email, String phoneNumber) {
        Guest guest = new Guest();
        guest.setId(id);
        guest.setName(name);
        guest.setEmail(email);
        guest.setPhoneNumber(phoneNumber);
        guestRepository.create(guest);
    }

    @Override
    public Guest getGuestById(Long id) {
        return guestRepository.read(id);
    }

    @Override
    public void updateGuest(Long id, String newName, int newRoomNumber) {
        Guest guest = guestRepository.read(id);
        if (guest != null) {
            guest.setName(newName);
            guestRepository.update(guest);
        }
    }

    @Override
    public void deleteGuestById(Long id) {
        guestRepository.delete(id);
    }
}