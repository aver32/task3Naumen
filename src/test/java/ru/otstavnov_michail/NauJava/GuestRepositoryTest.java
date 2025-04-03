package ru.otstavnov_michail.NauJava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otstavnov_michail.NauJava.modeldb.Guest;
import ru.otstavnov_michail.NauJava.repository.GuestRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestRepositoryTest {

    @Autowired
    private GuestRepository guestRepository;

    @Test
    void testFindByNameAndEmail() {
        Guest guest = new Guest();
        guest.setName("Test Guest");
        guest.setEmail("test@example.com");
        guest.setPhoneNumber("+1234567890");
        guestRepository.save(guest);

        List<Guest> result = guestRepository.findByNameAndEmail("Test Guest", "test@example.com");

        assertFalse(result.isEmpty());
        assertEquals("Test Guest", result.get(0).getName());
        assertEquals("test@example.com", result.get(0).getEmail());
    }

    @Test
    void testFindByNameOrPhoneNumber() {
        Guest guest = new Guest();
        guest.setName("Another Guest");
        guest.setEmail("another@example.com");
        guest.setPhoneNumber("+9876543210");
        guestRepository.save(guest);

        List<Guest> resultByName = guestRepository.findByNameOrPhoneNumber("Another Guest", "");
        List<Guest> resultByPhone = guestRepository.findByNameOrPhoneNumber("", "+9876543210");

        assertFalse(resultByName.isEmpty());
        assertFalse(resultByPhone.isEmpty());
        assertEquals(guest.getId(), resultByName.get(0).getId());
        assertEquals(guest.getId(), resultByPhone.get(0).getId());
    }
}
