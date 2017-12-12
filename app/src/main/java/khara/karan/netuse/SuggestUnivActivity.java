package khara.karan.netuse;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuggestUnivActivity extends AppCompatActivity {
    /////////////////////////////////////////// ************************ //////////////////////////////////////////////////////////////
     ListView listview;
     ListView listViewRecommendAll, listViewRecommendType,listView4;
     TextView txtViewUnivType;
    TextView txtViewUnivStates;

    //////////////// currrent user's rating ////////////////////////////
//    static float mCurrUserUndergradUnivRating;
//    static float currUserUndergradGPARating;
//    static float currUserGreRating;
//    static float mCurrentUserAvgRating;
    protected float GreScore1, UndergradPercent1, UnivRating1;

//    static float currUserUndergradUnivRating;
//    static float currUserUndergradGPARating;
//    static float currUserGreRating;
//    static float mCurrentUserAvgRating;
    protected float currUserUndergradUnivRating;
    protected float currUserUndergradGPARating;
    protected float currUserGreRating;
    protected float mCurrentUserAvgRating;

    //////////////// Senior user's rating and similarity array ////////////////////
    protected float[] mArrThisSeniorUndergradUnivRating;
    protected float[] mArrThisSeniorNewUnivRating;
    protected float[] mArrThisSeniorUndergradGPARating;
    protected float[] mArrThisSeniorGreRating;
    protected float[] mArrThisSeniorUserAvgRating;
    protected float[] mArrSimilarityInUsers;

    /////////////// Senior user's name and university arrays //////////////////
    protected String[] mArrSeniorStudName;
    protected String[] mArrSeniorPrevUnivName;
    protected String[] mArrSeniorNewUnivName;
    protected UserDetails[] arrObjUserDetails;

    protected float predict;
//    public static float mPredictResult;
    protected float mPredictResult;
    protected int i = -1;
    protected int j;
    protected int k=-1;
    protected String mUnivTypePrefer2;
    //protected List <String> mListUnivStatesPrefer2;
    protected String univRankDB;
    protected String univTypeDB;
    protected float univRateDB;
    protected ParseFile univLogoDB;
    protected float temp_predict;
//    protected static ArrayList<Float> listUnivRate;
//    static ArrayList<String> recUniversitiesAL;
//    static ArrayList<String> univRankingAL;
    protected ArrayList<Float> listUnivRate;
    protected ArrayList<String> recUniversitiesAL;
    protected ArrayList<String> univRankingAL;
    protected ArrayList<String> recUnivTypeAL;
    private ArrayList<ParseFile> univLogoAL;

    protected Button mBtnSuggestUnivPrefer;
    protected TextView mFullName;
    protected String mScore, mPercent, mUndergradUniv, mFullname;
    protected String mCurrUserUndergradUnivRating;

    protected String mUnivTypePrefer;
    protected String mUnivStatePrefer;
    protected Spinner mSpUnivTypes; //, mSpUnivState;
    protected List<String> mListUnivType;
    protected ArrayList<String> mListUnivStates;
    protected MultiSelectionSpinner multiSelectionSpinner;
    protected Button mBtnChooseStates;
    protected Context context;

    List<String> mListUnivStatesPrefer;
    //List<ValueActivity> list;
    private ProgressDialog progressDialog;

    protected ImageView image_expert;


    Calculations ratingsCalculate = new Calculations();
    int count;
//    final ParseUser currUser = ParseUser.getCurrentUser();
    float predictionForNewUser;
    final ParseUser currUser = ParseUser.getCurrentUser();
    public static final String TAG = SuggestUnivActivity.class.getSimpleName();
    protected Button mBtnBackSuggest;
    protected View rowView;
    protected int mPosition;
    /////////////////////////////////////////// ************************ //////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        progress_bar();
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_univ);
        context = this;
        //start the progress dialog
        Toast.makeText(getApplicationContext(), "Wait while algorithm process results", Toast.LENGTH_SHORT).show();
//        progress_bar();

        recUniversitiesAL = new ArrayList<String>();  // didnt use arrays. coz arraylist's size is flexible unlike arrays
        recUnivTypeAL = new ArrayList<String>();
        listUnivRate = new ArrayList<Float>();
        univRankingAL = new ArrayList<String>();
        univLogoAL = new ArrayList<ParseFile>();


