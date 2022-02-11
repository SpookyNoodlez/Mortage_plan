package fi.crosskey.mortage_plan;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.*;
import java.util.ArrayList;

public class LoadFileController {
    @FXML
    private Label resultDisplay;

    @FXML
    protected void loadFile() {
        ArrayList<Prospect> prospects = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("prospects.txt"));

            //Get rid of the first descriptive line
            String currentLine = reader.readLine();
            //read lines until reaching end of file
            while((currentLine = reader.readLine()) != null){
                //TEST
                //resultDisplay.setText(currentLine);
                //Split the string up and put it in a Prospect record
                prospects.add(stringToProspect(currentLine));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Concatenate string to display
        String displayString = "";
        for(int i = 0;i < prospects.size();i++){
            displayString = displayString
                            +"Prospect "
                            +(i+1)+": "
                            +prospects.get(i).customerName()
                            +" wants to borrow "
                            +prospects.get(i).totalLoan()
                            +" € for a period of "
                            +prospects.get(i).years()
                            +" years and pay "
                            +prospects.get(i).monthlyPayment()
                            +" € each month"
                            +"\n";
        }
        //Display result
        resultDisplay.setText(displayString);
    }

    private Prospect stringToProspect(String line){

        final String quotedNameRegex = "(\"[^\"]+\")"; //Anything except quotes between quotes
                                          //OR
        final String unquotedNameRegex = "([^,]+)"; //Anything except commas

        final String totalLoanRegex = "(\\d+\\.?\\d*)"; //One or more numbers followed by zero or one dots followed by any number of numbers
        final String interestRegex = "(\\d\\d?\\.?\\d*)"; //One or two numbers followed by zero or one dots followed by any number of numbers
        final String yearsRegex = "(\\d+)"; //One or more numbers

        String completedRegex = "(" + quotedNameRegex + "|" + unquotedNameRegex  + ")" + ","
                + totalLoanRegex + "," + interestRegex + "," + yearsRegex;
        //TODO: ADD DATA VERIFICATION
        try{
            String[] tokens = line.split(completedRegex);
            String name = tokens[0];
            double totalLoan = Double.parseDouble(tokens[1]);
            float interest = Float.parseFloat(tokens[2]) / 100.f; //convert from percent to decimal
            int years = Integer.parseInt(tokens[3]);

            LoanCalculator calculator = new LoanCalculator();
            double monthlyPayment = calculator.calculateMonthlyPayment(totalLoan,interest,years);

            return new Prospect(name, totalLoan, interest, years, monthlyPayment);
        }
        catch(NumberFormatException e){
            //If letters go in the number fields return a bad prospect to show a load error in the list
            return new Prospect("Bad data",-1,-1,-1,-1);
        }
    }
}