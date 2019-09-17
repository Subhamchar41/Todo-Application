package sample.controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DBHandllar;
import sample.module.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CellController extends JFXListCell<Task> {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView deleteButton;

    @FXML
    private Label timeDetect;

    private FXMLLoader loader;

    @FXML
    private ImageView updateDetails;

    @FXML
    void initialize() {


    }

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if(empty || task==null){
            setText(null);
            setGraphic(null);
        }

        else {
            if(loader==null){
                loader=new FXMLLoader(getClass().getResource("/sample/view/cell.fxml"));
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            titleLabel.setText(task.getTask());
            descriptionLabel.setText(task.getDescription());
            timeDetect.setText(task.getDatecreated().toString());

            int taskId=task.getTaskId();

            updateDetails.setOnMouseClicked(mouseEvent -> {
                //updateDetails.getScene().getWindow().hide();
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/update.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root=loader.getRoot();
                Stage stage=new Stage();
                UpdateController updateController=loader.getController();
                try {
                    updateController.initialize(taskId,task.getTask(),task.getDescription());

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


                stage.setScene(new Scene(root));
                stage.show();
            });


            deleteButton.setOnMouseClicked(mouseEvent -> {
                getListView().getItems().remove(getItem());

                DBHandllar handllar=new DBHandllar();
                try {
                    handllar.deleteTask(AppDetails.userId,taskId);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });



            setText(null);
            setGraphic(rootAnchorPane);

        }
    }


}
