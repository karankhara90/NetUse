package khara.karan.netuse.authentication;

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

import java.util.UUID;

import khara.karan.netuse.R;

public class emailVerification extends AppCompatActivity {
    protected EditText mEditEmailVerify;
    protected Button mEmailVerifyButton;
    protected String mUserEmail;
    private Context context;
    protected String verificationNo;
    protected String verificationNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditEmailVerify = (EditText)findViewById(R.id.emailVerify);
        mEmailVerifyButton=(Button) findViewById(R.id.email_verify_button);

        mEmailVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserEmail = mEditEmailVerify.getText().toString();
                Log.e("TAG", "email is::: " + mUserEmail);
                //String verificationNum = sendEmail(mUserEmail);
                verificationNo = UUID.randomUUID().toString();
                verificationNumber= verificationNo.substring(0, 7);
                System.out.println("verificationNumber = " + verificationNumber);
                Log.e("TAG", "verificationNumber::: " + verificationNumber);

                try {
//                    String fromEmail = "khara.karan007@gmail.com";
//                    String fromPassword="layara1990";
//                    String fromEmail = "karank9400@gmail.com";
//                    String fromPassword="KaranAndroid*";
                    String fromEmail = "karan140990@gmail.com";
                    String fromPassword="layara1990";

//                    String fromEmail = "karan1409@mail.com";
//                    String fromPassword="layara1990";

                    String toEmailAddress = mUserEmail;
//                    List toEmailList = Arrays.asList(toEmails
//                            .split("\\s*,\\s*"));
//                    String toEmailAddress =

                    String emailSubject ="NetUse verification Code";
                    String emailBody ="Your verification code is "+'\n'+verificationNumber+'\n'+" Copy this and paste in your app";



                    Log.e("TAG","recipient email: "+mUserEmail);
//                    GMailSender sender = new GMailSender("khara.karan007@gmail.com", "layara1990");
//                    sender.sendMail("NetUse verification Code",
//                            "Your verification code is \n"+verificationNumber+" .Copy this and paste in your app",
//                            "khara.karan007@gmail.com",
//                            mUserEmail);
                    new SendMailTask(emailVerification.this).execute(fromEmail,
                            fromPassword, toEmailAddress, emailSubject, emailBody);
                    Log.e("TAG","==========================================");

                    Intent intent = new Intent(context, VerifyConfirmation.class);
                    intent.putExtra("verificationNum",verificationNumber);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("SendMail exception is: ", e.getMessage(), e);
                }


            }
        });


    }

//    protected String sendEmail(String user_email) {
//        Log.i("Send email", "");
//        String[] TO = {""};
//        String[] CC = {""};
//        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
//        String verificationNumber = UUID.randomUUID().toString();
//        System.out.println("verificationNumber = " + verificationNumber);
//
//        emailIntent.setData(Uri.parse("mailto:")); //+user_email));
//        emailIntent.setType("text/plain");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, user_email);
//        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Verification code from UseNet");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your verification code is \n"+verificationNumber+" .Copy this and paste in your app");
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
//        return verificationNumber;
//    }

}
