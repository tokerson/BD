package sample;

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
        primaryStage.setTitle("Hello World");
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
