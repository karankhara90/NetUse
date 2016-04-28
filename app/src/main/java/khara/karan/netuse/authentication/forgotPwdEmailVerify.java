package khara.karan.netuse.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import khara.karan.netuse.LoginActivity;
import khara.karan.netuse.R;

public class forgotPwdEmailVerify extends AppCompatActivity {
    protected EditText mEditEmailVerify;
    protected Button mEmailVerifyButton;
    protected String mUserEmail;
    private Context context;
    protected String verificationNo;
    protected String verificationNumberSubString;
    protected ParseUser parseUser;
    protected String mPwdVerificationNo;
    protected String toEmailAddress;
    protected String editTextPwdConfirmationNo;
    protected String fromEmail;
    protected String fromPassword;
    protected int counter = 1;
    protected String oldIntentVal;
    protected TextView newLoginMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd_email_verify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        newLoginMsg = (TextView)findViewById(R.id.newLoginMsg);
        mEditEmailVerify = (EditText)findViewById(R.id.emailNewPwdVerify);
        mEmailVerifyButton=(Button) findViewById(R.id.email_new_pwd_verify_button);

        Intent oldIntent = getIntent();
        oldIntentVal = oldIntent.getStringExtra(getString(R.string.from_button));

        mEmailVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserEmail = mEditEmailVerify.getText().toString();
                Log.e("TAG", "email is::: " + mUserEmail);

                ParseUser.requestPasswordResetInBackground(mUserEmail, new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(context,"Check email and login with new password",Toast.LENGTH_LONG).show();
                            // An email was successfully sent with reset instructions.
                            Intent intent = new Intent(context, LoginActivity.class);
//                            newLoginMsg.setVisibility(View.VISIBLE);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        } else {
                            // Something went wrong. Look at the ParseException to see what's up.
                        }
                    }
                });

//                ParseQuery parseQuery2 = ParseUser.getQuery();
//                parseQuery2.orderByAscending("email");
//                parseQuery2.findInBackground(new FindCallback<ParseObject>() {
//                    @Override
//                    public void done(List<ParseObject> list, ParseException e) {
//                        for (ParseObject object2 : list) {
//                            if (object2.get("email").toString().equals(mUserEmail)) {
//                                Log.e("TAG",object2.get("email").toString() + " , -- , "+ mUserEmail);
//
//                                verificationNo = UUID.randomUUID().toString();
//                                verificationNumberSubString = verificationNo.substring(0, 7);
//                                System.out.println("verificationNumberSubString = " + verificationNumberSubString);
//                                Log.e("TAG", "verificationNumberSubString::: " + verificationNumberSubString);
//                                try
//                                {
//                                    fromEmail = "karan140990@gmail.com";
//                                    fromPassword="layara1990";
//
//                                    toEmailAddress = mUserEmail;
//                //                    List toEmailList = Arrays.asList(toEmails
//                //                            .split("\\s*,\\s*"));
//                //                    String toEmailAddress =
//
//                                    String emailSubject ="NetUse verification Code";
//                                    String emailBody ="Your verification code is "+'\n'+ verificationNumberSubString +'\n'+" Copy this and paste in your app";
//
//
//
//                                    Log.e("TAG","recipient email: "+toEmailAddress);
//                //                    GMailSender sender = new GMailSender("khara.karan007@gmail.com", "layara1990");
//                //                    sender.sendMail("NetUse verification Code",
//                //                            "Your verification code is \n"+verificationNumberSubString+" .Copy this and paste in your app",
//                //                            "khara.karan007@gmail.com",
//                //                            mUserEmail);
//                                    new SendMailTask(forgotPwdEmailVerify.this).execute(fromEmail,
//                                            fromPassword, toEmailAddress, emailSubject, emailBody);
//                                    Log.e("TAG", "==========================================");
//
//                                    Log.e("TAG", "oldIntentValue is: " + oldIntentVal);
//                                    if(oldIntentVal.equals("Forgot Password")){
//                                        Intent intent = new Intent(context, NewPwdVerification.class);
//                                        intent.putExtra("verificationNum", verificationNumberSubString);
//                                        intent.putExtra("emailEntered", toEmailAddress);
//                                        startActivity(intent);
//                                    }
//
//                                } catch (Exception e2) {
//                                    Log.e("SendMail exception is: ", e2.getMessage(), e2);
//                                }
//
//                                counter = 0;
//                                break;
//                            }
//
//                        }
//                        if(counter==1){
//                            AlertDialog.Builder builder = new AlertDialog.Builder((forgotPwdEmailVerify.this));
//
//                            builder.setTitle(getString(R.string.signup_error_title));
//                            builder.setMessage("There is no account on this email. Try again");
//                            builder.setPositiveButton(android.R.string.ok, null);
//                            AlertDialog dialog = builder.create();
//                            dialog.show();
//                        }
//
//
//                    }
//                });
            }
        });

    }

}
