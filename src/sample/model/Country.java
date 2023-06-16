package sample.model;

/**
 * Country Model Class
 * */
public class Country {
    private int countryID;
    private String countryName;

    /**
     * Country constructor
     *
     * @param countryName
     * @param countryID
     * */
    public Country(int countryID, String countryName){
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * Country ID getter
     * @return countryID
     * */
    public int getCountryID(){
        return countryID;
    }

    /**
     * Country Name getter
     * @return countryName
     * */
    public String getCountryName(){
        return countryName;
    }


    @Override
    public String toString(){
        return (countryName);
    }

}
