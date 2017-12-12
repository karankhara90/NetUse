package khara.karan.netuse;

/**
 * Created by karan on 11/14/15.
 */
public class UserDetails implements Cloneable { // implements cloneable coz it's array object is exactly copied to it's other array object
    float mArrThisSeniorUndergradUnivRating;
    float mArrThisSeniorNewUnivRating;
    float mArrThisSeniorUndergradGPARating;
    float mArrThisSeniorGreRating;
    float mArrThisSeniorUserAvgRating;
    float mArrSimilarityInUsers;

    /////////////// Senior user's name and university arrays //////////////////
    String mArrSeniorStudName;
    String mArrSeniorPrevUnivName;
    String mArrSeniorNewUnivName;

    public float getmArrThisSeniorUndergradUnivRating() {
        return mArrThisSeniorUndergradUnivRating;
    }

    public void setmArrThisSeniorUndergradUnivRating(float mArrThisSeniorUndergradUnivRating) {
        this.mArrThisSeniorUndergradUnivRating = mArrThisSeniorUndergradUnivRating;
    }

    public float getmArrThisSeniorNewUnivRating() {
        return mArrThisSeniorNewUnivRating;
    }

    public void setmArrThisSeniorNewUnivRating(float mArrThisSeniorNewUnivRating) {
        this.mArrThisSeniorNewUnivRating = mArrThisSeniorNewUnivRating;
    }

    public float getmArrThisSeniorUndergradGPARating() {
        return mArrThisSeniorUndergradGPARating;
    }

    public void setmArrThisSeniorUndergradGPARating(float mArrThisSeniorUndergradGPARating) {
        this.mArrThisSeniorUndergradGPARating = mArrThisSeniorUndergradGPARating;
    }

    public float getmArrThisSeniorGreRating() {
        return mArrThisSeniorGreRating;
    }

    public void setmArrThisSeniorGreRating(float mArrThisSeniorGreRating) {
        this.mArrThisSeniorGreRating = mArrThisSeniorGreRating;
    }

    public float getmArrThisSeniorUserAvgRating() {
        return mArrThisSeniorUserAvgRating;
    }

    public void setmArrThisSeniorUserAvgRating(float mArrThisSeniorUserAvgRating) {
        this.mArrThisSeniorUserAvgRating = mArrThisSeniorUserAvgRating;
    }

    public float getmArrSimilarityInUsers() {
        return mArrSimilarityInUsers;
    }

    public void setmArrSimilarityInUsers(float mArrSimilarityInUsers) {
        this.mArrSimilarityInUsers = mArrSimilarityInUsers;
    }

    public String getmArrSeniorStudName() {
        return mArrSeniorStudName;
    }

    public void setmArrSeniorStudName(String mArrSeniorStudName) {
        this.mArrSeniorStudName = mArrSeniorStudName;
    }

    public String getmArrSeniorPrevUnivName() {
        return mArrSeniorPrevUnivName;
    }

    public void setmArrSeniorPrevUnivName(String mArrSeniorPrevUnivName) {
        this.mArrSeniorPrevUnivName = mArrSeniorPrevUnivName;
    }

    public String getmArrSeniorNewUnivName() {
        return mArrSeniorNewUnivName;
    }

    public void setmArrSeniorNewUnivName(String mArrSeniorNewUnivName) {
        this.mArrSeniorNewUnivName = mArrSeniorNewUnivName;
    }

    public Object clone()throws CloneNotSupportedException{
        return super.clone();
    }
}
