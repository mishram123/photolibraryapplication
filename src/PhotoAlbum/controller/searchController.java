package PhotoAlbum.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.List;

import PhotoAlbum.model.Album;
import PhotoAlbum.model.Photo;
import PhotoAlbum.model.Tag;
import java.util.ArrayList;



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

    @FXML
    public void initialize() {
        ToggleGroup searchModeToggleGroup = new ToggleGroup();
        singleTagRadioButton.setToggleGroup(searchModeToggleGroup);
        andRadioButton.setToggleGroup(searchModeToggleGroup);
        orRadioButton.setToggleGroup(searchModeToggleGroup);
        singleTagRadioButton.setSelected(true);
    }

    @FXML
    public void handleSearchByTags(ActionEvent event) {
        List<Photo> searchResults;

        if (singleTagRadioButton.isSelected()) {
            String key = singleKey.getText();
            String value = singleValue.getText();
            searchResults = searchBySingleTag(key, value);
        } else if (andRadioButton.isSelected()) {
            String key1 = andKey1.getText();
            String value1 = andValue1.getText();
            String key2 = andKey2.getText();
            String value2 = andValue2.getText();
            searchResults = searchByConjunctiveTags(key1, value1, key2, value2);
        } else { // orRadioButton.isSelected()
            String key1 = andKey1.getText();
            String value1 = andValue1.getText();
            String key2 = andKey2.getText();
            String value2 = andValue2.getText();
            searchResults = searchByDisjunctiveTags(key1, value1, key2, value2);
        }

        // Display search results
        // ...
    }

    @FXML
    public void handleSearchByDate(ActionEvent event) {
        LocalDate start = LocalDate.parse(startDate.getText());
        LocalDate end = LocalDate.parse(endDate.getText());

        List<Photo> searchResults = searchByDateRange(start, end);

        // Display search results
        // ...
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

    @FXML
    public void handleBack(ActionEvent event) {
        // ...
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        // ...
    }

    @FXML
    public void handleQuit(ActionEvent event) {
        // ...
    }
}
