package Application;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StaffView {

    public Label labelUsername;
    public Label labelCardNo;
    public Label labelExpDate;
    public Label labelDT;
    public Label labelTotal;
    public TextField username;
    public AnchorPane lastTransactionPane;

    ConnectionClass connectionClass = new ConnectionClass();

    public void viewTransaction(ActionEvent event) throws SQLException { //to view the last transaction of a customer

        lastTransactionPane.setVisible(true); //Make the pane visible

        Connection connection = connectionClass.getConnection(); //create connection
        Statement statement = connection.createStatement(); //create statement

        //query to get transaction information of the customer
        String sql = "SELECT username, Card_No,Ex_Date,time,paid FROM transactions WHERE time IN (SELECT MAX( time ) FROM transactions WHERE username = '"+username.getText()+"');";
        ResultSet resultSet1 = statement.executeQuery(sql);

        while (resultSet1.next()){ //Load information on the required fields

            labelUsername.setText(resultSet1.getString(1));
            labelCardNo.setText(resultSet1.getString(2));
            labelExpDate.setText(resultSet1.getString(3));
            labelDT.setText(resultSet1.getString(4));
            labelTotal.setText("Rs. "+resultSet1.getString(5));
        }
    }

    public void logOutPushed(ActionEvent event) throws IOException { //load login screen

        Parent logOutParent  = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene logOutScene = new Scene(logOutParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(logOutScene);
        window.show();

    }
}
