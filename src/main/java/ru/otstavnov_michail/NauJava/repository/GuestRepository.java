package ru.otstavnov_michail.NauJava.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otstavnov_michail.NauJava.entity.Guest;
import ru.otstavnov_michail.NauJava.repository.CrudRepository;

import java.util.List;

@Component
public class GuestRepository implements CrudRepository<Guest, Long> {

    private final List<Guest> guestContainer;

    @Autowired
    public GuestRepository(List<Guest> guestContainer) {
        this.guestContainer = guestContainer;
    }

    @Override
    public void create(Guest guest) {
        guestContainer.add(guest);
    }

    @Override
    public Guest read(Long id) {
        return guestContainer.stream()
                .filter(guest -> guest.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Guest guest) {
        for (int i = 0; i < guestContainer.size(); i++) {
            if (guestContainer.get(i).getId().equals(guest.getId())) {
                guestContainer.set(i, guest);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        guestContainer.removeIf(guest -> guest.getId().equals(id));
    }
}
