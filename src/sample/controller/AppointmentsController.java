package sample.controller;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DAO.JDBC;
import sample.DAO.appointmentAccess;
import sample.DAO.contactAccess;
import sample.model.Appointments;
import sample.model.Contacts;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Appointments Controller - The dashboard for viewing, adding and managing appointments in the application
 * */
public class AppointmentsController implements Initializable {

    @FXML TableView<Appointments> appointmentsTableView;
    @FXML TableColumn<?,?> appointmentIdCol;
    @FXML TableColumn<?,?> titleCol;
    @FXML TableColumn<?,?> descriptionCol;
    @FXML TableColumn<?,?> locationCol;
    @FXML TableColumn<?,?> typeCol;
    @FXML TableColumn<?,?> dateCol;
    @FXML TableColumn<?,?> startTimeCol;
    @FXML TableColumn<?,?> endTimeCol;
    @FXML TableColumn<?,?> customerIdCol;
    @FXML TableColumn<?,?> contactCol;
    @FXML TableColumn<?,?> userIdCol;

    @FXML TextField modApptIdTxt;
    @FXML TextField modTitleTxt;
    @FXML TextField modDescriptionTxt;
    @FXML TextField modLocationTxt;
    @FXML TextField modTypeTxt;
    @FXML DatePicker modStartDate;
    @FXML ComboBox<String> modStartTimeCombo;
    @FXML ComboBox<String> modEndTimeCombo;
    @FXML TextField modCustomerIdTxt;
    @FXML ComboBox<Contacts> modContactCombo;
    @FXML TextField modUserIdTxt;

    @FXML TextField apptIdTxt;
    @FXML TextField titleTxt;
    @FXML TextField descriptionTxt;
    @FXML TextField locationTxt;
    @FXML TextField typeTxt;
    @FXML DatePicker startDate;
    @FXML ComboBox<String> startTimeCombo;
    @FXML ComboBox<String> endTimeCombo;
    @FXML TextField customerIdTxt;
    @FXML ComboBox<Contacts> contactCombo;
    @FXML TextField userIdTxt;

    @FXML RadioButton monthRadio;
    @FXML RadioButton weekRadio;
    @FXML RadioButton allRadio;


    private static Appointments appointmentToModify;

