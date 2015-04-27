package itesm.mx.finalprojectmobile20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import itesm.mx.finalprojectmobile20.chat.ChatMain;


public class Groups extends ActionBarActivity {

    ListView listaGrupos;
    ArrayList<String> nombres;
    //Cambiar por lista de otro
//    List<ParseObject> grupos = new ArrayList<ParseObject>();

    ArrayAdapter<String> adapter;
    String groups_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            groups_email = extras.getString("email");
        }

        listaGrupos = (ListView) findViewById(R.id.groups_grouplist_LV);

        nombres = new ArrayList<String>();
        final Button chat = (Button) findViewById(R.id.groups_chat_Btn);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chat.isPressed()){
                    Intent intent = new Intent(Groups.this, ChatMain.class);
                    intent.putExtra("email", groups_email);
                    startActivity(intent);
                }
            }
        };

        chat.setOnClickListener(listener);

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
            intent.putExtra("email",groups_email);
            startActivity(intent);
            return true;
        }
        if (id == R.id.menuGroups_userProfile) {
            Intent intent = new Intent(Groups.this, UserProfile.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
