package itesm.mx.finalprojectmobile20.chat;

/**
 * Created by AlejandroSanchez on 4/22/15.
 */
public class Chat {
    private String chat_message;
    private String chat_author;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Chat() {
    }

    Chat(String chat_message, String chat_author) {
        this.chat_message = chat_message;
        this.chat_author = chat_author;
    }

    public String getMessage() {
        return chat_message;
    }

    public String getAuthor() {
        return chat_author;
    }
}
