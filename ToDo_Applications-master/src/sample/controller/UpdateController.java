package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.animation.Shaker;
import sample.database.DBHandllar;
import sample.module.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

public class UpdateController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField updateTitle;

    @FXML
    private JFXTextField updateDescription;

    @FXML
    private JFXButton updateButton;
    public DBHandllar handllar;
    private  Task task=new Task();




    @FXML
    void initialize(int id,String title,String description) throws SQLException, ClassNotFoundException {
        handllar=new DBHandllar();

        updateTitle.setText(title);
        updateDescription.setText(description);

        updateButton.setOnAction(actionEvent -> {

            try {
                updateDetails(id);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }






    private void updateDetails(int idd) throws SQLException, ClassNotFoundException {

        if(!updateTitle.getText().trim().equals("") && !updateDescription.getText().trim().equals("")){

            Timestamp timestamp=new Timestamp(Calendar.getInstance().getTimeInMillis());


            task.setTask(updateTitle.getText().trim());
            task.setDescription(updateDescription.getText().trim());
            task.setTaskId(idd);
            task.setDatecreated(timestamp);
            handllar.updateDataBase(task,AppDetails.userId);
            updateButton.getScene().getWindow().hide();


        }

        else {
            Shaker title=new Shaker(updateTitle);
            updateTitle.setStyle("-fx-background-color: #ff8080");
            title.shake();

            Shaker description =new Shaker(updateDescription);
            updateDescription.setStyle("-fx-background-color: #ff8080");
            description.shake();

        }


    }
}