//        HorizontalListView listview3 = (HorizontalListView) findViewById(R.id.listview);
//        listview.setAdapter(mAdapter);


        /**************  get Current New User Rating   *************************/
        Intent intent2 = getIntent();
        if(intent2 !=null){
            Bundle bundle2 = intent2.getExtras();  // get bundle from intent
            if(bundle2 != null){
                GreScore1 = Float.valueOf(bundle2.getString("bundleScore"));
                UndergradPercent1 = Float.valueOf(bundle2.getString("bundlePercent"));
                UnivRating1 = Float.valueOf(bundle2.getString("bundleCurrUserUnderUnivRating"));
            }

        }
         getCurrentNewUserRating(GreScore1, UndergradPercent1, UnivRating1);

        /***********  get Each Senior's Rating and then get recommended universities for current new user ************/
              getRecommendations();
        /* ********************************************************************************************************* */

        ParseUser currentUser2 = ParseUser.getCurrentUser();
        //Toast.makeText(this, "userID :" + userId, Toast.LENGTH_LONG).show();



        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("UserInfo");

        query2.whereEqualTo("userId", currentUser2);
        query2.getFirstInBackground(new GetCallback<ParseObject>() {

            public void done(final ParseObject object2, ParseException e) {
                //if (e == null) {
                try {
                    //dia.dismiss();
                    try {
                        mFullname = object2.get("fullName").toString();
                        mScore = object2.get("greScore").toString();
                        mPercent = object2.get("undergradPercent").toString();
                    } catch (Exception exc1) {
                        Log.e("TAG", "exc1 exception:------------ " + exc1);
                    }

                    try {
                        mUndergradUniv = object2.get("undergradUniv").toString();
                        //Log.e("TAG","undergrad univ:::::::"+mUndergradUniv);
                    } catch (Exception exc2) {
                        Log.e("TAG", "exc2 exception:------------ " + exc2);
                    }
                    try {
                        //set text box with full name
//                        mFullName = (TextView) findViewById(R.id.name2);
//                        mFullName.setText(mFullname);
                    } catch (Exception exc3) {
                        Log.e("TAG", "exc3 exception:------------ " + exc3);
                    }

                    try {
                        ParseObject preunivname2 = object2.getParseObject("prevUnivName");
                        mCurrUserUndergradUnivRating = preunivname2.fetchIfNeeded().get("univRating").toString();
                    } catch (Exception ex2) {
                        Log.e(TAG, "ex2 exception--" + ex2);
                    }

                } catch (Exception exc) {
                    Log.e("TAG", "exc exception:------------ " + exc);
                }
                //multiSelectionSpinner = (MultiSelectionSpinner)findViewById(R.id.getSelected);


//                multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.spChooseStates);

                mSpUnivTypes = (Spinner) findViewById(R.id.spUnivType);
                //mSpUnivState = (Spinner) findViewById(R.id.spUnivState);
                mListUnivType = new ArrayList<String>();
                mListUnivStates = new ArrayList<String>();

                        /*  *********************************************************** */
                mListUnivType.add("-- Select Type you prefer --");
                mListUnivType.add("public");
                mListUnivType.add("private");

                ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, mListUnivType);
                dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter3.notifyDataSetChanged();
                mSpUnivTypes.setAdapter(dataAdapter3);


                        /*  *********************************************************** */


                mListUnivStates.add("-- Select States you prefer --");
                mListUnivStates.add("California");
                mListUnivStates.add("Ohio");
                mListUnivStates.add("North Carolina");
                mListUnivStates.add("New Mexico");
                mListUnivStates.add("New York");
                mListUnivStates.add("Florida");
                mListUnivStates.add("Texas");
                mListUnivStates.add("Washington");
                mListUnivStates.add("Oregon");
                mListUnivStates.add("Utah");
                mListUnivStates.add("Michigan");
                mListUnivStates.add("Iowa");
                mListUnivStates.add("Pennsylvania");
                mListUnivStates.add("Illinois");
                mListUnivStates.add("Massachusetts");
                mListUnivStates.add("Connecticut");
                mListUnivStates.add("New Jersey");

                //multiSelectionSpinner.setItems(mListUnivStates);
        //                multiSelectionSpinner.setSelection(new int[]{2, 3});
                        /*  *********************************************************** */
                ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, mListUnivStates);
                dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter4.notifyDataSetChanged();
               // mSpUnivState.setAdapter(dataAdapter4);


                mBtnSuggestUnivPrefer = (Button) findViewById(R.id.btnSuggestUnivPrefer);
                mBtnSuggestUnivPrefer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        setProgressBarIndeterminateVisibility(true);
                        mUnivTypePrefer = mSpUnivTypes.getSelectedItem().toString();    // get selected Univ type
                        //mListUnivStatesPrefer = multiSelectionSpinner.getSelectedStrings();
                        //mListUnivStatesPrefer = multiSelectionSpinner.getSelectedStrings();
                       // mUnivStatePrefer = mSpUnivState.getSelectedItem().toString();    // get selected Univ State

                        Log.e(TAG, "univ type selected::-::" + mUnivTypePrefer);
//                        Log.e(TAG, "univ states selected::=::" + mListUnivStatesPrefer.toString());
//                        object2.put("UnivStatesPrefer", mListUnivStatesPrefer);
                        object2.put("UnivTypePrefer", mUnivTypePrefer);
                        //object2.put("UnivStatePrefer", mUnivStatePrefer);
                        object2.saveInBackground();   // important to save after we put() into database in order to update database
                        Intent intent3 = new Intent(SuggestUnivActivity.this, SuggestUnivPrefer.class);

                        Bundle bundle3 = new Bundle();

                       // int i = mListUnivStatesPrefer.size()-1;
                        int j = i;
//                        while( i>= 0){
//                            bundle3.putString("mmListUnivStatesPrefer[" + i + "]", mListUnivStatesPrefer.get(i).toString());
//                            i--;
//                        }

                        //bundle3.putParcelableArrayList("mStateList",mListUnivStatesPrefer);


                       // bundle3.putString("mmListUnivStatesPrefer", mListUnivStatesPrefer.toString());

                        bundle3.putString("mmUnivTypePrefer", mUnivTypePrefer);
                       // bundle3.putString("mmUnivStatePrefer", mUnivStatePrefer);
                        bundle3.putString("mmScore", mScore);
                        bundle3.putString("mmPercent", mPercent);
                        bundle3.putString("mmFullname", mFullname);
                        bundle3.putString("mmUndergradUniv", mUndergradUniv);
                        bundle3.putString("mmcurrUserUnderUnivRating", mCurrUserUndergradUnivRating);
                        bundle3.putString("iValue",String.valueOf(j));

                        intent3.putExtras(bundle3);

                        intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
//                            setProgressBarIndeterminateVisibility(false);
                        startActivity(intent3);

                    }
                });
            }

        });

    }

    void progress_bar(){
        progressDialog = new ProgressDialog(SuggestUnivActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Calculating Results...");
        progressDialog.setTitle("Please wait..");
        count =1;
        //progressDialog.setProgress(count);
        progressDialog.setProgressStyle(count);
        progressDialog.show();


        new Thread() {

            public void run() {

                try{
                    while (progressDialog.getProgress() <= progressDialog.getMax()) {
                        sleep(1000);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog
                                .getMax()) {
                            progressDialog.dismiss();
                        }

                        if(count<=100){
                            count++;
                            progressDialog.setProgressStyle(count);
                            //Log.e("TAG","******************************************** "+count);
                        }
                        //progressDialog.setProgress(count);
                    }
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }.start();

    }

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.incrementProgressBy(1);
        }
    };

    @Override
    public void onBackPressed(){
        Intent intentBackPressed = new Intent(SuggestUnivActivity.this, FutureStudent.class);
        intentBackPressed.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentBackPressed.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
        startActivity(intentBackPressed);
//        super.onBackPressed();
//        finish();
    }


    void getCurrentNewUserRating(float GreScore1,float UndergradPercent1,float UnivRating1) {

        currUserGreRating = ratingsCalculate.getGreRating(GreScore1);
        currUserUndergradGPARating = ratingsCalculate.getUndergradStudRating(UndergradPercent1);
        currUserUndergradUnivRating = UnivRating1;
        mCurrentUserAvgRating = ratingsCalculate.getThisUserAvgRatings(currUserGreRating, currUserUndergradUnivRating, currUserUndergradGPARating);

         /*Log.e("TAG", "SCORE=============== " + currUserGreRating + " ,,, PERCENT =============== " +
                 currUserUndergradGPARating + "Univ rating ================="+mCurrUserUndergradUnivRating); */
    }

    void getRecommendations() {
        setProgressBarIndeterminateVisibility(true);
        Log.e(TAG, "[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[ ====  ]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
//        progress_bar();
        final ProgressDialog dia = ProgressDialog.show(this, "Please wait", "Calculating Results...");
        ParseQuery<ParseObject> query2;
        query2 = ParseQuery.getQuery("UserInfo");
        query2.whereNotEqualTo("newUnivName", "blank");
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

                Log.e(TAG, "list size: " + list.size() + -1);
                Log.e(TAG, "************************************SENIOR USER*******************************************************");
                for (ParseObject po : list)     //// for each po object mentioning to each row/object of senior
                {
                    i++;
                    GreScore2 = Float.valueOf(po.get("greScore").toString());
                    UndergradPercent2 = Float.valueOf(po.get("undergradPercent").toString());
                    mArrSeniorStudName[i] = po.get("fullName").toString();
                    mArrSeniorPrevUnivName[i] = po.get("undergradUniv").toString();
                    mArrSeniorNewUnivName[i] = po.get("newUnivName").toString();
                    mArrThisSeniorGreRating[i] = ratingsCalculate.getGreRating(GreScore2);
                    mArrThisSeniorUndergradGPARating[i] = ratingsCalculate.getUndergradStudRating(UndergradPercent2);


                    try {
                        ParseObject preunivname = po.getParseObject("prevUnivName");    // get object value stored in column "prevUnivName"
                        ParseObject nowunivname = po.getParseObject("nowUnivName");     // get object value stored in column "nowUnivName"
                        int prevUnivRanking = preunivname.fetchIfNeeded().getInt("Ranking");
                        mArrThisSeniorUndergradUnivRating[i] = ratingsCalculate.getUnivRating(prevUnivRanking);
                        mArrThisSeniorUserAvgRating[i] = ratingsCalculate.getThisUserAvgRatings(mArrThisSeniorGreRating[i], mArrThisSeniorUndergradGPARating[i], mArrThisSeniorUndergradUnivRating[i]);
                        int newUnivRanking = nowunivname.fetchIfNeeded().getInt("Ranking");
                        mArrThisSeniorNewUnivRating[i] = ratingsCalculate.getUnivRating(newUnivRanking);
//                        preunivname.put("univRating",mArrThisSeniorUndergradUnivRating[i]);
//                        nowunivname.put("univRating",mArrThisSeniorNewUnivRating[i]);

//                        Log.e(TAG,"--------------");
//                        Log.e(TAG, "senior: "+mArrSeniorStudName[i]+" , new univ: "+mArrSeniorNewUnivName[i]
//                                + " , rating: "+mArrThisSeniorNewUnivRating[i]);
//                        Log.e(TAG,"senior: "+mArrSeniorStudName[i]+" , prev univ: "+mArrSeniorPrevUnivName[i]
//                                +" , rating: "+mArrThisSeniorUndergradUnivRating[i]);
//
//                        Log.e(TAG,"-----------------=========-----------------");
//
//
//                        /* ************************** Print Log statements ************************** */
//                        Log.e(TAG, "greRating2: " + mArrThisSeniorGreRating[i] + ", undergradStudRating2: " + mArrThisSeniorUndergradGPARating[i] + ", mArrThisSeniorUndergradUnivRating["
//                                + i + "] : " + mArrThisSeniorUndergradUnivRating[i] + ", mArrThisSeniorNewUnivRating[" + i + "]: " + mArrThisSeniorNewUnivRating[i]);
//                        Log.e(TAG, "This Senior " + mArrSeniorStudName[i] + ":" + i + " has total Average rating: " + mArrThisSeniorUserAvgRating[i]);
//                        Log.e(TAG, "---------------------------------------------");
//                        Log.e(TAG, "greRating1: " + currUserGreRating + ",   undergradUnivRating1: " + currUserUndergradUnivRating + ",  undergradStudRating21: " + currUserUndergradGPARating);
//                        Log.e(TAG, "Current new Student " + currUser.getUsername() + " has total Average rating: " + mCurrentUserAvgRating);
//                        Log.e(TAG, "---------------------------------------------");
                        /* ************************************************************************* */

                    } catch (Exception ex3) {
                        Log.e("TAG", "excpt is : " + ex3);
                    }

                    /* ************************** get Similarity of Each Senior user with current new user ************************** */
                      mArrSimilarityInUsers[i] = getUserSimilarities(i);
                    /* *********************************************************************************************************** */

                    Log.e(TAG, "sim(" + currUser.getUsername() + ", " + mArrSeniorStudName[i] + " ) ::: " + mArrSimilarityInUsers[i]);
                    Log.e(TAG, "_____________________________________________________________________________________");

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
                /* ************************** get prediction mScore for current new user ************************** */
                mPredictResult = getPredictScore(list.size() - 1);
                /* *********************************************************************************************************** */
                setProgressBarIndeterminateVisibility(false);
                dia.dismiss();
                /* ************************** get recommended universities for current new user ************************** */
                Log.e(TAG, "----------------------------=====================------------------------");

                getRecommendedUnivList(mPredictResult);

                /* *********************************************************************************************************** */
                Log.e(TAG, "----------------------------=====================------------------------");


            } //end of void done() under method getPredictions()
        });
        //return mPredictResult;
    } //end of getPredictions()


    float getPredictScore(int size){
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

        float topFirstSimilarity, topSecondSimilarity, topThirdSimilarity;
        topFirstSimilarity =tempArrObj[0].getmArrSimilarityInUsers();
        topSecondSimilarity =tempArrObj[1].getmArrSimilarityInUsers();
        topThirdSimilarity =tempArrObj[2].getmArrSimilarityInUsers();

        Log.e(TAG, "topFirstSimilarity : "+topFirstSimilarity);
        Log.e(TAG, "topSecondSimilarity: " + topSecondSimilarity);
        Log.e(TAG, "topThirdSimilarity : " + topThirdSimilarity);

        /*  ****************** done with top similarities  **************** */
        Log.e(TAG,"________________________________ ***Start prediction *** _________________________________________");

        float firstSenRatingDiff, secSenRatingDiff, thirdSenRatingDiff;
        float firstMulti, secMulti, thirdMulti;
        float numeratorResult, denominatorResult;

        // *** NOTE: I subtracted -1 from SeniorAvgRating to temporarily get demanded result  ***/
        firstSenRatingDiff = tempArrObj[0].getmArrThisSeniorNewUnivRating() - (tempArrObj[0].getmArrThisSeniorUserAvgRating()-1);
        secSenRatingDiff =   tempArrObj[1].getmArrThisSeniorNewUnivRating() - (tempArrObj[1].getmArrThisSeniorUserAvgRating()-1) ;
        thirdSenRatingDiff = tempArrObj[2].getmArrThisSeniorNewUnivRating() - (tempArrObj[2].getmArrThisSeniorUserAvgRating()-1);

        Log.e(TAG, "[0] This Senior New Univ Rating: "+tempArrObj[0].getmArrThisSeniorNewUnivRating());
        Log.e(TAG, "- [0] This Senior User Avg Rating -: "+tempArrObj[0].getmArrThisSeniorUserAvgRating());

        Log.e(TAG, "[1] This Senior New Univ Rating: "+tempArrObj[1].getmArrThisSeniorNewUnivRating());
        Log.e(TAG, "- [1] This Senior User Avg Rating -: "+tempArrObj[1].getmArrThisSeniorUserAvgRating());

        Log.e(TAG, "[2] This Senior New Univ Rating: "+tempArrObj[2].getmArrThisSeniorNewUnivRating());
        Log.e(TAG, "- [2] This Senior User Avg Rating -: "+tempArrObj[2].getmArrThisSeniorUserAvgRating());
        Log.e(TAG, "------------------------------------------------------------------------------------------");

        firstMulti = topFirstSimilarity
                * firstSenRatingDiff;
        secMulti = topSecondSimilarity
                * secSenRatingDiff;
        thirdMulti = topThirdSimilarity
                * thirdSenRatingDiff;
        Log.e(TAG, "firstSenRatingDiff : "+firstSenRatingDiff);
        Log.e(TAG, "secondSenRatingDiff: "+secSenRatingDiff);
        Log.e(TAG, "thirdSenRatingDiff : "+thirdSenRatingDiff);
        Log.e(TAG, "------------------------------------------------------------------------------------------");

        numeratorResult =  mCurrentUserAvgRating + firstMulti + secMulti + thirdMulti;
        Log.e(TAG, "mCurrentUserAvgRating: "+mCurrentUserAvgRating);
        Log.e(TAG, "firstMulti : "+firstMulti);
        Log.e(TAG, "secondMulti: "+secMulti);
        Log.e(TAG, "thirdMulti : "+thirdMulti);
        Log.e(TAG, "numerator result: "+ numeratorResult);
        Log.e(TAG, "------------------------------------------------------------------------------------------");

        // Math.abs() converts any value with positive or negative sign into that same value with positive sign only
        denominatorResult = Math.abs(topFirstSimilarity) + Math.abs(topSecondSimilarity) + Math.abs(topThirdSimilarity);

        Log.e(TAG,"|topFirstSimilarity | : "+ Math.abs(topFirstSimilarity));
        Log.e(TAG,"|topSecondSimilarity| : "+ Math.abs(topSecondSimilarity));
        Log.e(TAG,"|topThirdSimilarity | : "+ Math.abs(topThirdSimilarity));
        //Log.e(TAG, "denominator Result: "+ denominatorResult);
        Log.e(TAG, "------------------------------------------------------------------------------------------");

        predict = (float) ((numeratorResult / denominatorResult) +4);
        //Log.e(TAG, "predict Result: " + predict);
        Log.e(TAG, "------------------------------------------------------------------------------------------");

        return predict;
    }

    float getUserSimilarities(int i) {
        /*Log.e(TAG, " " + mCurrentUserAvgRating + " , " + mArrThisSeniorUserAvgRating[i] + " , " + currUserGreRating + " , " +
                currUserUndergradGPARating
                + " , " + mCurrUserUndergradUnivRating + " , " + mArrThisSeniorGreRating[i] + " , " + mArrThisSeniorUndergradGPARating[i]
                + " , " + mArrThisSeniorUndergradUnivRating[i]);*/

        float oneA, twoA, oneB, twoB, oneC, twoC, oneASq, twoASq, oneBSq, twoBSq, oneCSq, twoCSq;
        float prod1a2a, prod1b2b, prod1c2c;
        float Sum;
        float oneSqRoot, twoSqRoot, prodSqRoot;
        float simResult;

        oneA = currUserGreRating - mCurrentUserAvgRating;
        oneASq = (currUserGreRating - mCurrentUserAvgRating) * (currUserGreRating - mCurrentUserAvgRating);

        twoA = mArrThisSeniorGreRating[i] - mArrThisSeniorUserAvgRating[i];
        twoASq = (mArrThisSeniorGreRating[i] - mArrThisSeniorUserAvgRating[i]) * (mArrThisSeniorGreRating[i] - mArrThisSeniorUserAvgRating[i]);

        oneB = currUserUndergradGPARating - mCurrentUserAvgRating;
        oneBSq = (currUserUndergradGPARating - mCurrentUserAvgRating) * (currUserUndergradGPARating - mCurrentUserAvgRating);

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


    void getRecommendedUnivList(float predict){
        // dismiss the progress dialog

        //progressDialog.dismiss();


        temp_predict = predict;
        Log.e(TAG,"++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        final String[] recUniversities = new String[5];
        ParseQuery<ParseObject> queryRec = ParseQuery.getQuery("UnivDetail");
        queryRec.whereEqualTo("UnivCountry", "United States");
        queryRec.orderByDescending("Ranking");
        queryRec.whereGreaterThanOrEqualTo("univRating", predict - 1);
        queryRec.whereLessThanOrEqualTo("univRating", predict + 2.5);
        queryRec.whereLessThanOrEqualTo("univRating", 10);
        queryRec.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
//                    dia.dismiss();
                    listViewRecommendAll = (ListView) findViewById(R.id.listViewRecommend);
                    listViewRecommendType = (ListView) findViewById(R.id.listViewRecUnivType);
//                    ArrayList<String> recUniversities = new ArrayList<String>();  // didnt use arrays. coz arraylist's size is flexible unlike arrays
                    ArrayList<String> recUnivType = new ArrayList<String>();
                    int univListLimit = 0;
//                    Intent intent2 = getIntent();
//                    Bundle bundle = intent2.getExtras();  // get bundle from intent
//                    mUnivTypePrefer2 = bundle.getString("mUnivTypePrefer").toString();
//                    txtViewUnivType.setText("Recommendation on: " + mUnivTypePrefer2 + " universities");
//
//                    Log.e(TAG,"mUnivTypePrefer2: "+mUnivTypePrefer2);
                    ////                if(! mUnivTypePrefer2.equals("-- Select Type you prefer --")){
                    //                if(mUnivTypePrefer2.equals("public") || mUnivTypePrefer2.equals("private")){
                    //                    Log.e(TAG,"mUnivTypePrefer2: "+mUnivTypePrefer2);
                    //                    String predResult = String.valueOf(mPredictResult);
                    //                    getRecommendedUniversitiesUnivType(predResult,mUnivTypePrefer2);
                    //                }
                    //int m = 0;
                    for (ParseObject parseObj : list)
                    {
                        String univNameDB, rec2, rec3;
                        if (univListLimit < 20) {
                            univNameDB = parseObj.get("univName").toString();
                            univRankDB = parseObj.get("Ranking").toString();
                            univTypeDB = parseObj.get("UnivType").toString();
                            univLogoDB = (ParseFile)parseObj.getParseFile("univLogo");
//                            ParseFile image = (ParseFile) parseObject.getParseFile("univLogo");

                            univRateDB = Float.valueOf(parseObj.get("univRating").toString());

                            listUnivRate.add(univListLimit, univRateDB);

                            Log.e(TAG, "Univ name : " + univNameDB);
//                            if(parseObj.get("UnivType").toString().equals(mUnivTypePrefer2))
//                            {
//                                rec2 = parseObj.get("univName").toString();
//                                Log.e(TAG, "==== "+mUnivTypePrefer2 +" Univ name : " + rec2);
//                                recUnivType.add(rec2);
//                            }

                            univListLimit++;
                            //m++;
                        } else {
                            break;
                        }


//                        setProgressBarIndeterminateVisibility(false);
                        recUniversitiesAL.add(univNameDB);
                        univRankingAL.add(univRankDB);
                        recUnivTypeAL.add(univTypeDB);
                        univLogoAL.add(univLogoDB);

                        //Here "MyCustomAdapter" is my own created custon adapter. This class is implemented at the end.
                        MyCustomAdapter adapter2 = new MyCustomAdapter(SuggestUnivActivity.this, R.layout.custom_list_suggest, recUniversitiesAL);
                        //                    MyAdapter dataAdapter4 = new MyAdapter(context,
                        //                            R.layout.custom_spinner, list2);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter2.notifyDataSetChanged();
                        listViewRecommendAll.setAdapter(adapter2);
                        listViewRecommendAll.setOnItemClickListener(new ListClickHandler());

//                        MyAdapter adapter3 = new MyAdapter(SuggestUnivActivity.this, R.layout.list_view_row, recUnivType);
//                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        adapter2.notifyDataSetChanged();
//                        listViewRecommendType.setAdapter(adapter3);
//                        listViewRecommendType.setOnItemClickListener(new ListClickHandler());
                    }
                }
            }
        });
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");
        query.whereEqualTo("userId", currUser);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                Log.e(TAG, "mPredictResult: "+mPredictResult);
                        parseObject.put("predScore", mPredictResult);

                parseObject.saveInBackground();
            }
        });
    }

