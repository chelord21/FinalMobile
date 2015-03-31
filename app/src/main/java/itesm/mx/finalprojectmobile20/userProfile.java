package itesm.mx.finalprojectmobile20;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;


public class UserProfile extends ActionBarActivity {

    //TextViews
    /*
        They display the currentUsers profile
            Displays the username and the email
            Back button returns to the screen of eventCreation

      */

    TextView userProfile_username_TV;
    TextView userProfile_email_TV;

    //Buttons
    Button userProfile_back_Btn;

    //ImageView
    ImageView userProfile_profile_IV;

    //Listeners
    View.OnClickListener userProfile_listener_OCL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userProfile_username_TV = (TextView) findViewById(R.id.userProf_username_TV);
        userProfile_email_TV = (TextView) findViewById(R.id.userProf_email_TV);

        userProfile_profile_IV = (ImageView) findViewById(R.id.userProf_profile_IV);
        userProfile_username_TV.setText(ParseUser.getCurrentUser().getUsername());
        userProfile_email_TV.setText(ParseUser.getCurrentUser().getEmail());
        userProfile_listener_OCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userProfile_back_Btn.isPressed()){
                    Toast.makeText(getApplicationContext(), "Works", Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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
