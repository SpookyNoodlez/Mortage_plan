package fi.crosskey.mortage_plan;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoadFileController {
    @FXML
    private Label resultDisplay;

    @FXML
    protected void loadFile() {
        LoanCalculator calculator = new LoanCalculator();



        resultDisplay.setText("Prospect 1: CustomerName wants to borrow X € for a period of Z years and pay E € each month");
    }
}