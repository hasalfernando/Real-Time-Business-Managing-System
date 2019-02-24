package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class CustomerView{ //The scene which loads first, when a customer logs in

    public void logOutPushed(ActionEvent event) throws IOException { //Load the login screen when the logout button is pushed

        Parent logOutParent  = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene logOutScene = new Scene(logOutParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(logOutScene);
        window.show();

    }

    public void loadProductCatalogue(ActionEvent event) throws IOException { //load the product catalogue

        Parent productParent  = FXMLLoader.load(getClass().getResource("ProductCatalogue.fxml"));
        Scene productScene = new Scene(productParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(productScene);
        window.show();

    }

    public void loadEnquiry(ActionEvent event) throws IOException { //load the send enquiry scene

        Parent enquiryParent  = FXMLLoader.load(getClass().getResource("Enquiry.fxml"));
        Scene enquiryScene = new Scene(enquiryParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(enquiryScene);
        window.show();

    }


    public void loadEditProfile(ActionEvent event) throws IOException { //load the edit profile scene

        Parent enquiryParent  = FXMLLoader.load(getClass().getResource("EditProfile.fxml"));
        Scene enquiryScene = new Scene(enquiryParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(enquiryScene);
        window.show();
    }
}
