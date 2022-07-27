package model;

import helper.LoginQuery;

import java.sql.SQLException;
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
    public ZonedDateTime timeDateEnd;
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

    /**
     * @param contactID  setContact method used to retrieve contact ID data from the contactID member in contact object model.
     */
    public void setContact(Integer contactID) throws SQLException {
        contact = LoginQuery.getContact(contactID);
    }

    /**
     * @return contact getContact method used to return a contact string.
     */
    public String getContact() {
        return contact;
    }

    /**
     * @return appointmentID getAppointmentID method used to retrieve an appointment ID.
     */
    public Integer getAppointmentID() {
        return appointmentID;
    }

    /**
     * @return title getTitle method used to return the title string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return getDescription method used to return the description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return getLocation method used to return the location string.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return type getType method used to return the type string.
     */
    public String getType() {
        return type;
    }

    /**
     * @return timeDateStart getTimeDateStart method used to return the timeDateStart ZonedDateTime.
     */
    public ZonedDateTime getTimeDateStart() {
        return timeDateStart;
    }

    /**
     * @return formattedStart getFormattedStart method used to convert and return LocalDateTime start to a formatted string.
     */
    public String getFormattedStart() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        String formattedStart = timeDateStart.format(formatter);
        return formattedStart;
    }

    /**
     * @return timeDateEnd getTimeDateEnd method used to return the timeDateStart ZonedDateTime.
     */
    public ZonedDateTime getTimeDateEnd() {
        return timeDateEnd;
    }

    /**
     * @return formattedEnd getFormattedStart method used to convert and return LocalDateTime end to a formatted string.
     */
    public String getFormattedEnd() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        String formattedEnd = timeDateEnd.format(formatter);
        return formattedEnd;
    }

    /**
     * @return customerID getCustomerID method used to return the customerID Integer.
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

