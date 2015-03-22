package itesm.mx.finalprojectmobile20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class NewUser extends ActionBarActivity {

    //Edit Text
    /*
        Here are the editTexts to the interface,
        newUser_username = Edit Text that carries the username
        newUser_email = Edit Text that carries the email of the user
        newUser_password = Edit Text ( Password) that carries the password
        newUser_rePassword = EditText (Password) that carries the same password

        function to verify is Repassword and Password match is the onClick event of the
        button Create

     */
    EditText newUser_username_ET;
    EditText newUser_email_ET;
    EditText newUser_password_ET;
    EditText newUser_rePasssword_ET;

    //Buttons
    /*
      Create = Button that sends the data of the user to the Database
      it verifies the password
     */

    Button newUser_create_Btn;

    //Listeners
    View.OnClickListener newUser_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        newUser_username_ET = (EditText) findViewById(R.id.newUser_username_ET);
        newUser_email_ET = (EditText) findViewById(R.id.newUser_email_ET);
        newUser_password_ET =(EditText) findViewById(R.id.newUser_pass_ET);
        newUser_rePasssword_ET = (EditText) findViewById(R.id.newUser_RePass_ET);

        newUser_create_Btn = (Button) findViewById(R.id.newUser_create_Btn);

        /*
            The listener just listens to the button Create, which sends the information to the Server
            the if verifies the passwords, if they match then it redirects to the main screen
         */

        newUser_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newUser_create_Btn.isPressed()){
                    String email = newUser_email_ET.getText().toString();
                    String password = newUser_password_ET.getText().toString();
                    String repassword = newUser_rePasssword_ET.getText().toString();
                    if(isValidEmail(email)){
                        if(password.equals(repassword)){
                            ParseUser user = new ParseUser();
                            user.setUsername(newUser_username_ET.getText().toString());
                            user.setPassword(password);
                            user.setEmail(email);

                            user.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e == null){
                                        Toast.makeText(getApplicationContext(),"User created succesfully",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Something went wrong, please try again.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Passwords don't match",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Invalid email",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        newUser_create_Btn.setOnClickListener(newUser_listener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_user, menu);
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

    /**
     * Returns a boolean value depending if email is valid or not.
     * The CharSequence argument is the email entered by the user
     * about to be verified
     * @param  target email entered by the user in the TextEdit 'email'
     * @return true if email is valid, false if not.
     */
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
