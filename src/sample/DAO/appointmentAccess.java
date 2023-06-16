package sample.DAO;

import javafx.beans.Observable;
import javafx.beans.value.ObservableNumberValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Appointments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Class for accessing the appointments table of the database
 * */
public class appointmentAccess {

    /**
     * Creates an observable list of all the appointments in the database.
     * @param connection
     * @throws SQLException
     * */
    public static ObservableList<Appointments> getAllAppointments(Connection connection) throws SQLException{
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, Contact_Name, User_ID FROM appointments tableA\n" +
                "INNER JOIN contacts tableB\n" +
                "ON tableA.Contact_ID = tableB.Contact_ID";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            String contactName = rs.getString("Contact_Name");
            int userID = rs.getInt("User_ID");
            Appointments appointment = new Appointments(appointmentID, title, description, location, type, start, end, customerID, contactName, userID);
            appointmentsObservableList.add(appointment);
        }
        return appointmentsObservableList;
    }

    /**
     * Observable list for all the types of appointments
     *
     * @throws SQLException
     * */
    public static ObservableList<String> getTypes() throws SQLException{
        ObservableList<String> typesObservableList = FXCollections.observableArrayList();
        String query = "SELECT DISTINCT type FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String type = rs.getString("Type");
            typesObservableList.add(type);
        }
        return typesObservableList;
    }



}
