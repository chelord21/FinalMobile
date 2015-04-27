package itesm.mx.finalprojectmobile20;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class AddGroup extends ActionBarActivity{
    private static final String TAG = "";

    /* Buttons */
    Button ag_changepic_bt;
    Button ag_save_bt;

    /* EditTexts */
    EditText ag_nombre_et;
    EditText ag_motto_et;

    /* ImageViews */
    ImageView ag_groupPic_iv;

    /* Images */
    Bitmap scaled;
    private Firebase ag_firebase_ref;

    /* Strings*/
    String email_user;
    String username;

    byte[] arrayFoto;

    private static final String FIREBASE_URL ="https://hop-in.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            email_user = extras.getString("email");
        }

        ag_changepic_bt = (Button)findViewById(R.id.ag_changePic_BT);
        ag_groupPic_iv = (ImageView)findViewById(R.id.ag_groupPic_IV);
        ag_save_bt = (Button)findViewById(R.id.ag_save_BT);
        ag_nombre_et = (EditText)findViewById(R.id.ag_groupName_ET);
        ag_motto_et = (EditText)findViewById(R.id.ag_groupMotto_ET);
        ag_firebase_ref = new Firebase(FIREBASE_URL).child("group");

        final CharSequence[] options = { "Take Photo", "Select from Gallery","Cancel" };

        ag_changepic_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddGroup.this);
                builder.setTitle("Edit Photo");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            try {
                                File f = new File(android.os.Environment.getExternalStorageDirectory(), "profile.jpg");

                                //if (f.exists() && f.canWrite()) f.delete();

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                startActivityForResult(intent, 1);

                            } catch (ActivityNotFoundException e) {
                                Log.e(TAG, "No camera: " + e);
                            } catch (Exception e) {
                                Log.e(TAG, "Cannot make photo: " + e);
                            }
                        } else if (options[item].equals("Select from Gallery")) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(intent, 2);
                            } catch (ActivityNotFoundException e) {
                                Log.e(TAG, "No gallery: " + e);
                            }
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        ag_save_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                scaled.compress(Bitmap.CompressFormat.PNG, 0, stream);
                arrayFoto = stream.toByteArray();
                */

                if(!ag_nombre_et.getText().toString().isEmpty() || !ag_motto_et.getText().toString().isEmpty()) {
                    String nombre = ag_nombre_et.getText().toString();
                    String motto = ag_motto_et.getText().toString();
                    HashMap<String, String> userList = new HashMap<String, String>();
                    Firebase ref = new Firebase(FIREBASE_URL + "users");
                    final SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
                    Query queryRef = ref.orderByChild("email").equalTo(email_user);

                    queryRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                            Map<String, Object> value = (Map<String, Object>)snapshot.getValue();
                            username = value.get("user").toString();
                            prefs.edit().putString("username", username).commit();
                            System.out.println(snapshot.getKey() + " was " + value.get("user") + " meters tall");
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

                    userList.put(username,email_user);
                    Grupo_Java grupo_java = new Grupo_Java(nombre,motto,userList);
                    ag_firebase_ref.push().setValue(grupo_java);
                    Intent intent = new Intent(AddGroup.this, Groups.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AddGroup.this, "Not all fields were filled. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_group, menu);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("profile.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                    scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);

                    ag_groupPic_iv.setImageBitmap(scaled);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                ag_groupPic_iv.setImageBitmap(thumbnail);
            }
        }
    }
}
