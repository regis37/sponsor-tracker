package de.thnuernberg.eit.regis.sponsor_tracker.dto;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Interaction;
import de.thnuernberg.eit.regis.sponsor_tracker.model.InteractionType;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Outcome;

import java.time.LocalDate;

public class InteractionResponse {

    private Long id;
    private LocalDate date;
    private InteractionType type;
    private String summary;
    private Outcome outcome;
    private LocalDate nextActionDate;
    private String nextActionNote;
    private String createdBy;

    // Au lieu de toute la company imbriquée : juste l'id et le nom
    private Long companyId;
    private String companyName;

    public InteractionResponse(Interaction interaction) {
        this.id = interaction.getId();
        this.date = interaction.getDate();
        this.type = interaction.getType();
        this.summary = interaction.getSummary();
        this.outcome = interaction.getOutcome();
        this.nextActionDate = interaction.getNextActionDate();
        this.nextActionNote = interaction.getNextActionNote();
        this.createdBy = interaction.getCreatedBy();

        // On extrait juste ce qui est utile
        if (interaction.getCompany() != null) {
            this.companyId = interaction.getCompany().getId();
            this.companyName = interaction.getCompany().getName();
        }
    }

    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public InteractionType getType() { return type; }
    public String getSummary() { return summary; }
    public Outcome getOutcome() { return outcome; }
    public LocalDate getNextActionDate() { return nextActionDate; }
    public String getNextActionNote() { return nextActionNote; }
    public String getCreatedBy() { return createdBy; }
    public Long getCompanyId() { return companyId; }
    public String getCompanyName() { return companyName; }
}