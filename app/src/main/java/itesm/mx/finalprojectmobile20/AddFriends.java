package itesm.mx.finalprojectmobile20;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddFriends extends ActionBarActivity {

    EditText add_find_ET;
    Button add_find_Btn;

    //Click listener
    View.OnClickListener add_click_OCL;

    String user_to_find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        add_find_Btn = (Button) findViewById(R.id.add_find_Btn);
        add_find_ET = (EditText) findViewById(R.id.add_find_ET);
        add_click_OCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add_find_Btn.isPressed()){
                    if(!add_find_ET.toString().isEmpty()){
                        user_to_find = add_find_ET.getText().toString();

                    }else{
                        Toast.makeText(getApplicationContext(),"Field Empty", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        };

        add_find_Btn.setOnClickListener(add_click_OCL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
