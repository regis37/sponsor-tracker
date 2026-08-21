package de.thnuernberg.eit.regis.sponsor_tracker.dto;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Company;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Sector;

import java.time.LocalDate;

public class CompanyResponse {

    private Long id;
    private String name;
    private Sector sector;
    private String website;
    private String city;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String notes;
    private String createdBy;
    private LocalDate createdAt;

    // Constructeur qui convertit une entité en DTO
    public CompanyResponse(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.sector = company.getSector();
        this.website = company.getWebsite();
        this.city = company.getCity();
        this.contactName = company.getContactName();
        this.contactEmail = company.getContactEmail();
        this.contactPhone = company.getContactPhone();
        this.notes = company.getNotes();
        this.createdBy = company.getCreatedBy();
        this.createdAt = company.getCreatedAt();
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public Sector getSector() { return sector; }
    public String getWebsite() { return website; }
    public String getCity() { return city; }
    public String getContactName() { return contactName; }
    public String getContactEmail() { return contactEmail; }
    public String getContactPhone() { return contactPhone; }
    public String getNotes() { return notes; }
    public String getCreatedBy() { return createdBy; }
    public LocalDate getCreatedAt() { return createdAt; }
}