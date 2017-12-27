package khara.karan.netuse;


/**
 * A login screen that offers login via email/password.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

//import com.firebase.ui.auth.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;
//import khara.karan.netuse.model.User;

public class SignUpActivity extends Activity {

    protected EditText mUsername;
    protected EditText mPassword;
    protected EditText mEmail;
    Spinner s2, s3;
   // protected EditText mFullName, mPercent, mGre, mToefl;
    protected Button mNext;

    protected Button mSignUpButton;

    // Firebase var
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDB;

    private ProgressDialog mDialog;
    DatabaseReference mUnivDetailsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this next line for progress bar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_sign_up);

//        mUnivDetailsDB = FirebaseDatabase.getInstance().getReference().child("UniversityProfile");

        // find views by ID -------------------------------------------------
        mUsername = (EditText)findViewById(R.id.usernameSignUpField);
        mPassword = (EditText)findViewById(R.id.passwordSignUpField);
        mEmail = (EditText)findViewById(R.id.emailSignUpField);

        /*** get firebase-authorization object  **/
        mAuth = FirebaseAuth.getInstance();
        // if the "Users" db doesn't exist, it will create it automatically.
        mUsersDB = FirebaseDatabase.getInstance().getReference().child("Users");

        mDialog = new ProgressDialog(this);

        mSignUpButton = (Button)findViewById(R.id.email_sign_up_button);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setMessage("Please wait...");
                mDialog.show();

//                String taskId = "iAt1l7AYxPc6oL7Q5tdSfNBDLn43";
//                DatabaseReference univRef = mUnivDetailsDB.child(taskId).child("univLogo");
//                univRef.setValue("gs://stunet-id.appspot.com/arizona_state_university_fulton.png");


                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String email = mEmail.getText().toString();

                // now we trim the whitespaces, in case user enters space accidently
                username = username.trim();
                password = password.trim();
                email = email.trim();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    mDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder((SignUpActivity.this));

                    builder.setMessage(getString(R.string.signup_error_message));
                    builder.setTitle(getString(R.string.signup_error_title));
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    // create new user

//                    setProgressBarIndeterminateVisibility(true);
                    registerUserToFirebase(username, password, email);

//                    newUser.put("isEmailVerified",true);

                    /* other fields can be set just like with ParseObject
                    newUser.put("phone", "650-555-0000");
                    newUser.put("Major","computer science"); */

//                            } else {
//                                // Sign up didn't succeed. Look at the ParseException to figure out what went wrong
//                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
//
//                                builder.setMessage(e.getMessage());
//                                builder.setTitle(getString(R.string.signup_error_title));
//                                builder.setPositiveButton(android.R.string.ok, null);
//                                AlertDialog dialog = builder.create();
//                                dialog.show();
//                            }
//                        }
//                    });
                }
            }
        });
    }

    private void showAlertDialog(String title, String message) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private  void registerUserToFirebase(final String username, String password, String email) {
        // firebase
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try{
                    mDialog.dismiss();
                    if (!task.isSuccessful()) {
                        //error registering user
                        showAlertDialog("Error", task.getException().getMessage());
                    } else {
                        // success.
//                        final FirebaseUser currentUser = task.getResult().getUser();
                        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        // to update user's info.
                        Log.e("TAG", "username::: "+username);
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();
                        Log.e("TAG", "currentUser.getDisplayName(): "+currentUser.getDisplayName());

                        currentUser.updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                khara.karan.netuse.model.User newUser = new khara.karan.netuse.model.User
                                        (currentUser.getDisplayName(), currentUser.getEmail(), "", currentUser.getUid());

                                try {
                                    Log.e("TAG", "currentUser.getDisplayName(): "+currentUser.getDisplayName());
                                    Log.e("TAG", "currentUser.getEmail(): "+currentUser.getEmail());
                                    Log.e("TAG", "photouri: "+"");
                                    Log.e("TAG", "currentUser.getUid()"+currentUser.getUid());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                Log.e("TAG", "newUser.getUserId()"+newUser.getUserId());
                                Log.e("TAG", "newUser.getDisplayName()"+newUser.getDisplayName());
                                // this will assign uid to new user just created into database.
                                mUsersDB.child(currentUser.getUid()).setValue(newUser);
//                                mUsersDB.child(currentUser.getDisplayName()).setValue(newUser);

                                // take user home.
                                finish();
                                startActivity(new Intent(SignUpActivity.this, CreateProfile.class));
                            }
                        });

                    }
                }catch (Exception e){//
                    System.out.println("Exception e: "+e);
                }
            }
        });
    }
}

