package de.thnuernberg.eit.regis.sponsor_tracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Sector;

@Entity
@Table(name = "sponsorships")
public class Sponsorship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private ContributionType contributionType;

    @Enumerated(EnumType.STRING)
    private SponsorshipStatus status;

    private LocalDate signedDate;

    @Column(length = 2000)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private String createdBy;
    private LocalDate createdAt;

    public Sponsorship() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public ContributionType getContributionType() { return contributionType; }
    public void setContributionType(ContributionType contributionType) { this.contributionType = contributionType; }

    public SponsorshipStatus getStatus() { return status;}
    public void setStatus(SponsorshipStatus status) {this.status = status;}

    public LocalDate getSignedDate() { return signedDate;}
    public void setSignedDate(LocalDate signedDate) { this.signedDate = signedDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDate getCreatedAt() { return createdAt;}
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }


}