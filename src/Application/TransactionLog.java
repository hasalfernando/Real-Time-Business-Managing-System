package Application;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TransactionLog implements Initializable{

    public ListView transacListView;
    public DatePicker datePicker;
    private HLListS lL_transactions = new HLListS();

    ConnectionClass connectionClass = new ConnectionClass();
    private Connection connection = connectionClass.getConnection();

    @Override
    public void initialize(URL location, ResourceBundle resources) { //To get all the transactions of the company

        try {

            String sql = "SELECT * FROM transactions;";
            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery(sql);

            while (resultSet1.next()) { //load results to the linked list
                lL_transactions.add(resultSet1.getString(1)+"\t"+resultSet1.getString(2)+"\t"+resultSet1.getString(3)+"\t\t"+resultSet1.getString(4)+"\t\t\t"+resultSet1.getString(5)+"\t\t"+resultSet1.getString(6)+"\t\t"+resultSet1.getString(7));
            }

            for(int i = 0; i<lL_transactions.size();i++) { //add the list items to the list view
                transacListView.getItems().add(lL_transactions.get(i+1));
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public void viewTranForDate(ActionEvent event) { //get transactions of a particular date

        try{

            //clear the transactions linked list and the list view
            lL_transactions.clear();
            transacListView.getItems().clear();

            //query to ge the transactions of the particular date
            String sql = "SELECT * FROM transactions WHERE date='"+datePicker.getValue()+"';";
            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery(sql); //execute the query

            while (resultSet1.next()) { //get the results to the list
                lL_transactions.add(resultSet1.getString(1)+"\t"+resultSet1.getString(2)+"\t\t"+resultSet1.getString(3)+"\t"+resultSet1.getString(4)+"\t\t\t"+resultSet1.getString(5)+"\t\t"+resultSet1.getString(6)+"\t\t"+resultSet1.getString(7));
             }

            for(int i = 0; i<lL_transactions.size();i++) { //load the results to the list view
               transacListView.getItems().add(lL_transactions.get(i+1));
            }
         }
        catch (SQLException ex){ //catch any SQL exception and print it on the console
            ex.printStackTrace();
        }
}
    public void loadMainMenu(ActionEvent event)  throws IOException { //load main menu

        Parent MainMenuParent  = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
        Scene MainMenuScene = new Scene(MainMenuParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(MainMenuScene);
        window.show();
    }

    public void logOutPushed(ActionEvent event) throws IOException { //load login scene

        Parent logOutParent  = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene logOutScene = new Scene(logOutParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(logOutScene);
        window.show();

    }

}
