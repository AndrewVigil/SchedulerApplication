package sample.model;

/**
 * Customer Model Class
 * */
public class Customers {

    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private int divisionID;
    private String divisionName;
    private String countryName;

    public Customers(int customerID, String customerName, String customerAddress, String customerPostalCode,
                     String customerPhoneNumber, String divisionName, String countryName) {

        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.countryName = countryName;
        this.divisionName = divisionName;
    }

    public Integer getCustomerID() {

        return customerID;
    }


    public void setCustomerID(Integer customerID) {

        this.customerID = customerID;
    }


    public String getCustomerName() {

        return customerName;
    }


    public void setCustomerName(String customerName) {

        this.customerName = customerName;
    }


    public String getCustomerAddress() {

        return customerAddress;
    }


    public void setCustomerAddress(String address) {

        this.customerAddress = address;
    }


    public String getCustomerPostalCode() {

        return customerPostalCode;
    }


    public void setCustomerPostalCode(String postalCode) {

        this.customerPostalCode = postalCode;
    }


    public String getCustomerPhoneNumber() {

        return customerPhoneNumber;
    }


    public void setCustomerPhoneNumber(String phone) {

        this.customerPhoneNumber = phone;
    }


    public Integer getCustomerDivisionID() {

        return divisionID;
    }


    public String getDivisionName() {

        return divisionName;
    }


    public void setCustomerDivisionID(Integer divisionID) {

        this.divisionID = divisionID;
    }

    public String getCountryName(){
        return countryName;
    }

    public void setCountryName(String countryName){
        this.countryName = countryName;
    }

}
