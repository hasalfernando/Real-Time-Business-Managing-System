package Application;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SelectQuantity implements Initializable{

    private ConnectionClass connectionClass = new ConnectionClass();

    public Label cTotal;
    public Label adTotal;
    public Button checkPriceBtn;
    public Label noItems;
    public Label message;
    public Label message2;
    public TextField amount;
    public Label fullTotal;
    public Label tempTotal;
    public Button proceedToNextBtn;
    public Button proceedToPayBtn;
    public Label displayPrice;
    public Label totalLabel;
    public ListView listInvoice;
    private int tempTotalValue = 0;
    private int iterator = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) { //Load the message in the label in the initialization phase

        tempTotalValue = 0;
        Main.fullTotalValue = 0;
        message.setText("How many items do you want from\n\n(Item)\t\t\t(Size)\t(Price(Rs.))\n"+Main.lL_Dest.get(iterator+1));
        fullTotal.setText("Rs. "+Integer.toString(Main.fullTotalValue));
        tempTotal.setText("Rs. 0");

    }

    public void checkPrice(ActionEvent event){ //Display the calculated price
        int Price = 0;
        Price = Main.lL_Dest_Price.get(iterator+1)*Integer.parseInt(amount.getText());
        message2.setText("Rs. "+Price);
        tempTotalValue = Main.fullTotalValue+Price;
        tempTotal.setText("Rs. "+tempTotalValue);
    }

    public void proceedToNext(ActionEvent event) { //load next item

        int price = Main.lL_Dest_Price.get(iterator+1)*Integer.parseInt(amount.getText());
        Main.amountList.add(iterator,Integer.parseInt(amount.getText()));
        Main.priceList.add(iterator,price);
        amount.setText(""); //Set amount to null

        listInvoice.getItems().add(Main.lL_Dest.get(iterator+1)+"\t\t\t"+Main.amountList.get(iterator)+"\t\t\t"+Main.priceList.get(iterator)+"\n");


        Main.fullTotalValue = Main.fullTotalValue+price;
        iterator++;
        if(iterator<Main.lL_Dest.size()) { //If there are any items left
            message.setText("How many items do you want from\n\n(Item)\t\t\t(Size)\t(Price(Rs.))\n" + Main.lL_Dest.get(iterator+1));
            message2.setText("");
            tempTotal.setText("");

            fullTotal.setText("Rs. " + Integer.toString(Main.fullTotalValue));
        }
        else{ //If there are no more items left

            //hide unwanted items and show the wanted button and the total

            checkPriceBtn.setVisible(false);
            noItems.setVisible(false);
            cTotal.setVisible(false);
            adTotal.setVisible(false);
            message2.setVisible(false);
            message.setVisible(false);
            amount.setVisible(false);
            fullTotal.setVisible(false);
            tempTotal.setVisible(false);
            proceedToNextBtn.setVisible(false);
            displayPrice.setVisible(true);
            totalLabel.setVisible(true);
            proceedToPayBtn.setVisible(true);
            displayPrice.setText("Rs. "+Integer.toString(Main.fullTotalValue));
        }
    }

    public void proceedToPay(ActionEvent event) throws IOException{ //load Enter Card scene

        Parent cardParent  = FXMLLoader.load(getClass().getResource("EnterCard.fxml"));
        Scene cardScene = new Scene(cardParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(cardScene);
        window.show();

    }

    public void loadPrevious(ActionEvent event) throws IOException { //load Product Catalogue scene

        Parent previousParent  = FXMLLoader.load(getClass().getResource("ProductCatalogue.fxml"));
        Scene previousScene = new Scene(previousParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(previousScene);
        window.show();

    }

    public void loadMainMenu(ActionEvent event)  throws IOException { //Load Main menu

        Parent MainMenuParent  = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
        Scene MainMenuScene = new Scene(MainMenuParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(MainMenuScene);
        window.show();
    }

    public void logOutPushed(ActionEvent event) throws IOException { //Load login screen

        Parent logOutParent  = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene logOutScene = new Scene(logOutParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(logOutScene);
        window.show();

    }

}
