package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.database.DBHandllar;
import sample.module.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AppDetailsFormController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField appdetailsTitle;

    @FXML
    private JFXTextArea appdetailsDescription;

    @FXML
    private JFXButton submitButton;

    @FXML
    private JFXButton countButton;

    @FXML
    private Label formLabel;

    private static int id;



   private DBHandllar handllar=new DBHandllar();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        id=AppDetails.userId;

        countButton.setOnAction(new actionListner());

        getCoutButton();


        appdetailsTitle.addEventHandler(MouseEvent.MOUSE_PRESSED,mouseEvent -> {
            FadeTransition labelTransition=new FadeTransition(Duration.millis(800),formLabel);
            labelTransition.setFromValue(1);
            labelTransition.setToValue(0);
            labelTransition.setCycleCount(1);
            labelTransition.setAutoReverse(false);
            labelTransition.play();
            formLabel.setVisible(false);
        });

        appdetailsDescription.addEventHandler(MouseEvent.MOUSE_PRESSED,mouseEvent -> {
            FadeTransition labelTransition=new FadeTransition(Duration.millis(800),formLabel);
            labelTransition.setFromValue(1);
            labelTransition.setToValue(0);
            labelTransition.setCycleCount(1);
            labelTransition.setAutoReverse(false);
            labelTransition.play();
            formLabel.setVisible(false);
        });

        java.sql.Timestamp timestamp= new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());

            submitButton.addEventHandler(MouseEvent.MOUSE_CLICKED,mouseEvent -> {
            saveDetails(timestamp);
        });

    }

    private void getCoutButton() throws SQLException, ClassNotFoundException {

        System.out.println("Tasks "+handllar.getCount(id));
        countButton.setText("Tasks ("+handllar.getCount(id)+")");
        FadeTransition buttonTransition=new FadeTransition(Duration.millis(700),countButton);
        buttonTransition.setFromValue(0);
        buttonTransition.setToValue(1);
        buttonTransition.setCycleCount(2);
        buttonTransition.setAutoReverse(false);
        buttonTransition.play();
        countButton.setVisible(true);
    }


    private void saveDetails(Timestamp timestamp) {
        Task task=new Task(AppDetails.userId,appdetailsTitle.getText().trim(),timestamp,appdetailsDescription.getText().trim());
        try {

            System.out.println("User ID "+task.getUserID());
            handllar.addTask(task);

            formLabel.setText(" Successfully Added");
            FadeTransition fadeTransition=new FadeTransition(Duration.millis(1000),formLabel);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(false);
            fadeTransition.play();
            formLabel.setVisible(true);



            clearAll();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void clearAll() {
        appdetailsTitle.setText("");
        appdetailsDescription.setStyle("");
    }

    private class actionListner implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {

            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/list.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root=loader.getRoot();
            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            countButton.getScene().getWindow().hide();
        }
    }
}
