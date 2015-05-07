package itesm.mx.finalprojectmobile20;

import android.content.Intent;
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

    ListView groupsLV;
    String group_name;
    String group_motto;
    List<String> groups_groupNameList;

    ArrayList<Grupo_Java> groups_groupList;

    ArrayAdapter<String> groups_adapter;

    String groups_childKey;
    String groups_groupName;
    String groups_groupMotto;
    Grupo_Java grupo_java;
    ArrayList<String> groups_groupUsers;
    static final int ADD_GROUP_REQUEST = 1;

    Button groups_loadGroups_BT;

    String user_email;
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Bundle extras = getIntent().getExtras();
        groups_groupUsers= new ArrayList<String>();
        groups_groupList = new ArrayList<Grupo_Java>();
        if(extras != null){
            user_email = extras.getString("email");
        }

        groupsLV = (ListView) findViewById(R.id.groups_grouplist_LV);

        Firebase ref = new Firebase("https://hop-in.firebaseio.com/" + "group");
        //final SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        Query queryRef = ref.orderByChild("grupo_nombre");

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                int counter=0;
                for (DataSnapshot child : snapshot.getChildren()) {
                    groups_childKey =child.getKey();
                    if(groups_childKey.equals("grupo_nombre")){
                        counter++;
                         groups_groupName = child.getValue().toString();
                    }else if(groups_childKey.equals("grupo_motto")){
                        counter++;
                        groups_groupMotto = child.getValue().toString();

                        System.out.println("Motto "+ groups_groupMotto);
                    }else if(groups_childKey.equals("grupo_users")){
                        counter++;
                        groups_groupUsers = (ArrayList<String>) child.getValue();

                        System.out.println("Users "+groups_groupUsers);
                    }
                    if(counter == 3){
                        try {
                            Thread.sleep(100);
                            System.out.println(groups_groupMotto + " " + groups_groupUsers +  " " + groups_groupName);
                            grupo_java = new Grupo_Java(groups_groupName, groups_groupMotto, groups_groupUsers);
                            groups_groupList.add(grupo_java);
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

        groups_groupNameList = new ArrayList<>();

        groups_loadGroups_BT = (Button) findViewById(R.id.groups_loadVar_Btn);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groups_loadGroups_BT.isPressed()){
                    for(int i = 0; i< groups_groupList.size(); i++){
                       ArrayList<String> array =groups_groupList.get(i).getGrupo_users();
                        for(int j = 0; j < array.size(); j++){
                            if(array.get(j).equals(user_email)){
                                groups_groupNameList.add(groups_groupList.get(i).getGrupo_nombre());
                                System.out.println("Entro al if con " + groups_groupList.get(i).getGrupo_nombre());
                            }
                        }

                    }

                    loadGroups();
                    groups_loadGroups_BT.setVisibility(View.INVISIBLE);
                }
            }
        };

         groupsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String groups_name_Str = groups_groupNameList.get(position);
               Intent intent = new Intent(Groups.this, Group.class);
               intent.putExtra("groupName", groups_name_Str);
               intent.putExtra("userEmail", user_email);
               startActivity(intent);
             }
         });

        groups_loadGroups_BT.setOnClickListener(listener);

     }

    public void loadGroups(){
        groups_adapter =new ArrayAdapter<String>(
                this,
                R.layout.activity_row,
                R.id.rowTV,
                groups_groupNameList
        );
        groupsLV.setAdapter(groups_adapter);
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
    public void onResume(){
        super.onResume();
        System.out.println("ENTERS ON RESUME");

        groups_loadGroups_BT.setVisibility(View.VISIBLE);
        groups_loadGroups_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groups_loadGroups_BT.isPressed()){
                    for(int i = 0; i< groups_groupList.size(); i++){
                        ArrayList<String> array =groups_groupList.get(i).getGrupo_users();
                        System.out.println("Checo " + groups_groupList.get(i).getGrupo_nombre());
                        for(int j = 0; j < array.size(); j++){
                            if(array.get(j).equals(user_email)){
                                groups_groupNameList.add(groups_groupList.get(i).getGrupo_nombre());
                                System.out.println("Entro al if con " + groups_groupList.get(i).getGrupo_nombre());
                            }
                        }

                    }
                    loadGroups();
                    groups_loadGroups_BT.setVisibility(View.INVISIBLE);
                }

            }
        });

        try{
            groups_adapter.clear();
            groups_adapter.addAll(groups_groupNameList);
            groups_adapter.notifyDataSetChanged();
        }catch(NullPointerException e){

        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }
    
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
