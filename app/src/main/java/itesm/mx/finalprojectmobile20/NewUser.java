package itesm.mx.finalprojectmobile20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


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
                    if(verifyPass(newUser_password_ET.getText().toString(), newUser_rePasssword_ET.getText().toString())
                            && verifyUser(newUser_username_ET.getText().toString())){
                        Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Passwords don't match",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        newUser_create_Btn.setOnClickListener(newUser_listener);

    }

     /*
        Created function to verify if user is created in the database already
        name is tentative : function
        name = verifyUser(String username)
        - returns Boolean 'true' if user doesn't exists
        -returns Boolean 'false' if user does exists

        Created function to verify if the passwords match in order to generate a password
        name is tentative: function
        name = verifyPass
        parameters = pass ( Password ) and rePass ( Repeated Password)
        -returns Boolean 'true' if passwords match
        -returns Boolean 'false' if passwords don't match
     */

    private boolean verifyPass(String pass, String rePass) {
        boolean verification=false;
        if(pass.equals(rePass)){
            verification =true;
            return verification;
        }
        return verification;
    }

    private boolean verifyUser(String username){
       /*
            Generate code through parse to verify the user in the Database
        */
        return false;
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
}
