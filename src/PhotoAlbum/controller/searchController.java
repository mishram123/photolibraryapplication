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

/**
 * The searchController class handles search functionality for photos in the app
 * The class includes methods for searching photos by tags or date reange
 */

public class searchController {
    @FXML
    private TextField singleKey;
    @FXML
    private TextField singleValue;
    @FXML
    private TextField andKey1;
    @FXML
    private TextField andValue1;
    @FXML
    private TextField andKey2;
    @FXML
    private TextField andValue2;
    @FXML
    private RadioButton singleTagRadioButton;
    @FXML
    private RadioButton andRadioButton;
    @FXML
    private RadioButton orRadioButton;
    @FXML
    private TextField startDate;
    @FXML
    private TextField endDate;

    /**
     * initializes the search mode toggle group and sets the singleTagRadioButton as the default
     */
    @FXML
    public void initialize() {
        ToggleGroup searchModeToggleGroup = new ToggleGroup();
        singleTagRadioButton.setToggleGroup(searchModeToggleGroup);
        andRadioButton.setToggleGroup(searchModeToggleGroup);
        orRadioButton.setToggleGroup(searchModeToggleGroup);
        singleTagRadioButton.setSelected(true);
    }

    /**
     * Handles searchign for photos based on tags
     * Calls one of the search helper methods based on the selected search mode
     * @param event the ActionEvent that triggered the method call
     */
    @FXML
    public void handleSearchByTags(ActionEvent event) {
        List<Photo> searchResults;

        if (singleTagRadioButton.isSelected()) {
            String key = andKey1.getText();
            String value = andValue1.getText();
            searchResults = searchBySingleTag(key, value);
        } else if (andRadioButton.isSelected()) {
            String key1 = andKey1.getText();
            String value1 = andValue1.getText();
            String key2 = andKey2.getText();
            String value2 = andValue2.getText();
            searchResults = searchByConjunctiveTags(key1, value1, key2, value2);
        } else if (orRadioButton.isSelected()){ // orRadioButton.isSelected()
            String key1 = andKey1.getText();
            String value1 = andValue1.getText();
            String key2 = andKey2.getText();
            String value2 = andValue2.getText();
            searchResults = searchByDisjunctiveTags(key1, value1, key2, value2);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Search Option Selected");
            alert.setContentText("Please select one of the search options.");
            alert.showAndWait();
            return;
        }

        // Display search results
        // ...
        showSearchResults(searchResults);
    }

    /**
     * Handles the search by date action event by parsing the start and end dates from the text fields
     * search for photos in the date range using the searchByDateRange method
     * Display search results
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleSearchByDate(ActionEvent event) {
        LocalDate start = LocalDate.parse(startDate.getText());
        LocalDate end = LocalDate.parse(endDate.getText());

        List<Photo> searchResults = searchByDateRange(start, end);

        // Display search results
        // ...
        showSearchResults(searchResults);
    }

    public void showSearchResults(List<Photo> searchResults) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PhotoAlbum/view/searchResults.fxml"));
            VBox searchResultsRoot = loader.load();

            SearchResultsController searchResultsController = loader.getController();
            searchResultsController.displaySearchResults(searchResults);

            Stage searchResultsStage = new Stage();
            searchResultsStage.setTitle("Search Results");
            searchResultsStage.setScene(new Scene(searchResultsRoot));
            searchResultsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Implement the following methods according to your data structure and model
    private List<Photo> searchBySingleTag(String key, String value) {
        // ...
        List<Photo> searchResults = new ArrayList<>();
        List<Album> albums = loginController.getU().getAlbums();
    
        for (Album album : albums) {
            for (Photo photo : album.getPhotos()) {
                for (Tag tag : photo.getTags()) {
                    if (tag.getKey().equals(key) && tag.getValue().equals(value)) {
                        searchResults.add(photo);
                        break;
                    }
                }
            }
        }
    
        return searchResults;
    }

    /**
     * Search for photos that have a single tag with the given key and value
     * @param key the key of the tag to search for
     * @param value the value of the tag to search for
     * @return a list of photos that have the specified tag
     */
    private List<Photo> searchByConjunctiveTags(String key1, String value1, String key2, String value2) {
        // ...
        List<Photo> searchResults = new ArrayList<>();
    List<Album> albums = loginController.getU().getAlbums();

    for (Album album : albums) {
        for (Photo photo : album.getPhotos()) {
            boolean hasTag1 = false;
            boolean hasTag2 = false;

            for (Tag tag : photo.getTags()) {
                if (tag.getKey().equals(key1) && tag.getValue().equals(value1)) {
                    hasTag1 = true;
                }
                if (tag.getKey().equals(key2) && tag.getValue().equals(value2)) {
                    hasTag2 = true;
                }
            }

            if (hasTag1 && hasTag2) {
                searchResults.add(photo);
            }
        }
    }

    return searchResults;
    }

    /**
     * searches for photos that have both tags with the given keys and values
     * @param key1 the key of the first tag
     * @param value1 the value of the first tag
     * @param key2 the key of the second tag
     * @param value2 the value of the second tag
     * @return a list of photos that have both tags
     */
    private List<Photo> searchByDisjunctiveTags(String key1, String value1, String key2, String value2) {
        // ...
        List<Photo> searchResults = new ArrayList<>();
        List<Album> albums = loginController.getU().getAlbums();
    
        for (Album album : albums) {
            for (Photo photo : album.getPhotos()) {
                boolean hasTag1 = false;
                boolean hasTag2 = false;
    
                for (Tag tag : photo.getTags()) {
                    if (tag.getKey().equals(key1) && tag.getValue().equals(value1)) {
                        hasTag1 = true;
                    }
                    if (tag.getKey().equals(key2) && tag.getValue().equals(value2)) {
                        hasTag2 = true;
                    }
                }
    
                if (hasTag1 || hasTag2) {
                    searchResults.add(photo);
                }
            }
        }
    
        return searchResults;

    }

    /**
     * Searches for photos that were taken within the specified date range
     * @param start tbe start date of the range to search for
     * @param end the end date of the range to search for
     * @return a list of photos that were taken within the specified date range
     */
    private List<Photo> searchByDateRange(LocalDate start, LocalDate end) {
        // ...
        List<Photo> searchResults = new ArrayList<>();
        List<Album> albums = loginController.getU().getAlbums();
    
        for (Album album : albums) {
            for (Photo photo : album.getPhotos()) {
                LocalDate photoDate = photo.getDateTaken().toLocalDate();
                if ((photoDate.isEqual(start) || photoDate.isAfter(start)) && (photoDate.isEqual(end) || photoDate.isBefore(end))) {
                    searchResults.add(photo);
                }
            }
        }
    
        return searchResults;
    }

    /**
     * Handles the logout action event
     * @param event the event triggered by the user
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
     * Handles the quit action event
     * @param event the action event triggered by the user
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
}
