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

import javafx.application.Platform;


public class AdminSystemController {
  @FXML
  private TextField usernameField = new TextField();
  @FXML
  private Label titleLabel;
  @FXML
  private Button createUserButton;
  @FXML
  private Button deleteUserButton;
  @FXML
  private Button quitButton;
  @FXML
  private Button logoutButton;

//   @FXML
//   private TableView<String> usernameListTable = new TableView<>();


    @FXML
    private TableView<User> usernameListTable = new TableView<>();

  //@FXML
  //ObservableList<String> userNameData = FXCollections.observableArrayList();

    @FXML
    ObservableList<User> usernames = FXCollections.observableArrayList();

//  TableColumn<String, String> usernameListColumn = new TableColumn<>("Username List");

    @FXML
    TableColumn<User, String> usernameListColumn = new TableColumn<>("Username List");

  @FXML
  public void initialize() {
    usernameListColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

    //usernameListColumn.setCellFactory(TextFieldTableCell.forTableColumn());

    // usernameListTable.setItems(userNameData);

    usernameListTable.setItems(usernames);

    //usernameListTable.getColumns().add(usernameListColumn);
    
  }

    @FXML
    private boolean createUser (ActionEvent event) throws IOException {
        String enteredUser = usernameField.getText();

        for (User existingUser: usernames) {
            if (existingUser.getUsername().equals(enteredUser)) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("userExists.fxml"));
                dialog.getDialogPane().setContent(loader.load());
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                Button closeButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
                closeButton.setOnAction(e -> dialog.close());
                dialog.showAndWait();
                return false;
            }
        }

        usernames.add(new User(enteredUser));
        usernameField.clear();
        usernameListTable.refresh();
        return true;

    }


    //@FXML
    /*private void createUser(ActionEvent event) throws IOException{
        String enteredUser = usernameField.getText();

        boolean userExists = usernameListTable.getItems().stream().anyMatch(item -> item.compareTo(enteredUser)==0);

        if(userExists){
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userExists.fxml"));
            dialog.getDialogPane().setContent(loader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            Button closeButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
            closeButton.setOnAction(e -> dialog.close());
            dialog.showAndWait();
        }else{
            userNameData.add(enteredUser);
            usernameField.clear();
            usernameListTable.refresh();
        }
    }*/

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