    /**
     * Initializes and populated the appointments table. Loads combo boxes.
     * @param resourceBundle
     * @param url
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        try {
            Connection connection = JDBC.getConnection();

            ObservableList<Appointments> allAppointments= appointmentAccess.getAllAppointments(connection);

            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

            appointmentsTableView.setItems(allAppointments);

            ObservableList<Contacts> allContacts = contactAccess.getAllContacts();
            contactCombo.setItems(allContacts);
            modContactCombo.setItems((allContacts));


            ObservableList<String> appointmentTimes = FXCollections.observableArrayList();

            LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
            LocalDateTime firstDT = LocalDateTime.of(LocalDate.now(), firstAppointment);
            ZonedDateTime firstEst = firstDT.atZone(ZoneId.of("America/New_York"));
            ZonedDateTime firstLocal = firstEst.withZoneSameInstant(ZoneId.systemDefault());
            firstAppointment = firstLocal.toLocalTime();

            LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);
            LocalDateTime lastDT = LocalDateTime.of(LocalDate.now(), lastAppointment);
            ZonedDateTime lastEst = lastDT.atZone(ZoneId.of("America/New_York"));
            ZonedDateTime lastLocal = lastEst.withZoneSameInstant(ZoneId.systemDefault());
            lastAppointment = lastLocal.toLocalTime();


            if (!firstAppointment.equals(0) || !lastAppointment.equals(0)){
                while(firstAppointment.isBefore(lastAppointment)){
                    appointmentTimes.add(String.valueOf(firstAppointment));
                    firstAppointment = firstAppointment.plusMinutes(15);
                }
            }

            modStartTimeCombo.setItems(appointmentTimes);
            modEndTimeCombo.setItems(appointmentTimes);
            startTimeCombo.setItems(appointmentTimes);
            endTimeCombo.setItems(appointmentTimes);

            int uniqueID = allAppointments.size();
            apptIdTxt.setText(String.valueOf(++uniqueID));

            System.out.println(ZonedDateTime.now());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Upon selecting "back to main" button, loads main screen.
     * @param actionEvent clicking backToMain button
     * */
    public void backToMainButton(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Upon selecting "modify appointment button, checks if appointment was selected and then takes gets information
     * about selected appointment and populates text fields and combo boxes for user modification/update.
     *
     * @param actionEvent "modify appointment" button clicked
     * @throws IOException
     * */
    public void modifyAppointmentButton(ActionEvent actionEvent) throws IOException, SQLException {
        appointmentToModify = appointmentsTableView.getSelectionModel().getSelectedItem();



        if(appointmentToModify == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Appointment Selected");
            alert.setTitle("Error");
            alert.showAndWait();
        }
        else{
          modApptIdTxt.setText(String.valueOf(appointmentToModify.getAppointmentID()));
          modTitleTxt.setText(String.valueOf(appointmentToModify.getTitle()));
          modDescriptionTxt.setText(String.valueOf(appointmentToModify.getDescription()));
          modTypeTxt.setText(String.valueOf(appointmentToModify.getType()));
          modLocationTxt.setText(String.valueOf(appointmentToModify.getLocation()));
          modStartDate.setValue(appointmentToModify.getStart().toLocalDate());
          modStartTimeCombo.setValue(String.valueOf(appointmentToModify.getStart().toLocalTime()));
          modEndTimeCombo.setValue(String.valueOf(appointmentToModify.getEnd().toLocalTime()));

          Contacts modifyContact = contactAccess.getContactByName(appointmentToModify.getContactName());
          modContactCombo.setValue(modifyContact);
//          modContactCombo.setValue(String.valueOf(appointmentToModify.getContactName()));
          modCustomerIdTxt.setText(String.valueOf(appointmentToModify.getCustomerID()));
          modUserIdTxt.setText(String.valueOf(appointmentToModify.getUserID()));
        }
    }

    /**
     * Save button for the section where user add a new appointment. Does time and date conversions.
     * Checks for time exceptions and appointment overlaps. Takes in text fields and combo boxes
     * and saves to database.
     *
     * @param actionEvent save button click
     * @throws IOException
     * @throws SQLException
     * */
    public void addSaveButton(ActionEvent actionEvent) throws IOException, SQLException{
        String appDate = startDate.getValue().toString();
        String appStartTime = startTimeCombo.getValue().toString();
        String appEndTime = endTimeCombo.getValue().toString();

        DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime appStartDT = LocalDateTime.parse(appDate + " " + appStartTime, newFormat);
        LocalDateTime appEndDT = LocalDateTime.parse(appDate + " " + appEndTime, newFormat);

        Timestamp startTS = Timestamp.valueOf(appStartDT);
        Timestamp endTS = Timestamp.valueOf(appEndDT);

        String title = titleTxt.getText();
        String descrip = descriptionTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();
        String custID = customerIdTxt.getText();
        String userID = userIdTxt.getText();
        Object start = startTimeCombo.getValue();
        Object end = endTimeCombo.getValue();
        Object d = startDate.getValue();

        if (start == null || end == null || d == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please verify all fields");
            alert.setTitle("Empty Field Error");
            alert.showAndWait();
            return;
        }

        if (title.equals("") || descrip.equals("") || location.equals("") || type.equals("") ||
        custID.equals("") || userID.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please verify all fields");
            alert.setTitle("Empty Field Error");
            alert.showAndWait();
            return;
        }

//        if(appDate.equals("") || appStartDT.equals(null) || appEndDT.equals(null)){
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Please verify date and times");
//            alert.setTitle("Empty Field Error");
//            alert.showAndWait();
//            return;
//        }

        if(appEndDT.isBefore(appStartDT)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "End Time is Before Start Time");
            alert.setTitle("Time Error");
            alert.showAndWait();
            return;
        }

        if(appEndDT.isEqual(appStartDT)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Start and End Time Are Equal");
            alert.setTitle("Time Error");
            alert.showAndWait();
            return;
        }




        int customerID = Integer.parseInt(customerIdTxt.getText());
        //Check for overlapping times
        Connection connection = JDBC.getConnection();
        ObservableList<Appointments> getAllAppointments = appointmentAccess.getAllAppointments(connection);
        for(Appointments appointment : getAllAppointments){
            LocalDateTime checkStart = appointment.getStart();
            LocalDateTime checkEnd = appointment.getEnd();

            if (appointment.getCustomerID() == customerID){
                if (checkStart.isBefore(appStartDT)  && checkEnd.isAfter(appStartDT)){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Overlap With An Existing Appointment");
                    alert.setTitle("Error");
                    alert.showAndWait();
                    return;
                }
                if (checkStart.isEqual(appStartDT)){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Overlap With An Existing Appointment");
                    alert.setTitle("Error");
                    alert.showAndWait();
                    return;
                }
                if (checkStart.isAfter(appStartDT) && checkStart.isBefore(appEndDT)){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Overlap With An Existing Appointment");
                    alert.setTitle("Error");
                    alert.showAndWait();
                    return;
                }
            }
        }

        String sql = "INSERT INTO appointments SET Appointment_ID=?,Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, User_ID=?, Contact_ID=?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, apptIdTxt.getText());
        ps.setString(2, titleTxt.getText());
        ps.setString(3, descriptionTxt.getText());
        ps.setString(4, locationTxt.getText());
        ps.setString(5, typeTxt.getText());
        ps.setTimestamp(6, startTS);
        ps.setTimestamp(7, endTS);
        ps.setInt(8, Integer.parseInt(customerIdTxt.getText()));
        ps.setInt(9, Integer.parseInt(userIdTxt.getText()));
        ps.setInt(10, contactCombo.getSelectionModel().getSelectedItem().getContactID());
        ps.executeUpdate();

        ObservableList<Appointments> refreshList = appointmentAccess.getAllAppointments(ps.getConnection());
        appointmentsTableView.setItems(refreshList);

        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/Appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 1000);
        stage.setTitle("Appointments Page");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Save button for the section where user modifies an existing appointment. Does time and date conversions.
     * Checks for time exceptions and appointment overlaps. Takes in text fields and combo boxes
     * and saves to database.
     *
     * @param actionEvent save button click
     * @throws IOException
     * @throws SQLException
     * */
    public void modifySaveButton(ActionEvent actionEvent) throws IOException, SQLException {

        try {
            String appDate = modStartDate.getValue().toString();
            String appStartTime = modStartTimeCombo.getValue().toString();
            String appEndTime = modEndTimeCombo.getValue().toString();

            DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime appStartDT = LocalDateTime.parse(appDate + " " + appStartTime, newFormat);
            LocalDateTime appEndDT = LocalDateTime.parse(appDate + " " + appEndTime, newFormat);

            Timestamp startTS = Timestamp.valueOf(appStartDT);
            Timestamp endTS = Timestamp.valueOf(appEndDT);

            String title = modTitleTxt.getText();
            String descrip = modDescriptionTxt.getText();
            String location = modLocationTxt.getText();
            String type = modTypeTxt.getText();
            String custID = modCustomerIdTxt.getText();
            String userID = modUserIdTxt.getText();

            if (title.equals("") || descrip.equals("") || location.equals("") || type.equals("") ||
                    custID.equals("") || userID.equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please verify all fields");
                alert.setTitle("Empty Field Error");
                alert.showAndWait();
                return;
            }

            if(appDate.equals("") || appStartDT.equals(null) || appEndDT.equals(null)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please verify date and times");
                alert.setTitle("Empty Field Error");
                alert.showAndWait();
                return;
            }

            if(appEndDT.isBefore(appStartDT)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "End Time is Before Start Time");
                alert.setTitle("Time Error");
                alert.showAndWait();
                return;
            }

            if(appEndDT.isEqual(appStartDT)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Start and End Time Are Equal");
                alert.setTitle("Time Error");
                alert.showAndWait();
                return;
            }

            int customerID = Integer.parseInt(modCustomerIdTxt.getText());
            //Check for overlapping times
            Connection connection = JDBC.getConnection();
            ObservableList<Appointments> getAllAppointments = appointmentAccess.getAllAppointments(connection);
            for(Appointments appointment : getAllAppointments){
                LocalDateTime checkStart = appointment.getStart();
                LocalDateTime checkEnd = appointment.getEnd();

                if (appointment.getCustomerID() == customerID){
                    if (checkStart.isBefore(appStartDT)  && checkEnd.isAfter(appStartDT)){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Overlap With An Existing Appointment");
                        alert.setTitle("Error");
                        alert.showAndWait();
                        return;
                    }
                    if (checkStart.isEqual(appStartDT)){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Overlap With An Existing Appointment");
                        alert.setTitle("Error");
                        alert.showAndWait();
                        return;
                    }
                    if (checkStart.isAfter(appStartDT) && checkStart.isBefore(appEndDT)){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Overlap With An Existing Appointment");
                        alert.setTitle("Error");
                        alert.showAndWait();
                        return;
                    }
                }
            }

            String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, modTitleTxt.getText());
            ps.setString(2, modDescriptionTxt.getText());
            ps.setString(3, modLocationTxt.getText());
            ps.setString(4, modTypeTxt.getText());
            ps.setTimestamp(5, startTS);
            ps.setTimestamp(6, endTS);
            ps.setInt(7, Integer.parseInt(modCustomerIdTxt.getText()));
            ps.setInt(8, Integer.parseInt(modUserIdTxt.getText()));
            ps.setInt(9, modContactCombo.getSelectionModel().getSelectedItem().getContactID());
            ps.setString(10, modApptIdTxt.getText());
            System.out.println(ps.toString());
            ps.executeUpdate();

