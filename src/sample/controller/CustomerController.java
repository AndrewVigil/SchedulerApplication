package sample.controller;

import sample.DAO.*;
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
import sample.model.Appointments;
import sample.model.Customers;
import sample.model.Country;
import sample.model.firstLevelDivision;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.sql.SQLException;


/***
 * Controller for managing customer management page.
 */

public class CustomerController implements Initializable {

    @FXML TableView<Customers> customerTableView;
    @FXML TableColumn<?,?> customerIdCol;
    @FXML TableColumn<?,?> customerNameCol;
    @FXML TableColumn<?,?> customerAddressCol;
    @FXML TableColumn<?,?> customerPostalCol;
    @FXML TableColumn<?,?> customerPhoneCol;
    @FXML TableColumn<?,?> countryCol;
    @FXML TableColumn<?,?> divisionCol;
    @FXML public ComboBox<Country> countryCombo;
    @FXML public ComboBox<firstLevelDivision> divisionCombo;
    @FXML public ComboBox<Country> modifyCountryCombo;
    @FXML public ComboBox<firstLevelDivision> modifyDivisionCombo;

    @FXML TextField idTxt;
    @FXML TextField nameTxt;
    @FXML TextField addressTxt;
    @FXML TextField postalTxt;
    @FXML TextField phoneTxt;

    @FXML TextField modifyIdTxt;
    @FXML TextField modifyNameTxt;
    @FXML TextField modifyAddressTxt;
    @FXML TextField modifyPostalTxt;
    @FXML TextField modifyPhoneTxt;

    private static Customers customerToModify;
    private Object ListManager;

    public static Customers getCustomerToModify(){return customerToModify;}

    /**
     * Initializes page, populates table view, and populates combo boxes
     *
     * @param resourceBundle
     * @param url
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = JDBC.getConnection();

            ObservableList<Customers> allCustomersList = customerAccess.getAllCustomers(connection);

            ObservableList<firstLevelDivision> allFirstLevelDivisions = firstLevelDivisionAccess.getAllFirstLevelDivisions();

            ObservableList<Country> allCountries = countryAccess.getCountries();

            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
            countryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
            divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
            customerTableView.setItems(allCustomersList);

            countryCombo.setItems(allCountries);
            divisionCombo.setItems(allFirstLevelDivisions);
            modifyCountryCombo.setItems(allCountries);
            modifyDivisionCombo.setItems(allFirstLevelDivisions);

            System.out.println(allCustomersList.size());
            int uniqueID = allCustomersList.size();
            idTxt.setText(String.valueOf(++uniqueID));

        } catch (Exception e){
            e.printStackTrace();
        }

    }



    private int countryID;
    /**
     * for new customer section - populates divisions based on selection of country in country combo box.
     * @param actionEvent selection of country
     * @throws SQLException
     * */
    public void onActionSelectDivision(ActionEvent actionEvent) throws SQLException {
        ObservableList<firstLevelDivision> allFirstLevelDivisions = firstLevelDivisionAccess.getAllFirstLevelDivisions();
        ObservableList<firstLevelDivision> allDivisionsFound = FXCollections.observableArrayList();
        countryID = countryCombo.getValue().getCountryID();
        for (firstLevelDivision d: allFirstLevelDivisions  ){
            if(countryID == d.getCountry_ID()){
                allDivisionsFound.add(d);
            }
        }
        divisionCombo.setItems(allDivisionsFound);
    }

    /**
     * for modify customer section - populates divisions based on selection of country in country combo box.
     * @param actionEvent selection of country
     * @throws SQLException
     * */
    public void modifyOnActionSelectDivision(ActionEvent actionEvent) throws SQLException{
        ObservableList<firstLevelDivision> allFirstLevelDivisions = firstLevelDivisionAccess.getAllFirstLevelDivisions();
        ObservableList<firstLevelDivision> allDivisionsFound = FXCollections.observableArrayList();
        countryID = modifyCountryCombo.getValue().getCountryID();
        for (firstLevelDivision d: allFirstLevelDivisions  ){
            if(countryID == d.getCountry_ID()){
                allDivisionsFound.add(d);
            }
        }
        modifyDivisionCombo.setItems(allDivisionsFound);
    }

