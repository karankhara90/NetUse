package khara.karan.netuse;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

/**
 * Created by karan on 11/13/15.
 */
public class UserRating {
    //////////////// currrent user's rating ////////////////////////////
    static float currUserUndergradUnivRating;
    static float currUserUndergradPercentRating;
    static float currUserGreRating;
    static float mCurrentUserAvgRating;

    //////////////// Senior user's rating and similarity array ////////////////////
    float[] mArrThisSeniorUndergradUnivRating;
    float[] mArrThisSeniorNewUnivRating;
    float[] mArrThisSeniorUndergradGPARating;
    float[] mArrThisSeniorGreRating;
    float[] mArrThisSeniorUserAvgRating;
    float[] mArrSimilarityInUsers;

    /////////////// Senior user's name and university arrays //////////////////
    String[] mArrSeniorStudName;
    String[] mArrSeniorPrevUnivName;
    String[] mArrSeniorNewUnivName;
    UserDetails[] arrObjUserDetails;

    static int i = -1;
    int j;

    final ParseUser currUser = ParseUser.getCurrentUser();
    public static final String TAG = SuggestUnivActivity.class.getSimpleName();


    void getCurrentNewUserRating(float GreScore1,float UndergradPercent1,float UnivRating1) {

        currUserGreRating = getGreRating(GreScore1);
        currUserUndergradPercentRating = getUndergradStudRating(UndergradPercent1);
        currUserUndergradUnivRating = UnivRating1;
        mCurrentUserAvgRating = getThisUserAvgRatings(currUserGreRating, currUserUndergradUnivRating, currUserUndergradPercentRating);

         /*Log.e("TAG", "SCORE=============== " + currUserGreRating + " ,,, PERCENT =============== " +
                 currUserUndergradPercentRating + "Univ rating ================="+currUserUndergradUnivRating); */

    }

