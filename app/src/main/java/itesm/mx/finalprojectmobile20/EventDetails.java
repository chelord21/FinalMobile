package itesm.mx.finalprojectmobile20;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class EventDetails extends ActionBarActivity {

    Button goToMaps;
    TextView ed_eventName;
    TextView ed_eventDate;
    TextView ed_eventTime;
    TextView ed_eventLocation;

    String chat_eventName;
    String chat_eventLocation;
    String chat_eventDate;
    String chat_eventTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        goToMaps = (Button)findViewById(R.id.ed_viewOnMaps_BT);
        ed_eventName = (TextView)findViewById(R.id.ed_groupName_TV);
        ed_eventDate = (TextView)findViewById(R.id.ed_startDate_TV);
        ed_eventTime = (TextView)findViewById(R.id.ed_startTime_TV);
        ed_eventLocation= (TextView)findViewById(R.id.ed_location_TV);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            chat_eventName = extras.getString("eventName");
            chat_eventLocation = extras.getString("eventLocation");
            chat_eventDate = extras.getString("eventDate");
            chat_eventTime = extras.getString("eventTime");
        }

        ed_eventName.setText(chat_eventName);
        ed_eventDate.setText(chat_eventDate);
        ed_eventTime.setText(chat_eventTime);
        ed_eventLocation.setText(chat_eventLocation);

        // Implementa el listener para el botón
        goToMaps.setOnClickListener(new View.OnClickListener() {
            //Implementa el método onClick para responder al evento de presionar el botón
            @Override
            public void onClick(View v){
                Uri uri = Uri.parse("geo:0,0?q="+ chat_eventLocation);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(mapIntent);
            }
        });
    }
}