    /**
     * For new customer section -- upon clicking save button, extracts customer information in fields,
     * checks for blanks and errors. Saves new customer to database.
     *
     * @param actionEvent new customer save button
     * @throws SQLException
     * @throws IOException
     * */
    public void saveButton(ActionEvent actionEvent) throws IOException, SQLException{
        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postal = postalTxt.getText();
        String phone = phoneTxt.getText();
        Object country = countryCombo.getValue();
        Object div = divisionCombo.getValue();

        if(name.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter customer name");
            alert.setTitle("Empty Field Error");
            alert.showAndWait();
            return;
        }
        else if(address.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter customer address");
            alert.setTitle("Empty Field Error");
            alert.showAndWait();
            return;
        }
        else if(postal.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter customer postal code");
            alert.setTitle("Empty Field Error");
            alert.showAndWait();
        }
        else if(phone.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter customer phone number");
            alert.setTitle("Empty Field Error");
            alert.showAndWait();
            return;
        }
        else if (country == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a country");
            alert.setTitle("Error");
            alert.showAndWait();
            return;
        }
        else if (div == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a division");
            alert.setTitle("Error");
            alert.showAndWait();
            return;
        }
        else{
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID)"+
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, idTxt.getText());
            ps.setString(2, nameTxt.getText());
            ps.setString(3, addressTxt.getText());
            ps.setString(4, postalTxt.getText());
            ps.setString(5, phoneTxt.getText());
            ps.setInt(6, divisionCombo.getSelectionModel().getSelectedItem().getDivisionID());
            ps.executeUpdate();

            ObservableList<Customers> refreshList = customerAccess.getAllCustomers(ps.getConnection());
            customerTableView.setItems(refreshList);

            idTxt.clear();
            nameTxt.clear();
            addressTxt.clear();
            postalTxt.clear();
            phoneTxt.clear();

