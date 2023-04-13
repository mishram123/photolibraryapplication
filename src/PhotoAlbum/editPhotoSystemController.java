package PhotoAlbum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import PhotoAlbum.Photo;
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

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;



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
    @FXML
    public void initialize() {
        String albumName = UserSystemController.getCurAlbum().getName();
        setAlbumName(albumName);
        displayEnlargedPhoto();
        setCaption();

        tagNameColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        tagValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
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

    private void displayEnlargedPhoto() {
        photo = photoDisplayController.getPhoto();
        Image image = new Image(photo.getFilePath());
        enlargedPhoto.setImage(image);
        photo = photoDisplayController.getPhoto();
    }

    public void setCaption() {
        captionLabel.setText(photo.getCaption());
    }

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

    public void addTag() {
        tagTableView.getItems().clear();
        // Implement your logic for adding a tag to the photo
        String key = tagNameTextField.getText();
        String value = tagValueTextField.getText();
        photo.addTag(key, value);

        doTableView();
    }

    private void movePhoto() {
        // Implement your logic for moving the photo
    }

    private void copyPhoto() {
        // Implement your logic for copying the photo
    }
    /**
 * Switches the scene to the album display page.
 * @param event The event that triggered the method call.
 * @throws IOException If an error occurs during loading the FXML file.
 */
@FXML
private void goBack(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("photoDisplay.fxml"));
    Parent userSystemPage = loader.load();
    photoDisplayController controller = loader.getController();
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
         FXMLLoader loader = new FXMLLoader(getClass().getResource("QuitDialog.fxml"));
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
  FXMLLoader loader = new FXMLLoader(getClass().getResource("LogoutDialog.fxml"));
  try {
      dialog.getDialogPane().setContent(loader.load());
  } catch (IOException e) {
      e.printStackTrace();
  }
  dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
  dialog.showAndWait().ifPresent(response -> {
      if(response == ButtonType.YES){
          FXMLLoader nextloader = new FXMLLoader(getClass().getResource("Login.fxml"));
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
    @FXML
    private void deleteTag(ActionEvent event) {
        // Implement your logic for deleting a tag from the photo
        Tag selectedTag = tagTableView.getSelectionModel().getSelectedItem();

        photo.removeTag(selectedTag);
        data.remove(selectedTag);

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
