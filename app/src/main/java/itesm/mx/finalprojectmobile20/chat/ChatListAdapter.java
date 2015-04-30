package itesm.mx.finalprojectmobile20.chat;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;

import itesm.mx.finalprojectmobile20.R;

/**
 * Created by AlejandroSanchez on 4/22/15.
 */
public class ChatListAdapter extends FirebaseListAdapter<Chat>  {

    private String chatUser;

    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, Chat.class, layout, activity);
        this.chatUser = mUsername;
    }

    protected void populateView(View view, Chat chat) {
        // Map a Chat object to an entry in our listview
        String author = chat.getAuthor();
        TextView authorText = (TextView) view.findViewById(R.id.author);
        authorText.setText(author);
        // If the message was sent by this user, color it differently
        if (author != null && author.equals(chatUser)) {
            Log.d("Prueba de tardanza coloreado author", "Se coloreo usuario rojo");
            authorText.setTextColor(Color.RED);
            authorText.setGravity(Gravity.RIGHT);
            ((TextView) view.findViewById(R.id.message)).setText(chat.getMessage());
            ((TextView) view.findViewById(R.id.message)).setGravity(Gravity.RIGHT);
        } else {
            Log.d("Prueba de tardanza coloreado author", "Se coloreo usuario azul");
            authorText.setTextColor(Color.BLUE);
            authorText.setGravity(Gravity.LEFT);
            ((TextView) view.findViewById(R.id.message)).setText(chat.getMessage());
            ((TextView) view.findViewById(R.id.message)).setGravity(Gravity.LEFT);
        }
    }
}
