package khara.karan.netuse.authentication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.UUID;

import khara.karan.netuse.R;

public class emailVerification extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
//        parseUser = ParseUser.getCurrentUser();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditEmailVerify = (EditText)findViewById(R.id.emailVerify);
        mEmailVerifyButton=(Button) findViewById(R.id.email_verify_button);

        Intent oldIntent = getIntent();
        oldIntentVal = oldIntent.getStringExtra(getString(R.string.from_button));

        mEmailVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserEmail = mEditEmailVerify.getText().toString();
                Log.e("TAG", "email is::: " + mUserEmail);

                ParseQuery parseQuery = ParseUser.getQuery();
                parseQuery.orderByAscending("email");
                //parseQuery.orderByDescending("email");
                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        for (ParseObject object : list) {

                            Log.e("TAG",object.get("email").toString() + " , -- , "+ mUserEmail);
                            if (object.get("email").toString().equals(mUserEmail)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder((emailVerification.this));

                                builder.setTitle(getString(R.string.signup_error_title));
                                builder.setMessage("There is already an account on this email. Use another email to register");
                                builder.setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                counter = 0;
                                break;
                            }

                        }

                        if(counter == 1){
                            //String verificationNum = sendEmail(mUserEmail);
                            verificationNo = UUID.randomUUID().toString();
                            verificationNumberSubString = verificationNo.substring(0, 7);
                            System.out.println("verificationNumberSubString = " + verificationNumberSubString);
                            Log.e("TAG", "verificationNumberSubString::: " + verificationNumberSubString);
                            try
                            {
                                fromEmail = "karan140990@gmail.com";
                                fromPassword="layara1990";

                                toEmailAddress = mUserEmail;
//                    List toEmailList = Arrays.asList(toEmails
//                            .split("\\s*,\\s*"));
//                    String toEmailAddress =

                                String emailSubject ="NetUse verification Code";
                                String emailBody ="Your verification code is "+'\n'+ verificationNumberSubString +'\n'+" Copy this and paste in your app";



                                Log.e("TAG","recipient email: "+toEmailAddress);
//                    GMailSender sender = new GMailSender("khara.karan007@gmail.com", "layara1990");
//                    sender.sendMail("NetUse verification Code",
//                            "Your verification code is \n"+verificationNumberSubString+" .Copy this and paste in your app",
//                            "khara.karan007@gmail.com",
//                            mUserEmail);
                                new SendMailTask(emailVerification.this).execute(fromEmail,
                                        fromPassword, toEmailAddress, emailSubject, emailBody);
                                Log.e("TAG", "==========================================");



                                Log.e("TAG", "oldIntentValue is: " + oldIntentVal);
                                if(oldIntentVal.equals("Sign Up")){
                                    Intent intent = new Intent(context, VerifyConfirmation.class);
                                    intent.putExtra("verificationNum", verificationNumberSubString);
                                    startActivity(intent);
                                }
                                counter =0;
                            } catch (Exception e2) {
                                Log.e("SendMail exception is: ", e2.getMessage(), e2);
                            }


                        }

                    }
                });


//                try{
//
//                    if(oldIntentVal.equals("Forgot Password")){
////                        confirmationMethod();
//                        Intent intent = new Intent(context, NewPwdVerification.class);
//                        intent.putExtra("verificationNum", verificationNumberSubString);
//                        intent.putExtra("emailEntered", toEmailAddress);
//                        startActivity(intent);
//                    }
//                }
//                catch (Exception e){
//                    Log.e("TAG","exception in forgot or sign up is: "+e);
//                }


            }
        });


    }

//    protected String sendEmail(String user_email) {
//        Log.i("Send email", "");
//        String[] TO = {""};
//        String[] CC = {""};
//        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
//        String verificationNumberSubString = UUID.randomUUID().toString();
//        System.out.println("verificationNumberSubString = " + verificationNumberSubString);
//
//        emailIntent.setData(Uri.parse("mailto:")); //+user_email));
//        emailIntent.setType("text/plain");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, user_email);
//        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Verification code from UseNet");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your verification code is \n"+verificationNumberSubString+" .Copy this and paste in your app");
//
//        try {
//            //startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            startActivity(emailIntent);
//            finish();
//            Toast.makeText(this, "Finished sending email...", Toast.LENGTH_LONG).show();
//            Log.i("TAG", "...........Finished sending email........");
//        }
//        catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
//        return verificationNumberSubString;
//    }

//    void confirmationMethod(){
//        LayoutInflater inflater = LayoutInflater.from(emailVerification.this);
//        View customView = inflater.inflate(R.layout.custom_dialog_new_pwd,null);
//
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//        alertDialog.setMessage("Enter the verification code sent to your email along with new password");
//        alertDialog.setView(customView);
//
//        alertDialog.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//
//        EditText confirmCodeForPwd = (EditText) findViewById(R.id.confirmCodeForPwd);
//        editTextPwdConfirmationNo = confirmCodeForPwd.getText().toString();
//
//        EditText editTextNewPwd1 = (EditText) findViewById(R.id.editTextNewPwd1);
//        final String newPwd1 = editTextNewPwd1.getText().toString();
//
//        EditText editTextNewPwd2 = (EditText) findViewById(R.id.editTextNewPwd2);
//        final String newPwd2 = editTextNewPwd2.getText().toString();
//
//        final TextView newLoginMsg = (TextView)findViewById(R.id.newLoginMsg);
//
//        alertDialog.setPositiveButton("Next",
//                new DialogInterface.OnClickListener(){
//                    public void onClick(DialogInterface dialogInterface, int id){
//                        Log.e("TAG", verificationNumberSubString + " ,,, "+editTextPwdConfirmationNo);
//                        Log.e("TAG", newPwd1+" ... "+newPwd2);
//
//                        if(verificationNumberSubString.equals(editTextPwdConfirmationNo)){
//                            Log.e("TAG", "======== +++ ==========");
//                            if(newPwd1.equals(newPwd2)){
//                                Log.e("TAG", "-------- +++ -------");
//                                //parseUser.put("password",newPwd1);
//                                parseUser.setPassword(newPwd1);
//                                newLoginMsg.setVisibility(View.VISIBLE);
//                            }
//                            else{
//                                AlertDialog.Builder builder = new AlertDialog.Builder(emailVerification.this);
//
//                                builder.setMessage("Passwords did not match");
//                                builder.setTitle("Try again");
//                                builder.setPositiveButton(android.R.string.ok, null);
//                                AlertDialog dialog = builder.create();
//                                dialog.show();
//                            }
//                        }
//                        else{
//                            AlertDialog.Builder builder = new AlertDialog.Builder(emailVerification.this);
//
//                            builder.setMessage("Verification code did not match");
//                            builder.setTitle("Try again");
//                            builder.setPositiveButton(android.R.string.ok, null);
//                            AlertDialog dialog = builder.create();
//                            dialog.show();
//                        }
//                    }
//                });
//    }

}
