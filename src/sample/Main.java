package sample;

/**
 * The DataBase program implements an application that
 * allows a user to make changes on a database of the
 * dental office. In this version it only allows making
 * changes to tables with Patients and Dentists.
 * It also shows some of the basic statistics from database.
 * Project made for DataBase classes at WUT EiTI Faculty.
 *
 * @author  Jakub Tokarzewski
 * @version 1.0
 * @since   2018-04-16
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    static Connection connection;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Baza Danych - Jakub Tokarzewski");
        primaryStage.setScene(new Scene(root, 970, 600));//golden proportion (a+b)/a = a/b
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            connection = DBUtil.getConnection();
        }
        catch(SQLException e){
            DBUtil.showError(e);
        }
        launch(args);
    }
}
