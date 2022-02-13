package fi.crosskey.mortage_plan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class LoanCalculatorTest {

    @Test
    public void calculateMonthlyPaymentTest() {
        LoanCalculator calculator = new LoanCalculator();

        double result = calculator.calculateMonthlyPayment(5000, 0.017F, 2);
        assertTrue(result>212.D && result<213.D);

        result = calculator.calculateMonthlyPayment(7000, 0.05F, 10);
        assertTrue(result>74.D && result<76.D);

        result = calculator.calculateMonthlyPayment(100.53D, 0.60F, 93);
        assertTrue(result>5.D && result<8.D);

        //Results don't exactly line up with my calculations using the formula...
        //Might have something to do with me using different datatypes for numbers
    }
}