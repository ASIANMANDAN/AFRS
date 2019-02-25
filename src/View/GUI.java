/**
 * The User Interface for the AFRS system
 *
 * @author Meghan Johnson - @mrj9235@rit.edu
 */
package View;

import Controller.RequestHandler;
import Model.Databases.AllDatabases;
import Model.Client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import Controller.Parser;

import javax.swing.*;
import java.util.ArrayList;


public class GUI extends Application{



    private Parser parser = new Parser();

    private RequestHandler handler;

    private static int tabID = 0;



    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates the scene and sets the stages scene and shows the application
     * @param stage this is the stage that will be shown in the application.
     */
    @Override
    public void start(Stage stage) {
       AllDatabases allDatabases = new AllDatabases();
       handler = new RequestHandler(allDatabases.getAirportDatabase(), allDatabases.getReservationDatabase(), allDatabases.getItineraryDatabase());

       stage.setTitle("AFRS");
       TabPane tabs = new TabPane();
       Tab tab = new Tab("AFRS Client " + tabID);
       tab.setContent(setUpPage(tabs, stage));
       tab.setId(Integer.toString(tabID));
       Client client = new Client(tabID);
       handler.addClient(client);
       tabs.getTabs().add(tab);
       Scene scene = new Scene(tabs);
       stage.setScene(scene);
       stage.show();
    }

    /**
     * Creates new border pane that has all the elements of the GUI
     *
     * @return the BorderPane that will be put back into the scene
     */
    public BorderPane setUpPage(TabPane tabs, Stage stage){
        BorderPane pane = new BorderPane();
        TextField input = new TextField();
        Text header1 = new Text("Welcome to the \n Airline Flight Reservation Server");
        Text header2 = new Text("Better than United Airlines");
        VBox header = new VBox();
        header1.setTextAlignment(TextAlignment.CENTER);
        header1.setFont(Font.loadFont("file:resources/fonts/monofonto.ttf", 35));
        header2.setTextAlignment(TextAlignment.CENTER);
        header2.setFont(Font.font("file:resources/fonts/monofonto.ttf", 15));
        header.getChildren().addAll(header1, header2);
        header.setAlignment(Pos.CENTER);
        Button enter = new Button("Submit");
        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        Button newTab = new Button("New Tab");
        HBox bottom = new HBox();
        bottom.getChildren().addAll(input, enter, undo, redo);
        TextArea results = new TextArea();
        results.setEditable(false);
        pane.setLeft(newTab);
        pane.setAlignment(newTab, Pos.TOP_LEFT);
        bottom.setHgrow(input, Priority.ALWAYS);
        pane.setCenter(results);
        pane.setBottom(bottom);
        pane.setTop(header);
        pane.setAlignment(header, Pos.CENTER);

        /**
         * sets the functionality for the enter button
         */
        enter.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //grabs the text from the input bar.
                String text = input.getText();
                //grabs the id from the current tab.
                int id = Integer.parseInt(tabs.getSelectionModel().getSelectedItem().getId());
                if( text != null){
                    ArrayList<String> splitText = parser.parseLine(text);
                    //creates new client and new tab if user types in connect
                    if(splitText.get(0).equals("connect")){
                        tabID++;
                        Tab tab = new Tab("AFRS Client " + tabID);
                        tab.setId(Integer.toString(tabID));
                        tabs.getTabs().add(tab);
                        tab.setContent(setUpPage(tabs, stage));
                        Client c = new Client(tabID);
                        handler.addClient(c);
                        input.clear();
                    }
                    //Deletes the current tab and the client with corresponding ID if user types disconnect
                    else if(splitText.get(0).equals("disconnect")){
                        int tabNum = Integer.parseInt(tabs.getSelectionModel().getSelectedItem().getId());
                        tabs.getTabs().remove(tabNum);
                        int i = 0;
                        while( i < handler.getClients().size()){
                            if(tabNum == handler.getClients().get(i).getId()){
                                handler.getClients().remove(i);
                                break;
                            }
                            i++;
                        }
                        input.clear();
                    }
                    //passes the request to the request handler and resets the text to include new request and response
                    else {
                        handler.setRequestInfo(splitText);
                        ArrayList<String> result = handler.execute(id);
                        input.clear();
                        results.setText(results.getText() + "\n"+ text + "\n" + result.toString());
                    }
                    //if all tabs are closed, the application ends
                    if(tabs.getTabs().size() == 0){
                        stage.close();
                    }
                }
            }
        });
        /**
         * sets functionality to enter key, identical to functionality of enter button
         */
        input.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String text = input.getText();
                int id = Integer.parseInt(tabs.getSelectionModel().getSelectedItem().getId());
                if( text != null){
                    if(text.equals("connect;")){
                        tabID++;
                        Tab tab = new Tab("AFRS Client " + tabID);
                        tab.setId(Integer.toString(tabID));
                        tabs.getTabs().add(tab);
                        tab.setContent(setUpPage(tabs, stage));
                        Client c = new Client(tabID);
                        handler.addClient(c);
                        input.clear();
                    }
                    else if(text.equals("disconnect;")){
                        int tabNum = Integer.parseInt(tabs.getSelectionModel().getSelectedItem().getId());
                        tabs.getTabs().remove(tabNum);
                        int i = 0;
                        while( i < handler.getClients().size()){
                            if(tabNum == handler.getClients().get(i).getId()){
                                handler.getClients().remove(i);
                                break;
                            }
                            i++;
                        }
                        input.clear();

                    }
                    else {
                        ArrayList<String> splitText = parser.parseLine(text);
                        handler.setRequestInfo(splitText);
                        ArrayList<String> result = handler.execute(id);
                        input.clear();
                        results.setText(results.getText() + "\n" +text + "\n" + result.toString());
                    }
                    if(tabs.getTabs().size() == 0){
                        stage.close();
                    }
                }
            }
        });
        /**
         * sets functionality to new tab button
         * when pressed, new tab will be created and new client will be
         * created with a corresponding ID and added to the client list in the request handler
         */
        newTab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tabID++;
                Tab tab = new Tab("AFRS Client " + tabID);
                tab.setId(Integer.toString(tabID));
                tabs.getTabs().add(tab);
                tab.setContent(setUpPage(tabs, stage));
                Client c = new Client(tabID);
                handler.addClient(c);

            }
        });
        /**
         * sets functionality to undo button
         */
        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id = Integer.parseInt(tabs.getSelectionModel().getSelectedItem().getId());
                ArrayList<String> text = new ArrayList<>();
                text.add("undo");
                handler.setRequestInfo(text);
                results.setText(results.getText() + "\n" + handler.execute(id).toString());
            }
        });
        /**
         * sets redo functionality to the redo button
         */
        redo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id = Integer.parseInt(tabs.getSelectionModel().getSelectedItem().getId());
                ArrayList<String> text = new ArrayList<>();
                text.add("redo");
                handler.setRequestInfo(text);
                results.setText(results.getText() + "\n" + handler.execute(id).toString());
            }
        });
        return pane;
    }

}
