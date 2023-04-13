package PhotoAlbum;

/**
 * @author Soban Chaudhry
 * @author Mannan Mishra
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import PhotoAlbum.Photo;
import javafx.scene.image.Image;

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
import java.util.Optional;

import javax.swing.Action;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;

/**
 * The photo Display Controller class is a controller class that handles the display of photos in the photos app
 * It manages displaying a selected photo, the ability to go back to the album displaym and the ability to edit and delete photos
 */
public class photoDisplayController {
    /**
     *The ImageView that displays the selected photo 
     */
    @FXML
    private ImageView enlargedPhoto;
    /**
     * The Label that displays the name of the album the photo belongs to
     */
    @FXML
    private Label albumLabel;
    /**
     * The currently selected Photo object
     */
    private Photo currentPhoto;
    /**
     * The album object that the currently selected photo belongs to
     */
    private Album currentAlbum;

    private static Photo curPhoto;

// public void initialize() {
//     if (imagePath != null && !imagePath.isEmpty()) {
//         Image image = new Image(imagePath);
//         enlargedPhoto.setImage(image);
//     } else {
//         System.err.println("Image path is not set or empty.");
//     }
// }

/**
 * Sets the currentPhoto varialbe to the given photo
 * @param photo the Photo object to set as the currentPhoto
 */
public void setPhoto(Photo photo) {
    this.currentPhoto = photo;
    curPhoto = photo;
    displayEnlargedPhoto();
}

/**
 * Sets the currentAlbum to the given album
 * @param album the Album object to set as the currentAlbum
 */
public void setAlbum(Album album) {
    this.currentAlbum = album;
}

public static Photo getPhoto() {
    return curPhoto;
}

/**
 * Displays the enlarged version of the currentPhoto in the enlargedPhoto ImageView
 */
private void displayEnlargedPhoto() {
    Image image = new Image(currentPhoto.getFilePath());
    enlargedPhoto.setImage(image);
}
/**
 * Sets the text of the albumLabel to the given albumName
 * @param albumName the name of the album to set as the text of the albumLabel
 */
public void setAlbumName(String albumName) {
    albumLabel.setText(albumName);
  }

  /**
   * Initializes the controller class. Sets the albumLabel text to the name of the current album
   */
public void initialize(){
    String albumName = UserSystemController.getCurAlbum().getName();
    setAlbumName(albumName);

}

/**
 * Switches the scene to the album display page.
 * @param event The event that triggered the method call.
 * @throws IOException If an error occurs during loading the FXML file.
 */
@FXML
    private void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("albumDisplay.fxml"));
        Parent userSystemPage = loader.load();
        albumDisplayController controller = loader.getController();
        //controller.setCurrentUser(currentUser);

        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.setRoot(userSystemPage);
    }

/**
 * Opens editphoto page and sets it as the current scene
 * @param Event the event that triggered this method
 * @throws IOException if the FXML file cannot be loaded
 */
@FXML
private void editPhoto(ActionEvent Event) throws IOException{
    FXMLLoader loader = new FXMLLoader(getClass().getResource("editPhoto.fxml"));
    Parent editPhotoPage = loader.load();
    editPhotoSystemController controller = loader.getController();

    Scene currentscene = ((Node) Event.getSource()).getScene();
    currentscene.setRoot(editPhotoPage);
}

/**
 * Displays a confirmation dialog for deleting a photo
 * If confirmed, deletes the current photo from current album and goes back to previous page
 * @param event the event that triggered this method
 * @throws IOException if the FXML file cannot be loaded
 */
@FXML
private void deletePhoto(ActionEvent Event) throws IOException{
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm Deletion");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to delete?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
        currentAlbum.deletePhoto(currentPhoto);
    
        goBack(Event);
    }



}

/**
 * Displays the next photo in the current album, if it exists
 * @param event The event that triggered this method
 * @throws IOException if the FXML file cannot be loaded
 */
@FXML
private void nextPhoto(ActionEvent event) throws IOException{
    Photo nextPhoto = null;
    int index = 0;
    boolean bool = false;
    for(int i = 0; i<currentAlbum.getPhotos().size(); i++){
        if(i+1<currentAlbum.getPhotos().size() && currentAlbum.getPhotos().get(i) == currentPhoto){
            index = i+1;
            bool = true;
            break;
        }
    }
    if(bool){
        nextPhoto = currentAlbum.getPhotos().get(index);
    }else{
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No more photos");
            alert.setHeaderText(null);
            alert.setContentText("There are no more next photos.");

            alert.showAndWait();
    }

    FXMLLoader loader = new FXMLLoader(getClass().getResource("photoDisplay.fxml"));
        Parent photoPage = loader.load();
        photoDisplayController controller = loader.getController();
        controller.setPhoto(nextPhoto);
        controller.setAlbum(currentAlbum);
    
        Scene scene = new Scene(photoPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
}

/**
 * Displays the previous photo in the current album, if it exists
 * @param event the event that triggered this method
 * @throws IOException if the FXML file cannot be loaded
 */
@FXML
private void prevPhoto(ActionEvent event) throws IOException{
    Photo prevPhoto = null;
    int index = 0;
    boolean bool = false;
    for(int i = 0; i<currentAlbum.getPhotos().size(); i++){
        if(i-1>=0 && currentAlbum.getPhotos().get(i) == currentPhoto){
            index = i-1;
            bool = true;
            break;
        }
    }
    if(bool){
        prevPhoto = currentAlbum.getPhotos().get(index);
    }else{
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No more photos");
            alert.setHeaderText(null);
            alert.setContentText("There are no more previous photos.");

            alert.showAndWait();
    }
    FXMLLoader loader = new FXMLLoader(getClass().getResource("photoDisplay.fxml"));
        Parent photoPage = loader.load();
        photoDisplayController controller = loader.getController();
        controller.setPhoto(prevPhoto);
        controller.setAlbum(currentAlbum);
    
        Scene scene = new Scene(photoPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
}
