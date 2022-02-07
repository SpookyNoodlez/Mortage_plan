module fi.crosskey.mortage_plan {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens fi.crosskey.mortage_plan to javafx.fxml;
    exports fi.crosskey.mortage_plan;
}