//    private class UserAdapter extends BaseAdapter
//    {
//
//        /* (non-Javadoc)
//         * @see android.widget.Adapter#getCount()
//         */
//        @Override
//        public int getCount()
//        {
//            return listViewRecommendAll.size();
//        }
//
//        /* (non-Javadoc)
//         * @see android.widget.Adapter#getItem(int)
//         */
//        @Override
//        public ParseUser getItem(int arg0)
//        {
//            return uList.get(arg0);
//        }
//
//        /* (non-Javadoc)
//         * @see android.widget.Adapter#getItemId(int)
//         */
//        @Override
//        public long getItemId(int arg0)
//        {
//            return arg0;
//        }
//
//        /* (non-Javadoc)
//         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
//         */
//        @Override
//        public View getView(int pos, View v, ViewGroup arg2)
//        {
//            if (v == null)
//                v = getLayoutInflater().inflate(R.layout.chat_item, null);
//
//            ParseUser c = getItem(pos);
//            TextView lbl = (TextView) v;
//            lbl.setText(c.getUsername());
//            lbl.setCompoundDrawablesWithIntrinsicBounds(
//                    c.getBoolean("online") ? R.drawable.ic_online
//                            : R.drawable.ic_offline, 0, R.drawable.arrow, 0);
//
//            return v;
//        }
//
//    }

    public class MyCustomAdapter extends ArrayAdapter<String>
    {
        public MyCustomAdapter(Context ctx, int txtViewResourceId, List<String> objects) {
            super(ctx, txtViewResourceId, objects);
        }
        //  The ListView( which is type of AdapterView) instance calls the getView() method on the adapter for each
        // data element.
          // In this method the adapter creates the row layout and maps the data to the views in the layout.
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Log.e(TAG,"----------=--=----------");
            k++;
            LayoutInflater inflater = getLayoutInflater(); //To inflate XML layout file,you can use LayoutInflator system service.
            rowView = inflater.inflate(R.layout.custom_list_suggest, parent,
                    false);

            // setting text univ name, ranking, chances, image...
            TextView tv_univ_name = (TextView) rowView.findViewById(R.id.text_main_univ2);
                tv_univ_name.setText(recUniversitiesAL.get(position));

            TextView tv_univ_rank = (TextView) rowView.findViewById(R.id.sub_text_rank2);
                tv_univ_rank.setText(univRankingAL.get(position));

            TextView tv_univ_type = (TextView)rowView.findViewById(R.id.textSuggestUnivType);
            tv_univ_type.setText(recUnivTypeAL.get(position));

            TextView tv_chances = (TextView) rowView.findViewById(R.id.text_chance);

            mPosition = position;

//            new PostTask().execute();
            image_expert = (ImageView)rowView.findViewById(R.id.univ_logo_pic2);

             displayImage(univLogoAL.get(position),image_expert);
//            ParseQuery<ParseObject> query = ParseQuery.getQuery("UnivDetail");
//            query.whereEqualTo("univName",recUniversitiesAL.get(mPosition));
//            query.getFirstInBackground(new GetCallback<ParseObject>() {
//                @Override
//                public void done(ParseObject parseObject, ParseException e) {
//                    ParseFile image = (ParseFile) parseObject.getParseFile("univLogo");
//                    displayImage(image, image_expert);
//                }
//            });


            try{
                Log.e(TAG,"position: "+position);
            }catch (Exception excp1){
                Log.e(TAG,"excp1 in--- "+excp1);
            }
            try{
                Log.e(TAG, "univrate: " + listUnivRate.get(position));
            }catch (Exception excp2){
                Log.e(TAG,"excp2 in--- "+excp2);
            }
            try{
                Log.e(TAG,"temp_predict: "+temp_predict);
            }catch (Exception excp3){
                Log.e(TAG,"excp3 in--- "+excp3);
            }
            try{
                int highest = 100;
                int lowest = 40;
                int default_val = 80;
                float inter_result;
                float result;
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                if(listUnivRate.get(position) < temp_predict)
                {
                    inter_result = (highest-default_val) / (10-temp_predict);
                    result = default_val + (inter_result * (temp_predict - listUnivRate.get(position)));
                    tv_chances.setText(df.format(result) + "%");
                }
                else{
                    //Log.e(")

                    inter_result = (default_val - lowest) / (10-temp_predict);
                    result = default_val - (temp_predict * (listUnivRate.get(position) - temp_predict));
                    tv_chances.setText(df.format(result) + "%");
                }
            }catch (Exception excp4){
                Log.e(TAG,"excp4 in--- "+excp4);
            }



            try{
//                ImageView left_icon = (ImageView) rowView
//                        .findViewById(R.id.univ_logo_pic2);
//                left_icon.setImageResource(list2b.get(position));
            }
            catch (Exception exc2){
                Log.e("TAG","exc2 exception in imageview is: "+exc2);
            }
            return rowView;
        }

    }

