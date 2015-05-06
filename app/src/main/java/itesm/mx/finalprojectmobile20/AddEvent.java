package itesm.mx.finalprojectmobile20;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;


public class AddEvent extends ActionBarActivity {

    //Edit Texts
    EditText ae_eventName_ET;
    EditText ae_eventLocation_ET;
    EditText ae_date_ET;
    EditText ae_time_ET;

    //Buttons
    Button ae_saveEvent_BT;

    //Firebase
    private Firebase ae_firebase_ref;
    private static final String FIREBASE_URL ="https://hop-in.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        ae_saveEvent_BT = (Button)findViewById(R.id.ae_saveEvent_BT);
        ae_eventName_ET = (EditText)findViewById(R.id.ae_eventName_ET);
        ae_eventLocation_ET = (EditText)findViewById(R.id.ae_eventLocation_ET);
        ae_date_ET = (EditText)findViewById(R.id.ae_date_ET);
        ae_time_ET = (EditText)findViewById(R.id.ae_time_ET);

        ae_firebase_ref = new Firebase(FIREBASE_URL).child("events");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
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
