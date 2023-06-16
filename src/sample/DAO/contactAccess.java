package sample.DAO;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Class for connecting to database and accessing contacts table.
 * */
public class contactAccess {

    /**
     * Connecting to the database and creating an observable list of all the contacts
     * @throws SQLException
     * */
    public static ObservableList<Contacts> getAllContacts() throws SQLException{
        ObservableList<Contacts> contactsObservableList = FXCollections.observableArrayList();
        String query = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contacts contact = new Contacts(contactID, contactName);
            contactsObservableList.add(contact);
        }
        return contactsObservableList;
    }

    /**
     * Method for getting all the Contact by their names.
     *
     * @throws SQLException
     * */
    public static Contacts getContactByName(String contactName) throws SQLException{
        String sql = "SELECT Contact_ID, Contact_Name FROM contacts WHERE Contact_Name=?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        Contacts contact = null;
        while (rs.next()){
            int contactID = rs.getInt("Contact_ID");
            contact = new Contacts(contactID, contactName);
        }
        return contact;
    }

    /**
     * Method used to find a contactID
     *
     * @throws SQLException
     * */
    public static String findContactID(String contactID) throws SQLException{
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT * FROM contacts WHERE Contact_Name=?");
        ps.setString(1, contactID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            contactID = rs.getString("Contact_ID");
        }
        return contactID;
    }

}
