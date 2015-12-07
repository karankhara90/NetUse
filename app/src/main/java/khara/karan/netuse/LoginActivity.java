package khara.karan.netuse;

import android.app.Activity;
import android.app.AlertDialog;
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
    protected EditText mPassword;
    protected Button mLoginButton;

    protected TextView mSignUpTextView;
    public static final String TAG=LoginActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this next line for progress bar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);
        mSignUpTextView= (TextView)findViewById(R.id.signUpText);
        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        mUsername=(EditText)findViewById(R.id.usernameField);
        mPassword=(EditText)findViewById(R.id.passwordField);
        mLoginButton=(Button)findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                username=username.trim();
                password=password.trim();
                if(username.isEmpty() || password.isEmpty() )
                {
                    AlertDialog.Builder builder= new AlertDialog.Builder((LoginActivity.this));

                    builder.setMessage(getString(R.string.login_error_message));
                    builder.setTitle(getString(R.string.login_error_title));
                    builder.setPositiveButton(android.R.string.ok,null);
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
                else {
                    // Login
                    //System.out.print("username nd pwd not empty .......................");
                    //Toast.makeText(LoginActivity.this, "username nd pwd not empty ....................", Toast.LENGTH_LONG).show();
                    setProgressBarIndeterminateVisibility(true);    // to show the progress bar

                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            setProgressBarIndeterminateVisibility(false);
                            if (e == null) {
                                //Success
                                ParseUser mCurrentUser = ParseUser.getCurrentUser();
                                ParseQuery<ParseObject> query2 = ParseQuery.getQuery("UserInfo");
                                query2.whereEqualTo("userId", mCurrentUser);
                                query2.getFirstInBackground(new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject parseObject, ParseException e) {
                                        if (e == null) {
                                            String str = parseObject.get("newUnivName").toString();

                                            if (! str.equals("blank")) {
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }
                                            else {
                                                Intent intent = new Intent(LoginActivity.this, FutureStudent.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }

                                        }
                                        else {
                                            Log.e(TAG, "e message: " + e.getMessage());
                                        }
                                    }
                                });
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle(R.string.login_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }

                    });
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
