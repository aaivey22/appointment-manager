package model;
/** Created class customer.java */
/** @author Angela Ivey */

import helper.LoginQuery;

import java.sql.SQLException;

/** This customer class holds the queried customer data from the database.*/
public class customer {
    public String countryName;
    public String customerID;
    public String customerName;
    public String address;
    public String postalCode;
    public String phone;
    public String divisionName;
    public int divisionID;
    public customer(String customerID, String customerName, String address, String postalCode, String phone, int divisionID) throws SQLException {

        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    // void is a data type that does not return anything
    public void setCountryName() throws SQLException {
        String countryID = LoginQuery.getCountryID(divisionID);
        countryName = LoginQuery.getcountryName(countryID);
    }

    public void setDivisionName() throws SQLException {
        divisionName = LoginQuery.getDivisionName(divisionID);
    }


    /** @return getCustomerID method used to retrieve customer ID data from the customerID member in customer object model. */
    public String getCustomerID(){
        return customerID;
    }

    /** @return getCustomerID method used to retrieve customer ID Integer data from the customerID member in customer object model. */
    public Integer getCustomerIDint(){
        return Integer.parseInt(customerID);
    }

    /** @return getCustomerName method used to retrieve customer name data from the customerName member in customer object model. */
    public String getCustomerName(){
        return customerName;
    }

    /** @return getAddress method used to retrieve customer address data from the address member in customer object model. */
    public String getAddress(){
        return address;
    }

    /** @return getPostalCode method used to retrieve customer postal code data from the postalCode member in customer object model. */
    public String getPostalCode(){
        return postalCode;
    }

    /** @return getPhone method used to retrieve customer phone number data from the phone member in customer object model. */
    public String getPhone(){
        return phone;
    }

    /** @return getCountryName method used to retrieve customer name data from the countryName member in customer object model. */
    public String getCountryName(){
        return countryName;
    }

    /** @return getDivisionName method used to retrieve customer division name data from the divisionName member in customer object model. */
    public String getDivisionName(){
        return divisionName;
    }

    /** @return getDivisionID method used to retrieve customer division id data from the divisionID member in customer object model. */
    public int getDivisionID(){
        return divisionID;
    }

}
