package sample.model;

/**
 * User Model Class
 * */
public class Users {

    private int userID;
    private String userName;
    private String userPassword;

    /**
     * User constructor
     *
     * @param userID
     * @param userName
     * @param userPassword
     * */
    public Users(int userID, String userName, String userPassword){
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * userID getter
     * @return userID
     * */
    public int userID() {
        return userID;
    }

    /**
     * Username getter
     * @return userName
     * */
    public String getUserName(){
        return userName;
    }

    /**
     * user password getter
     * @return userPassword
     * */
    public String getUserPassword(){
        return userPassword;
    }


}
