package khara.karan.netuse.MyPOJO;

/**
 * Created by kharak1 on 12/16/2017.
 */

public class UserInfo {
    String userId;
    String fullname;
    int percent;
    int gre;
    int toefl;
    String univnameSelected;
    String countrySelected;

    public UserInfo(String userId, String fullname, int percent, int gre, int toefl, String univnameSelected, String countrySelected) {
        this.userId = userId;
        this.fullname = fullname;
        this.percent = percent;
        this.gre = gre;
        this.toefl = toefl;
        this.univnameSelected = univnameSelected;
        this.countrySelected = countrySelected;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getGre() {
        return gre;
    }

    public void setGre(int gre) {
        this.gre = gre;
    }

    public int getToefl() {
        return toefl;
    }

    public void setToefl(int toefl) {
        this.toefl = toefl;
    }

    public String getUnivnameSelected() {
        return univnameSelected;
    }

    public void setUnivnameSelected(String univnameSelected) {
        this.univnameSelected = univnameSelected;
    }

    public String getCountrySelected() {
        return countrySelected;
    }

    public void setCountrySelected(String countrySelected) {
        this.countrySelected = countrySelected;
    }
}
