package khara.karan.netuse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class LoginActivity extends Activity {

    protected EditText mUsername;
    protected EditText mEmail;
    protected EditText mPassword;
    protected Button mLoginButton;
    protected TextView mForgotPwd;
    protected String mPwdVerificationNo;

    protected TextView mSignUpTextView;
    public static final String TAG=LoginActivity.class.getSimpleName();
    protected String mUserEmail;
    protected Context context;
    protected ParseUser parseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this next line for progress bar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);
       context = this;
//        parseUser = ParseUser.getCurrentUser();
//
//        mSignUpTextView= (TextView)findViewById(R.id.signUpText);
//        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, khara.karan.netuse.authentication.emailVerification.class);
//                intent.putExtra(getString(R.string.from_button), "Sign Up");
//                startActivity(intent);
//            }
//        });
//
//        mUsername=(EditText)findViewById(R.id.usernameField);
//        //mEmail = (EditText)findViewById(R.id.emailField);
//        mPassword=(EditText)findViewById(R.id.passwordField);
//        mLoginButton=(Button)findViewById(R.id.loginButton);
//        mLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = mUsername.getText().toString();
//                //String email = mEmail.getText().toString();
//                String password = mPassword.getText().toString();
//
//                username=username.trim();
////                email=email.trim();
//                password=password.trim();
//                if(username.isEmpty() || password.isEmpty() )
//                {
//                    AlertDialog.Builder builder= new AlertDialog.Builder((LoginActivity.this));
//
//                    builder.setMessage(getString(R.string.login_error_message));
//                    builder.setTitle(getString(R.string.login_error_title));
//                    builder.setPositiveButton(android.R.string.ok,null);
//                    AlertDialog dialog=builder.create();
//                    dialog.show();
//                }
//                else {
//                    // Login
//                    //System.out.print("username nd pwd not empty .......................");
//                    //Toast.makeText(LoginActivity.this, "username nd pwd not empty ....................", Toast.LENGTH_LONG).show();
//                    setProgressBarIndeterminateVisibility(true);    // to show the progress bar
//
//                    ParseUser.logInInBackground(username, password, new LogInCallback() {
//                        @Override
//                        public void done(ParseUser parseUser, ParseException e) {
//                            setProgressBarIndeterminateVisibility(false);
//                            if (e == null) {
//                                //Success
//                                //ParseUser mCurrentUser = ParseUser.getCurrentUser();
//                                ParseQuery<ParseObject> query2 = ParseQuery.getQuery("UserInfo");
//                                query2.whereEqualTo("userId", parseUser);
//                                query2.getFirstInBackground(new GetCallback<ParseObject>() {
//                                    @Override
//                                    public void done(ParseObject parseObject, ParseException e) {
//                                        if (e == null) {
//                                            String str = parseObject.get("newUnivName").toString();
//
//                                            if (! str.equals("blank")) {
//                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                startActivity(intent);
//                                            }
//                                            else {
////                                                Intent intent = new Intent(LoginActivity.this, FutureStudent.class);
////                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                                                startActivity(intent);
//                                            }
//
//                                        }
//                                        else {
//                                            Log.e(TAG, "e message: " + e.getMessage());
//                                        }
//                                    }
//                                });
//                            } else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                builder.setMessage(e.getMessage())
//                                        .setTitle(R.string.login_error_title)
//                                        .setPositiveButton(android.R.string.ok, null);
//                                AlertDialog dialog = builder.create();
//                                dialog.show();
//                            }
//                        }
//
//                    });
//                }
//            }
//        });
//
//        mForgotPwd = (TextView)findViewById(R.id.forgotPwdText);
//        mForgotPwd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                mPwdVerificationNo = UUID.randomUUID().toString();   /* UUID: representation of 128-bit Universally Unique
////                                                                         IDentifier (UUID). */
////                mPwdVerificationNo= mPwdVerificationNo.substring(0, 7);
////                System.out.println("password verificationNumberSubString = " + mPwdVerificationNo);
////                Log.e("TAG", "password verificationNumberSubString::: " + mPwdVerificationNo);
////
////                try {
////                    mUserEmail =  parseUser.getEmail();
////                    Log.e(TAG,"user email: "+mUserEmail);
////
////
////                    String fromEmail = "karan140990@gmail.com";
////                    String fromPassword="layara1990";
////
////
////                    String toEmailAddress = mUserEmail;
//////                    List toEmailList = Arrays.asList(toEmails
//////                            .split("\\s*,\\s*"));
//////                    String toEmailAddress =
////
////                    String emailSubject ="NetUse verification Code";
////                    String emailBody ="Your verification code is "
////                            +'\n'+mPwdVerificationNo+'\n'+" Copy this and paste in your app";
////
////
////
////                    Log.e("TAG","recipient email: "+mUserEmail);
//////                    GMailSender sender = new GMailSender("khara.karan007@gmail.com", "layara1990");
//////                    sender.sendMail("NetUse verification Code",
//////                            "Your verification code is \n"+verificationNumberSubString+" .Copy this and paste in your app",
//////                            "khara.karan007@gmail.com",
//////                            mUserEmail);
////                    new SendMailTask(LoginActivity.this).execute(fromEmail,
////                            fromPassword, toEmailAddress, emailSubject, emailBody);
////                    Log.e("TAG", "==========================================");
////
////                    LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
////                    View customView = inflater.inflate(R.layout.custom_dialog_new_pwd,null);
////
////                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
////                    alertDialog.setMessage("Enter the verification code sent to your email along with new password");
////                    alertDialog.setView(customView);
////
////                    alertDialog.setNegativeButton("Cancel",
////                            new DialogInterface.OnClickListener() {
////                                public void onClick(DialogInterface dialog, int id) {
////                                    dialog.cancel();
////                                }
////                            });
////
////                    EditText confirmCodeForPwd = (EditText) findViewById(R.id.confirmCodeForPwd);
////                    final String editTextPwdConfirmationNo = confirmCodeForPwd.getText().toString();
////
////                    EditText editTextNewPwd1 = (EditText) findViewById(R.id.editTextNewPwd1);
////                    final String newPwd1 = editTextNewPwd1.getText().toString();
////
////                    EditText editTextNewPwd2 = (EditText) findViewById(R.id.editTextNewPwd2);
////                    final String newPwd2 = editTextNewPwd2.getText().toString();
////
////                    final TextView newLoginMsg = (TextView)findViewById(R.id.newLoginMsg);
////
////                    alertDialog.setPositiveButton("Next",
////                            new DialogInterface.OnClickListener(){
////                                public void onClick(DialogInterface dialogInterface, int id){
////                                    Log.e(TAG, mPwdVerificationNo + " ,,, "+editTextPwdConfirmationNo);
////                                    Log.e(TAG, newPwd1+" ... "+newPwd2);
////
////                                    if(mPwdVerificationNo.equals(editTextPwdConfirmationNo)){
////                                        Log.e(TAG, "======== +++ ==========");
////                                        if(newPwd1.equals(newPwd2)){
////                                            Log.e(TAG, "-------- +++ -------");
////                                            //parseUser.put("password",newPwd1);
////                                            parseUser.setPassword(newPwd1);
////                                            newLoginMsg.setVisibility(View.VISIBLE);
////                                        }
////                                        else{
////                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
////
////                                            builder.setMessage("Passwords did not match");
////                                            builder.setTitle("Try again");
////                                            builder.setPositiveButton(android.R.string.ok, null);
////                                            AlertDialog dialog = builder.create();
////                                            dialog.show();
////                                        }
////                                    }
////                                }
////                    });
////
//////                    Intent intent = new Intent(context, VerifyConfirmation.class);
//////                    intent.putExtra("verificationNum",mPwdVerificationNo);
//////                    startActivity(intent);
////                } catch (Exception e) {
////                    Log.e("SendMail exception is: ", e.getMessage(), e);
////                }
//                Intent intent = new Intent(LoginActivity.this, khara.karan.netuse.authentication.forgotPwdEmailVerify.class);
//                intent.putExtra(getString(R.string.from_button), "Forgot Password");
//                startActivity(intent);
//            }
//        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
