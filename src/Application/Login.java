package Application;

import connectivity.ConnectionClass;
import  javafx.event.ActionEvent;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login {

    public TextField userName;
    public PasswordField userPassword;

    public void login(ActionEvent actionEvent) throws IOException {

        //Create a connection
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Main.userName = userName.getText();

        try {
            String cat = ""; //The category
            boolean valid = false;
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM user_auth WHERE username ='"+Main.userName+"'AND password = '"+userPassword.getText()+"';"; //Query to check whether username and password is valid

            String userDetails = "SELECT user_id,username,FirstName,LastName,address,category FROM user_auth WHERE username ='"+userName.getText()+"'AND password = '"+userPassword.getText()+"';";//Query to get the user's information
            ResultSet resultSet1 = statement.executeQuery(sql);

            if(resultSet1.next()){ //If the username exists
                valid = true;
            }
            else{ //give the error
                valid = false;
                Stage error = new Stage();

                error.initModality(Modality.APPLICATION_MODAL);
                error.setTitle("Wrong username or password");
                error.setMinWidth(500);

                Label label = new Label();
                label.setText("You have entered a wrong username or a password. Please try again !");

                Button closeButton = new Button("Close");
                closeButton.setOnAction(e -> error.close());

                VBox layout = new VBox(10);
                layout.getChildren().addAll(label,closeButton);
                layout.setAlignment(Pos.CENTER  );

                Scene scene = new Scene(layout);
                error.setScene(scene);
                error.showAndWait();

            }

            ResultSet resultSet2 = statement.executeQuery(userDetails);//execute query to get user details

            if (resultSet2.next()){ //get information to the variables

                Main.user_id = resultSet2.getString(1);
                Main.firstName = resultSet2.getString(3);
                Main.lastName = resultSet2.getString(4);
                Main.address = resultSet2.getString(5);
                cat = resultSet2.getString(6);
            }

            if(valid) { //Load scenes according to the category
                if (cat.equals("adm")) {
                    adminLoad(actionEvent);
                } else if (cat.equals("staff")) {
                    staffLoad(actionEvent);
                } else {
                    customerLoad(actionEvent);
                }
            }
        }
        catch (SQLException e) { //If there is any sql exception, print it on the console
            e.printStackTrace();
        }

    }

    public void signUpPushed(ActionEvent event) throws IOException{ //load sign up scene

        Parent signUpParent  = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene signUpScene = new Scene(signUpParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(signUpScene);
        window.show();

    }

    private void adminLoad(ActionEvent event) throws IOException{ //load admin view

        Parent adminParent  = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
        Scene adminScene = new Scene(adminParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(adminScene);
        window.show();

    }

    private void staffLoad(ActionEvent event) throws IOException{ //load staff view

        Parent staffParent  = FXMLLoader.load(getClass().getResource("StaffView.fxml"));
        Scene staffScene = new Scene(staffParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(staffScene);
        window.show();

    }

    private void customerLoad(ActionEvent event) throws IOException{ //load customer view

        Parent customerParent  = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
        Scene customerScene = new Scene(customerParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(customerScene);
        window.show();

    }


}
