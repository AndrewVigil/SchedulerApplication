package sample.DAO;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DAO.JDBC;
import sample.model.Country;
import sample.model.Customers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Class used to access customer table of database
 * */
public class customerAccess {

    public static ObservableList<Customers> getAllCustomers(Connection connection) throws SQLException{
        String query = "SELECT Customer_ID,Customer_Name, Postal_Code, Address, Phone, Division, Country FROM customers tableA\n" +
                "INNER JOIN first_level_divisions tableB\n" +
                "ON tableA.Division_ID = tableB.Division_ID\n" +
                "INNER JOIN countries tableC\n" +
                "ON tableB.Country_ID = tableC.Country_ID;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        ObservableList<Customers> customersObservableList = FXCollections.observableArrayList();

            while(rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhoneNumber = rs.getString("Phone");
                String division = rs.getString("Division");
                String country = rs.getString("Country");
                Customers customer = new Customers(customerID, customerName, customerAddress, customerPostalCode, customerPhoneNumber, division, country);
                customersObservableList.add(customer);
            }
            return customersObservableList;
    }


//
//    public static int addNewCustomer(Customers newCustomer) throws SQLException{
//
//
//    }

//    public static int updateCustomer(Customers updatedCustomer) throws SQLException{
//        String update = "UPDATE customers SET Customer_ID='customerID', Customer_Name='customerName', Address='Customer;
//
//        PreparedStatement ps = JDBC.getConnection().prepareStatement(update);
//        ps.executeUpdate();
//    }


}
