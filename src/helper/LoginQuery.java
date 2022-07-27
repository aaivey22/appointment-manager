package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class communicates with the database.
 */
public abstract class LoginQuery {

    /**
     * a ResultSet is a datatype that holds mysql query results.
     */
    private static ResultSet resultSet = null;

    /**
     * @param custID used in appmtCount() method.
     * @return rowsCounted SELECT method used to count data from appointments column in database and returns "rowsCounted".
     */
    public static int appmtCount(String custID) throws SQLException {
        String sql = "SELECT COUNT(Start) from appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, custID);
        resultSet = ps.executeQuery();
        resultSet.next();
        int rowsCounted = resultSet.getInt(1);
        return rowsCounted;
    }

    /**
     * @return listofContacts SELECT method returns a new list of contact names 'listofContacts' with the Contact_Name column data.
     */
    public static java.util.List<String> getContacts() throws SQLException {
        java.util.List<String> listofContacts = new ArrayList<String>();
        String sql = "SELECT Contact_Name from contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            listofContacts.add(resultSet.getString("Contact_Name"));
        }
        return listofContacts;
    }

    /**
     * @return listofCountries SELECT method returns a new list of countries 'listofCountries' with the Country column data.
     */
    public static java.util.List<String> getCountries() throws SQLException {
        java.util.List<String> listofCountries = new ArrayList<String>();
        String sql = "SELECT Country from countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            listofCountries.add(resultSet.getString("Country"));
        }
        return listofCountries;
    }

    /**
     * @return listofCustomers SELECT method returns a new list of customer names 'listofCustomers' with the Customer_Name column data.
     */
    public static java.util.List<String> getCustNames() throws SQLException {
        java.util.List<String> listofCustomers = new ArrayList<String>();
        String sql = "SELECT Customer_Name from customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            listofCustomers.add(resultSet.getString("Customer_Name"));
        }
        return listofCustomers;
    }

    /**
     * @param countryID used in getDivisions() method.
     * @return listofDivisions SELECT method returns a new list of divisions 'listofDivisions' with the Division by referencing the countryID.
     */
    public static java.util.List<String> getDivisions(String countryID) throws SQLException {
        java.util.List<String> listofDivisions = new ArrayList<String>();
        String sql = "SELECT Division from first_level_divisions WHERE Country_ID = \"" + countryID + "\"";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            listofDivisions.add(resultSet.getString("Division"));
        }
        return listofDivisions;
    }

    /**
     * @param countryName used in getcountryID() method.
     * @return countryID SELECT method returns a country ID by referencing Country in the database.
     */
    public static String getcountryID(String countryName) throws SQLException {
        String sql = "SELECT Country_ID FROM client_schedule.countries WHERE Country = \"" + countryName + "\"";
        String countryID = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            countryID = resultSet.getString("Country_ID");
        }
        return countryID;
    }

    /**
     * @param countryID used in getcountryName() method.
     * @return countryName SELECT method returns a country name by referencing Country_ID in the database.
     */
    public static String getcountryName(String countryID) throws SQLException {
        String sql = "SELECT Country FROM client_schedule.countries WHERE Country_ID = \"" + countryID + "\"";
        String countryName = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            countryName = resultSet.getString("Country");
        }
        return countryName;
    }

    /**
     * @param divisionID used in getCountryID() method.
     * @return countryID SELECT method returns a country ID by referencing Division_ID in the database.
     */
    public static String getCountryID(Integer divisionID) throws SQLException {
        String sql = "SELECT Country_ID FROM client_schedule.first_level_divisions WHERE Division_ID = \"" + divisionID + "\"";
        String countryID = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            countryID = resultSet.getString("Country_ID");
        }
        return countryID;
    }


    /**
     * @param customerName used in getCustomerID() method.
     * @return customerID SELECT method returns a customer ID by referencing Customer_Name in the database.
     */
    public static String getCustomerID(String customerName) throws SQLException {
        String sql = "SELECT Customer_ID FROM client_schedule.customers WHERE Customer_Name = \"" + customerName + "\"";
        String customerID = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            customerID = resultSet.getString("Customer_ID");
        }
        return customerID;
    }

    /**
     * @param contactName used in getContactID() method.
     * @return contactID SELECT method returns a contact ID by referencing Contact_Name in the database.
     */
    public static String getContactID(String contactName) throws SQLException {
        String sql = "SELECT Contact_ID FROM client_schedule.contacts WHERE Contact_Name = \"" + contactName + "\"";
        String contactID = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            contactID = resultSet.getString("Contact_ID");
        }
        return contactID;
    }

    /**
     * @param userName used in getUserID() method.
     * @return userID SELECT method returns a String User ID by referencing User_Name in the database.
     */
    public static String getUserID(String userName) throws SQLException {
        String sql = "SELECT User_ID FROM client_schedule.users WHERE User_Name = \"" + userName + "\"";
        String userID = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            userID = resultSet.getString("User_ID");
        }
        return userID;
    }

    /**
     * @param divisionName used in getDivisionID() method.
     * @return divisionID SELECT method returns a String Division ID by referencing Division Name in the database.
     */
    public static String getDivisionID(String divisionName) throws SQLException {
        String sql = "SELECT Division_ID FROM client_schedule.first_level_divisions WHERE Division = \"" + divisionName + "\"";
        String divisionID = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            divisionID = resultSet.getString("Division_ID");
        }
        return divisionID;
    }

    /**
     * @param divisionID used in getDivisionName() method.
     * @return divisionName SELECT method returns a String Division Name by referencing Division_ID in the database.
     */
    public static String getDivisionName(Integer divisionID) throws SQLException {
        String sql = "SELECT Division FROM client_schedule.first_level_divisions WHERE Division_ID = \"" + divisionID + "\"";
        String divisionName = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            divisionName = resultSet.getString("Division");
        }
        return divisionName;
    }

    /**
     * @return resultSet SELECT method returns all customer data from the customers table.
     */
    public static ResultSet getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return resultSet = ps.executeQuery();
    }

    /**
     * @return resultSet SELECT method returns all contact data from the contacts table.
     */
    public static ResultSet getAllContacts() throws SQLException {
        String sql = "SELECT * FROM client_schedule.contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return resultSet = ps.executeQuery();
    }

    /**
     * @return resultSet SELECT method returns all appointment data from the appointments table.
     */
    public static ResultSet getAllApps() throws SQLException {
        String sql = "SELECT * FROM client_schedule.appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return resultSet = ps.executeQuery();
    }

    /**
     * @param contactID used in getContact() method.
     * @return contact SELECT method returns a contact name whose contactID matches the Contact_ID in the database.
     */
    public static String getContact(Integer contactID) throws SQLException {
        String sql = "SELECT Contact_Name FROM client_schedule.contacts WHERE Contact_ID = \"" + contactID + "\"";
        String contact = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            contact = resultSet.getString("Contact_Name");
        }
        return contact;
    }

    /**
     * @param customerID used in getCustomerName() method.
     * @return customerName SELECT method returns a customer name whose customerID matches the Customer_ID in the database.
     */
    public static String getCustomerName(Integer customerID) throws SQLException {
        String sql = "SELECT Customer_Name FROM client_schedule.customers WHERE Customer_ID = \"" + customerID + "\"";
        String customerName = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            customerName = resultSet.getString("Customer_Name");
        }
        return customerName;
    }

    /**
     * @param userID used in getUserName() method.
     * @return userName SELECT method returns userName whose User_ID matches the User_Name in the database.
     */
    public static String getUserName(Integer userID) throws SQLException {
        String sql = "SELECT User_Name FROM client_schedule.users WHERE User_ID = \"" + userID + "\"";
        String userName = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            userName = resultSet.getString("User_Name");
        }
        return userName;
    }

    /**
     * @param customerID used in getCustomer() method.
     * @return resultSet SELECT method returns all customer data to populate in the customers table in the customers page.
     */
    public static ResultSet getCustomer(Integer customerID) throws SQLException {
        String sql = "SELECT * FROM client_schedule.customers WHERE Customer_ID = (?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        return resultSet = ps.executeQuery();
    }

    /**
     * @param appointmentID used in getAppointment() method.
     * @return resultSet SELECT method returns all appointment data to populate in the appointments table in the appointments page.
     */
    public static ResultSet getAppointment(Integer appointmentID) throws SQLException {
        String sql = "SELECT * FROM client_schedule.appointments WHERE Appointment_ID = (?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        return resultSet = ps.executeQuery();
    }


    /**
     * @return resultSet SELECT method returns all Date and Time data from the appointments table.
     */
    public static ResultSet getAllDateTimes() throws SQLException {
        String sql = "SELECT Start, End FROM client_schedule.appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return resultSet = ps.executeQuery();
    }

    /**
     * @return resultSet SELECT method returns all Date and Time data from the appointments table that is not associated with given Appointment ID
     */
    public static ResultSet getAllDateTimesModify(Integer appointmentID) throws SQLException {
        String sql = "SELECT Start, End FROM client_schedule.appointments WHERE Appointment_ID != (?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        return resultSet = ps.executeQuery();
    }

    /**
     * @return listofUsers SELECT method returns a new list of users 'listofUsers' with the Contact_Name column data.
     */
    public static java.util.List<String> getUsers() throws SQLException {
        java.util.List<String> listofUsers = new ArrayList<String>();
        String sql = "SELECT User_Name from users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            listofUsers.add(resultSet.getString("User_Name"));
        }
        return listofUsers;
    }

    /**
     * @param customerName,address,postalCode,phone,divisionID used in addCustomer() method.
     * @return rowsAffected insert method used to insert new customer data into the database and returns "rowsAffected".
     */
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

    /**
     * @param title,description,location,type,timeStartField,timeStartField,timeEndField,Customer_ID,User_ID,Contact_ID used in addAppointment() method.
     * @return rowsAffected insert method used to insert new appointment data into the database and returns "rowsAffected".
     */
    public static int addAppointment(String title, String description, String location, String type, LocalDateTime timeStartField, LocalDateTime timeEndField, String Customer_ID, String User_ID, String Contact_ID) throws SQLException {
        ZoneId UTC = ZoneId.of("UTC");

        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(timeStartField));
        ps.setTimestamp(6, Timestamp.valueOf(timeEndField));
        ps.setString(7, Customer_ID);
        ps.setString(8, User_ID);
        ps.setString(9, Contact_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * @param apptID used in getStartUTC method.
     * @return start SELECT method used to retrieve Start date time data from a specific appointment ID in UTC timezone and returns the data in the ZonedDateTime data type.
     */
    public static ZonedDateTime getStartUTC(Integer apptID) throws SQLException {
        ZoneId UTC = ZoneId.of("UTC");

        String sql = "SELECT Start FROM client_schedule.appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, apptID);
        resultSet = ps.executeQuery();
        ZonedDateTime start = null;
        while (resultSet.next()) {
            start = resultSet.getTimestamp("Start").toLocalDateTime().atZone(UTC);
        }
        return start;
    }

    /**
     * @param apptID used in getEndUTC method.
     * @return end getEndUTC method used to retrieve End date time data from a specific appointment ID in UTC timezone and returns the data in the ZonedDateTime data type.
     */
    public static ZonedDateTime getEndUTC(Integer apptID) throws SQLException {
        ZoneId UTC = ZoneId.of("UTC");
        String sql = "SELECT End FROM client_schedule.appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, apptID);
        resultSet = ps.executeQuery();
        ZonedDateTime end = null;
        while (resultSet.next()) {
            end = resultSet.getTimestamp("End").toLocalDateTime().atZone(UTC);
        }
        return end;
    }

    /**
     * @param customerName,address,postalCode,phone,divisionID,customerID used in addCustomer() method.
     * @return rowsAffected UPDATE method used to update customer data in the database and returns "rowsAffected".
     */
    public static int modifyCustomer(String customerName, String address, String postalCode, String phone, String divisionID, Integer customerID) throws SQLException {
        String sql = "UPDATE client_schedule.customers SET Customer_Name = (?), Address = (?), Postal_Code = (?), Phone = (?), Division_ID = (?) WHERE Customer_ID = (?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setString(5, divisionID);
        ps.setInt(6, customerID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * @param title,description,location,type,timeStartField,timeStartField,timeEndField,Customer_ID,User_ID,Contact_ID used in modifyAppointment() method.
     * @return rowsAffected modifyAppointment method used to update appointment data into the database and returns "rowsAffected".
     */
    public static int modifyAppointment(String title, String description, String location, String type, LocalDateTime timeStartField, LocalDateTime timeEndField, String Customer_ID, String User_ID, String Contact_ID, Integer Appointment_ID) throws SQLException {
        ZoneId UTC = ZoneId.of("UTC");

        String sql = "UPDATE  client_schedule.appointments SET Title = (?), Description = (?), Location = (?), Type = (?), Start = (?), End = (?), Customer_ID = (?), User_ID = (?), Contact_ID = (?) WHERE Appointment_ID = (?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(timeStartField));
        ps.setTimestamp(6, Timestamp.valueOf(timeEndField));
        ps.setString(7, Customer_ID);
        ps.setString(8, User_ID);
        ps.setString(9, Contact_ID);
        ps.setInt(10, Appointment_ID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * @param customerID used in deleteCustomer() method.
     * @return delete method used to delete a customer from the database and returns "rowsAffected".
     */
    public static int deleteCustomer(String customerID) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = (?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * @param appmtID used in deleteAppointment() method.
     * @return delete method used to delete a appointment from the database and returns "rowsAffected".
     */
    public static int deleteAppointment(Integer appmtID) throws SQLException {
        String sql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = (?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appmtID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * @param userName,password used in registerUser() method.
     * @return insert method used to insert user input for username and pword into the database and returns "rowsAffected".
     */
    public static int registerUser(String userName, String password) throws SQLException {
        String sql = "INSERT INTO USERS (User_Name, Password) VALUES(?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * @param userName,password used in authenticate() method.
     * @return authenticate function used to compare user input for username and pword against the database and returns a boolean response.
     */
    public static boolean authenticate(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        boolean authenticated = false;
        while (rs.next()) {
            if (!rs.getString("User_Name").equals(userName)) {
                System.out.println("Not Found");
                return false;
            }
            if (Objects.equals(password, rs.getString("Password"))) {
                System.out.println("Found");
                authenticated = true;
            } else return false;
        }
        return authenticated;
    }
}
