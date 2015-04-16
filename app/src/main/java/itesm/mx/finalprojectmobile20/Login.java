package itesm.mx.finalprojectmobile20;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import itesm.mx.finalprojectmobile20.chat.Message;


public class Login extends ActionBarActivity {
    //Edit Text
    EditText login_username_ET;
    EditText login_password_ET;

    //Image views
    ImageView login_logo_IV;
    ImageView aux;

    //Buttons
    Button login_login_Btn;
    Button login_newUser_Btn;
    Button login_recoverPass_Btn;

    //Listeners
    View.OnClickListener login_buttonsListener_VOL;

    public static final String YOUR_APPLICATION_ID = "apXAsVSwGzEOIs1zqznS8obypwm8SGtHcrVsvRDM";
    public static final String YOUR_CLIENT_KEY = "gPkvvX7CAJpsumX9YKeoUxEcXXCFVG81YSWnQfxN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Parse.enableLocalDatastore(this);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        ParseObject.registerSubclass(Message.class);
        login_username_ET = (EditText) findViewById(R.id.login_username_ET);
        login_password_ET =(EditText) findViewById(R.id.login_password_ET);
        login_logo_IV = (ImageView) findViewById(R.id.login_Logo_IV);
        login_login_Btn = (Button) findViewById(R.id.login_Log_Btn);
        login_newUser_Btn = (Button) findViewById(R.id.login_nuser_Btn);
        login_recoverPass_Btn = (Button) findViewById(R.id.login_recoverPass_BT);

        login_buttonsListener_VOL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.login_Log_Btn:
                        final String username = login_username_ET.getText().toString();
                        String password = login_password_ET.getText().toString();

                        ParseUser.logInInBackground(username, password, new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();
                                    Intent userProf = new Intent(Login.this, UserProfile.class);
                                    startActivity(userProf);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Username or password is wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    case R.id.login_nuser_Btn:
                        Intent newUser = new Intent(Login.this, NewUser.class);
                        startActivity(newUser);
                        break;
                }
            }
        };

        login_recoverPass_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(Login.this);
                alert.setTitle("Type your mail");
                final EditText input = new EditText(Login.this);
                alert.setView(input);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        final String mail = input.getText().toString();

                        ParseUser.requestPasswordResetInBackground(mail,
                                new RequestPasswordResetCallback() {
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            // An email was successfully sent with reset instructions.
                                            Toast.makeText(getApplicationContext(),
                                                    "Email has been sent to " + mail,
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Something went wrong. Look at the ParseException to see what's up.
                                            Toast.makeText(getApplicationContext(),
                                                    "We couldn't send the email. Please try again later.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(),
                                "Canceled",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        login_login_Btn.setOnClickListener(login_buttonsListener_VOL);
        login_newUser_Btn.setOnClickListener(login_buttonsListener_VOL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
