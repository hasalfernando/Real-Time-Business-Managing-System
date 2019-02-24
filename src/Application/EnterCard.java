package Application;

import connectivity.ConnectionClass;
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
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class EnterCard {

    public TextField cNum;
    public DatePicker eDate;
    public PasswordField sCode;

    private String toEmail =""; //To set bought products of the email
    private int transId = 0; //To set the Transaction id

    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    public void verifyCreditCardDetails(ActionEvent event) throws IOException{

    try{
        String getTransId = "SELECT MAX(transaction_id) FROM transactions;"; //To get the last transaction_id

        Statement statement = connection.createStatement();
        ResultSet resultSetTransId = statement.executeQuery(getTransId);

        if(resultSetTransId.next()){ //If there is a result that means there is at least one transaction done. Add 1 to it for the next transaction
            transId = resultSetTransId.getInt(1)+1;
        }
        else{ //If there are no transactions, the transaction_id should be 1
            transId =transId+1;
        }

    }
    catch (SQLException e) { //If any SQL exception occurs, print the exception on the console
        e.printStackTrace();
    }


        try {

            Statement statement = connection.createStatement();
            //Query to check whether the credit card is valid
            String sql = "SELECT * FROM credit_card WHERE Card_No ='" + cNum.getText() + "'AND Ex_Date = '" + eDate.getValue() + "'AND Sec_Code ='"+sCode.getText()+"';";
            ResultSet resultSet1 = statement.executeQuery(sql);

            if (resultSet1.next()) {

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); //To set the date and time of the transacction
                LocalDateTime now = LocalDateTime.now(); //TO get only the date

                //Query to update the transaction
                String forTransactions = "INSERT INTO transactions VALUES('"+ transId+"','" + Main.userName + "','" + cNum.getText() + "','" + eDate.getValue() + "','"+dtf.format(now)+"','"+java.time.LocalDate.now()+"','" + Main.fullTotalValue +"');";

                PreparedStatement preparedStatement = connection.prepareStatement(forTransactions); //prepare the statement with the query
                preparedStatement.execute(); //Execute the preapared statement

                //Let the user know the card has been accepted and his invoice will be received via an email.
                Stage success = new Stage();

                success.initModality(Modality.APPLICATION_MODAL);
                success.setTitle("Valid Card Accepted");
                success.setMinWidth(500);

                Label label = new Label();
                label.setText("The valid card you entered has been accepted and an invoice will be mailed to your registered email account.\nThank you !\nCome again soon !");

                Button closeButton = new Button("Okay");
                closeButton.setOnAction(e -> success.close());

                VBox layout = new VBox(10);
                layout.getChildren().addAll(label, closeButton);
                layout.setAlignment(Pos.CENTER);

                Scene scene = new Scene(layout,500,100);
                success.setScene(scene);
                success.showAndWait();

                //Clear the fields to confirm that the transaction is complete.
                cNum.setText("");
                eDate.setValue(null);
                sCode.setText("");
                sendInvoice();
                loadMainMenu(event); //Load main menu
            }
            else {

                //Show an error for the wrong credit card details
                Stage error = new Stage();

                error.initModality(Modality.APPLICATION_MODAL);
                error.setTitle("Wrong card Details");
                error.setMinWidth(500);

                Label label = new Label();
                label.setText("You have entered wrong Credit Card details. Please try again !");

                Button closeButton = new Button("Close");
                closeButton.setOnAction(e -> error.close());

                VBox layout = new VBox(10);
                layout.getChildren().addAll(label, closeButton);
                layout.setAlignment(Pos.CENTER);

                Scene scene = new Scene(layout,150,100);
                error.setScene(scene);
                error.showAndWait();


            }

            //System.out.println(resultSet2.getString("category"));

            //System.out.println(x);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void sendInvoice() {
        String to = Main.userName;

        // Sender's email ID needs to be mentioned
        String from = "jfsmarketting@gmail.com";
        final String username = "jfsmarketting@gmail.com";//change accordingly
        final String password ="jeffsmith123";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";

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

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Invoice");

            for(int i = 0; i<Main.lL_Dest.size();i++) {

                toEmail = toEmail+Main.lL_Dest.get(i + 1) + "\t\t\t" + Main.amountList.get(i) + "\t\t\t" + Main.priceList.get(i)+"\n";

            }


            // Now set the actual message
            message.setText("\t\t\t\t\t\t\t\tJeff's Fishing Shack\n\n\n\t\t\t\t\t\t\t\t\tTax Invoice\n\nJeff's Fishing Shack\nTrading as:Octopus Pty Ltd\nShop4, Hillarys Boat Harbour\nHillarys, WA, 6025\nT:0894022222\nE:Sales@JFS.com.au\n\nReceipt: #"+transId +"\t\t\t\t\t\t\t\t\t\t\t\t\t\tDate: "+java.time.LocalDate.now()+"\n\nCustomer: "+Main.firstName+" "+Main.lastName+"\n"+Main.address+"\n\nCustomer: #"+Main.user_id+"\nCustomer Email: "+Main.userName+"\n\nPurchases\n\nCode\t\t\tDesc\t\tSize\t\t\tCost\t\tQty\t\t\t\tAmount\n\n"+toEmail+"\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tTotal Paid: Rs."+Main.fullTotalValue+"\n\n\t\t\t\t\t\t\tThank you for your business.\n\t\t\t\t\t\tJeff's - where the real fishermen shop.");

            // Send message
            Transport.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadPrevious(ActionEvent event) throws IOException {
        Parent previousParent  = FXMLLoader.load(getClass().getResource("SelectQuantity.fxml"));
        Scene previousScene = new Scene(previousParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(previousScene);
        window.show();

    }

    public void loadMainMenu(ActionEvent event)  throws IOException {

        Parent MainMenuParent  = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
        Scene MainMenuScene = new Scene(MainMenuParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(MainMenuScene);
        window.show();
    }

    public void logOutPushed(ActionEvent event) throws IOException {

        Parent logOutParent  = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene logOutScene = new Scene(logOutParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(logOutScene);
        window.show();

    }

}