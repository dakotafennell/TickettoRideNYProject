package com.example.tickettoride;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

/**
 * TicketToRide class
 * This class will contain logic for displaying the game window and each of the game's components.
 */
public class TicketToRide extends Application
{
    public static final String TITLE = "Ticket to Ride: New York";
    public static final String AUTHORS = "By: Austin, Joseph, and Louis";
    public static final int HEIGHT = 1000;
    public static final int WIDTH = 1200;

    //Contains the number of players
    private int currentNumPlayers;

    //Stores Player objects in a list
    private ObservableList<Player> currentPlayers = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage)
    {
        //Creates a new StackPane for the splash screen
        StackPane splashScreen = new StackPane();

        //Get the image from the TransportationCard class
        TransportationCard transportationCard = new TransportationCard();
        Image cardImage = transportationCard.selectRandomCard();

        //Create an ImageView for the splash screen
        ImageView imageView = new ImageView(cardImage);

        //Set up Image Size.
        //Width of the splash screen
        imageView.setFitWidth(WIDTH);
        //Height of the splash screen
        imageView.setFitHeight(HEIGHT);
        //Preserve the ratio of the image
        imageView.setPreserveRatio(true);

        // Set the imageView as the background
        splashScreen.getChildren().add(imageView);

        // Create a label with the game title
        Label titleLabel = new Label(TITLE);
        titleLabel.setTextFill(Color.BLACK);
        //Sets the style of the titleLabel with a background color and 50% opacity with rounded corners, a black border
        //set to 3 pixels wide, and a font size of 50px and San-serif font.
        titleLabel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; " +
                "-fx-border-color: black; -fx-border-radius: 10; -fx-border-width: 3; -fx-font: 50px \"serif\"; -fx-font-weight: bold");
        //Increases the spacing between the titleLabel and the authorsLabel
        titleLabel.setPadding(new Insets(0, 0, 50, 0));


        //Create labels for Authors
        Label authorsLabel = new Label(AUTHORS);
        // Authors label
        //Font size: 25px
        //Text Color: white
        //Font Weight: BOLD
        authorsLabel.setStyle("-fx-font: 25px \"serif\"; " +
                "-fx-text-fill: WHITE;" +
                "-fx-font-weight: BOLD");
        authorsLabel.setPadding(new Insets(150, 0, 0, 0));

        //Creates a label for the "Click here to begin!" text
        Label clickToBeginLabel = new Label("Click anywhere to begin!");
        clickToBeginLabel.setTextFill(Color.BLACK);
        clickToBeginLabel.setFont(Font.font("serif", 20));

        //Adds the labels to the splash screen
        splashScreen.getChildren().addAll(titleLabel, authorsLabel, clickToBeginLabel);
        //Centers the labels vertically
        StackPane.setAlignment(titleLabel, Pos.CENTER);
        titleLabel.setPadding(new Insets(10));

        VBox clickToBeginLabelBox = new VBox();
        clickToBeginLabelBox.getChildren().add(clickToBeginLabel);

        //Modifies the style of the clickToBeginLabelBox adding a gap between the labels
        clickToBeginLabelBox.setStyle("-fx-padding: 100;");
        //Adds the clickToBeginLabelBox to the splash screen
        splashScreen.getChildren().add(clickToBeginLabelBox);

        clickToBeginLabelBox.setAlignment(Pos.BOTTOM_CENTER);

        //Creates a scene for the splash screen
        Scene splashScene = new Scene(splashScreen, WIDTH, HEIGHT);

        //Sets the programs icon
        Display.ChangeIcon(primaryStage);

        //Initialize the scene
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(splashScene);
        //Disables resizing the window
        primaryStage.setResizable(false);
        primaryStage.show();

        //Handle the click event to close the splash screen
        splashScene.setOnMouseClicked(event ->
        {
            //Closes the splash screen
            primaryStage.close();

            //Displays the player selection screen
            Stage playerSelectStage = new Stage();
            createPlayerSelection(playerSelectStage);
        });
    }

    //Returns the current number of players
    public ObservableList<Player> getCurrentPlayers()
    {
        return currentPlayers;
    }

    //sets up border and allows text to be displayed
    class LabelPane extends StackPane
    {
        public LabelPane(String title)
        {
            getChildren().add(new Label(title));
            setStyle(
                    "-fx-border-style: solid inside;"
                    +"-fx-border-width: 3;"
                    +"-fx-border-radius: 4;"
                    +"-fx-border-color: GRAY;"
            );
            setPadding(new Insets(20, 30,50,30));
        }
    }

    //Method that will display the player count and selection screen
    private void createPlayerSelection(Stage playerSelectStage)
    {
        //Creates a label with the games title
        Label titleLabel = new Label(TITLE);
        //Sets the font size of the title label
        titleLabel.setStyle("-fx-font-size: 32pt;");

        //Creates a label with the games authors
        Label authorsLabel = new Label(AUTHORS);
        //Sets the font size of the authors label
        authorsLabel.setStyle("-fx-font-size: 20;");
        //Sets the padding of the authors label
        authorsLabel.setPadding(new Insets(20, 0, 20, 0));

        //text area for player names
        Label playerLabel = new Label("Player Names:");
        TextArea taPlayer = new TextArea();
        //Sets the width of the text area
        taPlayer.setPrefColumnCount(10);
        //Sets the maximum width of the text area
        taPlayer.setMaxWidth(150);
        //Sets the maximum height of the text area
        taPlayer.setMaxHeight(50);
        taPlayer.setPromptText("Enter player name here");
        taPlayer.visibleProperty().setValue(false);
        //Creates a button for adding players
        Button btnAddPlayer = new Button("Add Player");
        //Creates a label for the color combobox
        Label colorLabel = new Label("Player Color:");
        //Creates a label for the Player name text area
        Label playerNameLabel = new Label("Player Name:");
        btnAddPlayer.visibleProperty().setValue(false);
        colorLabel.visibleProperty().setValue(false);
        playerNameLabel.visibleProperty().setValue(false);

        //Creates the ComboBox for selecting the player color
        ComboBox<String> colorComboBox = new ComboBox<>();
        //Adds the available player colors to the ComboBox (White, Blue, Purple, and Yellow) displaying the colors name
        //and the color itself so that when the color is selected the value of the Color object is returned
        colorComboBox.getItems().addAll("WHITE", "BLUE", "PURPLE", "YELLOW");
        //Sets the prompt text for the ComboBox
        colorComboBox.visibleProperty().setValue(false);

        //Creates the ComboBox for selecting the number of players
        ComboBox<Integer> playerComboBox = new ComboBox<>();
        playerComboBox.getItems().addAll(2, 3, 4);
        playerComboBox.setPromptText("Number of players");

        //Sets the default value of the ComboBox to blank
        playerComboBox.setValue(null);

        //Creates the Confirm button
        Button confirmButton = new Button("Confirm");

        confirmButton.setOnAction(e ->
        {
            //Sets the number of players to the value of the ComboBox
            int currentNumPlayers = playerComboBox.getValue();

            colorComboBox.visibleProperty().setValue(true);
            taPlayer.visibleProperty().setValue(true);
            btnAddPlayer.visibleProperty().setValue(true);

            btnAddPlayer.setOnAction(event ->
            {
                if (!taPlayer.getText().isEmpty() && colorComboBox.getValue() != null)
                {
                    Player playerInfo = createPlayer(taPlayer.getText(), Color.valueOf(colorComboBox.getValue()));
                    currentPlayers.add(playerInfo);
                    taPlayer.clear();

                    // Remove the selected color only if it exists in the ComboBox
                    if (colorComboBox.getItems().contains(colorComboBox.getValue()))
                    {
                        colorComboBox.getItems().remove(colorComboBox.getValue());
                    }

                    System.out.println(currentPlayers.size());

                    if (currentPlayers.size() == currentNumPlayers)
                    {
                        //Prints the name and color of each player to the console
                        for (Player player : currentPlayers)
                        {
                            System.out.println(player.getName() + " " + String.valueOf(player.getColor()));
                        }
                        //Opens the game interface
                        playerSelectStage.close();
                        Stage primaryStage = new Stage();
                        createGameInterface(primaryStage);
                    }
                }
                else
                {
                    System.out.println("Please enter player name and select color.");
                }
            });
        });

        // Create a VBox to arrange the UI elements
        VBox layout = new VBox(10);
        //Add the title and authors labels to the layout
        layout.getChildren().addAll(titleLabel, authorsLabel);
        layout.getChildren().addAll(playerComboBox, confirmButton);

        layout.setStyle("-fx-alignment: center; -fx-padding: 20px;");

        VBox layout2 = new VBox(10);
        layout2.getChildren().addAll(colorComboBox, taPlayer, btnAddPlayer);
        layout2.setAlignment(Pos.BOTTOM_CENTER);
        layout.getChildren().add(layout2);

        // Create the initial scene
        Scene scene = new Scene(layout, WIDTH, HEIGHT);

        //Set's the programs icon
        Display.ChangeIcon(playerSelectStage);

        //Sets the title of the player selection screen
        playerSelectStage.setTitle("Ticket to Ride - Select Players");
        //Disables resizing the window
        playerSelectStage.setResizable(false);
        playerSelectStage.setScene(scene);
        playerSelectStage.show();
    }

    private void createGameInterface(Stage primaryStage)
    {
        //Creates a new borderPane
        BorderPane borderPane = new BorderPane();

        //Creates a new board object then gets the map image from the board class
        Board board = new Board();
        ImageView ticketToRideNYMap = board.getTicketToRideImage();

        //Creates a VBox for the left side of the borderPane to display the players in turn order
        VBox leftPlayersVBox = new VBox();

        // Wrap the ImageView in a StackPane to apply padding
        StackPane imageContainer = new StackPane();
        //Adds the ticketToRideNYMap to the imageContainer
        imageContainer.getChildren().add(ticketToRideNYMap);

        HighlightRectanglesOnImageTest highlightRectangles = new HighlightRectanglesOnImageTest();
        Pane overlayPane = highlightRectangles.getOverlayPane();

        if(overlayPane != null)
        {
            imageContainer.getChildren().add(overlayPane);
        }
        else
        {
            System.out.println("Overlay Pane is null");
        }

        //Adds the image container to the center of the borderPane
        borderPane.setCenter(imageContainer);

        //Create HBox and buttons for top of game
        //This will have the rules, scoring, about, save, and load buttons
        HBox hBox = new HBox(5);

        //Creates a new menu bar
        MenuBar menuBar = new MenuBar();
        //Creates a new File menu
        Menu fileMenu = new Menu("File");
        //Creates a new About Menu
        Menu aboutMenu = new Menu("About");

        //Creates a new saveMenuItem for the File menu
        MenuItem saveMenuItem = new MenuItem("Save");
        //Creates a new loadMenuItem for the File menu
        MenuItem loadMenuItem = new MenuItem("Load");
        //Creates a new exitMenuItem for the File menu
        MenuItem exitMenuItem = new MenuItem("Exit");

        //Adds the menu items to the File menu
        fileMenu.getItems().addAll(saveMenuItem, loadMenuItem, exitMenuItem);

        //Creates a new rulesMenuItem for the About menu
        MenuItem rulesMenuItem = new MenuItem("Rules");
        //Creates a new scoringMenuItem for the About menu
        MenuItem scoringMenuItem = new MenuItem("Scoring");
        //Creates a new item for the About menu
        MenuItem aboutMenuItem = new MenuItem("About");

        //Adds the menu items to the About menu
        aboutMenu.getItems().addAll(rulesMenuItem, scoringMenuItem, aboutMenuItem);

        menuBar.getMenus().addAll(fileMenu, aboutMenu);
        hBox.getChildren().addAll(menuBar);

        //Event for the exitMenuItem
        exitMenuItem.setOnAction(actionEvent ->
        {
            //Closes the program
            primaryStage.close();
        });

        //Creates the event and text for the rules button
        rulesMenuItem.setOnAction(actionEvent ->
        {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("The Rules");
            ButtonType buttonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("""
                    The Game Turn consists of doing one of the following three actions: draw Transportation cards
                    claim a Route, or draw Destination Ticket cards.
                    __________________________
                    Draw Transportation Cards
                    -Transportation Cards match route colors on the board.
                    -Taxis are multicolored and act as wild cards.
                    -You may have any number of Transport cards in your hand.
                    -You can draw up to Two cards.
                    -This can be from the deck or take any one of the five face up cards.
                    -If your taking a face up taxi card, you cannot draw any other card.
                    __________________________
                    Claim a Route
                    -A route is the set of spaces of same color that link adjacent Locations.
                    -To claim a route, you must have the same amount of color matched cards as the route.
                    -Gray routes are available to any color of Transportation Cards.
                    -You can claim any open Route on the board, even if it is not connected to a Route you previously
                    claimed. You cannot claim more than one route per turn.
                    ____________________________
                    Draw Destination Ticket Cards
                    -Each Destination Ticket Card shows the two points, starting and stopping, and a point value.
                    -To complete a Destination Ticket card, you must connect the two locations on the card by creating
                    a continuous path. You may have any number of Destination tickets.
                    -You can draw two cards from the deck, you must keep at least one.
                    """);
            dialog.getDialogPane().getButtonTypes().add(buttonType);
            dialog.showAndWait();
        });

        //Creates the event and text for the scoring button
        scoringMenuItem.setOnAction(actionEvent ->
        {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("The Scoring");
            ButtonType buttonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("""
                                          The Scoring of the Game
                                         _______________________
                                  
                    When a player has two or fewer Taxis left in their supply, each player, including
                    that player, gets one last turn. Then the game ends and players calculate their
                    final scores using the scorecard:
                    
                    -First, each player scores points for each Route they claimed during the game
                     based on the route Scoring Table printed on the board.
                     
                    -Then, each player reveals all their destination cards, adds the value of each
                     card they completed, and subtracts the value of any card they failed to complete.
                     
                    -Finally, each player scores one points for each Tourist Attraction that is connected
                     to one or more of the Routes they claimed.
                                  
                                  The player with the most points wins.\s
                    """);
            dialog.getDialogPane().getButtonTypes().add(buttonType);
            dialog.showAndWait();
        });

        //Creates the event and text for the about button
        aboutMenuItem.setOnAction(actionEvent ->
        {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("About");
            ButtonType buttonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("""
                                  _________________________
                                  CPT-231 Group Project:
                                  Ticket to Ride: New York
                                  
                                  By: Austin Bradley,
                                         Louis Fennell III,
                                         Joseph Lemois
                                  __________________________
                                  """);
            dialog.getDialogPane().getButtonTypes().add(buttonType);
            dialog.showAndWait();
        });

        //Create Button to select random card.
        TransportationCards transportationCard = new TransportationCards();

        // Create ImageView to display the selected card image
        ImageView cardImage = new ImageView();
        // Set height and width of card image.
        cardImage.setFitWidth(250);
        cardImage.setFitHeight(200);

        // Create Button to select random card
        Button btnRandomCard = new Button("Please Select A Card");

        // Define the URL for the button background image
        String imageUrl = getClass().getResource("/com/example/tickettoride/TransportCards/BackTransportationCard.png").toExternalForm();
        // Set the button's background to the image
        btnRandomCard.setStyle(
                "-fx-background-image: url('" + imageUrl + "'); " +
                "-fx-background-position: center; " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-size: cover; " +
                "-fx-font: 22px \"PLUSH\"; " +
                "-fx-text-fill: RED;" +
                "-fx-font-weight: BOLD;"
        );

        //Set height and width of button
        btnRandomCard.setPrefHeight(100);
        btnRandomCard.setPrefWidth(150);

        //Set Button Action
        btnRandomCard.setOnAction(event ->
        {
            // Use the instance of selectRandomCard
            transportationCard.selectRandomCard();
            // Update the ImageView with the new card image
            cardImage.setImage(TransportationCards.cardImageView.getImage());
        });

        // Add Button and ImageView to Right Side
        VBox rightVBox = new VBox(); // Create a VBox for layout
        // Add the button and ImageView to the VBox
        rightVBox.getChildren().addAll(btnRandomCard, cardImage);
        borderPane.setRight(rightVBox);

        Display display = new Display();

        //Label containing the first Player's name
        VBox playerVBox = display.getPlayerInfoVBox();

        //Adds the first Player's information to the leftPlayersVBox
        leftPlayersVBox.getChildren().add(playerVBox);

        //setup of borderPane displays titles
        //borderPane.setTop(new LabelPane("By: Austin, Joseph, and Louis!"));
        borderPane.setTop(hBox);
        borderPane.setBottom(new LabelPane("Displays current players hand!"));
        borderPane.setLeft(leftPlayersVBox);

        //Initializes the scene
        Scene scene = new Scene(borderPane, WIDTH, HEIGHT);

        Display.ChangeIcon(primaryStage); //Sets the programs icon
        primaryStage.setTitle(TITLE); //Title of Game
        //Disables resizing the window
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Separate method for creating a player object
    private Player createPlayer(String playerName, Color color)
    {
        return new Player(playerName, 0, 0, 0, new ArrayList<>(), new ArrayList<>(), 15, color);
    }

    public static void main(String[] args)
    {
        launch();
    }
}