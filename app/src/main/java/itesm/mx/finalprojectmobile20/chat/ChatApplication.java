package itesm.mx.finalprojectmobile20.chat;

import com.firebase.client.Firebase;

/**
 * Created by AlejandroSanchez on 4/22/15.
 */
public class ChatApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
   }
}