            Connection connection = JDBC.getConnection();
            ObservableList<Customers> allCustomersList = customerAccess.getAllCustomers(connection);
            int uniqueID = allCustomersList.size();
            idTxt.setText(String.valueOf(++uniqueID));

            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/Customer.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 1000);
            stage.setTitle("Customers Page");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * button for returning to main screen.
     *
     * @param actionEvent Back to main button
     * @throws IOException
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
     * Modify customer button. Checks for selection, extracts customer information from selection and populates fields
     * for user modification. Checks for input errors and saves to database.
     *
     * @param actionEvent selecting the modify customer button
     * @throws IOException
     * @throws SQLException
     * */
    public void modifyCustomerButton(ActionEvent actionEvent) throws IOException, SQLException{
        customerToModify = customerTableView.getSelectionModel().getSelectedItem();


        if (customerToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Customer Selected");
            alert.setTitle("Error");
            alert.showAndWait();
        }
        else {
            modifyIdTxt.setText(String.valueOf(customerToModify.getCustomerID()));
            modifyNameTxt.setText(String.valueOf(customerToModify.getCustomerName()));
            modifyAddressTxt.setText(String.valueOf(customerToModify.getCustomerAddress()));
            modifyPostalTxt.setText(String.valueOf(customerToModify.getCustomerPostalCode()));
            modifyPhoneTxt.setText(String.valueOf(customerToModify.getCustomerPhoneNumber()));

            Country modifyCountry = countryAccess.getCountryByName(customerToModify.getCountryName());
            modifyCountryCombo.setValue(modifyCountry);
            firstLevelDivision modifyDivision = firstLevelDivisionAccess.getFirstLevelDivisionsByName(customerToModify.getDivisionName());
            modifyDivisionCombo.setValue(modifyDivision);
        }
    }

    /**
     * For modify customer section -- upon clicking save button, extracts customer information in fields,
     * checks for blanks and errors. Saves customer to database.
     *
     * @param actionEvent new customer save button
     * @throws SQLException
     * @throws IOException
     * */
    public void saveModifyButton(ActionEvent actionEvent) throws IOException, SQLException{
        String name = modifyNameTxt.getText();
        String address = modifyAddressTxt.getText();
        String postal = modifyPostalTxt.getText();
        String phone = modifyPhoneTxt.getText();

        if(name.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter customer name");
            alert.setTitle("Empty Field Error");
            alert.showAndWait();
            return;
        }
        else if(address.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter customer address");
            alert.setTitle("Empty Field Error");
            alert.showAndWait();
            return;
        }
        else if(postal.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter customer postal code");
            alert.setTitle("Empty Field Error");
            alert.showAndWait();
            return;
        }
        else if(phone.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter customer phone number");
            alert.setTitle("Empty Field Error");
            alert.showAndWait();
            return;
        }
        else {
            String sql = "UPDATE customers SET Customer_ID=?, Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, modifyIdTxt.getText());
            ps.setString(2, modifyNameTxt.getText());
            ps.setString(3, modifyAddressTxt.getText());
            ps.setString(4, modifyPostalTxt.getText());
            ps.setString(5, modifyPhoneTxt.getText());
            ps.setInt(6, modifyDivisionCombo.getSelectionModel().getSelectedItem().getDivisionID());
            ps.setString(7, modifyIdTxt.getText());
            ps.executeUpdate();

            ObservableList<Customers> refreshList = customerAccess.getAllCustomers(ps.getConnection());
            customerTableView.setItems(refreshList);

            modifyIdTxt.clear();
            modifyNameTxt.clear();
            modifyAddressTxt.clear();
            modifyPostalTxt.clear();
            modifyPhoneTxt.clear();

            ObservableList<firstLevelDivision> allFirstLevelDivisions = firstLevelDivisionAccess.getAllFirstLevelDivisions();
            ObservableList<Country> allCountries = countryAccess.getCountries();
            modifyCountryCombo.setItems(allCountries);
            modifyDivisionCombo.setItems(allFirstLevelDivisions);

            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/Customer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 1000);
            stage.setTitle("Customers Page");
            stage.setScene(scene);
            stage.show();
        }
    }


    /**
     * Deletion of customer -- Checks for selection, prompts with confirmation, and deletes customer from database.
     *
     * @param actionEvent "Delete customer" button
     * @throws IOException
     * @throws SQLException
     * */
    public void deleteCustomerButton(ActionEvent actionEvent) throws IOException, SQLException{
        customerToModify = customerTableView.getSelectionModel().getSelectedItem();

        if (customerToModify == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer Not Selected");
            alert.setTitle("Error");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
            alert.setTitle("Confirm Deletion");
            Optional<ButtonType> result = alert.showAndWait();


            if(result.isPresent() && result.get() == ButtonType.OK){
                int deleteCustomerID = customerTableView.getSelectionModel().getSelectedItem().getCustomerID();
                Connection connection = JDBC.getConnection();
                ObservableList<Appointments> getAllAppointments = appointmentAccess.getAllAppointments(connection);
                for(Appointments appointment : getAllAppointments){
                    if(appointment.getCustomerID() == deleteCustomerID){
                        String delete = "DELETE FROM appointments WHERE customer_ID = ?";
                        PreparedStatement ps = JDBC.getConnection().prepareStatement(delete);
                        ps.setInt(1, deleteCustomerID);
                        ps.executeUpdate();
                    }
                }

                String sql = "DELETE FROM customers WHERE Customer_ID = ?";
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
//                int deleteCustomerID = customerTableView.getSelectionModel().getSelectedItem().getCustomerID();
                ps.setInt(1, deleteCustomerID);
                ps.executeUpdate();

                ObservableList<Customers> refreshList = customerAccess.getAllCustomers(ps.getConnection());
                customerTableView.setItems(refreshList);
            }
        }

    }

}
