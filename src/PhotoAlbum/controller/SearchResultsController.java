package PhotoAlbum.controller;

/**
 * @author Soban Chaudhry
 * @author Mannan Mishra
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.List;

import PhotoAlbum.model.Album;
import PhotoAlbum.model.Photo;
import PhotoAlbum.model.Tag;
import java.util.ArrayList;

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

import PhotoAlbum.model.Album;
import PhotoAlbum.model.Photo;
import PhotoAlbum.model.User;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import java.util.List;

import PhotoAlbum.model.Album;
import PhotoAlbum.model.Photo;
import PhotoAlbum.model.Tag;

public class SearchResultsController {
    @FXML
    private TilePane searchResultsTilePane;

    @FXML
    private Label searchResultsLabel;

    @FXML
    private Button backButton;

    @FXML
    private Button createAlbumButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button quitButton;

    static List<Photo> searchRes;

    public void displaySearchResults(List<Photo> searchResults) {
        searchResultsTilePane.getChildren().clear();
        searchRes = searchResults;

        for (Photo photo : searchResults) {
            ImageView imageView = new ImageView(photo.getFilePath());
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setOnMouseClicked(event -> {
                // Handle photo click if needed
            });
            searchResultsTilePane.getChildren().add(imageView);
        }
    }

    @FXML
    public void createAlbumFromSearchResults(ActionEvent event) {
        List<Album> albums = loginController.getU().getAlbums();
        if (searchRes == null || searchRes.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Search Results");
            alert.setContentText("There are no search results to create an album from.");
            alert.showAndWait();
            return;
        }
    
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Album");
        dialog.setHeaderText("Create a new album from search results");
        dialog.setContentText("Enter album name:");
    
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(albumName -> {
            if (albumName.trim().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Album Name");
                alert.setContentText("Album name cannot be empty.");
                alert.showAndWait();
                return;
            }
    
            for (Album existingAlbum : albums) {
                if (existingAlbum.getName().equals(albumName)) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Duplicate Album Name");
                    alert.setContentText("An album with this name already exists. Please choose a different name.");
                    alert.showAndWait();
                    return;
                }
            }
    
            Album newAlbum = new Album(albumName);
            newAlbum.setPhotos(searchRes);
            albums.add(newAlbum);
        });
    }

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
}
