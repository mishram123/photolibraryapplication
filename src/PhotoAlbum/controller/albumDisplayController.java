package PhotoAlbum.controller;

/**
 * @author Soban Chaudhry
 * @author Mannan Mishra
 */

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

import PhotoAlbum.model.Album;
import PhotoAlbum.model.Photo;
import PhotoAlbum.model.User;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;

/**
 * The album display controller class is responsible for controlling the view of the photo album
 * It sets the current album and user, and displays photo previews of the current album
 * It allows the user to add photos to the album, click on photos to view them in full-screen.
 * and navigate back to the user system view
 */
public class albumDisplayController {
    /**
     * Label displaying the name of the album
     */
    @FXML
    private Label albumNameLabel;
    /**
     * TilePane displaying the photo previews of the current album
     */
    @FXML
    private TilePane photoTilePane;
    /**
     * Button for navigating back to the User System
     */
    @FXML
    private Button backButton;
    /**
     * Button for adding a photo to the current album
     */
    @FXML
    private Button addPhotoButton;

    /**
     * The current album being displayed
     */
    private static Album currentAlbum;

    /**
     * The current user
     */
    private User currentUser;

    /**
     * Initializes the album display view with the current album and displays photo previews
     */
    public void initialize() {
        currentAlbum = UserSystemController.getCurAlbum();
        displayPhotoPreviews();
      }

    /**
     * Sets the current album
     * @param album the album to set as the current album
     */
    public void setCurrentAlbum(Album album) {
        currentAlbum = album;
        albumNameLabel.setText(album.getName());
        displayPhotoPreviews();
    }

    /**
     * Sets the current user
     * @param user the user to set as the current user
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Displays photo previews of the current album
     */
    private void displayPhotoPreviews() {
        photoTilePane.getChildren().clear();
        for (Photo photo : currentAlbum.getPhotos()) {
            ImageView imageView = new ImageView(photo.getFilePath());
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setOnMouseClicked(event -> {
                try {
                    handlePhotoClick(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            photoTilePane.getChildren().add(imageView);
        }
    }

    /**
     * Adds a photo to the current album
     * @param event the event that triggered the method
     */
    @FXML
    private void addPhoto(ActionEvent event) {
        currentAlbum = UserSystemController.getCurAlbum();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a photo");
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            String photoName = selectedFile.getName();
            String photoPath = selectedFile.toURI().toString();
            LocalDateTime photoDateTime = LocalDateTime.now();
            Photo newPhoto = new Photo(photoName, photoPath, photoDateTime);
            currentAlbum.addPhoto(newPhoto);
            displayPhotoPreviews();
        }
    }

    
    /**
     * This method is called when the user clicks on a photo in the album display and opens the photo in the photo display range
     * It gets the selected photo from the current album and sets it in the photo display controller
     * It loads the photo display FXML file and sets the scene to show it
     * @param event the mouse event that triggered this method
     * @throws IOException if the photo display FXML is not found or if there is an error loading it
     */
    @FXML
    public void handlePhotoClick(MouseEvent event) throws IOException {
        ImageView clickedPhoto = (ImageView) event.getSource();
        // Get the clicked Photo object from the currentAlbum (you might need to implement a method to find a photo by its file path)
        Photo selectedPhoto = currentAlbum.findPhotoByFilePath(clickedPhoto.getImage().getUrl());
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PhotoAlbum/view/photoDisplay.fxml"));
        Parent photoPage = loader.load();
        photoDisplayController controller = loader.getController();
        controller.setPhoto(selectedPhoto);
        controller.setAlbum(currentAlbum);
    
        Scene scene = new Scene(photoPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is called when the user clicks on the "back" button in the album display page and takes the user back to the user system page
     * It loads the user System FXML and sets the scene to show it
     * @param event the action event that triggers the method
     * @throws IOException if the user System FXML file is not found or if there is an error loading it
     */
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PhotoAlbum/view/userSystem.fxml"));
        Parent userSystemPage = loader.load();
        UserSystemController controller = loader.getController();
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
}
