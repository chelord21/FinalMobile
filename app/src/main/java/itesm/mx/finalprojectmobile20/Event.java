package itesm.mx.finalprojectmobile20;

/**
 * Created by Marco, collaborated by Chelord on 5/1/15.
 */
public class Event {
    private String eventName;
    private String groupName;
    private String eventLocation;

    private Event() {

    }

    private Event(String eventName, String groupName) {
        this.eventName = eventName;
        this.groupName = groupName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventName() {
        return eventName;
    }

    public String getGroupName() {
        return groupName;
    }
}
