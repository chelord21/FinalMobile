package itesm.mx.finalprojectmobile20;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.Parse;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "APPLICATION ID", "CLIENT KEY");

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

                        break;
                    case R.id.login_nuser_Btn:

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
