package itesm.mx.finalprojectmobile20;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class AddGroup extends ActionBarActivity{
    private static final String TAG = "";

    /* Buttons */
    Button ag_changepic_bt;
    Button ag_save_bt;

    /* EditTexts */
    EditText ag_groupName_et;
    EditText ag_groupMotto_et;

    /* ImageViews */
    ImageView ag_groupPic_iv;

    /* Images */
    Bitmap scaled;
    private Firebase ag_firebase_ref;
    String imageString;

    /* Strings*/
    String user_email;
    String user_name;

    byte[] photoArray;

    private static final String FIREBASE_URL ="https://hop-in.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            user_email = extras.getString("email");
        }

        ag_changepic_bt = (Button)findViewById(R.id.ag_changePic_BT);
        ag_groupPic_iv = (ImageView)findViewById(R.id.ag_groupPic_IV);
        ag_save_bt = (Button)findViewById(R.id.ag_save_BT);
        ag_groupName_et = (EditText)findViewById(R.id.ag_groupName_ET);
        ag_groupMotto_et = (EditText)findViewById(R.id.ag_groupMotto_ET);
        ag_firebase_ref = new Firebase(FIREBASE_URL).child("group");

        final CharSequence[] photo_DialogOptions = { "Take Photo", "Select from Gallery","Cancel" };

        ag_changepic_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog_builder = new AlertDialog.Builder(AddGroup.this);
                dialog_builder.setTitle("Edit Photo");
                dialog_builder.setItems(photo_DialogOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (photo_DialogOptions[item].equals("Take Photo")) {
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
                        } else if (photo_DialogOptions[item].equals("Select from Gallery")) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(intent, 2);
                            } catch (ActivityNotFoundException e) {
                                Log.e(TAG, "No gallery: " + e);
                            }
                        } else if (photo_DialogOptions[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                dialog_builder.show();
            }
        });

        ag_save_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                scaled.compress(Bitmap.CompressFormat.PNG, 0, stream);
                photoArray = stream.toByteArray();
                */
                if(isNetworkConnected()) {
                    if (!ag_groupName_et.getText().toString().isEmpty() && !ag_groupMotto_et.getText().toString().isEmpty()) {
                        String group_name = ag_groupName_et.getText().toString();
                        String group_motto = ag_groupMotto_et.getText().toString();
                        ArrayList<String> group_userList;
                        Firebase ref = new Firebase(FIREBASE_URL + "users");
                        Query queryRef = ref.orderByChild("email").equalTo(user_email);

                        queryRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                                Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
                                user_name = value.get("user").toString();
                                System.out.println("User: " + value.get("user"));
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

                    group_userList = new ArrayList<>();
                    group_userList.add(user_email);
                    Grupo_Java grupo_java = new Grupo_Java(group_name, group_motto, group_userList);
                    ag_firebase_ref.push().setValue(grupo_java);
                        Toast.makeText(getApplicationContext(), "Group created", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(AddGroup.this, "Not all fields were filled. Please try again.", Toast.LENGTH_SHORT).show();
                   }
                }else{
                    Toast.makeText(AddGroup.this, "Cannot complete action because you are not connected to internet", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onBackPressed() {
        Toast.makeText(AddGroup.this, "Group was not created", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    private class MyNullKeySerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object nullKey, JsonGenerator jsonGenerator, SerializerProvider unused)
            throws IOException, JsonProcessingException
         {
            jsonGenerator.writeFieldName("");
        }
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
