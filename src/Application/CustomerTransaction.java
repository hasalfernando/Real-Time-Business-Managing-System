package Application;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class CustomerTransaction extends StaffView{ //The Parent class of this CustomerTransaction class is the StaffView class

    public ListView transactionsList;
    private HLListS lL_Transactions = new HLListS();


    public void viewAllTransactions(ActionEvent event) {

        //To hide the lastTransaction pane and to make the transactions list view visible
            lastTransactionPane.setVisible(false);
            transactionsList.setVisible(true);
        try {
            //To execute the query to get all the transactions related to one person
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();

            String sql = "SELECT * FROM transactions WHERE username ='"+username.getText()+"';";
            Statement statement = connection.createStatement(); //create statement
            ResultSet resultSet1 = statement.executeQuery(sql); //execute query

            //To make sure there are no pre-loaded values in the linked list nor the list view
            lL_Transactions.clear();
            transactionsList.getItems().clear();

            while (resultSet1.next()) { //get the result values using the resultSet
                lL_Transactions.add(resultSet1.getString(1)+"\t\t"+resultSet1.getString(2)+"\t\t"+resultSet1.getString(3)+"\t\t"+resultSet1.getString(4)+"\t\t"+resultSet1.getString(5)+"\t\t"+resultSet1.getString(6)+"\t\t"+resultSet1.getString(7));
            }

            for(int i =0; i<lL_Transactions.size();i++) { //load the values in the linked list to the list view
                transactionsList.getItems().add(lL_Transactions.get(i+1));
            }

        }
        catch (SQLException ex){ //If an sql exception occurs, print the exception on the console
            ex.printStackTrace();
        }
    }

    public void hideListView(){ //to hide the list view. Has to use this to hide because the parent class has no way to hide the pane
        transactionsList.setVisible(false);
    }

    public void loadMainMenu(ActionEvent event)  throws IOException { //To load the main menu which is the AdminView

        Parent MainMenuParent  = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
        Scene MainMenuScene = new Scene(MainMenuParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(MainMenuScene);
        window.show();
    }

}

