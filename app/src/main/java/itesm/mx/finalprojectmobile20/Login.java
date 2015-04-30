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
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class Login extends ActionBarActivity {
    //Edit Text
    EditText login_username_ET;
    EditText login_password_ET;

    private static final String FIREBASE_URL ="https://hop-in.firebaseio.com/";
    String email;

    //Image views
    ImageView login_logo_IV;

    //Buttons
    Button login_login_Btn;
    Button login_newUser_Btn;
    Button login_recoverPass_Btn;

    //Listeners
    View.OnClickListener login_buttonsListener_VOL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Firebase.setAndroidContext(this);
        final Firebase fireBaseRef = new Firebase(FIREBASE_URL);

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
                        email = login_username_ET.getText().toString();
                        String password = login_password_ET.getText().toString();

                        fireBaseRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                                Toast.makeText(getApplicationContext(), "Welcome " + email, Toast.LENGTH_SHORT).show();
                                Intent userProf = new Intent(Login.this, Groups.class);
                                userProf.putExtra("email", email);
                                startActivity(userProf);
                            }
                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                Toast.makeText(getApplicationContext(), "Email or password is wrong", Toast.LENGTH_SHORT).show();
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
