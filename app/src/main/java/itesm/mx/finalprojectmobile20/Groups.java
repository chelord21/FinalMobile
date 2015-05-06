package itesm.mx.finalprojectmobile20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;


public class Groups extends ActionBarActivity {

    ListView listaGrupos;
    ArrayList<String> nombres;
    String group_name;
    String group_motto;
    ArrayList<String> lista;
    List<String> grupos;

    ArrayList<Grupo_Java> gruposDePersonas;

    ArrayAdapter<String> adapter;

    String key ;
    String nombre;
    String motto;
    Grupo_Java grupo_java;
    ArrayList<String> users;
    static final int ADD_GROUP_REQUEST = 1;


    String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Bundle extras = getIntent().getExtras();
        users= new ArrayList<String>();
        gruposDePersonas = new ArrayList<Grupo_Java>();
        if(extras != null){
            user_email = extras.getString("email");
        }

        listaGrupos = (ListView) findViewById(R.id.groups_grouplist_LV);

        nombres = new ArrayList<String>();
        Firebase ref = new Firebase("https://hop-in.firebaseio.com/" + "group");
        final SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        Query queryRef = ref.orderByChild("grupo_nombre");

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                int counter=0;
                for (DataSnapshot child : snapshot.getChildren()) {
                    key =child.getKey();
                    if(key.equals("grupo_nombre")){
                        counter++;
                         nombre = child.getValue().toString();
                    }else if(key.equals("grupo_motto")){
                        counter++;
                        motto = child.getValue().toString();

                        System.out.println("Motto "+motto);
                    }else if(key.equals("grupo_users")){
                        counter++;
                        users = (ArrayList<String>) child.getValue();

                        System.out.println("Users "+users);
                    }
                    if(counter == 3){
                        try {
                            Thread.sleep(100);
                            System.out.println(motto + " " + users +  " " + nombre);
                            grupo_java = new Grupo_Java(nombre,motto,users);
                            gruposDePersonas.add(grupo_java);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
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

        grupos = new ArrayList<>();

        final Button chat = (Button) findViewById(R.id.groups_loadVar_Btn);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chat.isPressed()){
                    for(int i = 0; i< gruposDePersonas.size(); i++){
                       ArrayList<String> array =gruposDePersonas.get(i).getGrupo_users();
                        for(int j = 0; j < array.size(); j++){
                            if(array.get(j).equals(user_email)){
                                grupos.add(gruposDePersonas.get(i).getGrupo_nombre());
                                System.out.println("Entro al if con " + gruposDePersonas.get(i).getGrupo_nombre());
                            }
                        }

                    }

                    loadFunction();
                    chat.setVisibility(View.INVISIBLE);
                }
            }
        };

         listaGrupos.setAdapter(adapter);

         listaGrupos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String groups_name_Str = grupos.get(position);
               Intent intent = new Intent(Groups.this, Group.class);
               intent.putExtra("groupName", groups_name_Str);
               startActivity(intent);
             }
         });

        chat.setOnClickListener(listener);

     }

    public void loadFunction(){
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(
                this,
                R.layout.activity_row,
                R.id.rowTV,
                grupos
        );
        listaGrupos.setAdapter(adapter);
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
            startActivityForResult(intent, ADD_GROUP_REQUEST);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == ADD_GROUP_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Group created", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
