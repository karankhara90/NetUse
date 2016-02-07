package khara.karan.netuse;

/**
 * Created by karan on 11/15/15.
 */
public class Calculations {

    float getUnivRating(int univRank){
        float univRate=0;
        int a=1,b=15;
        double rate =10.0;

        while(b<=240)
        {
            if(a>=241)
            {   univRate = (float)2.0;  }

            if (univRank >= a && univRank <= b) {
                univRate = (float)rate;
                break;
            }
            else{
                a+=15;
                b+=15;
                rate = rate - 0.5;
            }
        }
        return univRate;
    }

    float getGreRating(float greScore) {
        float greRating;
        float temp = greScore - 240;
        greRating = temp / 10;
        return greRating;
    }

    float getUndergradStudRating(float undergradPercent) {
        undergradPercent = undergradPercent / 10;
        return undergradPercent;
    }

    float getThisUserAvgRatings(float greRating, float undergradUnivRating, float undergradStudRating) {
        float totalUserRating;
        float sum = greRating + undergradStudRating + undergradUnivRating;
        float div = sum / 3;
        totalUserRating = div;
        return totalUserRating;
    }
}
