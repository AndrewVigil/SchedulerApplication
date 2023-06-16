package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import sample.model.Appointments;

import javax.swing.*;

/**
 * Simple main screen with buttons for navigation to Appointments, Reports, or Customers pages.
 * */
public class MainScreenController implements Initializable {

    /**
     * Takes user to appointments page.
     *
     * @param actionEvent Appointments button clicked
     * @throws IOException
     * */
    public void appointmentButtonClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/Appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 1000);
        stage.setTitle("Appointments Page");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Takes user to the customers page.
     *
     * @param actionEvent Customer button clicked
     * @throws IOException
     * */
    public void customerButtonClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/Customer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("Customers Page");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Takes user to the Reports page.
     *
     * @param actionEvent Reports button clicked
     * @throws IOException
     * */
    public void reportButtonClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/Reports.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Reports Page");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Closes application
     *
     * @param actionEvent On exit button clicked
     * @throws IOException
     * */
    public void onExitButtonClick(ActionEvent actionEvent) throws IOException{
        System.exit(0);
    }

    /**
     * Nothing to initialize
     *
     * @param url
     * @param resourceBundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
