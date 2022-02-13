package fi.crosskey.mortage_plan;

public class StringToProspectConverter {

    public Prospect stringToProspect(String line){

        final String quotedNameRegex = "(\"[^\"]+\")"; //Anything except quotes between quotes
        //OR
        final String unquotedNameRegex = "([^,]+)"; //Anything except commas

        final String totalLoanRegex = "(\\d+\\.?\\d*)"; //One or more numbers followed by zero or one dots followed by any number of numbers
        final String interestRegex = "(\\d\\d?\\.?\\d*)"; //One or two numbers followed by zero or one dots followed by any number of numbers
        final String yearsRegex = "(\\d+)"; //One or more numbers

        String completedRegex = "(" + quotedNameRegex + "|" + unquotedNameRegex  + ")" + ","
                + totalLoanRegex + "," + interestRegex + "," + yearsRegex;

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
            for(int i = 0; i <= tokens.length-4; i++){
                name += tokens[i];
                //add back the comma
                if(i != tokens.length-4){
                    name += ",";
                }
            }
            //Remove quotes from the name
            name = name.replace("\"","");

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
