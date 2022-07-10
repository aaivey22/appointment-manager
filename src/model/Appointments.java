package model;

import helper.LoginQuery;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This Appointments class holds the queried Appointment data from the database.
 */
public class Appointments {
    public Integer appointmentID;
    public String title;
    public String description;
    public String location;
    public String type;
    public String contact;
    public ZonedDateTime timeDateStart;
    public String formattedStart;
    public ZonedDateTime timeDateEnd;
    public String formattedEnd;
    public int customerID;
    public int userID;
    public int contactID;

    public Appointments(Integer appointment_ID, String title, String description, String location, String type, ZonedDateTime timeDateStart, ZonedDateTime timeDateEnd, Integer customer_ID, Integer user_ID, Integer contact_ID) {
        this.appointmentID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.timeDateStart = timeDateStart;
        this.timeDateEnd = timeDateEnd;
        this.customerID = customer_ID;
        this.userID = user_ID;
        this.contactID = contact_ID;
    }

    ;

    /**
     * @return setContact method used to retrieve customer ID data from the customerID member in customer object model.
     */
    public void setContact(Integer contactID) throws SQLException {
        contact = LoginQuery.getContact(contactID);
    }

    /**
     * @return getCustomerID method used to retrieve customer ID data from the customerID member in customer object model.
     */
    public String getContact() {
        return contact;
    }

    /**
     * @return getContactID method used to retrieve customer ID data from the customerID member in customer object model.
     */
    public Integer getAppointmentID() {
        return appointmentID;
    }

    /**
     * @return getCustomerID method used to retrieve customer ID data from the customerID member in customer object model.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return getDescription method used to retrieve customer ID data from the customerID member in customer object model.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return getLocation method used to retrieve customer ID data from the customerID member in customer object model.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return getType method used to retrieve customer ID data from the customerID member in customer object model.
     */
    public String getType() {
        return type;
    }

    /**
     * @return getTimeDateStart method used to retrieve customer ID data from the customerID member in customer object model.
     */
    public ZonedDateTime getTimeDateStart() {
        return timeDateStart;
    }

    /**
     * @return getFormattedStart method used to convert and return LocalDateTime start to a formatted string.
     */
    public String getFormattedStart() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        String formattedStart = timeDateStart.format(formatter);
        return formattedStart;
    }

    /**
     * @return getTimeDateEnd method used to retrieve customer ID data from the customerID member in customer object model.
     */
    public ZonedDateTime getTimeDateEnd() {
        return timeDateEnd;
    }

    /**
     * @return getFormattedStart method used to convert and return LocalDateTime start to a formatted string.
     */
    public String getFormattedEnd() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        String formattedEnd = timeDateEnd.format(formatter);
        return formattedEnd;
    }

    /**
     * @return getCustomerID method used to retrieve customer ID data from the customerID member in customer object model.
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
     * @return getUserID method used to retrieve customer ID data from the customerID member in customer object model.
     */
    public Integer getUserID() {
        return userID;
    }

    /**
     * @return getContactID method used to retrieve customer ID data from the customerID member in customer object model.
     */
    public Integer getContactID() {
        return contactID;
    }


}

