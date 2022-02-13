package fi.crosskey.mortage_plan;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringToProspectConverterTest {

    @Test
    void stringToProspectTest() {
        StringToProspectConverter converter = new StringToProspectConverter();
        Prospect badProspect = new Prospect("Bad data",-1,-1,-1,-1);
        LoanCalculator calculator = new LoanCalculator();

        //Test 1
        String name = "Olof Svensson";
        double totalLoan = 6000.D; float interest = 9.F/100; int years = 7;
        double monthlyPayment = calculator.calculateMonthlyPayment(totalLoan,interest,years);
        Prospect expected = new Prospect(name,totalLoan,interest,years, monthlyPayment);
        Prospect actual = converter.stringToProspect("Olof Svensson,6000,9,7");
        assertEquals(expected, actual);

        //Test 2
        name = "åäö";
        totalLoan = 789.67; interest = 14.87F/100; years = 45;
        monthlyPayment = calculator.calculateMonthlyPayment(totalLoan,interest,years);
        expected = new Prospect(name,totalLoan,interest,years, monthlyPayment);
        actual = converter.stringToProspect("åäö,789.67,14.87,45");
        assertEquals(expected, actual);

        //Test 3
        expected = badProspect;
        actual = converter.stringToProspect(".aaa33++=");
        assertEquals(expected, actual);

        //Test 2
        name = "Greta,Grön";
        totalLoan = 12; interest = 1/100; years = 1;
        monthlyPayment = calculator.calculateMonthlyPayment(totalLoan,interest,years);
        expected = new Prospect(name,totalLoan,interest,years, monthlyPayment);
        actual = converter.stringToProspect("\"Greta,Grön\",12,1,1");
        assertEquals(expected, actual);
    }
}