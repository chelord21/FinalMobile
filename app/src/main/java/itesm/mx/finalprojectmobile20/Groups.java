package itesm.mx.finalprojectmobile20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import itesm.mx.finalprojectmobile20.chat.ChatMain;


public class Groups extends ActionBarActivity {

    ListView listaGrupos;
    ArrayList<String> nombres;
    String group_name;
    String group_motto;
    ArrayList<String> lista;
    List<String> grupos;

    ArrayAdapter<String> adapter;
    String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            user_email = extras.getString("email");
        }

        listaGrupos = (ListView) findViewById(R.id.groups_grouplist_LV);

        nombres = new ArrayList<String>();
        final Button chat = (Button) findViewById(R.id.groups_chat_Btn);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chat.isPressed()){
                    Intent intent = new Intent(Groups.this, ChatMain.class);
                    intent.putExtra("email", user_email);
                    startActivity(intent);
                }
            }
        };

        Firebase ref = new Firebase("https://hop-in.firebaseio.com/" + "group");
        final SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        Query queryRef = ref.orderByChild("grupo_nombre");

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Map<String, Object> value = (Map<String, Object>)snapshot.getValue();
                String grupo_name = value.get("grupo_nombre").toString();
                String grupo_motto = value.get("grupo_motto").toString();
                lista =(ArrayList<String>) value.get("grupo_users");
                prefs.edit().putString("grupo_name", grupo_name).apply();
                prefs.edit().putString("grupo_motto",grupo_motto).apply();
                Set<String> set = new HashSet<>();
                set.addAll(lista);
                prefs.edit().putStringSet("grupo_users",set).apply();
                System.out.println("id is "+ snapshot.getKey() + " motto is " + value.get("grupo_motto") + "group name is "+ grupo_name + " users " + lista);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Map<String,?> keys = prefs.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values", entry.getKey() + ": " +
                    entry.getValue().toString());
        }
        grupos = new ArrayList<>();

        Set<String> set = (Set<String>) keys.get("grupo_users");
        group_name = keys.get("grupo_name").toString();
        group_motto = keys.get("grupo_motto").toString();
        for (String s : set) {
            if(s.equals(user_email)){
                grupos.add(group_name);
                System.out.println(s);
            }

        }

        System.out.println(grupos);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(
               this,
               R.layout.activity_row,
               R.id.rowTV,
               grupos
        );
        listaGrupos.setAdapter(adapter);

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
            intent.putExtra("email", user_email);
            startActivity(intent);
            return true;
        }
        if (id == R.id.menuGroups_userProfile) {
            Intent intent = new Intent(Groups.this, UserProfile.class);
            intent.putExtra("email", user_email);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