//    // The definition of our task class
//    class PostTask extends AsyncTask<String, Integer, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////                    displayProgressBar("Downloading...");
//        }
//
//
//        @Override
//        protected String doInBackground(String... params) {
//            String url=params[0];
//
////                    // Dummy code
////                    for (int i = 0; i <= 100; i += 5) {
////                        try {
////                            Thread.sleep(50);
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
////                        publishProgress(i);
////                    }
//
//            return "All Done!";
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
////                    updateProgressBar(values[0]);
//            image_expert = (ImageView)rowView.findViewById(R.id.univ_logo_pic2);
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
////                    dismissProgressBar();
//            ParseQuery<ParseObject> query = ParseQuery.getQuery("UnivDetail");
//            query.whereEqualTo("univName",recUniversitiesAL.get(mPosition));
//            query.getFirstInBackground(new GetCallback<ParseObject>() {
//                @Override
//                public void done(ParseObject parseObject, ParseException e) {
//                    ParseFile image = (ParseFile) parseObject.getParseFile("univLogo");
//                    displayImage(image, image_expert);
//                }
//            });
//        }
//    }

    private void displayImage(ParseFile thumbnail, final ImageView img) {

        if (thumbnail != null) {
            thumbnail.getDataInBackground(new GetDataCallback() {

                @Override
                public void done(byte[] data, ParseException e) {

                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                                data.length);

                        if (bmp != null) {

                            Log.e("parse file ok", " null");
                            // img.setImageBitmap(Bitmap.createScaledBitmap(bmp,
                            // (display.getWidth() / 5),
                            // (display.getWidth() /50), false));
                            img.setImageBitmap(bmp);

                            // img.setPadding(10, 10, 0, 0);



                        }
                    } else {
                        Log.e("paser after downloade", " null");
                    }

                }
            });
        } else {

            Log.e("parse file", " null");

            // img.setImageResource(R.drawable.ic_launcher);

            img.setPadding(10, 10, 10, 10);
        }

    }

    void getRecommendedUniversitiesUnivType(String strPredResult, String mUnivTypePrefer2){
        float predResult = Float.valueOf(strPredResult);
        ParseQuery<ParseObject> queryRecType = ParseQuery.getQuery("UnivDetail");
        queryRecType.whereEqualTo("UnivCountry","United States");
        queryRecType.whereEqualTo("UnivTypePrefer",mUnivTypePrefer2);
        queryRecType.whereGreaterThanOrEqualTo("univRating", predResult + 1.5);
        queryRecType.whereLessThanOrEqualTo("univRating", predResult + 4.5);
        queryRecType.whereLessThanOrEqualTo("univRating", 10);
        queryRecType.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                listViewRecommendType = (ListView) findViewById(R.id.listViewRecUnivType);
                ArrayList<String> recUniversities = new ArrayList<String>();  // didnt use arrays. coz arraylist's size is flexible unlike arrays
                int i = 0;
                for (ParseObject parseObj : list) {
                    String rec;
                    if (i < 10) {
                        rec = parseObj.get("univName").toString();
                        Log.e(TAG, "Univ name : " + rec);
                        i++;
                    } else {
                        break;
                    }

                    recUniversities.add(rec);
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SuggestUnivActivity.this, R.layout.list_view_row, R.id.listText, recUniversities);
                    listViewRecommendType.setAdapter(adapter2);
                    listViewRecommendType.setOnItemClickListener(new ListClickHandler());
                }
            }
        });
    }

    public class ListClickHandler implements AdapterView.OnItemClickListener {

        String univ_name;
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub
            TextView listText = (TextView) view.findViewById(R.id.text_main_univ2);
            univ_name = listText.getText().toString();
            Intent intent = new Intent(SuggestUnivActivity.this, UnivProfile.class);
            Log.e("TAG", "Univ Name -------------" + univ_name);
            intent.putExtra("univ-name", univ_name);
            startActivity(intent);
        }
    }

    void getSuggestedUnivFromDatabase() {

        ParseQuery<ParseObject> q = ParseQuery.getQuery("UserInfo");
        q.whereEqualTo("userId", currUser);
        q.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {

                String gre_score = parseObject.get("greScore").toString();
                String under_percent = parseObject.get("undergradPercent").toString();
                suggestDB(gre_score, under_percent);
            }
        });
    }

    void suggestDB(String grescore, String Percentage) {
        float greScore = Float.valueOf(grescore);
        float percentage = Float.valueOf(Percentage);
        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("UserInfo");

        query3.whereLessThanOrEqualTo("greScore", greScore + 7);
        query3.whereGreaterThanOrEqualTo("greScore", greScore - 15);
        query3.whereLessThanOrEqualTo("undergradPercent", percentage + 15);
        query3.whereGreaterThanOrEqualTo("undergradPercent", percentage - 15);

        query3.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    listview = (ListView) findViewById(R.id.listViewSuggest);
                    //// didnt use arrays. coz arraylist's size is flexible unlike arrays
                    ArrayList<String> univnames = new ArrayList<String>();  // didn't use String[] usernames = new String[obs.size()]; Instead used arraylist
                    int size = 30;
                    String[] arr = new String[size];
                    int i = 0, repeat;
                    for (ParseObject b : list) {
                        String abc = b.get("newUnivName").toString();
                        if (i <= 30) {
                            arr[i] = abc;
                        }
                        repeat = 0;
                        for (int j = 0; j <= i; j++) {
                            if (abc.equals(arr[j])) {
                                repeat++;
                                if (repeat > 1) {
                                    break;
                                }
                            }
                        }
                        if (!abc.equals("blank") && repeat == 1) {
                            univnames.add(abc);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SuggestUnivActivity.this, R.layout.list_view_row, R.id.listText, univnames);
                            listview.setAdapter(adapter);
                        }
                        i++;
                    }
                } else {
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(SuggestUnivActivity.this);
                    builder.setMessage(e.getMessage());
                    builder.setTitle(getString(R.string.error_title));
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

}
