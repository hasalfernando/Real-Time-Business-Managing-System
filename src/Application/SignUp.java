package Application;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUp {

    public TextField phone;
    public TextField firstName;
    public TextField lastName;
    public TextField address;
    public TextField password;
    public TextField passwordConfirmation;
    public TextField email;
    public CheckBox agreeNewsLetter;
    public TextField pinConfirmation;
    public Button pinConfirmationButton;

    public String pin = "";
    public String emailValue ="";
    public String phoneValue ="";
    public String fNameValue ="";
    public String lNameValue ="";
    public String addressValue ="";
    public String passwordValue ="";

    public int currentId =0;
    public int nextId = 0;

    public void signUpUser(ActionEvent event){ //to sign up the user

        String getUserId = "SELECT MAX(user_id) FROM user_auth;"; //get the last user id

        pin = Integer.toString((int)(Math.random()*9000)+1000); //generate a pin to be sent to confirm the correct email address

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery(getUserId);

            if(resultSet1.next()){ //get the id from the results to the currentId variable
                currentId = resultSet1.getInt(1);
            }
        } catch (SQLException e) { //catch any SQL exception
            e.printStackTrace();
        }


        fNameValue = firstName.getText();
        lNameValue = lastName.getText();
        phoneValue = phone.getText();
        addressValue = address.getText();
        passwordValue = password.getText();
        emailValue = email.getText();
        nextId = currentId+1;

        boolean allFieldsNull = emailValue.equals("") | fNameValue.equals("") | lNameValue.equals("") | phoneValue.equals("") | addressValue.equals("") | passwordValue.equals("");

        if (!allFieldsNull) { //If everything is filled

                if (agreeNewsLetter.isSelected()) { //If the user has agreed to receive the monthly news letter

                    if (validateEmail(emailValue)) { //If the typed email address is actually an email address

                        if(validatePassword(passwordValue)) { //If the password is strong

                            if(passwordValue.equals(passwordConfirmation.getText())) { //If the password field and the password confirmation field matches
                                sendPin(); //Send the pin to the typed address

                                //Let the user know that the pin has been sent
                                Stage pinSent = new Stage();

                                pinSent.initModality(Modality.APPLICATION_MODAL);
                                pinSent.setTitle("Pin Sent");
                                pinSent.setMinWidth(500);
                                pinSent.setMinHeight(100);

                                Label label = new Label();
                                label.setText("A security pin has been sent to your email to confirm the account.\nPlease enter it in the pin confirmation box and click confirm.");

                                Button closeButton = new Button("Ok");
                                closeButton.setOnAction(e -> pinSent.close());

                                VBox layout = new VBox();
                                layout.getChildren().addAll(label, closeButton);
                                layout.setAlignment(Pos.CENTER);

                                Scene scene = new Scene(layout);
                                pinSent.setScene(scene);
                                pinSent.showAndWait();
                                pinConfirmation.setVisible(true);
                                pinConfirmationButton.setVisible(true);

                            }
                            else{ //Show password error
                                Stage error = new Stage();

                                error.initModality(Modality.APPLICATION_MODAL);
                                error.setTitle("Password Error");
                                error.setMinWidth(500);
                                error.setMinHeight(100);

                                Label label = new Label();
                                label.setText("Make sure to add matching password in the confirmation field.");

                                Button closeButton = new Button("Ok");
                                closeButton.setOnAction(e -> error.close());

                                VBox layout = new VBox();
                                layout.getChildren().addAll(label, closeButton);
                                layout.setAlignment(Pos.CENTER);

                                Scene scene = new Scene(layout);
                                error.setScene(scene);
                                error.showAndWait();

                            }
                        }
                        else{ //Show weak password error
                            Stage error = new Stage();

                            error.initModality(Modality.APPLICATION_MODAL);
                            error.setTitle("Weak Password");
                            error.setMinWidth(500);
                            error.setMinHeight(100);

                            Label label = new Label();
                            label.setText("Weak password !\nPlease enter a password which has 8 or more characters and \nhas at least 2 non-Alphabetical characters.");

                            Button closeButton = new Button("Ok");
                            closeButton.setOnAction(e -> error.close());

                            VBox layout = new VBox();
                            layout.getChildren().addAll(label, closeButton);
                            layout.setAlignment(Pos.CENTER);

                            Scene scene = new Scene(layout);
                            error.setScene(scene);
                            error.showAndWait();

                        }
                } else { //Show wrong email error
                    Stage error = new Stage();

                    error.initModality(Modality.APPLICATION_MODAL);
                    error.setTitle("Enter a valid email");
                    error.setMinWidth(500);
                    error.setMinHeight(100);

                    Label label = new Label();
                    label.setText("Please enter a valid email address to proceed.");

                    Button closeButton = new Button("Ok");
                    closeButton.setOnAction(e -> error.close());

                    VBox layout = new VBox();
                    layout.getChildren().addAll(label, closeButton);
                    layout.setAlignment(Pos.CENTER);

                    Scene scene = new Scene(layout);
                    error.setScene(scene);
                    error.showAndWait();

                }
            } else { //Show that the user has to agree to receive the monthly email to proceed
                Stage error = new Stage();

                error.initModality(Modality.APPLICATION_MODAL);
                error.setTitle("Agree to receive Newsletter");
                error.setMinWidth(500);
                error.setMinHeight(100);

                Label label = new Label();
                label.setText("To proceed, please agree to receive the monthly email newsletter.");

                Button closeButton = new Button("Ok");
                closeButton.setOnAction(e -> error.close());

                VBox layout = new VBox();
                layout.getChildren().addAll(label, closeButton);
                layout.setAlignment(Pos.CENTER);

                Scene scene = new Scene(layout);
                error.setScene(scene);
                error.showAndWait();
            }
        }
        else{ //Ask the user to fill all the fields
                Stage error = new Stage();

                error.initModality(Modality.APPLICATION_MODAL);
                error.setTitle("Fill the required fields");
                error.setMinWidth(500);
                error.setMinHeight(100);

                Label label = new Label();
                label.setText("Filling all the fields are compulsory. Fill every field and click sign up.");

                Button closeButton = new Button("Ok");
                closeButton.setOnAction(e -> error.close());

                VBox layout = new VBox();
                layout.getChildren().addAll(label, closeButton);
                layout.setAlignment(Pos.CENTER);

                Scene scene = new Scene(layout);
                error.setScene(scene);
                error.showAndWait();

            }

    }

    private boolean validatePassword(String password){ //Check whether the password is strong

        Pattern patternPass = Pattern.compile("[^\\\\p{IsAlphabetic}^\\\\p{IsDigit}]"); //The regex to check the non alphabetic characters
        Matcher match = null;
        int count = 0;

        for(int i =0;i<password.length();i++){
            match = patternPass.matcher(Character.toString(password.charAt(i)));
            if(match.find()){ //If the character is non-Alphabetic
                count ++;
            }
        }
        if((count>=2) & (password.length()>=8)){ //If the password has 2 or more non-Alphabetic characters and the length is greater than or equal to eight
            return true;
        }
        else {
            return false;
        }
    }

    private boolean validateEmail(String email) { //Validate whether the text is in email address format

        boolean matched = false;
        //Regex to check the email addresses
        String emailRegex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern pattern = Pattern.compile(emailRegex);
        matched = pattern.matcher(email).matches(); //run a pattern match

        return matched;
    }

    private void sendPin(){ //Send the pin to the user's email

        String to = emailValue;

        String from = "********@gmail.com"; //from
        final String username = "********@gmail.com";//the email username
        final String password = "********";//email password

        // sending email through the host smtp.gmail.com
        String host = "smtp.gmail.com";

        //set properties
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

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From in the email
            message.setFrom(new InternetAddress(from));

            // Set To: in the email
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: in the email
            message.setSubject("Your Pin for JFS");

            // Set the message
            message.setText("This is your pin for the JFS account registration.\nPlease confirm it in the application.\n"+pin+"\n\n If you didn't enter your email in the JFS app, someone has entered your email in our application. You don't have to worry about that because without this pin, that person can't register your email. If you want you can report this to us by replying to this email.");

            // Send message
            Transport.send(message);
        }
        catch (MessagingException e) { //Catch any messaging exceptions occurred in the process
            throw new RuntimeException(e);
        }

}

    public void goToLogin(ActionEvent event) throws IOException { //load login scene

        Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginScene = new Scene(loginParent, 900, 600);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();

    }

    public void pinConfirmation(ActionEvent event) throws IOException{ //Confirm whether the user has received the pin

        if(pinConfirmation.getText().equals(pin)) {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();

            try {
                String sql = "INSERT INTO user_auth VALUES('" + nextId + "','" + emailValue + "','" + fNameValue + "','" + lNameValue + "','" + Integer.parseInt(phoneValue) + "','" + addressValue + "','" + passwordValue + "','" + "cst" + "');";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.execute();
                firstName.setText("");
                lastName.setText("");
                phone.setText("");
                address.setText("");
                password.setText("");
                email.setText("");
                passwordConfirmation.setText("");
                pinConfirmation.setPromptText("Confirm Pin");
                agreeNewsLetter.setSelected(false);

                Stage success = new Stage();//Give the successs message to the user

                success.initModality(Modality.APPLICATION_MODAL);
                success.setTitle("Registration Successful");
                success.setMinWidth(500);
                success.setMinHeight(100);

                Label labelRegistration = new Label();
                labelRegistration.setText("Your registration process was successful. \nNow you can login to the system using the email and password you registerd.");

                Button closeButtonRegistration = new Button("Ok");
                closeButtonRegistration.setOnAction(e -> success.close());

                VBox layoutRegistration = new VBox();
                layoutRegistration.getChildren().addAll(labelRegistration, closeButtonRegistration);
                layoutRegistration.setAlignment(Pos.CENTER);

                Scene sceneRegistration = new Scene(layoutRegistration);
                success.setScene(sceneRegistration);
                success.showAndWait();
                goToLogin(event); //Load login screen

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            Stage errorPin = new Stage(); //Give an error to the user

            errorPin.initModality(Modality.APPLICATION_MODAL);
            errorPin.setTitle("Pin Error");
            errorPin.setMinWidth(500);
            errorPin.setMinHeight(100);

            Label labelConPw = new Label();
            labelConPw.setText("The pin you entered is wrong ! Please try again.");

            Button closeButtonConPw = new Button("Ok");
            closeButtonConPw.setOnAction(e -> errorPin.close());

            VBox layoutConPw = new VBox();
            layoutConPw.getChildren().addAll(labelConPw, closeButtonConPw);
            layoutConPw.setAlignment(Pos.CENTER);

            Scene sceneConPw = new Scene(layoutConPw);
            errorPin.setScene(sceneConPw);
            errorPin.showAndWait();

        }

    }


}
