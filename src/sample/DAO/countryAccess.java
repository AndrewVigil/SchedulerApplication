package sample.DAO;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DAO.JDBC;
import sample.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for access the country table in the database.
 * */
public class countryAccess extends Country{

    /**
     * Country Access constructor
     * @param countryID
     * @param countryName
     * */
    public countryAccess(int countryID, String countryName){
        super(countryID, countryName);
    }

    /**
     * Creates an observable list of all countries
     *
     * @throws SQLException
     * */
    public static ObservableList<Country> getCountries() throws SQLException{
        ObservableList<Country> countriesObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country from countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country country = new Country(countryID, countryName);
            countriesObservableList.add(country);
        }
        return countriesObservableList;
    }

    /**
     * Method for getting countries by name
     * @param countryName
     * @throws SQLException
     * */
    public static Country getCountryByName(String countryName) throws SQLException{
        String sql = "SELECT Country_ID, Country from countries WHERE Country=?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, countryName);
        ResultSet rs = ps.executeQuery();
        Country country = null;
        while (rs.next()){
            int countryID = rs.getInt("Country_ID");
            country = new Country(countryID, countryName);
        }
        return country;
    }

}
