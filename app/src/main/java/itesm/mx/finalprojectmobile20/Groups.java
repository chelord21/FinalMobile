package itesm.mx.finalprojectmobile20;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class Groups extends ActionBarActivity {

    ListView listaGrupos;
    ArrayList<String> nombres;

    List<ParseObject> grupos = new ArrayList<ParseObject>();

    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        listaGrupos = (ListView) findViewById(R.id.groups_grouplist_LV);

        nombres = new ArrayList<String>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    grupos = scoreList;
                    for(int i=0; i<scoreList.size(); i++){

                    }
                } else {
                  Log.d("Mensaje error", "Can't retrieve information");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_groups, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuGroups_addGroup) {
            Intent intent = new Intent(Groups.this, AddGroup.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
