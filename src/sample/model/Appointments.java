package sample.model;

import java.time.LocalDateTime;

/**
 * Appointment Model Class
 *
 * */
public class Appointments {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    LocalDateTime start;
    LocalDateTime end;
    public int customerID;
    public String contactName;
    public int userID;

    /**
     * Method for creating appointments.
     *
     * @param appointmentID
     * @param contactName
     * @param title
     * @param customerID
     * @param description
     * @param end
     * @param start
     * @param location
     * @param type
     * @param userID
     * */
    public Appointments (int appointmentID, String title, String description, String location,  String type, LocalDateTime start, LocalDateTime end,
                         int customerID, String contactName, int userID){
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.contactName = contactName;
        this.userID = userID;
    }

    /**
     * Appointment ID Getter
     * @return appointmentID
     * */
    public int getAppointmentID(){
        return appointmentID;
    }

    /**
     * Location Getter
     *
     * @return location
     * */
    public String getLocation(){
        return location;
    }

    /**
     * Description Getter
     *
     * @return description
     * */
    public String getDescription(){
        return description;
    }

    /**
     * Title Getter
     *
     * @return title
     * */
    public String getTitle(){
        return title;
    }

    /**
     * Type getter
     * @return type
     * */
    public String getType(){
        return type;
    }

    /**
     * Date time start getter
     *
     * @return start
     * */
    public LocalDateTime getStart(){
        return start;
    }

    /**
     * Date time end getter
     *
     * @return end
     * */
    public LocalDateTime getEnd(){
        return end;
    }

    /**
     * customer ID getter
     * @return customerID
     * */
    public int getCustomerID(){
        return customerID;
    }

    /**
     * Contact name getter
     *
     * @return contactName
     * */
    public String getContactName(){
        return contactName;
    }

    /**
     * User ID getter
     * @return userID
     * */
    public int getUserID(){
        return userID;
    }
}
