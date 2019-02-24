package Application;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AddStaff {

    public PasswordField addPassword;
    public TextField addUsername;
    public PasswordField confirmPassword;

    private ConnectionClass connectionClass = new ConnectionClass();
    private Connection connection = connectionClass.getConnection();

    public void addUser() {

        if (addPassword.getText().equals(confirmPassword.getText())) { //To make sure that user has entered the same password in both password and confirmation field.
            try {
                //Query to insert the values given by the admin to the user_auth table of the database
                String sql = "INSERT INTO user_auth(username,password,category) VALUES('" + addUsername.getText() + "','" + addPassword.getText() + "','" + "staff" + "');";
                PreparedStatement preparedStatement = connection.prepareStatement(sql); //prepare the statement
                preparedStatement.execute(); //execute the prepared statement

                //Load a new stage on the scene to let the user know that the staff member has been added successfully
                Stage success = new Stage();
                success.initModality(Modality.APPLICATION_MODAL);
                success.setTitle("Member Added");
                success.setMinWidth(500);

                Label label = new Label();
                label.setText("The staff member has been added successfully.\nNow s/he can log into the system.");

                Button closeButton = new Button("Ok");
                closeButton.setOnAction(e -> success.close());

                VBox layout = new VBox(10);
                layout.getChildren().addAll(label,closeButton);
                layout.setAlignment(Pos.CENTER  );

                Scene scene = new Scene(layout,150,100);
                success.setScene(scene);
                success.showAndWait();

                addUsername.setText("");
                addPassword.setText("");
                confirmPassword.setText("");


            } catch (SQLException e) { //To catch any SQl exception
                e.printStackTrace();
            }
        }
        else { //Load a new stage to show the user that there is a difference in the two password fields.
            Stage error = new Stage();

            error.initModality(Modality.APPLICATION_MODAL);
            error.setTitle("Password Error");
            error.setMinWidth(500);

            Label label = new Label();
            label.setText("The two password fields don't match. Please try again !");

            Button closeButton = new Button("Ok");
            closeButton.setOnAction(e -> error.close());

            VBox layout = new VBox(10);
            layout.getChildren().addAll(label,closeButton);
            layout.setAlignment(Pos.CENTER  );

            Scene scene = new Scene(layout,150,100);
            error.setScene(scene);
            error.showAndWait();

        }
    }

       public void logOutPushed(ActionEvent event) throws IOException { //To load the login screen when the logout button is pushed

          Parent logOutParent  = FXMLLoader.load(getClass().getResource("Login.fxml"));
          Scene logOutScene = new Scene(logOutParent, 900, 600);

         Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

         window.setScene(logOutScene);
        window.show();

    }

    public void loadMainMenu(ActionEvent event)  throws IOException{ //To load the main menu

        Parent MainMenuParent  = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
        Scene MainMenuScene = new Scene(MainMenuParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(MainMenuScene);
        window.show();
    }
}