    void getEachSeniorRating() {
        ParseQuery<ParseObject> query2;
        query2 = ParseQuery.getQuery("UserInfo");
        query2.whereNotEqualTo("newUnivName", "blank");
//        query2.whereNotEqualTo("fullName", fullname);
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                mArrSeniorStudName = new String[list.size()];
                mArrSeniorPrevUnivName = new String[list.size()];
                mArrSeniorNewUnivName = new String[list.size()];
                mArrThisSeniorGreRating = new float[list.size()];
                mArrThisSeniorUndergradGPARating = new float[list.size()];
                mArrThisSeniorUndergradUnivRating = new float[list.size()];
                mArrThisSeniorNewUnivRating = new float[list.size()];
                mArrThisSeniorUserAvgRating = new float[list.size()];
                mArrSimilarityInUsers = new float[list.size()];
                arrObjUserDetails = new UserDetails[list.size()];

                float GreScore2, UndergradPercent2;

                Log.e(TAG, "list size -1" + list.size() + -1);
                Log.e(TAG, "************************************SENIOR USER*******************************************************");
                for (ParseObject po : list)     //// for each po object mentioning to each row/object of senior
                {
                    i++;
                    GreScore2 = Float.valueOf(po.get("greScore").toString());
                    UndergradPercent2 = Float.valueOf(po.get("undergradPercent").toString());
                    mArrSeniorStudName[i] = po.get("fullName").toString();
                    mArrSeniorPrevUnivName[i] = po.get("undergradUniv").toString();
                    mArrSeniorNewUnivName[i] = po.get("newUnivName").toString();
                    mArrThisSeniorGreRating[i] = getGreRating(GreScore2);
                    mArrThisSeniorUndergradGPARating[i] = getUndergradStudRating(UndergradPercent2);

                    Log.e(TAG, "i is: " + i);

                    try {
                        ParseObject preunivname = po.getParseObject("prevUnivName");    // get object value stored in column "prevUnivName"
                        ParseObject nowunivname = po.getParseObject("nowUnivName");     // get object value stored in column "nowUnivName"

                        mArrThisSeniorUndergradUnivRating[i] = (float) preunivname.fetchIfNeeded().getInt("univRating");
                        mArrThisSeniorUserAvgRating[i] = getThisUserAvgRatings(mArrThisSeniorGreRating[i], mArrThisSeniorUndergradGPARating[i], mArrThisSeniorUndergradUnivRating[i]);
                        mArrThisSeniorNewUnivRating[i] = (float) nowunivname.fetchIfNeeded().getInt("univRating");

                        /* ************************** Print Log statements ************************** */
                        Log.e(TAG, "greRating2: " + mArrThisSeniorGreRating[i] + ", undergradStudRating2: " + mArrThisSeniorUndergradGPARating[i] + ", mArrThisSeniorUndergradUnivRating["
                                + i + "] : " + mArrThisSeniorUndergradUnivRating[i] + ", mArrThisSeniorNewUnivRating[" + i + "]: " + mArrThisSeniorNewUnivRating[i]);
                        Log.e(TAG, "This Senior " + mArrSeniorStudName[i] + ":" + i + " has total Average rating: " + mArrThisSeniorUserAvgRating[i]);
                        Log.e(TAG, "---------------------------------------------");
                        Log.e(TAG, "greRating1: " + currUserGreRating + ",   undergradUnivRating1: " + currUserUndergradUnivRating + ",  undergradStudRating21: " + currUserUndergradPercentRating);
                        Log.e(TAG, "Current new Student " + currUser.getUsername() + " has total Average rating: " + mCurrentUserAvgRating);
                        Log.e(TAG, "---------------------------------------------");
                        /* ************************************************************************* */

                    } catch (Exception ex3) {
                        Log.e("TAG", "excpt is : " + ex3);
                    }

                    /* ************************** get Similarity of Each Senior user with current new user ************************** */
                    mArrSimilarityInUsers[i] = getUserSimilarities(i);
                    Log.e(TAG, "sim(" + currUser.getUsername() + ", " + mArrSeniorStudName[i] + " ) ::: " + mArrSimilarityInUsers[i]);
                    Log.e(TAG, "_____________________________________________________________________________________");
                    /* *********************************************************************************************************** */

                }  //end of for loop

                for (j = 0; j < list.size(); j++) {
                    arrObjUserDetails[j] = new UserDetails();
                    arrObjUserDetails[j].setmArrSeniorStudName(mArrSeniorStudName[j]);
                    arrObjUserDetails[j].setmArrSeniorPrevUnivName(mArrSeniorPrevUnivName[j]);
                    arrObjUserDetails[j].setmArrSeniorNewUnivName(mArrSeniorNewUnivName[j]);
                    arrObjUserDetails[j].setmArrThisSeniorGreRating(mArrThisSeniorGreRating[j]);
                    arrObjUserDetails[j].setmArrThisSeniorUndergradGPARating(mArrThisSeniorUndergradGPARating[j]);
                    arrObjUserDetails[j].setmArrThisSeniorUndergradUnivRating(mArrThisSeniorUndergradUnivRating[j]);
                    arrObjUserDetails[j].setmArrThisSeniorUserAvgRating(mArrThisSeniorUserAvgRating[j]);
                    arrObjUserDetails[j].setmArrThisSeniorNewUnivRating(mArrThisSeniorNewUnivRating[j]);

                    arrObjUserDetails[j].setmArrSimilarityInUsers(mArrSimilarityInUsers[j]);

                }


                topSimilarities(list.size() - 1);

            } //end of void done() under method getEachSeniorRating()
        });
    } //end of getEachSeniorRating()


    void topSimilarities(int size){
        UserDetails[] tempArrObj = new UserDetails[size+1];
        tempArrObj[0] = new UserDetails();
        tempArrObj[1] = new UserDetails();
        tempArrObj[2] = new UserDetails();
        int k=0;

        float[] tempArrSimInUsers = new float[size+1];

        /*  **NOTE: here clone() is used to copy one array into another completely**  */
        tempArrSimInUsers = mArrSimilarityInUsers.clone();  // put similarity array into temp similarity array
        Arrays.sort(tempArrSimInUsers);
        Log.e(TAG,"mArrSimilarityInUsers["+0+"]"+mArrSimilarityInUsers[0] + "mArrSimilarityInUsers["+size+"]"+mArrSimilarityInUsers[size]);
        Log.e(TAG, "First 3 similarities: " + tempArrSimInUsers[size] + ", " + tempArrSimInUsers[size-1] + ", " + tempArrSimInUsers[size-2]);

        j =0;
        while(j <= size){
            while(mArrSimilarityInUsers[j] != tempArrSimInUsers[size] && j<=size ){
                j++;
            }
            Log.e(TAG,"j: "+j);
            tempArrObj[k] = arrObjUserDetails[j];
            size--;
            k++;
            j=0;
            if(k >2){
                break;
            }
        }
        Log.e(TAG, "tempArrObj[0]"+tempArrObj[0].getmArrSimilarityInUsers());
        Log.e(TAG, "tempArrObj[1]" + tempArrObj[1].getmArrSimilarityInUsers());
        Log.e(TAG, "tempArrObj[2]" + tempArrObj[2].getmArrSimilarityInUsers());

        float topFirstSimilarity, topSecondSimilarity, topThirdSimilarity;
        topFirstSimilarity =tempArrObj[0].getmArrSimilarityInUsers();
        topSecondSimilarity =tempArrObj[1].getmArrSimilarityInUsers();
        topThirdSimilarity =tempArrObj[2].getmArrSimilarityInUsers();

        /*  ****************** done with top similarities  **************** */
        Log.e(TAG,"________________________________ ***Start prediction *** _________________________________________");

        float firstSenRatingDiff, secSenRatingDiff, thirdSenRatingDiff;
        float firstMulti, secMulti, thirdMulti;
        float numeratorResult, denominatorResult, predict;
        firstSenRatingDiff = tempArrObj[0].getmArrThisSeniorNewUnivRating() - tempArrObj[0].getmArrThisSeniorUserAvgRating();
        secSenRatingDiff =   tempArrObj[1].getmArrThisSeniorNewUnivRating() - tempArrObj[1].getmArrThisSeniorUserAvgRating() ;
        thirdSenRatingDiff = tempArrObj[2].getmArrThisSeniorNewUnivRating() - tempArrObj[2].getmArrThisSeniorUserAvgRating();

        Log.e(TAG, "tempArrObj[0] ThisSeniorNewUnivRating(): "+tempArrObj[0].getmArrThisSeniorNewUnivRating());
        Log.e(TAG, "- tempArrObj[0] ThisSeniorUserAvgRating(): "+tempArrObj[0].getmArrThisSeniorUserAvgRating());

        Log.e(TAG, "tempArrObj[1] ThisSeniorNewUnivRating(): "+tempArrObj[1].getmArrThisSeniorNewUnivRating());
        Log.e(TAG, "- tempArrObj[1] ThisSeniorUserAvgRating(): "+tempArrObj[1].getmArrThisSeniorUserAvgRating());

        Log.e(TAG, "tempArrObj[2] ThisSeniorNewUnivRating(): "+tempArrObj[2].getmArrThisSeniorNewUnivRating());
        Log.e(TAG, "- tempArrObj[2] ThisSeniorUserAvgRating(): "+tempArrObj[2].getmArrThisSeniorUserAvgRating());
        Log.e(TAG, "------------------------------------------------------------------------------------------");

        firstMulti = topFirstSimilarity
                * firstSenRatingDiff;
        secMulti = topSecondSimilarity
                * secSenRatingDiff;
        thirdMulti = topThirdSimilarity
                * thirdSenRatingDiff;
        Log.e(TAG, "firstSenRatingDiff: "+firstSenRatingDiff);
        Log.e(TAG, "secSenRatingDiff: "+secSenRatingDiff);
        Log.e(TAG, "thirdSenRatingDiff: "+thirdSenRatingDiff);

        numeratorResult =  mCurrentUserAvgRating + firstMulti + secMulti + thirdMulti;

        Log.e(TAG, "mCurrentUserAvgRating: "+mCurrentUserAvgRating);
        Log.e(TAG, "firstMulti: "+firstMulti);
        Log.e(TAG, "secMulti: "+secMulti);
        Log.e(TAG, "thirdMulti: "+thirdMulti);
        Log.e(TAG, "numerator result: "+ numeratorResult);

         // Math.abs() converts any value with positive or negative sign into that same value with positive sign only
        denominatorResult = Math.abs(topFirstSimilarity) + Math.abs(topSecondSimilarity) + Math.abs(topThirdSimilarity);

        Log.e(TAG,"Math.abs(topFirstSimilarity): "+ Math.abs(topFirstSimilarity));
        Log.e(TAG,"Math.abs(topSecondSimilarity): "+ Math.abs(topSecondSimilarity));
        Log.e(TAG,"Math.abs(topThirdSimilarity): "+ Math.abs(topThirdSimilarity));
        Log.e(TAG, "denominator Result: "+ denominatorResult);

        predict = numeratorResult / denominatorResult;
        Log.e(TAG, "predict Result: "+ predict);

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

    float getUserSimilarities(int i) {
        Log.e(TAG, " " + mCurrentUserAvgRating + " , " + mArrThisSeniorUserAvgRating[i] + " , " + currUserGreRating + " , " +
                currUserUndergradPercentRating
                + " , " + currUserUndergradUnivRating + " , " + mArrThisSeniorGreRating[i] + " , " + mArrThisSeniorUndergradGPARating[i]
                + " , " + mArrThisSeniorUndergradUnivRating[i]);

        float oneA, twoA, oneB, twoB, oneC, twoC, oneASq, twoASq, oneBSq, twoBSq, oneCSq, twoCSq;
        float prod1a2a, prod1b2b, prod1c2c;
        float Sum;
        float oneSqRoot, twoSqRoot, prodSqRoot;
        float simResult;

        oneA = currUserGreRating - mCurrentUserAvgRating;
        oneASq = (currUserGreRating - mCurrentUserAvgRating) * (currUserGreRating - mCurrentUserAvgRating);

        twoA = mArrThisSeniorGreRating[i] - mArrThisSeniorUserAvgRating[i];
        twoASq = (mArrThisSeniorGreRating[i] - mArrThisSeniorUserAvgRating[i]) * (mArrThisSeniorGreRating[i] - mArrThisSeniorUserAvgRating[i]);

        oneB = currUserUndergradPercentRating - mCurrentUserAvgRating;
        oneBSq = (currUserUndergradPercentRating - mCurrentUserAvgRating) * (currUserUndergradPercentRating - mCurrentUserAvgRating);

        twoB = mArrThisSeniorUndergradGPARating[i] - mArrThisSeniorUserAvgRating[i];
        twoBSq = (mArrThisSeniorUndergradGPARating[i] - mArrThisSeniorUserAvgRating[i]) * (mArrThisSeniorUndergradGPARating[i] - mArrThisSeniorUserAvgRating[i]);

        oneC = currUserUndergradUnivRating - mCurrentUserAvgRating;
        oneCSq = (currUserUndergradUnivRating - mCurrentUserAvgRating) * (currUserUndergradUnivRating - mCurrentUserAvgRating);

        twoC = mArrThisSeniorUndergradUnivRating[i] - mArrThisSeniorUserAvgRating[i];
        twoCSq = (mArrThisSeniorUndergradUnivRating[i] - mArrThisSeniorUserAvgRating[i]) * (mArrThisSeniorUndergradUnivRating[i] - mArrThisSeniorUserAvgRating[i]);

        prod1a2a = oneA * twoA;
        prod1b2b = oneB * twoB;
        prod1c2c = oneC * twoC;

        Sum = prod1a2a + prod1b2b + prod1c2c;

        oneSqRoot = (float)Math.sqrt(oneASq + oneBSq + oneCSq);
        twoSqRoot = (float)Math.sqrt(twoASq + twoBSq + twoCSq);
        prodSqRoot = oneSqRoot * twoSqRoot;

        simResult = Sum / prodSqRoot;

        return simResult;
    }

    float getPredictions(int j){
        float result=1;
        Log.e(TAG, "mArrSimilarityInUsers["+j+"]: "+ mArrSimilarityInUsers[j]);
        Log.e(TAG, "mArrSimilarityInUsers["+j+-1+"]: "+ mArrSimilarityInUsers[j-1]);
        Log.e(TAG, "mArrSimilarityInUsers["+j+-2+"]: "+ mArrSimilarityInUsers[j-2]);



        return result;
    }


}
