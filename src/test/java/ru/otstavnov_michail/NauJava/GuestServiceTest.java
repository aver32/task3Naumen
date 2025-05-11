package ru.otstavnov_michail.NauJava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.otstavnov_michail.NauJava.modeldb.Guest;
import ru.otstavnov_michail.NauJava.repository.GuestRepository;
import ru.otstavnov_michail.NauJava.service.GuestService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestService guestService;

    private Guest guest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        guest = new Guest();
        guest.setId(1);
        guest.setName("Иван Иванов");
    }

    @Test
    void getAllGuests_ReturnsList() {
        when(guestRepository.findAll()).thenReturn(Arrays.asList(guest));

        List<Guest> guests = guestService.getAllGuests();
        assertEquals(1, guests.size());
        assertEquals("Иван Иванов", guests.get(0).getName());
    }

    @Test
    void getGuestById_Found() {
        when(guestRepository.findById(1)).thenReturn(Optional.of(guest));
        Optional<Guest> result = guestService.getGuestById(1);
        assertTrue(result.isPresent());
        assertEquals("Иван Иванов", result.get().getName());
    }

    @Test
    void getGuestById_NotFound() {
        when(guestRepository.findById(2)).thenReturn(Optional.empty());
        Optional<Guest> result = guestService.getGuestById(2);
        assertFalse(result.isPresent());
    }

    @Test
    void saveGuest_Success() {
        when(guestRepository.save(guest)).thenReturn(guest);
        Guest result = guestService.saveGuest(guest);
        assertEquals("Иван Иванов", result.getName());
    }

    @Test
    void deleteGuest_VerifyCall() {
        guestService.deleteGuest(1);
        verify(guestRepository, times(1)).deleteById(1);
    }
}
