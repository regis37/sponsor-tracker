package de.thnuernberg.eit.regis.sponsor_tracker.dto;

import java.util.Map;

public class StatsSummary {

    private long totalCompanies;
    private long totalEvents;
    private long totalInteractions;
    private long totalSponsorships;
    private Map<String, Long> companiesByOutcome;
    private double totalSecured;
    private long companiesToFollowUp;

    public StatsSummary(long totalCompanies, long totalEvents, long totalInteractions,
                        long totalSponsorships, Map<String, Long> companiesByOutcome,
                        double totalSecured, long companiesToFollowUp) {
        this.totalCompanies = totalCompanies;
        this.totalEvents = totalEvents;
        this.totalInteractions = totalInteractions;
        this.totalSponsorships = totalSponsorships;
        this.companiesByOutcome = companiesByOutcome;
        this.totalSecured = totalSecured;
        this.companiesToFollowUp = companiesToFollowUp;
    }

    public long getTotalCompanies() { return totalCompanies; }
    public long getTotalEvents() { return totalEvents; }
    public long getTotalInteractions() { return totalInteractions; }
    public long getTotalSponsorships() { return totalSponsorships; }
    public Map<String, Long> getCompaniesByOutcome() { return companiesByOutcome; }
    public double getTotalSecured() { return totalSecured; }
    public long getCompaniesToFollowUp() { return companiesToFollowUp; }
}