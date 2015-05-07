package itesm.mx.finalprojectmobile20;

/**
 * Created by Marco, collaborated by Chelord on 5/1/15.
 */
public class Event {
    String eventName;
    String eventLocation;
    String eventTime;
    String eventDate;
    String groupName;

    public Event() {

    }

    public Event(String eventName, String eventLocation, String eventTime, String eventDate) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventTime = eventTime;
        this.eventDate = eventDate;
    }

    public Event(String eventName, String eventLocation, String eventTime, String eventDate, String groupName) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventTime = eventTime;
        this.eventDate = eventDate;
        this.groupName = groupName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String eventDate) {
        this.groupName = groupName;
    }
}
