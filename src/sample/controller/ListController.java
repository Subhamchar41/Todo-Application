package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import sample.animation.Shaker;
import sample.database.DBHandllar;
import sample.module.Task;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXListView<Task> listTask;

    @FXML
    private JFXTextField titleText;

    @FXML
    private JFXTextField descriptionTextField;

    @FXML
    private JFXButton buttonTextField;

    @FXML
    private ImageView refreashButton;


  private ObservableList<Task> list;
  private ObservableList<Task> refrashlist;
  public DBHandllar handllar;


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {



        handllar=new DBHandllar();
        list =FXCollections.observableArrayList();

        ResultSet set=handllar.getTaskDetais(AppDetails.userId);



        while (set.next()){
            Task task=new Task();
            task.setTaskId(set.getInt("taskid"));
            task.setTaskId(set.getInt("taskid"));
            task.setTask(set.getString("task"));
            task.setDatecreated(set.getTimestamp("datecreated"));
            task.setDescription(set.getString("description"));
            list.add(task);
        }

        listTask.setItems(list);
        listTask.setCellFactory(CellController ->new CellController());


        buttonTextField.setOnAction(actionEvent -> {
            try {
                addFromButton();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


        refreashButton.setOnMouseClicked(mouseEvent -> {
            refreshList();
        });

    }

    private void addFromButton() throws SQLException, ClassNotFoundException {
        if(!titleText.getText().trim().equals("") && !descriptionTextField.getText().trim().equals("")){

            Timestamp timestamp=new Timestamp(Calendar.getInstance().getTimeInMillis());

            Task newTask=new Task();
            newTask.setId(AppDetails.userId);
            newTask.setTask(titleText.getText().trim());
            newTask.setDatecreated(timestamp);
            newTask.setDescription(descriptionTextField.getText().trim());
            handllar.addTask(newTask);

            titleText.setText("");
            descriptionTextField.setText("");

            initialize();
        }
        else {

            Shaker title=new Shaker(titleText);
            titleText.setStyle("-fx-background-color: #ff8080");
            title.shake();

            Shaker description =new Shaker(descriptionTextField);
            descriptionTextField.setStyle("-fx-background-color: #ff8080");
            description.shake();
        }
    }

    public void refreshList(){
        refrashlist=FXCollections.observableArrayList();
        handllar=new DBHandllar();

        try {
            ResultSet resultSet=handllar.getTaskDetais(AppDetails.userId);

            while (resultSet.next()){
                Task addTask=new Task();
                addTask.setTaskId(resultSet.getInt("taskid"));
                addTask.setTaskId(resultSet.getInt("taskid"));
                addTask.setTask(resultSet.getString("task"));
                addTask.setDatecreated(resultSet.getTimestamp("datecreated"));
                addTask.setDescription(resultSet.getString("description"));
                refrashlist.add(addTask);
            }

            listTask.setItems(refrashlist);

            listTask.setCellFactory(CellController-> new CellController());


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}


