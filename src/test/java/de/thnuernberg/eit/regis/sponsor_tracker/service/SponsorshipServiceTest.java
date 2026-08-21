package de.thnuernberg.eit.regis.sponsor_tracker.service;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Sponsorship;
import de.thnuernberg.eit.regis.sponsor_tracker.model.SponsorshipStatus;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.CompanyRepository;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.EventRepository;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.SponsorshipRepository;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Company;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Event;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;

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
                sponsorship(500.0, SponsorshipStatus.CANCELLED)));

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
                sponsorship(2000.0, SponsorshipStatus.NEGOTIATION)));

        double secured = service.securedAmountForEvent(1L);

        assertEquals(0.0, secured);
    }

    @Test
    void create_whenCompanyAndEventExist_savesSponsorship() {
        Company company = new Company();
        company.setId(1L);
        Event event = new Event();
        event.setId(1L);

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        // Le mock du save renvoie l'objet qu'on lui passe
        when(sponsorshipRepository.save(any(Sponsorship.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Sponsorship input = new Sponsorship();
        input.setAmount(2000.0);

        // Act
        Optional<Sponsorship> result = service.create(input, 1L, 1L, "Regis");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Regis", result.get().getCreatedBy());
        assertEquals(company, result.get().getCompany());
        assertEquals(event, result.get().getEvent());
    }

    @Test
    void create_whenCompanyMissing_returnsEmpty() {
        when(companyRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Sponsorship> result = service.create(new Sponsorship(), 99L, 1L, "Regis");

        assertFalse(result.isPresent());
    }

    @Test
    void create_whenEventMissing_returnsEmpty() {
        Company company = new Company();
        company.setId(1L);
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(eventRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Sponsorship> result = service.create(new Sponsorship(), 1L, 99L, "Regis");

        assertFalse(result.isPresent());
    }
}