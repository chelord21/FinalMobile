package itesm.mx.finalprojectmobile20;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


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
    }
}
