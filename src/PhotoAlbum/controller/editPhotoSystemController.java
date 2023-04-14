package PhotoAlbum.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import java.time.LocalDateTime;
import PhotoAlbum.model.*;

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

import javax.swing.Action;

import PhotoAlbum.model.Album;
import PhotoAlbum.model.Photo;
import PhotoAlbum.model.Tag;

import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;

/**
 * @author Soban Chaudhry
 * @author Mannan Mishra
 * This controller is responsible for controlling the view of the editPhoto system
 * 
 */

public class editPhotoSystemController {
  @FXML
    private MenuBar menuBar;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Label captionLabel;
    @FXML
    private Button editCaptionButton;
    @FXML
    private Button addTagButton;
    @FXML
    private Button movePhotoButton;
    @FXML
    private Button copyPhotoButton;
    @FXML
    private Button quitButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label albumNameLabel;
    @FXML
    private TableView<Tag> tagTableView;
    @FXML
    private TableColumn<Tag, String> tagNameColumn;
    @FXML
    private TableColumn<Tag, String> tagValueColumn;
    @FXML
    private Label tagsLabel;
    
    @FXML
    private TextField tagNameTextField;
    @FXML
    private TextField tagValueTextField;
    @FXML
    private Button deleteTagButton;
    @FXML
    private ImageView enlargedPhoto;

    List<Tag> data = new ArrayList<>();

    private static Photo photo;

    /**
      * Sets the text of the albumLabel to the given albumName
      * @param albumName the name of the album to set as the text of the albumLabel
      */
    public void setAlbumName(String albumName) {
        albumNameLabel.setText(albumName);
    
    }
    /**
     * This method initializes the controller and sets up the UI components and event listeners
     * It retreives the current album name, sets the album name in the UI, displays the enlarged photo, sets the photo caption in the UI, sets up the tag table view with the appropriate columns and data
     * sets up event listeners for various buttons
     */
    @FXML
    public void initialize() {
        String albumName = UserSystemController.getCurAlbum().getName();
        setAlbumName(albumName);
        displayEnlargedPhoto();
        setCaption();
        

        tagNameColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        tagValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        doTableView();
        // Initialize your controller here
        // Add event listeners or bindings

        // editCaptionButton.setOnAction(event -> editCaption());
        // addTagButton.setOnAction(event -> addTag());
        // movePhotoButton.setOnAction(event -> movePhoto());
        // copyPhotoButton.setOnAction(event -> copyPhoto());
        // quitButton.setOnAction(event -> quit());
        // logoutButton.setOnAction(event -> logout());
        // deleteTagButton.setOnAction(event -> deleteTag());
    }

    /**
     * The method populates the tag table view with the tags associated with the current photo
     * It first clears any existing data in the table view and then retrieves the tages associated with the photo
     * It created new rows of data for each tag and adds them to the table view
     * Finally, it sets the table view items to the updated data list
     */
    public void doTableView() {

        // tagTableView.getItems().clear();
        
        data.clear();
    
        // Add the actual data rows
        for (Tag tag: photo.getTags()) {
            String keyField = tag.getKey();
            String valueField = tag.getValue(); // You will need to modify this to get the actual date range
            data.add(new Tag(keyField, valueField));
            
        }
    
        // Convert the list to an ObservableList
        ObservableList<Tag> observableData = FXCollections.observableArrayList(data);
    
        // Set the items of the TableView to the updated data list
        tagTableView.setItems(observableData);
    }

    /**
     * This method displays the enlarged photo in the UI by retreivving the photo object from the photo display controller
     * loading the image file, setting the image in the image view component
     */
    private void displayEnlargedPhoto() {
        photo = photoDisplayController.getPhoto();
        Image image = new Image(photo.getFilePath());
        enlargedPhoto.setImage(image);
        photo = photoDisplayController.getPhoto();
    }

    /**
     * This mthod sets the photo caption in the UI by retrieving the cpation from the current photo object
     * setting the caption text in the caption label
     */
    public void setCaption() {
        captionLabel.setText(photo.getCaption());
    }

