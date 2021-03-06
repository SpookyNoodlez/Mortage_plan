package fi.crosskey.mortage_plan;

public class LoanCalculator {

    static final int MONTHS_IN_A_YEAR = 12;

    public double calculateMonthlyPayment(double totalLoan, float yearlyInterest, int years){
        int monthsToPay = years*MONTHS_IN_A_YEAR;
        int monthsLeftToPay = monthsToPay;
        double amountLeftToPay = totalLoan;
        double monthlyInterest = yearlyInterest/MONTHS_IN_A_YEAR;

        double loanWithInterest = 0.D;
        while (monthsLeftToPay > 0){
            //start by adding the interest to the loan at the start of the month
            amountLeftToPay = amountLeftToPay + amountLeftToPay * monthlyInterest;

            //pay back a month's worth
            double monthsWorth = amountLeftToPay/monthsLeftToPay;
            amountLeftToPay -= monthsWorth;
            monthsLeftToPay--;

            //add it to the record of payments
            loanWithInterest += monthsWorth;
        }

        //Divide the payments equally between all months
        return loanWithInterest/monthsToPay;
    }
}
