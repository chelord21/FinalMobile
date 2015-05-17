package itesm.mx.finalprojectmobile20;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.firebase.client.Firebase;

import java.io.IOException;


public class AddEvent extends ActionBarActivity {

    //Edit Texts
    EditText ae_eventName_ET;
    EditText ae_eventLocation_ET;
    EditText ae_date_ET;
    EditText ae_time_ET;

    //Buttons
    Button ae_saveEvent_BT;

    //Strings
    String ae_eventName;
    String ae_eventLocation;
    String ae_eventDate;
    String ae_eventTime;
    String group_selectedGroup;

    //Firebase
    private Firebase ae_firebase_ref;
    private static final String FIREBASE_URL ="https://hop-in.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Firebase.setAndroidContext(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            group_selectedGroup = extras.getString("groupName");
        }
        setTitle("New Event");

        ae_saveEvent_BT = (Button)findViewById(R.id.ae_saveEvent_BT);
        ae_eventName_ET = (EditText)findViewById(R.id.ae_eventName_ET);
        ae_eventLocation_ET = (EditText)findViewById(R.id.ae_eventLocation_ET);
        ae_date_ET = (EditText)findViewById(R.id.ae_date_ET);
        ae_time_ET = (EditText)findViewById(R.id.ae_time_ET);

        ae_firebase_ref = new Firebase(FIREBASE_URL).child("events");

        ae_saveEvent_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                scaled.compress(Bitmap.CompressFormat.PNG, 0, stream);
                arrayFoto = stream.toByteArray();
                */
                if(isNetworkConnected()) {
                    if (!ae_date_ET.getText().toString().isEmpty() && !ae_eventLocation_ET.getText().toString().isEmpty() && !ae_eventName_ET.getText().toString().isEmpty() && !ae_time_ET.getText().toString().isEmpty()) {
                        ae_eventName = ae_eventName_ET.getText().toString();
                        ae_eventLocation = ae_eventLocation_ET.getText().toString();
                        ae_eventTime = ae_time_ET.getText().toString();
                        ae_eventDate = ae_date_ET.getText().toString();

                        Event newEvent = new Event(ae_eventName, ae_eventLocation, ae_eventTime, ae_eventDate, group_selectedGroup);
                        ae_firebase_ref.push().setValue(newEvent);
                        finish();
                    }
                    else{
                        Toast.makeText(AddEvent.this, "Not all fields were filled. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AddEvent.this, "Cannot complete action because you are not connected to internet", Toast.LENGTH_SHORT).show();

                }
            }
        });
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

    @Override
    public void onBackPressed() {
        Toast.makeText(AddEvent.this, "Event was not created", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    private class MyNullKeySerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object nullKey, JsonGenerator jsonGenerator, SerializerProvider unused)
                throws IOException, JsonProcessingException
        {
            jsonGenerator.writeFieldName("");
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }
}
