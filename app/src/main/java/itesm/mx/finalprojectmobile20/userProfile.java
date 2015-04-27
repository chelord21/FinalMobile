package itesm.mx.finalprojectmobile20;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;


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

    //ImageView
    ImageView userProfile_profile_IV;


    // Bitmap
    Bitmap scaled;

    //Listeners
    View.OnClickListener userProfile_listener_OCL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userProfile_username_TV = (TextView) findViewById(R.id.userProf_username_TV);
        userProfile_email_TV = (TextView) findViewById(R.id.userProf_email_TV);
        userProfile_edit_Btn = (Button) findViewById(R.id.userProf_edit_Btn);
        userProfile_profile_IV = (ImageView) findViewById(R.id.userProf_profile_IV);

        //Crear el de firebase
        final CharSequence[] options = { "Take Photo", "Select from Gallery","Cancel" };

        userProfile_edit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }

//    @Override
//    public void onBackPressed(){
//        Intent intent=new Intent(getApplicationContext(),Login.class);
//        Toast.makeText(getApplicationContext(), "Works", Toast.LENGTH_SHORT).show();
//        startActivity(intent);
//    }

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

                    userProfile_profile_IV.setImageBitmap(scaled);
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
                userProfile_profile_IV.setImageBitmap(thumbnail);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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
