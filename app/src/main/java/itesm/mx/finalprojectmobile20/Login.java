package itesm.mx.finalprojectmobile20;

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

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseException;

import itesm.mx.finalprojectmobile20.chat.ChatActivity;
import itesm.mx.finalprojectmobile20.chat.Message;


public class Login extends ActionBarActivity {
    //Edit Text
    EditText login_username_ET;
    EditText login_password_ET;

    //Image views
    ImageView login_logo_IV;

    //Buttons
    Button login_login_Btn;
    Button login_newUser_Btn;

    //Listeners
    View.OnClickListener login_buttonsListener_VOL;

    public static final String YOUR_APPLICATION_ID = "AERqqIXGvzH7Nmg45xa5T8zWRRjqT8UmbFQeeI";
    public static final String YOUR_CLIENT_KEY = "8bXPznF5eSLWq0sY9gTUrEF5BJlia7ltmLQFRh";

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
                                    Intent chat = new Intent(Login.this, ChatActivity.class);
                                    startActivity(chat);
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
