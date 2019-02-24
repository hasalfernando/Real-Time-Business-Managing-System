package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class Enquiry {

    public TextArea enquiryText;
    public TextField emailText;
    public PasswordField passwordText;

    public void sendEnquiry(ActionEvent event) {

        String to = "jfsmarketting@gmail.com"; //The email address the email should be sent to

        String from = emailText.getText(); //The from email address shown in the email
        final String username = emailText.getText();//The email username needed to log in
        final String password = passwordText.getText();//The password needed to log in

        // Sending email through the host smtp.gmail.com
        String host = "smtp.gmail.com";

        //Set Properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.required","true");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: in the email
            message.setFrom(new InternetAddress(from));

            // Set To: in the email
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: of the email
            message.setSubject("Enquiry");

            // Set the message of the email
            message.setText(enquiryText.getText());

            // Send message
            Transport.send(message);

            //Create a new temporary stage to let the user know that the enquiry has been sent
            Stage success = new Stage();

            success.initModality(Modality.APPLICATION_MODAL);
            success.setTitle("Enquiry Sent");
            success.setMinWidth(500);

            Label label = new Label();
            label.setText("Your enquiry has been successfully submitted !");

            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> success.close());

            VBox layout = new VBox(10);
            layout.getChildren().addAll(label,closeButton);
            layout.setAlignment(Pos.CENTER  );

            Scene scene = new Scene(layout);
            success.setScene(scene);
            success.showAndWait();

        } catch (MessagingException ex) { //If any exception occurs
            throw new RuntimeException(ex);
        }

    }

    public void loadMainMenu(ActionEvent event)  throws IOException { //To load main menu

        Parent MainMenuParent  = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
        Scene MainMenuScene = new Scene(MainMenuParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(MainMenuScene);
        window.show();
    }

    public void logOutPushed(ActionEvent event) throws IOException { //To load login scene

        Parent logOutParent  = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene logOutScene = new Scene(logOutParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(logOutScene);
        window.show();

    }

}
