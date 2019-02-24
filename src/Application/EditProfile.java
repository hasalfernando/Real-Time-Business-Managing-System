package Application;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditProfile extends SignUp implements Initializable { //This class is inheriting from the SignUp class


    @Override
    public void initialize(URL location, ResourceBundle resources) { //Load the information relevant to the user when the screen loads

        try {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            String sql = "SELECT * FROM user_auth WHERE username ='"+Main.userName+"' ;";
            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery(sql);

            while (resultSet1.next()) {
                email.setText(resultSet1.getString(2));
                firstName.setText(resultSet1.getString(3));
                lastName.setText(resultSet1.getString(4));
                phone.setText(resultSet1.getString(5));
                address.setText(resultSet1.getString(6));
                password.setText(resultSet1.getString(7));
                agreeNewsLetter.setSelected(true);
            }

            }
        catch (SQLException e) { //catch any sql exception that takes place
            e.printStackTrace();
        }

    }

    public void updateInfo(ActionEvent event) throws IOException{
        if(pinConfirmation.getText().equals(pin)) {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();

            try {
                String sql = "UPDATE user_auth SET username= '" + emailValue + "',FirstName='" + fNameValue + "',LastName='" + lNameValue + "',phone='" + Integer.parseInt(phoneValue) + "',address='" + addressValue + "',password='" + passwordValue + "' WHERE username ='"+Main.userName+"';";
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

    public void loadMainMenu(ActionEvent event)  throws IOException { //Load CustomerView scene

        Parent MainMenuParent  = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
        Scene MainMenuScene = new Scene(MainMenuParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(MainMenuScene);
        window.show();
    }

    public void logOutPushed(ActionEvent event) throws IOException { //Load the login screen

        Parent logOutParent  = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene logOutScene = new Scene(logOutParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(logOutScene);
        window.show();

    }

}
