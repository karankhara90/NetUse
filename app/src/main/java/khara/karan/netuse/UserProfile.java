package khara.karan.netuse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class UserProfile extends ActionBarActivity {
    protected TextView mFullName;
    protected TextView mPrevUniv;
    protected TextView mUnivCountry;
    protected TextView mPercentage;
    protected TextView mGreScore;
    protected TextView mToeflScore;

    protected Button mBtnBackProfile;

    ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mFullName = (TextView)findViewById(R.id.textProfileFullName);
        mPrevUniv = (TextView)findViewById(R.id.textProfilePrevUni);
        mUnivCountry = (TextView)findViewById(R.id.textProfileCountry);
        mPercentage = (TextView)findViewById(R.id.textProfilePercent);
        mGreScore = (TextView)findViewById(R.id.textProfileGre);
        mToeflScore = (TextView)findViewById(R.id.textProfileToefl);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
               mFullName.setText(parseObject.get("fullName").toString());
                try{
                    ParseObject ob =  parseObject.getParseObject("prevUnivName");
                    mPrevUniv.setText(ob.fetchIfNeeded().get("univName").toString());
                } catch (Exception ex){
                    Log.e("TAG","ex exception in userprofile is: "+ex);
                }

                mUnivCountry.setText(parseObject.get("country").toString());
                mPercentage.setText(parseObject.get("undergradPercent").toString());
                mGreScore.setText(parseObject.get("greScore").toString());
                mToeflScore.setText(parseObject.get("toeflScore").toString());

                mBtnBackProfile = (Button) findViewById(R.id.btnBackProfile);
                mBtnBackProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UserProfile.this, FutureStudent.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                        startActivity(intent);
                    }
                });
            }
        });
    }

}
