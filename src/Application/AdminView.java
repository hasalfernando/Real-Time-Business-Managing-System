package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminView {

    public void logOutPushed(ActionEvent event) throws IOException { //To load login screen

        Parent logOutParent  = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene logOutScene = new Scene(logOutParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(logOutScene);
        window.show();

    }

    public void loadAddStaff(ActionEvent event) throws IOException{ //To load the Add Staff screen

        Parent addStaffParent  = FXMLLoader.load(getClass().getResource("AddStaff.fxml"));
        Scene addStaffScene = new Scene(addStaffParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(addStaffScene);
        window.show();

    }
    public void loadCusTransactions(ActionEvent event) throws IOException { //To load the Customer Transaction screen

        Parent cusTranParent  = FXMLLoader.load(getClass().getResource("CustomerTransaction.fxml"));
        Scene cusTranScene = new Scene(cusTranParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(cusTranScene);
        window.show();
    }

    public void loadCusView(ActionEvent event) throws IOException{ //To load the customer view screen

        Parent cusViewParent  = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
        Scene cusViewScene = new Scene(cusViewParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(cusViewScene);
        window.show();
    }

    public void loadStfView(ActionEvent event) throws IOException{ //To load the staff view screen

        Parent stfViewParent  = FXMLLoader.load(getClass().getResource("StaffView.fxml"));
        Scene stfViewScene = new Scene(stfViewParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(stfViewScene);
        window.show();
    }

    public void loadTransLog(ActionEvent event) throws IOException{ //To load the transaction log screen

        Parent transactionsParent  = FXMLLoader.load(getClass().getResource("TransactionLog.fxml"));
        Scene transactionsScene = new Scene(transactionsParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(transactionsScene);
        window.show();
    }
}
