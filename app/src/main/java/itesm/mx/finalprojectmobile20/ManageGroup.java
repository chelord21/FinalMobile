package itesm.mx.finalprojectmobile20;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ManageGroup extends ActionBarActivity {

    Button editPhoto;
    Button saveChanges;
    Button addFriend;
    EditText groupName_ET;
    EditText groupMotto_ET;
    TextView groupName_TV;
    TextView groupMotto_TV;
    ListView members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);

        editPhoto = (Button)findViewById(R.id.mg_changePic_BT);
        saveChanges = (Button)findViewById(R.id.mg_saveChanges_BT);
        addFriend = (Button)findViewById(R.id.mg_addFriend_BT);
        groupName_ET = (EditText)findViewById(R.id.mg_groupName_ET);
        groupMotto_ET = (EditText)findViewById(R.id.mg_groupMotto_ET);
        groupName_TV = (TextView)findViewById(R.id.mg_groupname_TV);
        groupMotto_TV = (TextView)findViewById(R.id.mg_groupMotto_TV);
        members = (ListView)findViewById(R.id.mg_members_LV);


        addFriend.setOnClickListener(new View.OnClickListener() {
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

                            /* Verificar que el usuario exista y mostrar un mensaje de "Usuario encontrado o algo así" */


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
                }
                else{
                    Toast.makeText(getApplicationContext(), "Cannot complete action because you are not connected to internet", Toast.LENGTH_SHORT).show();
                }
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
