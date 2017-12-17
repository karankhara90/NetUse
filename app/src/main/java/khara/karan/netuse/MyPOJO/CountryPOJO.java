package khara.karan.netuse.MyPOJO;

import java.util.ArrayList;

/**
 * Created by kharak1 on 12/16/2017.
 */

public class CountryPOJO {
    String countryName;
    ArrayList<String> countryNameList;

    public CountryPOJO(){

    }
    public CountryPOJO(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public ArrayList<String> getCountryNameList() {
        return countryNameList;
    }

    public void setCountryNameList(ArrayList<String> countryNameList) {
        this.countryNameList = countryNameList;
    }
}
