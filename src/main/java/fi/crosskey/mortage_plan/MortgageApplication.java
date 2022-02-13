package fi.crosskey.mortage_plan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MortgageApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MortgageApplication.class.getResource("loadFileView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Mortgage Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}