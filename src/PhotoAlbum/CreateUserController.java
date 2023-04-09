package PhotoAlbum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Platform;
import java.io.IOException;

import javax.swing.Action;

import javafx.scene.layout.AnchorPane;


import javafx.application.Platform;


public class CreateUserController {

  @FXML
  private AnchorPane root;
  @FXML
  private TextField albumNameTextField;
  @FXML
  private Button okButton;

  private static String albumName;

  public CreateUserController() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createUserBox.fxml"));
    fxmlLoader.setController(this);

    try {
        fxmlLoader.load();
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
  public AnchorPane getRoot() {
    return root;
  }

  public static String getAlbumName() {
    return albumName;
  }

  @FXML
  private void createAlbum(){
    albumName = albumNameTextField.getText();
    try{
    ActionEvent event1 = new ActionEvent();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("userSystem.fxml"));
    Parent secondPage = loader.load();

    // Get the current scene
    Scene currentScene = ((Node) event1.getSource()).getScene();

    // Set the new root node to the current scene
    currentScene.setRoot(secondPage);
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  public void initialize(){
    okButton.setOnAction(event -> createAlbum());

    
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
