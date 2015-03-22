package itesm.mx.finalprojectmobile20;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by AlejandroSanchez on 3/21/15.
 */
@ParseClassName("Message")
public class Message extends ParseObject {
    public String getUserId() {
        return getString("userId");
    }

    public String getBody() {
        return getString("body");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setBody(String body) {
        put("body", body);
    }
}

