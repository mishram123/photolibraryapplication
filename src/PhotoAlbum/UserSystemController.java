package PhotoAlbum;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UserSystemController {
  
  @FXML
  private AnchorPane anchorPane;
  @FXML
  private TableView table;
  @FXML
  private TableColumn albumNameColumn;
  @FXML
  private TableColumn numPhotosColumn;
  @FXML
  private TableColumn dateRangeColumn;
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




}
