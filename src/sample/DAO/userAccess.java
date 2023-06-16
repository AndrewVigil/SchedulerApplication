package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for accessing the user table of the database
 * */
public class userAccess {

    public static ObservableList<Users> getAllUsers(Connection connection) throws SQLException {
        String query = "SELECT * FROM users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        ObservableList<Users> usersObservableList = FXCollections.observableArrayList();

        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            Users user = new Users(userID, userName, userPassword);
            usersObservableList.add(user);
        }
        return usersObservableList;
    }

    public static int validateUser(String userName, String password) throws SQLException {
        try {
            String sql = "SELECT * FROM users WHERE user_name = '" + userName + "' AND password = '" + password + "'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("User_Name").equals(userName)) {
                    if (rs.getString("Password").equals(password)) {
                        return rs.getInt("User_ID");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