    /**
     * Handles user's request to edit caption of a photo
     * Opens a dialog box prompting the user to enter a new caption
     * updates the photo's caption with the new value
     * if the user cancels, the photo caption remainds unchanged
     */
    @FXML
    private void editCaption() {
        // Implement your logic for editing the caption of the photo
        String newCaption = null;
        TextInputDialog dialog = new TextInputDialog(newCaption);
        dialog.setTitle("Caption");
        dialog.setHeaderText("Enter new Caption:");
        dialog.setContentText("New Caption:");

        // Show the dialog and wait for user input
        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) {
            newCaption = result.get();
            photo.setCaption(newCaption);
            setCaption();
        }
    }
    /**
     * Adds a new tag to the photo and updates the tag table view
     * Clears the tag table view and the text fields after adding a tag
     */
    public void addTag() {
        tagTableView.getItems().clear();
        // Implement your logic for adding a tag to the photo
        String key = tagNameTextField.getText();
        String value = tagValueTextField.getText();
        photo.addTag(key, value);

        doTableView();

        tagNameTextField.clear();
        tagValueTextField.clear();
    }

    /**
     * Allows the user to move a selected photo to a different album
     * Displays a dialog box to allow user to choose destination
     * If the selected album is not found, show error message
     * If the user selects the same album as current album, show error message
     * If the user selects a valid destination album, removes the photo from the current album and adds it to the destination album
     * updates the user interface by going back to the previous screen
     */
    @FXML
    private void movePhoto() {
    // 1. Show a dialog to let the user select the destination album
    List<Album> albums = loginController.getU().getAlbums();
    List<String> albumNames = albums.stream().map(Album::getName).collect(Collectors.toList());
    ChoiceDialog<String> dialog = new ChoiceDialog<>(null, albumNames);
    dialog.setTitle("Move Photo");
    dialog.setHeaderText("Select the destination album:");
    dialog.setContentText("Choose an album:");

    Optional<String> result = dialog.showAndWait();
    
    if (result.isPresent()) {
        String destinationAlbumName = result.get();
        Album destinationAlbum = albums.stream().filter(album -> album.getName().equals(destinationAlbumName)).findFirst().orElse(null);
        
        if (destinationAlbum == null) {
            // If the destination album is not found, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Move Photo Error");
            alert.setContentText("The destination album cannot be found. Please try again.");
            alert.showAndWait();
            return;
        }
        
        if (UserSystemController.getCurAlbum() == destinationAlbum) {
            // If the user selected the same album, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Move Photo Error");
            alert.setContentText("The destination album is the same as the current album. Please choose a different album.");
            alert.showAndWait();
            return;
        }
        
        // 2. Remove the photo from the current album
        UserSystemController.getCurAlbum().deletePhoto(photo);

        // 3. Add the photo to the destination album
        destinationAlbum.addPhoto(photo);

        // 4. Update the user interface
        // You may need to call a method to refresh the UI or go back to the previous screen
        // For example, if you have a method to go back to the photo display screen:
        try {
            goBack(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

    /**
     * copies the selected photo to a user-selected album
     * Displays a dialog box to allow user to choose destination album
     * If the selected album is not found, show error message
     * If the user selects the same album as current album, show error message
     * If the user selects a valid desitnation album, adds the photo to the album and updates the user interface
     */
    @FXML
    private void copyPhoto() {
        // Implement your logic for copying the photo
        List<Album> albums = loginController.getU().getAlbums();
        List<String> albumNames = albums.stream().map(Album::getName).collect(Collectors.toList());
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, albumNames);
        dialog.setTitle("Move Photo");
        dialog.setHeaderText("Select the destination album:");
        dialog.setContentText("Choose an album:");
    
        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) {
            String destinationAlbumName = result.get();
            Album destinationAlbum = albums.stream().filter(album -> album.getName().equals(destinationAlbumName)).findFirst().orElse(null);
            
            if (destinationAlbum == null) {
                // If the destination album is not found, show an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Move Photo Error");
                alert.setContentText("The destination album cannot be found. Please try again.");
                alert.showAndWait();
                return;
            }
            
            if (UserSystemController.getCurAlbum() == destinationAlbum) {
                // If the user selected the same album, show an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Move Photo Error");
                alert.setContentText("The destination album is the same as the current album. Please choose a different album.");
                alert.showAndWait();
                return;
            }
            
    
            // 3. Add the photo to the destination album
            destinationAlbum.addPhoto(photo);
    
            // 4. Update the user interface
            // You may need to call a method to refresh the UI or go back to the previous screen
            // For example, if you have a method to go back to the photo display screen:

        }
        
    }
    /**
 * Switches the scene to the album display page.
 * @param event The event that triggered the method call.
 * @throws IOException If an error occurs during loading the FXML file.
 */
@FXML
public void goBack(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/PhotoAlbum/view/albumDisplay.fxml"));
        Parent userSystemPage = loader.load();
        albumDisplayController controller = loader.getController();
        //controller.setCurrentUser(currentUser);

        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.setRoot(userSystemPage);
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
 * When the deleteTag button is clicked, the tag is removed from table view and Photo
 * @param event the event that initiates the method
 */
    @FXML
    private void deleteTag(ActionEvent event) {
        // Implement your logic for deleting a tag from the photo
        Tag selectedTag = tagTableView.getSelectionModel().getSelectedItem();

        for (int i = 0; i < photo.getTags().size(); i++) {
            if (photo.tagEquals(selectedTag, photo.getTags().get(i))) {
                photo.removeTag(photo.getTags().get(i));
                data.remove(selectedTag);
                break;
            }
        }

        // photo.removeTag(selectedTag);
        // data.remove(selectedTag);

        data.clear();
        tagTableView.getItems().clear();

        doTableView();

    }

    // public editPhotoSystemController() {

    //     // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editPhoto.fxml"));
    //     // fxmlLoader.setRoot(this);
    //     // fxmlLoader.setController(this);

    //     // try {
    //     //     fxmlLoader.load();
    //     // } catch (IOException e) {
    //     //     throw new RuntimeException(e);
    //     // }
    // }

    // @FXML
    // public void initialize() {
    //     // Initialize your UI components here
    // }

    // Define your event handlers here

}
