package de.thnuernberg.eit.regis.sponsor_tracker.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    private LocalDate date;

    private double targetBudget;


    @Column(length = 2000)
    private String description;

    public Event() {}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate  getDate() { return date; }
    public void setDate(LocalDate  date) { this.date = date; }

    public double getTargetBudget() { return targetBudget; }
    public void setTargetBudget(double targetBudget) { this.targetBudget = targetBudget; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

}