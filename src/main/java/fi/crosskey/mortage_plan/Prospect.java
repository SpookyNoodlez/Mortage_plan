package fi.crosskey.mortage_plan;

public record Prospect(String customerName, double totalLoan, float interest, int years, double monthlyPayment) { }