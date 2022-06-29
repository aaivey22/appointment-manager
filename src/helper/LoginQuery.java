package helper;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

public abstract class LoginQuery {

    private static ResultSet resultSet = null;

    /** @return SELECT method used to count data from appointments column in database and returns "rowsCounted". */
    public static int getNumRecords(String date) throws SQLException {
        String sql = "SELECT COUNT(Start) from appointments WHERE Start >= \"" + date + " 00:00:00\" AND Start < \""+ date + " 23:59:59\"";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        resultSet.next();
        int rowsCounted = resultSet.getInt(1);
        return rowsCounted;
    }

    /** @return SELECT method returns a new list of contact names 'listofContacts' with the Contact_Name column data. */
    public static java.util.List<String> getContacts() throws SQLException {
        java.util.List<String> listofContacts = new ArrayList<String>();
        String sql = "SELECT Contact_Name from contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while(resultSet.next()) {
            listofContacts.add(resultSet.getString("Contact_Name"));
        }
        return listofContacts;
    }

    /** @return SELECT method returns a new list of countries 'listofCountries' with the Country column data. */
    public static java.util.List<String> getCountries() throws SQLException {
        java.util.List<String> listofCountries = new ArrayList<String>();
        String sql = "SELECT Country from countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while(resultSet.next()) {
            listofCountries.add(resultSet.getString("Country"));
        }
        return listofCountries;
    }

    /** @return SELECT method returns a new list of divisions 'listofDivisions' with the Division column data. */
    public static java.util.List<String> getDivisions(String countryID) throws SQLException {
        java.util.List<String> listofDivisions = new ArrayList<String>();
        String sql = "SELECT Division from first_level_divisions WHERE Country_ID = \"" + countryID + "\"";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while(resultSet.next()) {
            listofDivisions.add(resultSet.getString("Division"));
        }
        return listofDivisions;
    }

    /** @return SELECT method returns a String country ID whose country name matches with the selected country. */
    public static String getcountryID(String countryName) throws SQLException {
        java.util.List<String> listofCountries = new ArrayList<String>();
        String sql = "SELECT Country_ID FROM client_schedule.countries WHERE Country = \"" + countryName + "\"";
        String countryID = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while(resultSet.next()) {
            countryID = resultSet.getString("Country_ID");
        }
        return countryID;
    }

    /** @return SELECT method returns a String Division ID whose division name matches with the selected division. */
    public static String getDivisionID(String divisionName) throws SQLException {
        String sql = "SELECT Division_ID FROM client_schedule.first_level_divisions WHERE Division = \"" + divisionName + "\"";
        String divisionID = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while(resultSet.next()) {
            divisionID = resultSet.getString("Division_ID");
        }
        return divisionID;
    }

    /** @return insert method used to insert user input for username and pword into the database and returns "rowsAffected". */
    public static int addCustomer(String customerName, String address, String postalCode, String phone, String divisionID) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setString(5, divisionID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }




    /** @return insert method used to insert user input for username and pword into the database and returns "rowsAffected". */
    public static int registerUser(String userName, String password) throws SQLException {
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
