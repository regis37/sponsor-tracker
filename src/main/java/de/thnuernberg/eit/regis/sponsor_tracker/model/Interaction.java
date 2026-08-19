package de.thnuernberg.eit.regis.sponsor_tracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "interactions")
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private InteractionType type;

    @NotBlank(message = "Summary is required")
    @Column(length = 2000)
    private String summary;

    @Enumerated(EnumType.STRING)
    private Outcome outcome;

    private LocalDate nextActionDate;
    private String nextActionNote;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private String createdBy;

    public Interaction() {}

    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public InteractionType getType() { return type; }
    public void setType(InteractionType type) { this.type = type; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public Outcome getOutcome() { return outcome; }
    public void setOutcome(Outcome outcome) { this.outcome = outcome; }

    public LocalDate getNextActionDate() { return nextActionDate; }
    public void setNextActionDate(LocalDate nextActionDate) { this.nextActionDate = nextActionDate; }

    public String getNextActionNote() { return nextActionNote; }
    public void setNextActionNote(String nextActionNote) { this.nextActionNote = nextActionNote; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}
