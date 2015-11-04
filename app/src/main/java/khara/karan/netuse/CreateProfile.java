package khara.karan.netuse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class CreateProfile extends ActionBarActivity {

    Spinner spinner1, spinner2;
    protected EditText mFullName, mPercent, mGre, mToefl;
    protected TextView mUserName;
    protected Button mNext;
    protected Context context;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context=this;
       // Intent intent2 = getIntent();
        //String get_username = intent2.getStringExtra("mUsername");
        String get_username = ParseUser.getCurrentUser().getUsername().toString();
        //get value from edit text fields
        mUserName = (TextView) findViewById(R.id.getUserName);
        mUserName.setText(get_username);

        //Toast.makeText(CreateProfile.this, "passed username: "+mUserName.getText().toString(), Toast.LENGTH_LONG).show();

        mFullName = (EditText) findViewById(R.id.myFullName);
        mPercent = (EditText) findViewById(R.id.myPercent);
        mGre = (EditText) findViewById(R.id.myGre);
        mToefl = (EditText) findViewById(R.id.myToefl);
        mNext = (Button) findViewById(R.id.myNext);
        mGre.setError(null);
        //get value from Spinner fields
        spinner1 = (Spinner) findViewById(R.id.spCountry);
        // s1.setOnItemSelectedListener(this);
        List<String> list1 = new ArrayList<String>();
        list1.add("--Choose Country--");
        list1.add("India");
        list1.add("US");
        list1.add("Australia");

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.notifyDataSetChanged();
        spinner1.setAdapter(dataAdapter2);


        spinner2 = (Spinner) findViewById(R.id.spUniversity);

        List<String> list2 = new ArrayList<String>();
        list2.add("--Choose University--");
        list2.add("IIT");
        list2.add("NIT");
        list2.add("Thapar University");
        list2.add("PEC University");
        list2.add(" Manipal University");
        list2.add("VIT Vellore ");
        list2.add("Delhi University ");
        list2.add("UIET Punjab");
        list2.add("Punjab Technical University Colleges");


        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter3.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter3);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mUserName.setText(getIntent().getStringExtra("username_pass"));
                //Bundle bundle = new Bundle();
                //bundle.putString("mTextNewGroup",mTextNewGroup.getText().toString());

                String username = ParseUser.getCurrentUser().getUsername().toString();
                String useremail= ParseUser.getCurrentUser().getEmail().toString();
                //ParseUser.getCurrentUser().pastoString();
                //String username = mUserName.getText().toString();
                Toast.makeText(CreateProfile.this, "getting username: "+username, Toast.LENGTH_LONG).show();
                String fullname = mFullName.getText().toString();
                int percent  = Integer.parseInt(mPercent.getText().toString());
                 int gre    = Integer.parseInt(mGre.getText().toString());
                 int toefl   = Integer.parseInt(mToefl.getText().toString());
                String univname = spinner2.getSelectedItem().toString();
                String country = spinner1.getSelectedItem().toString();


                ParseUser newUser2= ParseUser.getCurrentUser();
                ParseObject userInfo = new ParseObject("UserInfo");

               // ParseUser userInfo= ParseUser.getCurrentUser();
                userInfo.put("userId",newUser2);
                userInfo.put("fullName", fullname);
                userInfo.put("undergradPercent", percent);
                userInfo.put("greScore", gre);
                userInfo.put("toeflScore", toefl);
                userInfo.put("undergradUniv", univname);
                userInfo.put("country", country);
                userInfo.put("newUnivName","blank");
                userInfo.saveInBackground();
                //userInfo.saveInBackground(new MySaveCallBack(context));



                Intent intent = new Intent(CreateProfile.this, FutureStudent.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                startActivity(intent);

                if (username.isEmpty()|| fullname.isEmpty()){ //|| percent.isEmpty() || gre.isEmpty() ||toefl.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder((CreateProfile.this));

                    builder.setMessage(getString(R.string.create_profile_error_message));
                    builder.setTitle(getString(R.string.create_profile_error_title));
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    // create user's profile
                    setProgressBarIndeterminateVisibility(true);
                   /* ParseUser newUser = new ParseUser();

                    //newUser.setUsername(username);
                    //newUser.set
                    newUser.put("fullName", fullname);
                    newUser.put("percent", percent);
                    newUser.put("gre", gre);
                    newUser.put("toefl", toefl);
                    newUser.put("univName",univname);
                    newUser.put("country",country);

                    Intent intent = new Intent(CreateProfile.this, MainActivity.class);
                   // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                    startActivity(intent);  */

                    //newUser.
//                    newUser.signUpInBackground(new SignUpCallback() {
//                        @Override
//                        public void done(ParseException e) {
//                            setProgressBarIndeterminateVisibility(false);
//                            if (e == null) {
//                                // Hooray! Let them use the app now.
//                                Intent intent = new Intent(CreateProfile.this, MainActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
//                                startActivity(intent);
////                            } else {
////                                // Sign up didn't succeed. Look at the ParseException to figure out what went wrong
////                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateProfile.this);
////
////                                builder.setMessage(e.getMessage());
////                                builder.setTitle(getString(R.string.signup_error_title));
////                                builder.setPositiveButton(android.R.string.ok, null);
////                                AlertDialog dialog = builder.create();
////                                dialog.show();
////                            }
//                        }
//                    });


                }

            }

        });


    }

}
