package itesm.mx.finalprojectmobile20;

/**
 * Created by Marco, collaborated by Chelord on 5/1/15.
 */
public class Event {
    private String eventName;
    private String eventLocation;
    private String eventTime;
    private String eventDate;

    public Event() {

    }

    public Event(String eventName, String eventLocation, String eventTime, String eventDate) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventTime = eventTime;
        this.eventDate = eventDate;
    }
}
