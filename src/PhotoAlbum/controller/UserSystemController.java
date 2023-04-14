package PhotoAlbum.controller;
/**
 * @author Soban Chaudhry
 * @author Mannan Mishra
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.collections.*;
import javafx.application.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import java.util.Optional;
import PhotoAlbum.model.*;


import javax.swing.text.DefaultStyledDocument.ElementSpec;

import PhotoAlbum.model.Album;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;

/**
 * This class represents the controller for the User system GUI
 * Contains methods for initializing the GUI components
 * creating, deleting, renaming albums, opening selected album and displaying albums in a TableView
 */
public class UserSystemController {
  
  @FXML
  private AnchorPane anchorPane;
  @FXML
  private TableView<MyData> table;
  @FXML
  private TableColumn<MyData, String> albumNameColumn;
  @FXML
  private TableColumn<MyData, String> numPhotosColumn;
  @FXML
  private TableColumn<MyData, String> dateRangeColumn;
  @FXML 
  private ScrollBar scrollBar;
  @FXML
  private Label albumsLabel;
  @FXML
  private Label userLabel;
  @FXML
  private Button deleteAlbumButton;
  @FXML
  private Button renameAlbumButton;
  @FXML
  private Button createAlbumButton;
  @FXML
  private Button searchButton;
  @FXML
  private Button openAlbumButton;

  private static Album curAlbum;

  ObservableList<String> albumNames = FXCollections.observableArrayList();
  ObservableList<String> photoNums = FXCollections.observableArrayList();
  ObservableList<String> dates = FXCollections.observableArrayList();
  List<MyData> data = new ArrayList<>();

  /**
   * Sets the username label to the given username
   * @param username the username of the current user
   */
  public void setUsername(String username) {
    userLabel.setText("User: " + username);
  }
  
  /**
   * Populates the table view with the user's albums and data
   */
  public void doTableView() {
    
    // Add the actual data rows
    for (Album album : loginController.getU().getAlbums()) {
        String albumName = album.getName();
        int numPhotos = album.getNumPhotos();
        String dateRange = album.getDateRange();
        data.add(new MyData(albumName, numPhotos, dateRange));
        
    }

    // Convert the list to an ObservableList
    ObservableList<MyData> observableData = FXCollections.observableArrayList(data);

    // Set the items of the TableView to the updated data list
    table.setItems(observableData);
}

  /**
   * Initializes the GUI components and sets the username Label and TableView columns
   */
  public void initialize() {
    String username = loginController.getU().getUsername();
    setUsername(username);

      albumNameColumn.setCellValueFactory(new PropertyValueFactory<>("albumName"));
      numPhotosColumn.setCellValueFactory(new PropertyValueFactory<>("numPhotos"));
      dateRangeColumn.setCellValueFactory(new PropertyValueFactory<>("dateRange"));
      doTableView(); 

    
  }
  /**
   * This method creates a new album when the "Create Album" button is clicked. 
   * It prompts the user to enter a name for the album and adds it to the users list. It also updates the table view with the new album
   * @param Event the event generated when the "Create Album" button is clicked
   * @throws IOException if there is an error with the input/output
   */
  @FXML
  public void createAlbum(ActionEvent Event) throws IOException{
    String albumname = null;
    TextInputDialog dialog = new TextInputDialog(albumname);
        dialog.setTitle("Create Album");
        dialog.setHeaderText("Enter a name for the album:");
        dialog.setContentText("Album Name:");

        // Show the dialog and wait for user input
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(newName -> {
          loginController.getU().addAlbum(new Album(newName));

          data.add(new MyData(newName, 0, " "));
          ObservableList<MyData> observableData = FXCollections.observableArrayList(data);

    // Set the items of the TableView to the updated data list
    table.setItems(observableData);

        });
  }

  /**
   * This method deletes the selected album when the "Delete Album" button is clicked
   * it removes the album from the user's album list and updates the table view
   * @param event the even generated when the "Delete Album" button is clicked
   */
  @FXML
  public void deleteAlbum(ActionEvent event){
    MyData selectedItem = table.getSelectionModel().getSelectedItem();

    for(int i = 0; i<loginController.getU().getAlbums().size(); i++){
      if(loginController.getU().getAlbums().get(i).getName() == selectedItem.getAlbumName()){
        loginController.getU().getAlbums().remove(i);
      }
    }

    table.getItems().clear();
    data.clear();
    
    table.refresh();
    doTableView();
    

  }
  
