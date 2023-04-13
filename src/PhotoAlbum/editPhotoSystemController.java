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

    @FXML
    public void initialize() {
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

    private void quit() {
        // Implement your logic for quitting the application
    }

    private void logout() {
        // Implement your logic for logging out of the application
    }

    private void deleteTag() {
        // Implement your logic for deleting a tag from the photo
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
