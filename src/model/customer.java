package model;

public class customer {
    public String customerID;
    public String customerName;
    public String address;
    public String postalCode;
    public String phone;
    public int divisionID;
    public customer(String customerID, String customerName, String address, String postalCode, String phone, int divisionID){
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    public String getCustomerID(){
        return customerID;
    }

    public String getCustomerName(){
        return customerName;
    }

    public String getAddress(){
        return address;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public String getPhone(){
        return phone;
    }

    public int getDivisionID(){
        return divisionID;
    }

}
