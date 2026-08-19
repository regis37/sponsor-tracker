package de.thnuernberg.eit.regis.sponsor_tracker.service;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Sponsorship;
import de.thnuernberg.eit.regis.sponsor_tracker.model.SponsorshipStatus;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.CompanyRepository;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.EventRepository;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.SponsorshipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SponsorshipServiceTest {

    @Mock
    private SponsorshipRepository sponsorshipRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private SponsorshipService service;

    private Sponsorship sponsorship(double amount, SponsorshipStatus status) {
        Sponsorship s = new Sponsorship();
        s.setAmount(amount);
        s.setStatus(status);
        return s;
    }

    @Test
    void securedAmount_countsOnlySignedAndReceived() {
        when(sponsorshipRepository.findByEventId(1L)).thenReturn(List.of(
            sponsorship(1000.0, SponsorshipStatus.NEGOTIATION),
            sponsorship(2000.0, SponsorshipStatus.SIGNED),
            sponsorship(3000.0, SponsorshipStatus.RECEIVED),
            sponsorship(500.0,  SponsorshipStatus.CANCELLED)
        ));

        double secured = service.securedAmountForEvent(1L);

        assertEquals(5000.0, secured);
    }

    @Test
    void securedAmount_returnsZeroWhenNoSponsorships() {
        when(sponsorshipRepository.findByEventId(1L)).thenReturn(List.of());

        double secured = service.securedAmountForEvent(1L);

        assertEquals(0.0, secured);
    }

    @Test
    void securedAmount_returnsZeroWhenOnlyNegotiation() {
        when(sponsorshipRepository.findByEventId(1L)).thenReturn(List.of(
            sponsorship(1000.0, SponsorshipStatus.NEGOTIATION),
            sponsorship(2000.0, SponsorshipStatus.NEGOTIATION)
        ));

        double secured = service.securedAmountForEvent(1L);

        assertEquals(0.0, secured);
    }
}