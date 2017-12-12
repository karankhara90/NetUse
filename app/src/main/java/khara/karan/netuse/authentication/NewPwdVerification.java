package khara.karan.netuse.authentication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import khara.karan.netuse.LoginActivity;
import khara.karan.netuse.R;

public class NewPwdVerification extends AppCompatActivity {

    protected EditText confirmCodeForPwd;
    protected String editTextPwdConfirmationNo;
    protected EditText editTextNewPwd1;
    protected String newPwd1;
    protected String newPwd2;
    protected TextView newLoginMsg;
    protected Button newPwdBtn;
    protected String gotNewPwdConfirmationCode;
    protected ParseUser parseUser;
    protected Context context;
    protected EditText editTextNewPwd2;
    protected String mEmailEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pwd_verification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //parseUser = ParseUser.getCurrentUser();
        context = this;

        confirmCodeForPwd = (EditText) findViewById(R.id.confirmCodeForPwd);
        editTextNewPwd1 = (EditText) findViewById(R.id.editTextNewPwd1);
        editTextNewPwd2 = (EditText) findViewById(R.id.editTextNewPwd2);
        newLoginMsg = (TextView)findViewById(R.id.newLoginMsg);
        newPwdBtn = (Button)findViewById(R.id.newPwdBtn);

        Bundle bundle = getIntent().getExtras();
        gotNewPwdConfirmationCode = bundle.getString("verificationNum", "Could not get code");
        mEmailEntered = bundle.getString("emailEntered", "error getting email");
        Log.e("TAG", "code sent is: " + gotNewPwdConfirmationCode);

        newPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPwdConfirmationNo = confirmCodeForPwd.getText().toString();
                newPwd1 = editTextNewPwd1.getText().toString();
                newPwd2 = editTextNewPwd2.getText().toString();

                Log.e("TAG", gotNewPwdConfirmationCode + " ,,, "+editTextPwdConfirmationNo);


                if(gotNewPwdConfirmationCode.equals(editTextPwdConfirmationNo)){
                    Log.e("TAG", "======== +++ ==========");
                    if(newPwd1.equals(newPwd2)){
                        Log.e("TAG", "-------- +++ -------");
                        //parseUser.put("password",newPwd1);
                        Log.e("TAG", newPwd1+" ... "+newPwd2);

                        Log.e("TAG", "email is_____" + mEmailEntered);
                        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
//                        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("_User");
                        parseQuery.whereEqualTo("email", mEmailEntered);

                        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
                            @Override
                            public void done(ParseUser parseObject, ParseException e) {
//                                parseObject.put("password", newPwd1);
//                                parseObject.put("username", newPwd1);
                                parseObject.setUsername("kkk");
                                parseObject.setPassword(newPwd1);
                                //parseObject.put("online", false);
//                                Log.e("TAG", "obj id is_____ __ " + parseObject.getObjectId().toString());
//                                Log.e("TAG", "obj id is__ __ ___ " + parseObject.get("objectId").toString());
                                parseObject.saveInBackground();
//                                String gotPwd = parseObject.getString("password");
                                Log.e("TAG", "new username is_____ __" + parseObject.getUsername());
//                                newLoginMsg.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });

                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewPwdVerification.this);

                        builder.setMessage("Passwords did not match");
                        builder.setTitle("Try again");
                        builder.setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewPwdVerification.this);

                    builder.setMessage("Verification code did not match");
                    builder.setTitle("Try again");
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });




    }

}
