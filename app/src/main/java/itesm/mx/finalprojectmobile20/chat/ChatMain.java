package itesm.mx.finalprojectmobile20.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Map;

import itesm.mx.finalprojectmobile20.AddEvent;
import itesm.mx.finalprojectmobile20.EventDetails;
import itesm.mx.finalprojectmobile20.ManageGroup;
import itesm.mx.finalprojectmobile20.R;

public class ChatMain extends ActionBarActivity {

    private static final String FIREBASE_URL ="https://hop-in.firebaseio.com/";

    private String chat_username;
    private String user_email;
    private String chat_eventKey;
    private Firebase chat_firebase_ref;
    private ValueEventListener chat_event_VEL;
    private ChatListAdapter chat_listAdapter_LA;

    EditText chat_input_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_email = extras.getString("userEmail");
            chat_eventKey = extras.getString("key");
        }

        getUsername();
        chat_firebase_ref = new Firebase(FIREBASE_URL + "events/" + chat_eventKey).child("chat");
        chat_input_ET = (EditText) findViewById(R.id.messageInput);

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        chat_input_ET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                    handled = true;
                }
                return handled;
            }
        });
    }

    private void getUsername() {
        //User name set

        Firebase ref = new Firebase(FIREBASE_URL + "users");
        final SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        Query queryRef = ref.orderByChild("email").equalTo(user_email);

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Map<String, Object> value = (Map<String, Object>)snapshot.getValue();
                chat_username = value.get("user").toString();
                prefs.edit().putString("username", chat_username).commit();
                System.out.println("user is " + value.get("user"));
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
    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView =(ListView) findViewById(android.R.id.list);
        // Tell our list adapter that we only want 50 messages at a time
        chat_listAdapter_LA = new ChatListAdapter(chat_firebase_ref.limit(50), this, R.layout.chat_message, chat_username);
        listView.setAdapter(chat_listAdapter_LA);
        chat_listAdapter_LA.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chat_listAdapter_LA.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
        chat_event_VEL = chat_firebase_ref.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(ChatMain.this, "Chat is Online", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatMain.this, "Chat is Offline", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        chat_firebase_ref.getRoot().child(".info/connected").removeEventListener(chat_event_VEL);
        chat_listAdapter_LA.cleanup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuChat_eventDetails) {
            Intent intent = new Intent(ChatMain.this, EventDetails.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendMessage() {
        EditText inputText = (EditText) findViewById(R.id.messageInput);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, chat_username);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            chat_firebase_ref.push().setValue(chat);
            inputText.setText("");
        }
    }
}
