package khara.karan.netuse;



/**
 * A login screen that offers login via email/password.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {

    protected EditText mUsername;
    protected EditText mPassword;
    protected EditText mEmail;
    Spinner s2, s3;
   // protected EditText mFullName, mPercent, mGre, mToefl;
    protected Button mNext;

    protected Button mSignUpButton;

    //Spinner s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this next line for progress bar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_sign_up);


        // find views by ID -------------------------------------------------
        mUsername = (EditText)findViewById(R.id.usernameField);
        mPassword = (EditText)findViewById(R.id.passwordField);
        mEmail = (EditText)findViewById(R.id.emailField);

     /*--   mFullName = (EditText) findViewById(R.id.myFullName);
        mPercent = (EditText) findViewById(R.id.myPercent);
        mGre = (EditText) findViewById(R.id.myGre);
        mToefl = (EditText) findViewById(R.id.myToefl);
        mNext = (Button) findViewById(R.id.myNext);

        //--get value from Spinner fields
        spinner1 = (Spinner) findViewById(R.id.spCountry);
        // s1.setOnItemSelectedListener(this);
        List<String> list2 = new ArrayList<String>();
        list2.add("--Choose Country--");
        list2.add("India");
        list2.add("US");
        list2.add("Australia");

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.notifyDataSetChanged();
        spinner1.setAdapter(dataAdapter2);


        spinner2 = (Spinner) findViewById(R.id.spUniversity);

        List<String> list3 = new ArrayList<String>();
        list3.add("--Choose University--");
        list3.add("IIT");
        list3.add("NIT");
        list3.add("Thapar University");
        list3.add("PEC University");
        list3.add(" Manipal University");
        list3.add("VIT Vellore ");
        list3.add("Delhi University ");
        list3.add("UIET Punjab");
        list3.add("Punjab Technical University Colleges");

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter3.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter3);
--*/

        mSignUpButton = (Button)findViewById(R.id.email_sign_up_button);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String email = mEmail.getText().toString();
      /*          String mFullname = mFullName.getText().toString();
                String mPercent  = mPercent.getText().toString();
                String gre    = mGre.getText().toString();
                String toefl   = mToefl.getText().toString();

                String univname = spinner2.getSelectedItem().toString();
                String country = spinner1.getSelectedItem().toString();  */


                // now we trim the whitespaces, in case user enters space accidently
                username = username.trim();
                password = password.trim();
                email = email.trim();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder((SignUpActivity.this));

                    builder.setMessage(getString(R.string.signup_error_message));
                    builder.setTitle(getString(R.string.signup_error_title));
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    // create new user
                    setProgressBarIndeterminateVisibility(true);
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    newUser.setEmail(email);
                    newUser.put("isEmailVerified",true);

                    /* other fields can be set just like with ParseObject
                    newUser.put("phone", "650-555-0000");
                    newUser.put("Major","computer science"); */


                    /* signUpInBackground method does the similar thing, as AsyncTask does.
                       This saves the user in the background thread and calls a special callback method when its complete
                      */
                    newUser.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            setProgressBarIndeterminateVisibility(false);
                            if (e == null) {
                                Intent intent = new Intent(SignUpActivity.this, CreateProfile.class);

                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                                startActivity(intent);
                            } else {
                                // Sign up didn't succeed. Look at the ParseException to figure out what went wrong
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);

                                builder.setMessage(e.getMessage());
                                builder.setTitle(getString(R.string.signup_error_title));
                                builder.setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
            }
        });


    }
}

