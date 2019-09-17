package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.animation.Shaker;
import sample.database.Const;
import sample.database.DBHandllar;
import sample.module.Task;
import sample.module.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton loginRegister;

    @FXML
    private JFXTextField loginUsername;

    @FXML
    private JFXPasswordField loginPassword;

    private int userIdd;



    private User user=new User();

    @FXML
    void initialize() {


        loginRegister.setOnAction(actionEvent -> {
            loginButton.getScene().getWindow().hide();
            signUp(); // signup button
        });


        loginButton.setOnAction(actionEvent -> {


            if(!loginUsername.getText().equals("") && !loginPassword.getText().equals(null)){
                user.setUserName(loginUsername.getText().trim());
                user.setPassword(loginPassword.getText().trim());
                try {
                    login(user);

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Errror");
            }
        });

    }



    private void signUp() {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/signup.fxml"));
        try {
            loader.load(); }
        catch (IOException e) {
            e.printStackTrace(); }

        Parent root=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        stage.setResizable(false);
    }



    private void login(User userr) throws SQLException, ClassNotFoundException {

        DBHandllar handllar=new DBHandllar();
        ResultSet resultSet=handllar.getUSer(userr);

        int c=0;

        while (resultSet.next()){
            userIdd=resultSet.getInt("userid");
                c++;
        }

         if(c==1){

             loginButton.getScene().getWindow().hide();
             System.out.println("Login success "+loginUsername.getText());
             loginPassword.setStyle(null);
             loginUsername.setStyle(null);
             showAddItem();


        }
        else{
            System.out.println("Invalid User Name or Password");
            Shaker passShaker=new Shaker(loginPassword);
            passShaker.shake();
            loginPassword.setStyle("-fx-background-color: #ff8080");
            Shaker usenameShaker=new Shaker(loginUsername);
            usenameShaker.shake();
            loginUsername.setStyle("-fx-background-color: #ff8080");


        }

    }

    private void showAddItem()  {

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/appdetails.fxml"));
        try {
            loader.load();
        } catch (IOException e) {

        }

        Parent root=loader.getRoot();
        Stage stage=new Stage();



       AppDetails details=loader.getController();
       details.setUserIdd(userIdd);


        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    }
}
