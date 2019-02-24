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
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ProductCatalogue implements Initializable{

    public ListView<String> productList;
    public ListView<String> destProductList;

    private HLListS lL_Source = new HLListS();
    private HLListI lL_Source_Price = new HLListI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        loadData();
    }

    private void loadData(){

        //Clear the list views and linked lists to make sure there are no values in those
        lL_Source.clear();
        lL_Source_Price.clear();
        Main.lL_Dest.clear();
        Main.lL_Dest_Price.clear();


        try {
            //Create a connection
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();

            String sql = "SELECT item_code,name,size,price FROM product_table;"; //Query to get the product information
            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery(sql);

            while (resultSet1.next()) {

                //Add product information in strings to the string list
                lL_Source.add(resultSet1.getString(1)+"\t\t"+resultSet1.getString(2)+"\t\t"+resultSet1.getString(3)+"\t\t\t\t"+resultSet1.getString(4));

                //Add product prices to a int linked list
                lL_Source_Price.add(resultSet1.getInt(4));

                //Sort the two lists
                sorter(lL_Source,lL_Source_Price);
            }

            //add the linked list items to the list view
            for(int i = 0; i<lL_Source.size();i++) {
                productList.getItems().add(lL_Source.get(i+1));
            }
        }
        catch (SQLException ex){ //Catch any sql exception and print it on the console
            ex.printStackTrace();
        }
    }

    public void addToList(MouseEvent event) { //Action to add items one by one from the source list view to the destination list view

        int selected = 0;
        selected = productList.getSelectionModel().getSelectedIndex(); //Get the selected index of the list view

        if (selected >= 0) { //If something is selected

            //Clear the two list views
            productList.getItems().clear();
            destProductList.getItems().clear();

            //Add selected items of source lists to the destination lists
            Main.lL_Dest.add(lL_Source.get(selected+1));
            Main.lL_Dest_Price.add(lL_Source_Price.get(selected+1));

            //Remove the sent items from the source lists
            lL_Source.remove(selected+1);
            lL_Source_Price.remove(selected+1);

            //Sort the destination lists
            sorter(Main.lL_Dest,Main.lL_Dest_Price);

            //add the remaining source list to the first list view
            for(int i = 0; i<lL_Source.size();i++) {
                productList.getItems().add(lL_Source.get(i+1));
            }

            //Add the destination list to the 2nd listview
            for(int i = 0; i<Main.lL_Dest.size();i++) {
                destProductList.getItems().add(Main.lL_Dest.get(i+1));
            }

        }
        else{ //If nothing is selected give an error
            Stage error = new Stage();

            error.initModality(Modality.APPLICATION_MODAL);
            error.setTitle("Select an item");
            error.setMinWidth(500);
            error.setMinHeight(100);

            Label label = new Label();
            label.setText("Please select an item before clicking the button !");

            Button closeButton = new Button("Ok");
            closeButton.setOnAction(e -> error.close());

            VBox layout = new VBox();
            layout.getChildren().addAll(label,closeButton);
            layout.setAlignment(Pos.CENTER  );

            Scene scene = new Scene(layout);
            error.setScene(scene);
            error.showAndWait();

        }
    }

    public void removeFromList(MouseEvent mouseEvent) { //Action to remove one by one from the destination list view

        int selected;
        selected = destProductList.getSelectionModel().getSelectedIndex(); //Get the selected index of the destination list view

        if (selected >= 0) { //If one is selected

            //clear the list views
            productList.getItems().clear();
            destProductList.getItems().clear();

            //Add from destination lists to source lists
            lL_Source.add(Main.lL_Dest.get(selected+1));
            lL_Source_Price.add(Main.lL_Dest_Price.get(selected+1));

            //Remove the selected items from the destination lists
            Main.lL_Dest.remove(selected+1);
            Main.lL_Dest_Price.remove(selected+1);

            //Sort the two sort lists
            sorter(lL_Source,lL_Source_Price);

            //add the source list to the source list view
            for(int i = 0; i<lL_Source.size();i++) {
                productList.getItems().add(lL_Source.get(i+1));
            }

            //add the destination list to the destination list view
            for(int i = 0; i<Main.lL_Dest.size();i++) {
                destProductList.getItems().add(Main.lL_Dest.get(i+1));
            }

        }
        else{ //Give an error to the user and ask to select one
            Stage error = new Stage();

            error.initModality(Modality.APPLICATION_MODAL);
            error.setTitle("Select an item");
            error.setMinWidth(500);
            error.setMinHeight(100);

            Label label = new Label();
            label.setText("Please select an item before clicking the button !");

            Button closeButton = new Button("Ok");
            closeButton.setOnAction(e -> error.close());

            VBox layout = new VBox();
            layout.getChildren().addAll(label,closeButton);
            layout.setAlignment(Pos.CENTER  );

            Scene scene = new Scene(layout);
            error.setScene(scene);
            error.showAndWait();

        }
    }

    public void addAllToList(MouseEvent mouseEvent) { //Add everything in the source list view to the destination list view

        //clear the two list views
        productList.getItems().clear();
        destProductList.getItems().clear();

        //Add the source lists to the destination lists
        Main.lL_Dest.addAll(lL_Source);
        Main.lL_Dest_Price.addAll(lL_Source_Price);

        //Clear the source lists
        lL_Source.clear();
        lL_Source_Price.clear();

        //Sort the destination lists
        sorter(Main.lL_Dest,Main.lL_Dest_Price);

        //Add the destination list to the destination list view
        for(int i = 0; i<Main.lL_Dest.size();i++) {
            destProductList.getItems().add(Main.lL_Dest.get(i+1));
        }

    }

    public void removeAllFromList(MouseEvent mouseEvent) { //Remove everything in the destination list view and add it to the source list view

        //clear the list views
        destProductList.getItems().clear();
        productList.getItems().clear();

        //Add the destination lists to the source lists
        lL_Source.addAll(Main.lL_Dest);
        lL_Source_Price.addAll(Main.lL_Dest_Price);

        //Clear the destination lists
        Main.lL_Dest.clear();
        Main.lL_Dest_Price.clear();

        //Sort the source lists
        sorter(lL_Source,lL_Source_Price);

        //Add the source lists to the source list view
        for(int i = 0; i<lL_Source.size();i++) {
            productList.getItems().add(lL_Source.get(i+1));
        }

    }

        private void sorter(HLListS list, HLListI priceList){

            // Iterate the unsorted list one by one
            for (int i = 1; i < list.size(); i++) {
                // Find the minimum element in unsorted list
                int min = i;
                for (int j = i + 1; j <= list.size(); j++) {

                    if (list.get(j).compareTo(list.get(min)) < 0) {
                        min = j;

                    }
                }

                // Swap the minimum element with the first element of the string list
                String temp = list.get(min);
                list.set(min,list.get(i));
                list.set(i,temp);

                // Swap the minimum element with the first element of the int list
                int tempPrice = priceList.get(min);
                priceList.set(min,priceList.get(i));
                priceList.set(i,tempPrice);

            }
        }

    public void loadSelectQuantities(ActionEvent event) throws IOException{ //load the select quantities scene

        Parent sQParent  = FXMLLoader.load(getClass().getResource("SelectQuantity.fxml"));
        Scene sQScene = new Scene(sQParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(sQScene);
        window.show();
    }

    public void logOutPushed(ActionEvent event) throws IOException { //load the login screen

        Parent logOutParent  = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene logOutScene = new Scene(logOutParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(logOutScene);
        window.show();

    }

    public void loadMainMenu(ActionEvent event) throws IOException { //load the main menu

        Parent mainMenuParent  = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
        Scene mainMenuScene = new Scene(mainMenuParent, 900, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(mainMenuScene);
        window.show();
    }
}