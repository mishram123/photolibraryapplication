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
    //@FXML
    //private TableView<Tag> tagTableView;
    //@FXML
    //private TableColumn<Tag, String> tagNameColumn;
    //@FXML
    //private TableColumn<Tag, String> tagValueColumn;
    //@FXML
    //private Label tagsLabel;
    
    //@FXML
    //private TextField tagNameTextField;
    //@FXML
    //private TextField tagValueTextField;
    //@FXML
    //private Button deleteTagButton;

    public editPhotoSystemController() {
        // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editPhoto.fxml"));
        // fxmlLoader.setRoot(this);
        // fxmlLoader.setController(this);

        // try {
        //     fxmlLoader.load();
        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }
    }

    // @FXML
    // public void initialize() {
    //     // Initialize your UI components here
    // }

    // Define your event handlers here

}
