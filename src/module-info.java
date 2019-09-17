module ToDo.Applications {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;

    opens sample;
    opens sample.controller;
}