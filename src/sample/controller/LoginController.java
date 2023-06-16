package sample.controller;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.DAO.JDBC;
import sample.DAO.appointmentAccess;
import sample.DAO.userAccess;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import sample.model.Appointments;
import sample.utilities.Logger;

import java.time.ZoneId;


/**
 * Class for controlling the login page where user signs in.
 * */
public class LoginController implements Initializable {

    @FXML TextField userNameTxt;
    @FXML TextField passwordTxt;
    @FXML Label locationLabel;
    @FXML Label userLoginLabel;
    @FXML Label usernameLabel;
    @FXML Label passwordLabel;
    @FXML Label userLocationLabel;
    @FXML Button loginButton;

    private String errorTitle;
    private String errorHeader;
    private String errorMessage;

    /**
     * Button for attempting login. Checks login credentials and records if attempt was successful or not.
     * Loads main screen upon login.
     *
     * @param actionEvent Login Button pushed
     * @throws IOException
     * @throws SQLException
     * */
    public void toMainScreenButton(ActionEvent actionEvent)throws IOException, SQLException {
        String userNameInput = userNameTxt.getText();
        String passwordInput = passwordTxt.getText();
        int userId = userAccess.validateUser(userNameInput, passwordInput);

        boolean notification = false;

        if(userId >0 ) {
            Logger.log(userNameTxt.getText(), true);

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime now15 = now.plusMinutes(15);
            Connection connection = JDBC.getConnection();
            ObservableList<Appointments> getAllAppointments = appointmentAccess.getAllAppointments(connection);
            for (Appointments appointment : getAllAppointments) {
                LocalDateTime checkAppointment = appointment.getStart();
                if (appointment.userID == userId) {
                    if (checkAppointment.isAfter(now) && checkAppointment.isBefore(now15)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have an upcoming appointment: " + "\n" +
                                "Appointment ID: " + appointment.getAppointmentID() + "\n" + "Date and Time: " + appointment.getStart());
                        alert.setTitle("Upcoming Appointment");
                        notification = true;
                        alert.showAndWait();
                        break;
                    }
                }
            }

        }


        else {
            Logger.log(userNameTxt.getText(), false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorTitle);
            alert.setHeaderText(errorHeader);
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
        if(!notification){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No Upcoming Appointments");
            alert.setTitle("");
            alert.showAndWait();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 700);
        stage.setTitle("Main Menu Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes page.
     *
     * @param url
     * @param resources
     * */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        locationLabel.setText(ZoneId.systemDefault().toString());
        Locale userLocale = Locale.getDefault();
        resources = ResourceBundle.getBundle("sample/utilities/Login", userLocale);
        usernameLabel.setText(resources.getString("usernameLabel"));
        passwordLabel.setText(resources.getString("passwordLabel"));
        userLocationLabel.setText(resources.getString("userLocationLabel"));
        loginButton.setText(resources.getString("loginButton"));
        userLoginLabel.setText(resources.getString("userLoginLabel"));
        errorTitle = resources.getString("errorTitle");
        errorHeader = resources.getString("errorHeader");
        errorMessage = resources.getString("errorMessage");
    }
}

