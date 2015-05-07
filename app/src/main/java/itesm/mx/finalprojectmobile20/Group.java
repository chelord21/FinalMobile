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

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;

import itesm.mx.finalprojectmobile20.chat.ChatMain;


public class Group extends ActionBarActivity {

    //ListView
    ListView group_EventsLV;

    //Button
    Button group_loadEvents_BT;

    //Lists
    List<String> group_eventNameList;
    ArrayList<Event> group_eventList;
    ArrayAdapter<String> group_eventAdapter;

    //Strings
    String group_eventName;
    String group_eventLocation;
    String group_eventDate;
    String group_eventTime;
    String group_childKey;
    String group_eventKeyID;
    String group_KeyID;
    String group_userEmail;
    String group_selectedGroup;
    ArrayList<String> groups_groupUsers;

    //Event
    Event newEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            group_userEmail = extras.getString("userEmail");
            group_selectedGroup = extras.getString("groupName");
            group_KeyID = extras.getString("group_key");
            groups_groupUsers = extras.getStringArrayList("users");
        }

        setTitle(group_selectedGroup);

        group_eventList = new ArrayList<Event>();

        group_EventsLV = (ListView)findViewById(R.id.group_eventList_LV);

        Firebase ref = new Firebase("https://hop-in.firebaseio.com/" + "events");
        Query queryRef = ref.orderByChild("eventName");
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                int counter=0;
                for (DataSnapshot child : snapshot.getChildren()) {
                    group_eventKeyID = snapshot.getKey();
                    group_childKey = child.getKey();
                    if(group_childKey.equals("eventDate")){
                        counter++;
                         group_eventDate = child.getValue().toString();
                         System.out.println("Date: "+ group_eventDate);
                    }else if(group_childKey.equals("eventLocation")){
                        counter++;
                        group_eventLocation = child.getValue().toString();

                        System.out.println("Location: "+ group_eventLocation);
                    }else if(group_childKey.equals("eventName")){
                        counter++;
                        group_eventName = child.getValue().toString();
                        System.out.println("Name: "+ group_eventName);
                    } else if (group_childKey.equals("eventTime")) {
                        counter++;
                        group_eventTime = child.getValue().toString();
                        System.out.println("Time: "+ group_eventTime);
                    }
                    if(counter == 4){
                        try {
                            Thread.sleep(100);
                            System.out.println(group_eventName + " " + group_eventLocation +  " " + group_eventTime + " " + group_eventDate);
                            newEvent = new Event(group_eventName, group_eventLocation, group_eventTime, group_eventDate);
                            group_eventList.add(newEvent);
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
        
        group_eventNameList = new ArrayList<>();

        group_loadEvents_BT = (Button) findViewById(R.id.group_load_Btn);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(group_loadEvents_BT.isPressed()){
                    for(int i = 0; i< group_eventList.size(); i++){
                        group_eventNameList.add(group_eventList.get(i).getEventName());
                    }
                    loadEvents();
                    group_loadEvents_BT.setVisibility(View.INVISIBLE);
                }
            }
        };

         group_EventsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(Group.this, ChatMain.class);
               intent.putExtra("key", group_eventKeyID);
               intent.putExtra("userEmail", group_userEmail);
               intent.putExtra("eventName", group_eventName);
               intent.putExtra("eventLocation", group_eventLocation);
               intent.putExtra("eventTime", group_eventTime);
               intent.putExtra("eventDate", group_eventDate);
               startActivity(intent);
             }
         });

        group_loadEvents_BT.setOnClickListener(listener);
    }

    public void loadEvents(){
        group_eventAdapter =new ArrayAdapter<String>(
                this,
                R.layout.activity_row,
                R.id.rowTV,
                group_eventNameList
        );
        group_EventsLV.setAdapter(group_eventAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuGroup_addEvent) {
            Intent intent = new Intent(Group.this, AddEvent.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menuGroup_modifyGroup) {
            Intent intent = new Intent(Group.this, ManageGroup.class);
            intent.putExtra("group_key", group_KeyID);
            intent.putExtra("users", groups_groupUsers);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("ENTERS ON RESUME");
        group_loadEvents_BT.setVisibility(View.VISIBLE);
        group_loadEvents_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(group_loadEvents_BT.isPressed()){
                    for(int i = 0; i< group_eventList.size(); i++){
                        group_eventNameList.add(group_eventList.get(i).getEventName());
                    }
                    loadEvents();
                    group_loadEvents_BT.setVisibility(View.INVISIBLE);
                }

            }
        });

        try{
            group_eventAdapter.clear();
            group_eventAdapter.addAll(group_eventNameList);
            group_eventAdapter.notifyDataSetChanged();
        }catch(NullPointerException e){

        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
