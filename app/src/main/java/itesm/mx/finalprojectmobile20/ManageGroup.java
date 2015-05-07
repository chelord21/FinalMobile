package itesm.mx.finalprojectmobile20;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ManageGroup extends ActionBarActivity {

    Button manageGroup_editPhoto_BtnUI;
    Button manageGroup_saveChanges_BtnUI;
    Button manageGroup_addFriend_BtnUI;
    TextView manageGroup_groupName_TV;
    TextView manageGroup_groupMotto_TV;
    ListView manageGroup_members_LV;

    //Firebase
    private static final String FIREBASE_URL ="https://hop-in.firebaseio.com/";

    //Strings
    Strings manageGroup_user;
    String group_KeyID;
    ArrayList<Strings> groups_groupUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            group_KeyID = extras.getExtras.getString("group_key");
            groups_groupUsers = extras.getExtras.getArrayList("users");
        }

        manageGroup_editPhoto_BtnUI = (Button)findViewById(R.id.mg_changePic_BT);
        manageGroup_saveChanges_BtnUI = (Button)findViewById(R.id.mg_saveChanges_BT);
        manageGroup_addFriend_BtnUI = (Button)findViewById(R.id.mg_addFriend_BT);
        manageGroup_groupName_TV = (TextView)findViewById(R.id.mg_groupName_ET);
        manageGroup_groupMotto_TV = (TextView)findViewById(R.id.mg_groupMotto_ET);
        manageGroup_members_LV = (ListView)findViewById(R.id.mg_members_LV);


        manageGroup_addFriend_BtnUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(ManageGroup.this);
                    alert.setTitle("Type friend's mail");
                    final EditText input = new EditText(ManageGroup.this);
                    alert.setView(input);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            final String email = input.getText().toString();
                            Toast.makeText(getApplicationContext(),
                                    "Please wait",
                                    Toast.LENGTH_SHORT).show();
                            addFriend(email);
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(getApplicationContext(),
                                    "Canceled",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot complete action because you are not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void addFriend(String email) {
        Firebase ref = new Firebase(FIREBASE_URL + "users");
        Query queryRef = ref.orderByChild("email").equalTo(email);
        
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Map<String, Object> value = (Map<String, Object>)snapshot.getValue();
                if (!value = null) {
                    manageGroup_user = value.get("user").toString();
                    groups_groupUsers.add(email);

                    Firebase usersRef = new Firebase(FIREBASE_URL + "users");
                    Map<String, Object> users = new HashMap<String, Object>();
                    users.put("users", groups_groupUsers);
                    usersRef.child(group_KeyID).updateChildren(users);

                    System.out.println("User: " + manageGroup_user + "Users: " + groups_groupUsers);
                } else {
                    Toast.makeText(getApplicationContext(), "User was not found", Toast.LENGTH_SHORT).show();
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
