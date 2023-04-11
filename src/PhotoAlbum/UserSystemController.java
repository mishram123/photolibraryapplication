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
import javafx.beans.property.ReadOnlyIntegerWrapper;


import javax.swing.text.DefaultStyledDocument.ElementSpec;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;

public class UserSystemController {
  
  @FXML
  private AnchorPane anchorPane;
  @FXML
  private TableView<MyData> table;
  @FXML
  private TableColumn<MyData, String> albumNameColumn;
  @FXML
  private TableColumn<MyData, String> numPhotosColumn;
  @FXML
  private TableColumn<MyData, String> dateRangeColumn;
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

  private static Album curAlbum;

  ObservableList<String> albumNames = FXCollections.observableArrayList();
  ObservableList<String> photoNums = FXCollections.observableArrayList();
  ObservableList<String> dates = FXCollections.observableArrayList();
  List<MyData> data = new ArrayList<>();

  
  public void setUsername(String username) {
    userLabel.setText("User: " + username);
  }
  
  
  public void doTableView() {
    

    // Add a blank row to the beginning of the list
   

    // Add the actual data rows
    for (Album album : loginController.getU().getAlbums()) {
        String albumName = album.getName();
        int numPhotos = album.getNumPhotos();
        String dateRange = album.getName(); // You will need to modify this to get the actual date range
        data.add(new MyData(albumName, numPhotos, dateRange));
        
    }

    // Convert the list to an ObservableList
    ObservableList<MyData> observableData = FXCollections.observableArrayList(data);

    // Set the items of the TableView to the updated data list
    table.setItems(observableData);
}

  
  public void initialize() {
    String username = loginController.getU().getUsername();
    setUsername(username);

      albumNameColumn.setCellValueFactory(new PropertyValueFactory<>("albumName"));
      numPhotosColumn.setCellValueFactory(new PropertyValueFactory<>("numPhotos"));
      dateRangeColumn.setCellValueFactory(new PropertyValueFactory<>("dateRange"));
      doTableView(); 

    
  }

  @FXML
    public void createAlbum(ActionEvent event) throws IOException{
        String albumName = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createAlbumBox.fxml"));
        Parent secondPage = loader.load();

        // Get the current scene
        Scene currentScene = ((Node) event.getSource()).getScene();
    
        // Set the new root node to the current scene
        currentScene.setRoot(secondPage);

        albumName = CreateAlbumBoxController.getAlbumName();
        loginController.getU().addAlbum(new Album(albumName));

        if(data.size()==0){
          data.add(new MyData(albumName, 0, albumName));
          data.add(new MyData(null, 0, null));
        }else{
          data.add(new MyData(albumName, 0, albumName));
        }
        
        
        // Convert the list to an ObservableList
    ObservableList<MyData> observableData = FXCollections.observableArrayList(data);

    // Set the items of the TableView to the updated data list
    table.setItems(observableData);
        

    }  

  @FXML
  public void deleteAlbum(ActionEvent event){
    MyData selectedItem = table.getSelectionModel().getSelectedItem();

    for(int i = 0; i<loginController.getU().getAlbums().size(); i++){
      if(loginController.getU().getAlbums().get(i).getName() == selectedItem.getAlbumName()){
        loginController.getU().getAlbums().remove(i);
      }
    }

    table.getItems().clear();
    data.clear();
    
    table.refresh();
    doTableView();
    

  }
  
  @FXML
  private void renameAlbum(ActionEvent event) throws IOException{
    MyData selectedItem = table.getSelectionModel().getSelectedItem();
    
    String albumName = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("renameAlbumBox.fxml"));
        Parent secondPage = loader.load();

        // Get the current scene
        Scene currentScene = ((Node) event.getSource()).getScene();
    
        // Set the new root node to the current scene
        currentScene.setRoot(secondPage);

        albumName = CreateAlbumBoxController.getAlbumName();

        for(int i = 0; i<loginController.getU().getAlbums().size(); i++){
          if(loginController.getU().getAlbums().get(i).getName().compareTo(selectedItem.getAlbumName()) ==0){
            loginController.getU().getAlbums().get(i).setName(albumName);
            break;
          }
        }
        table.getItems().clear();
        data.clear();
    
        table.refresh();
        doTableView();
  }

  @FXML
  public void openAlbum(ActionEvent event) throws IOException {
    // Album selectedAlbum = table.getSelectionModel().getSelectedItem();
    // if (selectedAlbum == null) {
    //     // Show an error message or a prompt to select an album
    //     return;
    // }

    MyData selectedItem = table.getSelectionModel().getSelectedItem();

    Album selectedAlbum = null;

    for(int i = 0; i<loginController.getU().getAlbums().size(); i++){
      if(loginController.getU().getAlbums().get(i).getName() != null) {
        if(loginController.getU().getAlbums().get(i).getName().compareTo(selectedItem.getAlbumName()) ==0){
          selectedAlbum = loginController.getU().getAlbums().get(i);
          curAlbum = selectedAlbum;
          break;
        }
      }
    }

    FXMLLoader loader = new FXMLLoader(getClass().getResource("albumDisplay.fxml"));
    Parent secondPage = loader.load();

    // Get the current scene
    Scene currentScene = ((Node) event.getSource()).getScene();
    
    // Set the new root node to the current scene
    currentScene.setRoot(secondPage);
  }

  public static Album getCurAlbum () {
    return curAlbum;
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

  public static class MyData {
    private final String albumName;
    private final int numPhotos;
    private final String dateRange;

    public MyData(String albumName, int numPhotos, String dateRange) {
        this.albumName = albumName;
        this.numPhotos = numPhotos;
        this.dateRange = dateRange;
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getNumPhotos() {
        return numPhotos;
    }

    public String getDateRange() {
        return dateRange;
    }
}


}
