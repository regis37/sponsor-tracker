package de.thnuernberg.eit.regis.sponsor_tracker.dto;

import de.thnuernberg.eit.regis.sponsor_tracker.model.ContributionType;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Sponsorship;
import de.thnuernberg.eit.regis.sponsor_tracker.model.SponsorshipStatus;

import java.time.LocalDate;

public class SponsorshipResponse {

    private Long id;
    private double amount;
    private ContributionType contributionType;
    private SponsorshipStatus status;
    private LocalDate signedDate;
    private String notes;
    private String createdBy;
    private LocalDate createdAt;

    // company aplatie
    private Long companyId;
    private String companyName;

    // event aplati
    private Long eventId;
    private String eventName;

    public SponsorshipResponse(Sponsorship sponsorship) {
        this.id = sponsorship.getId();
        this.amount = sponsorship.getAmount();
        this.contributionType = sponsorship.getContributionType();
        this.status = sponsorship.getStatus();
        this.signedDate = sponsorship.getSignedDate();
        this.notes = sponsorship.getNotes();
        this.createdBy = sponsorship.getCreatedBy();
        this.createdAt = sponsorship.getCreatedAt();

        if (sponsorship.getCompany() != null) {
            this.companyId = sponsorship.getCompany().getId();
            this.companyName = sponsorship.getCompany().getName();
        }
        if (sponsorship.getEvent() != null) {
            this.eventId = sponsorship.getEvent().getId();
            this.eventName = sponsorship.getEvent().getName();
        }
    }

    public Long getId() { return id; }
    public double getAmount() { return amount; }
    public ContributionType getContributionType() { return contributionType; }
    public SponsorshipStatus getStatus() { return status; }
    public LocalDate getSignedDate() { return signedDate; }
    public String getNotes() { return notes; }
    public String getCreatedBy() { return createdBy; }
    public LocalDate getCreatedAt() { return createdAt; }
    public Long getCompanyId() { return companyId; }
    public String getCompanyName() { return companyName; }
    public Long getEventId() { return eventId; }
    public String getEventName() { return eventName; }
}