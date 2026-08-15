package de.thnuernberg.eit.regis.sponsor_tracker.dto;

public class BudgetSummary {

    private double targetBudget;
    private double secured;
    private double remaining;

    public BudgetSummary(double targetBudget, double secured, double remaining) {
        this.targetBudget = targetBudget;
        this.secured = secured;
        this.remaining = remaining;
    }


    public double getTargetBudget() { return targetBudget; }
    public double getSecured() { return secured; }
    public double getRemaining() { return remaining; }
}

