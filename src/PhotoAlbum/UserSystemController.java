package PhotoAlbum;

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

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import javafx.application.Platform;

public class UserSystemController {
  
  @FXML
  private AnchorPane anchorPane;
  @FXML
  private TableView<Album> table = new TableView<>();
  @FXML
  private TableColumn<Album, String> albumNameColumn = new TableColumn<>("Album Name");
  @FXML
  private TableColumn<Album, Integer> numPhotosColumn = new TableColumn<>("Number of Photos");
  @FXML
  private TableColumn<Album, String> dateRangeColumn = new TableColumn<>("Date Range");
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

  ObservableList<Album> albums = FXCollections.observableArrayList();
  List<Album> userAlbums = new ArrayList<Album>();


  
  public void setUsername(String username) {
    userLabel.setText("User: " + username);
  }
  
  
  public void initialize() {
    String username = loginController.getU().getUsername();
    setUsername(username);

    albumNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    numPhotosColumn.setCellValueFactory(new PropertyValueFactory<>("numPhotos"));
    dateRangeColumn.setCellValueFactory(new PropertyValueFactory<>("dateRange"));

    table.getColumns().addAll(albumNameColumn, numPhotosColumn, dateRangeColumn);
   
    for(int i = 0; i<loginController.getU().getAlbums().size(); i++){
      userAlbums.add(loginController.getU().getAlbums().get(i));
    }
    albums.addAll(loginController.getU().getAlbums());
    table.setItems(albums);
  }

  @FXML
    public void createAlbum(ActionEvent event) throws IOException{
        String albumName = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createUserBox.fxml"));
        Parent secondPage = loader.load();

        // Get the current scene
        Scene currentScene = ((Node) event.getSource()).getScene();
    
        // Set the new root node to the current scene
        currentScene.setRoot(secondPage);

        albumName = CreateUserController.getAlbumName();
        albums.add(new Album(albumName));
        table.refresh();

    }  
  
  
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
