package Application;

import connectivity.ConnectionClass;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.sql.*;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main extends Application {

    public static HLListS lL_Dest = new HLListS();
    public static HLListI lL_Dest_Price = new HLListI();

    public static List<Integer> amountList = new ArrayList<>();
    public static List<Integer> priceList = new ArrayList<>();

    public static String user_id = "";
    public static String userName = "";
    public static String firstName = "";
    public static String lastName = "";
    public static String address = "";
    public static ActionEvent event;
    public static int fullTotalValue = 0; //Total price of the items bought by the customer

    @Override
    public void start(Stage primaryStage) throws Exception { //Load the login scene on the main stage

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        primaryStage.setTitle("J.F.S - Jeff's Fishing Shack");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException { //launch the application

        int nextSendMonth = 0;
        int updateYear = 0;
        int updateMonth = 0;
        int updateDay = 0;
        //Create a connection
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        HLListS addressList = new HLListS();

        Calendar cal = Calendar.getInstance();

        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        try {

            String sql = "SELECT MONTH(send_date) FROM nl_date"; //Query to get the product information
            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery(sql);

            while (resultSet1.next()) {
                //Add product information in strings to the string list
                nextSendMonth = resultSet1.getInt(1);
            }

        } catch (SQLException ex) { //Catch any sql exception and print it on the console
            ex.printStackTrace();
        }

        if (nextSendMonth == month) {
            try {

                String sql = "SELECT username FROM user_auth;"; //Query to get the product information
                Statement statement = connection.createStatement();
                ResultSet resultSet1 = statement.executeQuery(sql);

                while (resultSet1.next()) {
                    //Add product information in strings to the string list
                    addressList.add(resultSet1.getString(1));
                }

            } catch (SQLException ex) { //Catch any sql exception and print it on the console
                ex.printStackTrace();
            }

            String from = "********@gmail.com"; //The from email address shown in the email
            final String username = "********@gmail.com";//The email username needed to log in
            final String password = "********";//The password needed to log in

            // Sending email through the host smtp.gmail.com
            String host = "smtp.gmail.com";

            //Set Properties
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.required", "true");

            // Get the Session object.
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            for (int i = 0; i < addressList.size(); i++) {
                try {
                    // Create a default MimeMessage object.
                    Message message = new MimeMessage(session);

                    // Set From: in the email
                    message.setFrom(new InternetAddress(from));

                    // Set To: in the email
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(addressList.get(i + 1)));

                    // Set Subject: of the email
                    message.setSubject("Newsletter");

                    // Create the message body part
                    BodyPart messageBodyPartTwo = new MimeBodyPart();

                    // Now set the message text
                    messageBodyPartTwo.setText("This is the JFS Monthly newsletter.");

                    // Create a multipart message
                    Multipart multipart = new MimeMultipart();

                    // Set text message part
                    multipart.addBodyPart(messageBodyPartTwo);

                    // attach the newsletter
                    messageBodyPartTwo = new MimeBodyPart();
                    String filename = "src/Application/Newsletter/Newsletter.pdf";
                    DataSource source = new FileDataSource(filename);
                    messageBodyPartTwo.setDataHandler(new DataHandler(source));
                    messageBodyPartTwo.setFileName(filename);
                    multipart.addBodyPart(messageBodyPartTwo);

                    // Set the multipart to the message
                    message.setContent(multipart);

                    // Send message
                    Transport.send(message);

                }
                catch (MessagingException ex) { //If any exception occurs
                    throw new RuntimeException(ex);
                }
            }
            if(month == 12){ //If the current month is the last month
                updateYear = year+1;
                updateMonth = 1;
                updateDay = 1;
            }
            else{ //If the current month is another regular month
                updateYear = year;
                updateMonth = month+1;
                updateDay = 1;
            }
            String dateToUpdate = Integer.toString(updateYear)+"-"+Integer.toString(updateMonth)+"-"+Integer.toString(updateDay);
            String sql = "UPDATE nl_date SET send_date='"+ dateToUpdate+"';";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();

        }

        launch(args);
    }

}
