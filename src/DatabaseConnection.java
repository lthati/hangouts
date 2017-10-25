import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created by sartjais on 10/24/17.
 */
public class DatabaseConnection {

    public Connection getConnection() throws SQLException {

        String user = "root";
        String password = "abc123";
        String serverName = "localhost";
        int portNumber = 3306 ;

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", user);
        connectionProps.put("password", password);

        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:" + "mysql" + "://" +
                            serverName +
                            ":" + portNumber + "/CiscoEvents",
                    connectionProps);
        }
        catch (Exception ex){
            System.out.println(ex);
        }
        return conn;
    }

    public void addEmail(String email){
        Connection connection;
        try {
            connection = getConnection();
            CallableStatement query = connection.prepareCall("{call addEmail(?)}");
            query.setString("InputEmail", email);
            query.executeQuery();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<String> getEmails(){
        List<String> emails = new ArrayList<String>();
        Connection connection;
        try {
            connection = getConnection();
            CallableStatement query = connection.prepareCall("{call getEmails()}");
            ResultSet results = query.executeQuery();
            while(results.next()){
                emails.add(results.getString("Email"));
            }
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return emails;
    }

    public void addEvent(String description, String organizerName, String organizerEmail, String location, String eventDate, String startTime, String endTime, int guestCount){
        Connection connection;
        try {
            connection = getConnection();
            CallableStatement query = connection.prepareCall("{call addEvent(?,?,?,?,?,?,?,?)}");
            query.setString("InputDescription", description);
            query.setString("InputOrganizerName", organizerName);
            query.setString("InputOrganizerEmail", organizerEmail);
            query.setString("InputLocation", location);
            query.setString("InputEventDate", eventDate);
            query.setString("InputStartTime", startTime);
            query.setString("InputEndTime", endTime);
            query.setInt("InputGuestCount", guestCount);
            query.executeQuery();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Event> getAllEvents(){
        List<Event> events = new ArrayList<Event>();
        Connection connection;
        try {
            connection = getConnection();
            CallableStatement query = connection.prepareCall("{call getAllEvents()}");
            ResultSet results = query.executeQuery();
            while(results.next()){
                Event event = new Event();
                event.setEventId(results.getInt("EventsId"));
                event.setDescription(results.getString("Description"));
                event.setOrganizerName(results.getString("OrganizerName"));
                event.setOrganizerEmail(results.getString("OrganizerEmail"));
                event.setLocation(results.getString("Location"));
                event.setEventDate(results.getString("EventDate"));
                event.setStartTime(results.getString("StartTime"));
                event.setEndTime(results.getString("EndTime"));
                event.setGuestCount(results.getInt("GuestsCount"));
                events.add(event);
            }
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return events;
    }

    public Event getEvent(int eventId){
        Event event = new Event();
        Connection connection;
        try {
            connection = getConnection();
            CallableStatement query = connection.prepareCall("{call getEventbyId(?)}");
            query.setInt("InputEventId", eventId);
            ResultSet results = query.executeQuery();
            while(results.next()){
                event.setEventId(results.getInt("EventsId"));
                event.setDescription(results.getString("Description"));
                event.setOrganizerName(results.getString("OrganizerName"));
                event.setOrganizerEmail(results.getString("OrganizerEmail"));
                event.setLocation(results.getString("Location"));
                event.setEventDate(results.getString("EventDate"));
                event.setStartTime(results.getString("StartTime"));
                event.setEndTime(results.getString("EndTime"));
                event.setGuestCount(results.getInt("GuestsCount"));
                System.out.println(event.getDescription());
            }
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return event;
    }

    public void updateGuestCount(int eventId){
        Connection connection;
        try {
            connection = getConnection();
            CallableStatement query = connection.prepareCall("{call updateGuestCountByEventId(?)}");
            query.setInt("InputEventId", eventId);
            query.executeQuery();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args){
        DatabaseConnection db = new DatabaseConnection();
        db.updateGuestCount(1);
    }
}

