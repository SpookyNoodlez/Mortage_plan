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
                //Split the string up and put it in a Prospect record
                prospects.add(stringToProspect(currentLine));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String displayString = "";
        for(int i = 0;i < prospects.size();i++){
            //Negative values can only be entered by my bad data prospect
            if (prospects.get(i).totalLoan() < 0){
                displayString += "BAD ENTRY! CHECK \"prospects.txt\" AT THE FOLLOWING LINE: " + i + "\n";
            }
            //Concatenate string to display
            else{
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

        //test
        boolean b = line.matches(completedRegex);

        //If the line doesn't fit the format
        if (!line.matches(completedRegex)){
            return new Prospect("Bad data",-1,-1,-1,-1);
        }

        try{
            //Split by commas, go backwards and combine the last ones
            String[] tokens = line.split(",");

            //The last tokens are always years followed by interest and the loan amount
            int years = Integer.parseInt(tokens[tokens.length-1]);
            float interest = Float.parseFloat(tokens[tokens.length-2]) / 100.f; //convert from percent to decimal
            double totalLoan = Double.parseDouble(tokens[tokens.length-3]);

            //All remaining tokens make up the name
            String name = "";
            for(int i = 0; i < tokens.length-4; i++){
                name += tokens[i];
                //add back the comma
                if(i != 0){
                    name += ",";
                }
            }




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