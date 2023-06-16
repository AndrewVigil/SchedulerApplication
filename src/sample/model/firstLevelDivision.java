package sample.model;

/**
 * First Level Division Model Class
 * */
public class firstLevelDivision {
    private int divisionID;
    private String divisionName;
    public int country_ID;


    /**
     * First level division constructor
     *
     * @param divisionName
     * @param divisionID
     * @param country_ID
     * */
    public firstLevelDivision(int divisionID, String divisionName, int country_ID){
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.country_ID = country_ID;
    }

    /**
    * division ID getter
     * @return divisionID
    * */
    public int getDivisionID(){
        return divisionID;
    }

    /**
     * Division name getter
     * @return divisionName
     * */
    public String getDivisionName(){
        return divisionName;
    }

    /** country ID getter
     *
     * @return country_ID
     */
    public int getCountry_ID(){
        return country_ID;
    }

    @Override
    public String toString(){
        return (divisionName);
    }

}
