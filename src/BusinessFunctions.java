import java.util.List;

/**
 * Created by sartjais on 10/24/17.
 */
public class BusinessFunctions {

    private DatabaseConnection dbConnection;

    public DatabaseConnection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public BusinessFunctions() {
        setDbConnection(new DatabaseConnection());
    }

    public void addEmail(String email) {
        if (email != null && !email.isEmpty()) {
            getDbConnection().addEmail(email);
        }
    }

    public List<String> getEmails() {
        return getDbConnection().getEmails();
    }

    public void addEvent(String description, String organizerName, String organizerEmail, String location, String eventDate, String startTime, String endTime, int guestCount) {
        getDbConnection().addEvent(description, organizerName, organizerEmail, location, eventDate, startTime, endTime, guestCount);
        List<String> emails = getEmails();
        //Send Email
    }

    public List<Event> getAllEvents() {
        return getDbConnection().getAllEvents();
    }

    public Event getEvent(int eventId) {
        return getDbConnection().getEvent(eventId);
    }

    public void updateGuestCount(int eventId, String email) {
        Event event = getEvent(eventId);
        getDbConnection().updateGuestCount(eventId);
        //Send Email with outlook attachment
    }
}
