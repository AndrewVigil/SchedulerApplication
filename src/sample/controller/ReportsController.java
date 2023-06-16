package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DAO.JDBC;
import sample.DAO.appointmentAccess;
import sample.DAO.contactAccess;
import sample.DAO.customerAccess;
import sample.model.Appointments;
import sample.model.Contacts;
import sample.model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Reports page controller.
 * */
public class ReportsController implements Initializable {

    @FXML ComboBox typeCombo;
    @FXML ComboBox monthCombo;
    @FXML ComboBox<Contacts> contactCombo;
    @FXML TableView<Appointments> reportTableView;
    @FXML TableColumn <?,?> apptIdCol;
    @FXML TableColumn <?,?> titleCol;
    @FXML TableColumn <?,?> typeCol;
    @FXML TableColumn <?,?> descriptionCol;
    @FXML TableColumn <?,?> startCol;
    @FXML TableColumn <?,?> endCol;
    @FXML TableColumn <?,?> customerIdCol;
    @FXML Label numberCustomer;
    @FXML Label typeLabel;
    @FXML Label monthLabel;

    /**
     * Populates table view appointments and combo boxes.
     *
     * @param resourceBundle
     * @param url
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            Connection connection = JDBC.getConnection();

            ObservableList<Customers> allCustomersList = customerAccess.getAllCustomers(connection);

            int customerNumber = allCustomersList.size();
            System.out.println(customerNumber);
            numberCustomer.setText(String.valueOf(customerNumber));

            ObservableList<Contacts> allContacts = contactAccess.getAllContacts();
            contactCombo.setItems(allContacts);

            ObservableList<Appointments> allAppointments = appointmentAccess.getAllAppointments(connection);
            reportTableView.setItems(allAppointments);

            apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            ObservableList<String> allTypes = appointmentAccess.getTypes();
            typeCombo.setItems(allTypes);

            ObservableList<String> months = FXCollections.observableArrayList();
            months.add("January");
            months.add("February");
            months.add("March");
            months.add("April");
            months.add("May");
            months.add("June");
            months.add("July");
            months.add("August");
            months.add("September");
            months.add("October");
            months.add("November");
            months.add("December");
            monthCombo.setItems(months);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * For appointment type total report. Obtains all appointments and loops through looking for appointments
     * with the user-selected type. Counts total and displays.
     *
     * @param actionEvent Selecting type in combo box
     * @throws IOException
     * @throws SQLException
     * */
    public void onSelectType(ActionEvent actionEvent) throws IOException, SQLException{

        String type = typeCombo.getValue().toString();
        System.out.println(type);
        Connection connection = JDBC.getConnection();
        ObservableList<Appointments> allAppointments = appointmentAccess.getAllAppointments(connection);
        int typeCount = 0;
        FilteredList<Appointments> matchType = allAppointments.filtered(t ->{
            if(t.getType().equals(type)){
                    System.out.println("Test");
                    return true;
            } return false;
        });
        typeCount = matchType.size();
        System.out.println(typeCount);
        typeLabel.setText(String.valueOf(typeCount));
    }

    /**
     * For appointment month total report. Obtains all appointments and loops through looking for appointments
     * with the user-selected month. Counts total and displays.
     *
     * @param actionEvent Selecting month in combo box
     * @throws IOException
     * @throws SQLException
     * */
    int monthsCount = 0;
    public void onSelectMonth(ActionEvent actionEvent) throws IOException, SQLException{

        String month = monthCombo.getValue().toString();
        Connection connection = JDBC.getConnection();
        ObservableList<Appointments> allAppointments = appointmentAccess.getAllAppointments(connection);
        ObservableList<Appointments> appointmentsFiltered = allAppointments.filtered(t ->{
            if(t.getStart().getMonth().name().equalsIgnoreCase(month)){
                monthsCount++;
                System.out.println("Test");
                return true;
            } return false;
        });
        System.out.println(monthsCount);
        monthLabel.setText(String.valueOf(monthsCount));
    }

    private String contactName;
    /**
     * Takes a selected contact and populates the table view with appointments for that contact.
     *
     * @param actionEvent on selection of contact in combo box
     * @throws IOException
     * @throws SQLException
     * */
    public void onSelectContact(ActionEvent actionEvent) throws IOException, SQLException{

        Connection connection = JDBC.getConnection();
        ObservableList<Appointments> allAppointments = appointmentAccess.getAllAppointments(connection);
        ObservableList<Appointments> allContactAppointments = FXCollections.observableArrayList();

        contactName = contactCombo.getValue().toString();
            for (Appointments c: allAppointments){
                if(contactName.equals(c.getContactName())){
                    allContactAppointments.add(c);
                }
            }
            reportTableView.setItems(allContactAppointments);

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }


    /**
     * Takes user back to main menu screen.
     *
     * @param actionEvent  main menu button click
     * @throws IOException
     * */
    public void toMainMenu(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

}

