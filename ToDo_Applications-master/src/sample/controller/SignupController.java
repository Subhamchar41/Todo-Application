package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.animation.Shaker;
import sample.database.DBHandllar;
import sample.module.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignupController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField signupFirstName;

    @FXML
    private JFXPasswordField signupPassword;

    @FXML
    private JFXTextField signupLastName;

    @FXML
    private JFXTextField signupUserName;

    @FXML
    private JFXButton signupButton;

    @FXML
    private JFXTextField signupLocation;

    @FXML
    private CheckBox signupMaleCheckBox;

    @FXML
    private CheckBox signupFemaleCheckBox;

    @FXML
    private AnchorPane rootAnchor;

    private DBHandllar handllar=new DBHandllar();

    @FXML
    void initialize() {


       signupButton.setOnAction(actionEvent -> {
           try {
               createUser();
               getRoot();
           } catch (SQLException e) {
               e.printStackTrace();
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           }
       });
    }


        private void getRoot() {
            try {
                AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/sample/view/loginsample.fxml"));
                getAnimation(anchorPane);

                rootAnchor.getChildren().setAll(anchorPane);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private void getAnimation(AnchorPane anchorPanee) {
        FadeTransition fadeTransition=new FadeTransition(Duration.millis(1000),anchorPanee);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
        fadeTransition.play();
    }


    private void createUser() throws SQLException, ClassNotFoundException {
        String gender="";

        if(signupFemaleCheckBox.isSelected()){
           gender="Female";
           signupMaleCheckBox.setSelected(false);
        }
        else {
            gender="Male";
            signupFemaleCheckBox.setSelected(false);
        }

        User user=new User(signupFirstName.getText().trim(),signupLastName.getText().trim(),signupUserName.getText().trim(),signupPassword.getText().trim(),signupLocation.getText().trim(),gender);

        if(handllar.signUpDataBase(user)==0)
        {
            handllar.signUpDataBase(user);
        }

        else{
            Shaker shaker=new Shaker(signupUserName);
            shaker.shake();
            signupUserName.setStyle("-fx-background-color: #ff8080");
        }

    }


}
