package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.firstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Class to access the first divisions table in the database
 * */
public class firstLevelDivisionAccess extends firstLevelDivision {

    /**
     * Super constructor for accessing first level divisions.
     *
     * @param divisionID
     * @param country_ID
     * @param divisionName
     * */
    public firstLevelDivisionAccess(int divisionID, String divisionName, int country_ID){
        super(divisionID, divisionName, country_ID);
    }

    public static ObservableList<firstLevelDivision> getAllFirstLevelDivisions() throws SQLException {
        ObservableList<firstLevelDivision> firstLevelDivisionsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int country_ID = rs.getInt("COUNTRY_ID");
            firstLevelDivision firstLevelDivision = new firstLevelDivision(divisionID, divisionName, country_ID);
            firstLevelDivisionsObservableList.add(firstLevelDivision);
        }
        return firstLevelDivisionsObservableList;

    }


    public static firstLevelDivision getFirstLevelDivisionsByName(String divisionName) throws SQLException {
        String sql = "SELECT * from first_level_divisions WHERE Division=?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, divisionName);
        ResultSet rs = ps.executeQuery();
        firstLevelDivision division = null;
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            int country_ID = rs.getInt("COUNTRY_ID");
            division = new firstLevelDivision(divisionID, divisionName, country_ID);
        }
        return division;

    }



}
