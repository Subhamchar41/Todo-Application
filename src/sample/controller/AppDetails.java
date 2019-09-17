package sample.controller;

import javafx.animation.FadeTransition;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import sample.animation.Shaker;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class AppDetails {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label appdetailsLabel;

    @FXML
    private ImageView addButton;

    @FXML
    private AnchorPane anchorView;
    public static int userId;



    @FXML
    void initialize() {

        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED,mouseEvent -> {
            System.out.println("Clicked");
            Shaker shaker=new Shaker(addButton);
            shaker.shake();

            addButton.relocate(0,20);
            appdetailsLabel.relocate(0,20);
            FadeTransition lableTransition=new FadeTransition(Duration.millis(1000),appdetailsLabel);
            FadeTransition buttonTransition=new FadeTransition(Duration.millis(1000),addButton);

            getTransition(lableTransition);getTransition(buttonTransition);

            getView();


        });

    }

    private void getTransition(FadeTransition fadeTransition) {

        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setAutoReverse(false);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }

    private void getView() {
        try {
            AnchorPane rootPane= FXMLLoader.load(getClass().getResource("/sample/view/appdetailform.fxml"));



            AppDetails.userId=getUserIdd();

            FadeTransition screenTransition=new FadeTransition(Duration.millis(1000),rootPane);
            screenTransition.setFromValue(0);
            screenTransition.setToValue(1);
            screenTransition.setCycleCount(1);
            screenTransition.setAutoReverse(false);
            screenTransition.play();
            anchorView.getChildren().setAll(rootPane);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    public int getUserIdd() {
        return this.userId;
    }

    public void setUserIdd(int userIdd) {
        this.userId = userIdd;
    }

}
