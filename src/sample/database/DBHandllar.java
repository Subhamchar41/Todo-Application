package sample.database;


import sample.animation.Shaker;
import sample.module.Task;
import sample.module.User;

import java.sql.*;

public class DBHandllar extends Config{


   private Connection connection;
   private PreparedStatement preparedStatement;

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        connection= DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbScema,dbname,dbPass);

        return connection;

    }

    public int getCount(int userID) throws SQLException, ClassNotFoundException {
        ResultSet set=null;

        preparedStatement=getConnection().prepareStatement("SELECT COUNT(*) FROM todo.tasks where userid=?");
        preparedStatement.setInt(1,userID);
        set=preparedStatement.executeQuery();

        while (set.next()){
            return set.getInt(1);
        }
        return set.getInt(1);
    }




    public int signUpDataBase(User user) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;


      preparedStatement=getConnection().prepareStatement("SELECT * FROM "+Const.USER_TABLE+" WHERE "+Const.USER_USERNAME+"=?");
      preparedStatement.setString(1,user.getUserName());
      resultSet=preparedStatement.executeQuery();

        int ce=0;
        while (resultSet.next()){
            ce++;
        }

        if(ce>0)
        {
            ce++;
            return ce;
        }

        else {
            ce=0;
            String sql = "INSERT INTO " + Const.USER_TABLE + " ( " + Const.USER_FIRSTNAME + "," + Const.USER_LASTNAME + "," + Const.USER_USERNAME + "," + Const.USER_PASSWORD + "," + Const.USER_LOCATION + "," + Const.USER_GENDER + ")" + " VALUES " + "(?,?,?,?,?,?)";
            preparedStatement = getConnection().prepareStatement(sql);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getLocation());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.executeUpdate();
            return ce;

        }
    }

    public ResultSet getUSer(User user) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;


        preparedStatement=getConnection().prepareStatement("SELECT * FROM "+Const.USER_TABLE+" WHERE "+Const.USER_USERNAME+"=?"+" AND "+Const.USER_PASSWORD+"=?");

        preparedStatement.setString(1,user.getUserName());
        preparedStatement.setString(2,user.getPassword());


        resultSet=preparedStatement.executeQuery();

        return resultSet;

    }



    public void addTask(Task taskk) throws SQLException, ClassNotFoundException {

        try {
            preparedStatement=getConnection().prepareStatement("INSERT INTO tasks ( userid , task, datecreated, description) VALUES (?,?,?,?)");
            preparedStatement.setInt(1,taskk.getUserID());
            preparedStatement.setString(2,taskk.getTask());
            preparedStatement.setTimestamp(3,taskk.getDatecreated());
            preparedStatement.setString(4,taskk.getDescription());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        }catch (Exception e)
        {
            System.out.println(" Messege "+e.getMessage());
        }
    }

    public ResultSet getTaskDetais(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM todo.tasks where userid=?");
        preparedStatement.setInt(1,id);

        resultSet=preparedStatement.executeQuery();

        return resultSet;
    }

    public void deleteTask(int userID,int taskID) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("DELETE FROM tasks WHERE userid=? AND taskid=?");
        preparedStatement.setInt(1,userID);
        preparedStatement.setInt(2,taskID);
        preparedStatement.execute();
        preparedStatement.close();
    }


    public void updateDataBase(Task task,int id) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("UPDATE tasks SET task=?,description=?,datecreated=? Where userid=? AND taskid=?");
        preparedStatement.setString(1,task.getTask());
        preparedStatement.setString(2,task.getDescription());
        preparedStatement.setTimestamp(3,task.getDatecreated());
        preparedStatement.setInt(4,id);
        preparedStatement.setInt(5,task.getTaskId());
        preparedStatement.execute();
        preparedStatement.close();

    }
}