  /**
   * This method renames the selected album when the "Rename Album" button is clicked
   * It prompts the user to enter a new name for the album and updates the album name in the model and table view
   * @param event the event generated when the "Rename Album" button is clicked
   * @throws IOException if there is an error with Input/Output
   */
  @FXML
  private void renameAlbum(ActionEvent event) throws IOException {
    MyData selectedItem = table.getSelectionModel().getSelectedItem();
    if (selectedItem != null) {
        // Create a TextInputDialog to get the new album name from the user
        TextInputDialog dialog = new TextInputDialog(selectedItem.getAlbumName());
        dialog.setTitle("Rename Album");
        dialog.setHeaderText("Enter a new name for the album:");
        dialog.setContentText("Album Name:");

        // Show the dialog and wait for user input
        Optional<String> result = dialog.showAndWait();

        // If the user clicks OK, update the album name in the model and the table view
        result.ifPresent(newName -> {
          for(int i = 0; i<loginController.getU().getAlbums().size(); i++){
            if(loginController.getU().getAlbums().get(i).getName() == selectedItem.getAlbumName()){
              loginController.getU().getAlbums().get(i).setName(newName);
              break;
            }
          }
          table.getItems().clear();
          data.clear();
      
          table.refresh();
          doTableView();
        });
    }
}

  /**
   * This method opens the selected album when the "Open Album" button is clicked
   * It gets the selected album from the table view and sets it as the current album
   * it then loads the album display page and sets it as the root node of the current scene
   * @param event the event generated when the "Open Album" button is clicked
   * @throws IOException if there is an error with the Input/Output
   */
  @FXML
  public void openAlbum(ActionEvent event) throws IOException {
    // Album selectedAlbum = table.getSelectionModel().getSelectedItem();
    

    MyData selectedItem = table.getSelectionModel().getSelectedItem();

    Album selectedAlbum = null;

    for(int i = 0; i<loginController.getU().getAlbums().size(); i++){
      if(loginController.getU().getAlbums().get(i).getName() != null) {
        if(loginController.getU().getAlbums().get(i).getName().compareTo(selectedItem.getAlbumName()) ==0){
          selectedAlbum = loginController.getU().getAlbums().get(i);
          curAlbum = selectedAlbum;
          break;
        }
      }
    }

    if (selectedAlbum == null) {
      return;
  }

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/PhotoAlbum/view/albumDisplay.fxml"));
    Parent secondPage = loader.load();

    // Get the current scene
    Scene currentScene = ((Node) event.getSource()).getScene();
    
    // Set the new root node to the current scene
    currentScene.setRoot(secondPage);
  }

  public static Album getCurAlbum () {
    return curAlbum;
  }
  /**
     * This method is called when the user clicks the quit button.
     * It displays a confirmation dialog and exits the application if the user selects yes
     * @param event the ActionEvent object representing the click of the quit button
     * @throws IOException if an error occurs in the QuitDialog.fxml file
     */
  @FXML
  private void quit(ActionEvent event) throws IOException{
      Dialog<ButtonType> dialog = new Dialog<>();
      dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/PhotoAlbum/view/QuitDialog.fxml"));
      dialog.getDialogPane().setContent(loader.load());
      dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
      dialog.showAndWait().ifPresent(response -> {
          if (response == ButtonType.YES) {
              // If the user clicked YES, exit the program
              Platform.exit();
          }
      });
  }
  /**
    * Handles the logout action event by showing a dialog to confirm logout and redirecting to the login page
    * @param event the action event triggering the method
    */
  @FXML
  private void logout(ActionEvent event){
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/PhotoAlbum/view/LogoutDialog.fxml"));
    try {
        dialog.getDialogPane().setContent(loader.load());
    } catch (IOException e) {
        e.printStackTrace();
    }
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
    dialog.showAndWait().ifPresent(response -> {
        if(response == ButtonType.YES){
            FXMLLoader nextloader = new FXMLLoader(getClass().getResource("/PhotoAlbum/view/Login.fxml"));
            Parent secondPage = null;
            try {
               secondPage = nextloader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Get the current scene
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Set the new root node to the current scene
            currentScene.setRoot(secondPage);
        }
    });

  }

  /**
   * Represents a data object for use in the table view of albums
   */
  public static class MyData {
    /**
     * the name of the album
     */
    private final String albumName;
    /**
     * The number of photos in the album
     */
    private final int numPhotos;
    /**
     * the date range of the photos in the album
     */
    private final String dateRange;

    /**
     * Constructs a new MyData object with the specified album name, number of photos, and date range
     * @param albumName the name of the album
     * @param numPhotos the number of photos in the album
     * @param dateRange the date range of the photos in the album
     */
    public MyData(String albumName, int numPhotos, String dateRange) {
        this.albumName = albumName;
        this.numPhotos = numPhotos;
        this.dateRange = dateRange;
    }

    /**
     * Returns the name of the album
     * @return the name of the album
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * Returns the number of photos in the album
     * @return number of photos in the album
     */
    public int getNumPhotos() {
        return numPhotos;
    }

    /**
     * Returns the date range of the photos in the album
     * @return date range of the photos in the album
     */
    public String getDateRange() {
        return dateRange;
    }
}


}
