package fi.crosskey.mortage_plan;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LoadFileController {
    @FXML
    private Label resultDisplay;

    @FXML
    protected void loadFile() {
        ArrayList<Prospect> prospects = new ArrayList<>();
        StringToProspectConverter converter = new StringToProspectConverter();

        try {
            //Making sure the file is read using UTF_8 charset
            File file = new File("prospects.txt");
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);

            //Get rid of the first descriptive line
            String currentLine = reader.readLine();
            //read lines until reaching end of file
            while((currentLine = reader.readLine()) != null){
                //Split the string up and put it in a Prospect record
                prospects.add(converter.stringToProspect(currentLine));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String displayString = "";
        for(int i = 0;i < prospects.size();i++){
            //Negative values can only be entered by my 'bad data' prospect
            if (prospects.get(i).totalLoan() < 0){
                displayString += "BAD ENTRY! CHECK \"prospects.txt\" AT LINE: " + (i+1) + "\n";
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
}