            ObservableList<Appointments> refreshList = appointmentAccess.getAllAppointments(ps.getConnection());
            appointmentsTableView.setItems(refreshList);

            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/Appointments.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 1000);
            stage.setTitle("Appointments Page");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Radio button to show all appointments in table
     *
     * @param actionEvent "All" radio is selected
     * @throws SQLException
     * */
    public void allRadioSelected(ActionEvent actionEvent) throws SQLException{
        Connection connection = JDBC.getConnection();

        ObservableList<Appointments> allAppointments= appointmentAccess.getAllAppointments(connection);

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        appointmentsTableView.setItems(allAppointments);
    }

    /**
     * Radio button to show all appointments for current month in table.
     *
     * @param actionEvent "month" radio button is selected
     * @throws SQLException
     * */
    public void monthRadioSelected(ActionEvent actionEvent) throws SQLException{
        try {

            Connection connection = JDBC.getConnection();
            ObservableList<Appointments> allAppointments = appointmentAccess.getAllAppointments(connection);
            ObservableList<Appointments> appointmentsMonth = FXCollections.observableArrayList();

            LocalDateTime currentMonthStart = LocalDateTime.now().minusMonths(1);
            LocalDateTime currentMonthEnd = LocalDateTime.now().plusMonths(1);

            //First Lambda for populating the appointments observable list with appointments during the current month
            if (allAppointments != null)
                allAppointments.forEach(appointment -> {
                    if (appointment.getEnd().isAfter(currentMonthStart) && appointment.getEnd().isBefore(currentMonthEnd)){
                        appointmentsMonth.add(appointment);
                    }
                    appointmentsTableView.setItems(appointmentsMonth);
                });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Radio button to show appointments for the current week in table.
     *
     * @param actionEvent "week' radio button is selected
     * @throws SQLException
     * */
    public void weekRadioSelected(ActionEvent actionEvent) throws SQLException{
        try {

            Connection connection = JDBC.getConnection();
            ObservableList<Appointments> allAppointments= appointmentAccess.getAllAppointments(connection);
            ObservableList<Appointments> appointmentsWeek = FXCollections.observableArrayList();

            LocalDateTime currentWeekStart = LocalDateTime.now().minusWeeks(1);
            LocalDateTime currentWeekEnd = LocalDateTime.now().plusWeeks(1);

            //First Lambda for populating the appointments observable list with appointments during the current week

            if (allAppointments != null)
                allAppointments.forEach(appointment -> {
                    if (appointment.getEnd().isAfter(currentWeekStart) && appointment.getEnd().isBefore(currentWeekEnd)){
                        appointmentsWeek.add(appointment);
                    }
                    appointmentsTableView.setItems(appointmentsWeek);
                });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Button to delete selected appointment. Checks if item is selected, prompts confirmation and then
     * deletes from database.
     *
     * @param actionEvent delete appointment button
     * @throws IOException
     * @throws SQLException
     * */
    public void deleteAppointmentButton(ActionEvent actionEvent) throws IOException, SQLException {
        appointmentToModify = appointmentsTableView.getSelectionModel().getSelectedItem();

        if (appointmentToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment Not Selected");
            alert.setTitle("Error");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?");
            alert.setTitle("Confirm Deletion");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String sql = "DELETE FROM appointments WHERE Appointment_ID=?";
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                int deleteAppointmentID = appointmentsTableView.getSelectionModel().getSelectedItem().getAppointmentID();
                ps.setInt(1, deleteAppointmentID);
                ps.executeUpdate();

                ObservableList<Appointments> refreshList = appointmentAccess.getAllAppointments(ps.getConnection());
                appointmentsTableView.setItems(refreshList);
            }
        }
    }
}
