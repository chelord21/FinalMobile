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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class UserProfile extends ActionBarActivity {
    private static final String TAG = "";

    //TextViews
    /*
        They display the currentUsers profile
            Displays the username and the email
            Back button returns to the screen of eventCreation

      */

    TextView userProfile_username_TV;
    TextView userProfile_email_TV;

    //Buttons
    Button userProfile_edit_Btn;
    Button userProdile_save_Btn;

    //ImageView
    ImageView userProfile_profile_IV;


    // Bitmap
    Bitmap scaled;

    //String
    String userID;

    //Boolean
    Boolean changesFlag;

    //Listeners
    View.OnClickListener userProfile_listener_OCL;

    //Strings
    private String user_email_S;
    private static final String FIREBASE_URL ="https://hop-in.firebaseio.com/";
    private String userProfile_username_S;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userProfile_username_TV = (TextView) findViewById(R.id.userProf_username_TV);
        userProfile_email_TV = (TextView) findViewById(R.id.userProf_email_TV);
        userProfile_edit_Btn = (Button) findViewById(R.id.userProf_edit_Btn);
        userProfile_profile_IV = (ImageView) findViewById(R.id.userProf_profile_IV);
        userProdile_save_Btn = (Button) findViewById(R.id.userProf_save_BT);

        changesFlag = false;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_email_S = extras.getString("email");
        }

        if(isNetworkConnected()){
            setUser();
        }else{
            Toast.makeText(UserProfile.this, "Couldn't retrive user information because you are not connected to internet.", Toast.LENGTH_SHORT).show();
        }


        userProfile_email_TV.setText(user_email_S);

        final CharSequence[] options = { "Take Photo", "Select from Gallery","Cancel" };

        userProfile_edit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changesFlag = true;

                AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
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
                            }
                            catch (ActivityNotFoundException e) {
                                Log.e(TAG, "No camera: " + e);
                            }
                            catch ( Exception e ) {
                                Log.e(TAG, "Cannot make photo: " + e);
                            }
                        }
                        else if (options[item].equals("Select from Gallery")) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(intent, 2);
                            }
                            catch (ActivityNotFoundException e) {
                                Log.e(TAG, "No gallery: " + e);
                            }
                        }
                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        userProdile_save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()) {
                    saveImage(scaled);
                    changesFlag = false;
                    Toast.makeText(UserProfile.this, "Image Saved", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UserProfile.this, "Cannot complete action. You are not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (changesFlag) {
            Toast.makeText(UserProfile.this, "Image was not saved", Toast.LENGTH_SHORT).show();
        }
        super.onBackPressed();
    }

    private void setUser() {
        //User name set

        Firebase ref = new Firebase(FIREBASE_URL + "users");
        Query queryRef = ref.orderByChild("email").equalTo(user_email_S);

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Map<String, Object> value = (Map<String, Object>)snapshot.getValue();
                userID = snapshot.getKey();
                userProfile_username_S = value.get("user").toString();

                byte[] imageByteArray;

                try{
                    imageByteArray = decodeImage(value.get("image").toString());
                    userProfile_profile_IV.setImageBitmap(BitmapFactory.decodeByteArray(imageByteArray,0,imageByteArray.length));
                }catch(NullPointerException e){
                    Toast.makeText(UserProfile.this, "Error, no image to retrive", Toast.LENGTH_SHORT).show();
                }

                System.out.println("User in datasnapshot: " + userProfile_username_S);
                userProfile_username_TV.setText(userProfile_username_S);
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
                scaled = (BitmapFactory.decodeFile(picturePath));
            }

            userProfile_profile_IV.setImageBitmap(scaled);
        }
    }

    private void saveImage(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] imageArray = stream.toByteArray();

        String imageDataString = encodeImage(imageArray);

        Firebase ref = new Firebase(FIREBASE_URL + "users");
        Map<String, Object> imageString = new HashMap<String, Object>();
        imageString.put("image", imageDataString);
        ref.child(userID).updateChildren(imageString);
    }

    public static String encodeImage(byte[] imageByteArray){
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }


    public static byte[] decodeImage(String imageDataString) {
        return Base64.decode(imageDataString, Base64.DEFAULT);
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
