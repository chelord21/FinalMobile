package itesm.mx.finalprojectmobile20;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class EventDetails extends ActionBarActivity {

    Button goToMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        goToMaps = (Button)findViewById(R.id.ed_viewOnMaps_BT);

        // Implementa el listener para el botón
        goToMaps.setOnClickListener(new View.OnClickListener() {
            //Implementa el método onClick para responder al evento de presionar el botón
            @Override
            public void onClick(View v){
                /*
                String direccionStr = direccion.getText().toString();
                direccionStr = direccionStr.replace(' ', '+');
                */

                Uri uri = Uri.parse("geo:0,0?q="+ " "/*direccionStr*/);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(mapIntent);
            }
        });
    }
}
