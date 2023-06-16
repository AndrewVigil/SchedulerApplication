package sample.model;

/**
 * Contacts model class
 * */
public class Contacts {
    public int contactID;
    public String contactName;
    public String contactEmail;

    /**
     * Contacts constructor
     *
     * @param contactName
     * @param contactID
     * */
    public Contacts(int contactID, String contactName){
        this.contactID = contactID;
        this.contactName = contactName;
//        this.contactEmail = contactEmail;
    }


    /**
     * Contact ID getter
     * @return contactID
     * */
    public int getContactID(){
        return contactID;
    }

    /**
     * Contact name getter
     * @return contactName
     * */
    public String getContactName(){
        return contactName;
    }

    /**
     * Contact Email getter
     * @return contactEmail
     * */
    public String getContactEmail(){
        return contactEmail;
    }

    @Override
    public String toString(){
        return (contactName);
    }

}
