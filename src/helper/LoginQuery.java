package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class LoginQuery {

    /** @return insert method used to insert user input for username and pword into the database and returns "rowsAffected". */
    public static int insert(String userName, String password) throws SQLException {
        String sql = "INSERT INTO USERS (User_Name, Password) VALUES(?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** @return authenticate function used to compare user input for username and pword against the database and returns a boolean response. */
    public static boolean authenticate(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        boolean authenticated = false;
        while(rs.next()){
            if(!rs.getString("User_Name").equals(userName)){
                System.out.println("Not Found");
                return false;
            }
            if(Objects.equals(password, rs.getString("Password"))){
                System.out.println("Found");
                authenticated = true;
            } else return false;
        }
        return authenticated;
    }
}
