package khara.karan.netuse.authentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import khara.karan.netuse.R;
import khara.karan.netuse.SignUpActivity;

public class VerifyConfirmation extends AppCompatActivity {
    protected EditText editTextCode;
    protected Button btnEnterCode;
    protected String gotCodeFromEditText;
    protected String gotConfirmationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_verify_confirmation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnEnterCode = (Button)findViewById(R.id.btnEnterCode);
        editTextCode = (EditText)findViewById(R.id.editTextVerifyCode);


        Bundle bundle = getIntent().getExtras();
        gotConfirmationCode = bundle.getString("verificationNum", "Could not get code");
        Log.e("TAG","code sent is: "+gotConfirmationCode);
        //Log.e("TAG", "code entered is: " + gotCodeFromEditText);


        btnEnterCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotCodeFromEditText= editTextCode.getText().toString();
                Log.e("TAG","now code sent is: "+gotConfirmationCode);
                Log.e("TAG", "now code entered is: " + gotCodeFromEditText);

                if(gotCodeFromEditText.equals(gotConfirmationCode)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(VerifyConfirmation.this);

                    builder.setMessage(" Now you can register with this email...!! \n " +
                            "Click on 'Next' to register your profile ");
                    builder.setTitle("Congratulations");
                    //builder.setPositiveButton(android.R.string.ok, null);
                    builder.setNeutralButton("Next", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(VerifyConfirmation.this,SignUpActivity.class);
                            startActivity(intent);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(VerifyConfirmation.this);

                    builder.setMessage("Error verifying code");
                    builder.setTitle(getString(R.string.signup_error_title));
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }
}
