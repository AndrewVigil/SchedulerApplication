package sample;

import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Node;
import sample.DAO.JDBC;
import java.io.IOException;


/**
 * Main class to startup the Scheduler Application
 * */
public class Main extends Application {

    /**
     * Starts the Scheduler application.
     * @param primaryStage primary stage to start the application
     * */
    @Override
    public void start(Stage primaryStage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/Login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 700, 775));
        primaryStage.show();
    }

    /**
     * Main method to start the application. Makes the connection to database.
     * @param args CommandLine arguments
     * */
    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
    }
}
