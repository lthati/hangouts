import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.lang.Object;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
        Event currentEvent = new Event();
        currentEvent.setDescription(description);
        currentEvent.setOrganizerName(organizerName);
        currentEvent.setOrganizerEmail(organizerEmail);
        currentEvent.setLocation(location);
        currentEvent.setEventDate(eventDate);
        currentEvent.setStartTime(startTime);
        currentEvent.setEndTime(endTime);
        currentEvent.setGuestCount(guestCount);
        String subject = "A New Event has been created";
        String message = createEmailBody(currentEvent);
        sendEmail(emails, message, subject);
        //Send Email
    }

    public List<Event> getAllEvents() {
        return getDbConnection().getAllEvents();
    }

    public Event getEvent(int eventId) {
        return getDbConnection().getEvent(eventId);
    }

    public void updateGuestCount(int eventId, String email, String name) {
        Event event = getEvent(eventId);
        getDbConnection().updateGuestCount(eventId);
        String subject = "Thanks for RSVPing";
        String message =createEmailBodySignUp(name, event);
        List<String> emails = new ArrayList<String>();
        emails.add(email);
        sendEmail(emails, message, subject);

        //Send Email with outlook attachment
    }

    public void sendEmail(List<String> to, String body, String subject) {
        Address[] toAddress = new InternetAddress[to.size()];
        for (int i=0; i < to.size(); i++){
            try{
                toAddress[i] = new InternetAddress(to.get(i));
            }
            catch (MessagingException mex) {
                mex.printStackTrace();
            }
        }
        String from = "events@cisco.com";
        String username = "hangouts.cisco@gmail.com";
        String password = "cisco123";
        Properties properties = System.getProperties();
        String host = "smtp.gmail.com";
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.user", username);
        properties.put("mail.smtp.password", password);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        username, password);// Specify the Username and the PassWord
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipients(Message.RecipientType.TO, toAddress);

            message.setSubject(subject);

            message.setText(body);

            Transport transport = session.getTransport("smtp");
            transport.connect(host, username, password);
            transport.send(message);
            transport.close();
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public String createEmailBody(Event event){
        String body = "";
        body = "Hi there, " + event.getOrganizerName() + " had a created a new event " + event.getDescription() +
                " which will be held on " + event.getEventDate() + " from " + event.getStartTime() + " till " + event.getEndTime() +
                " in " + event.getLocation() +". Make sure to sign up online on the website to reserve you spot. There are only " +
                event.getGuestCount() + " spots available. If you have any questions, please email at " + event.getOrganizerEmail();
        return body;
    }

    public String createEmailBodySignUp(String name, Event event){
        String body = "";
        body = "Hi " + name + ", Thanks for signing up for " + event.getDescription() +
                " which will be held on " + event.getEventDate() + " from " + event.getStartTime() + " till " + event.getEndTime() +
                " in " + event.getLocation() +". " + event.getOrganizerName() +" will happy to see you. If you have any questions, please email at "
                + event.getOrganizerEmail() + " We will see you soon";
        return body;
    }

    public static void main(String[] args){
            BusinessFunctions bf = new BusinessFunctions();
            bf.updateGuestCount(2, "lthati@cisco.com",  "Leela Thati");
    }

}
