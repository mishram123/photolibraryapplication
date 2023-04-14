package PhotoAlbum.controller;

/**
 * @author Soban Chaudhry
 * @author Mannan Mishra
 */

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

import PhotoAlbum.model.User;
import javafx.application.Platform;

/**
 * The AdminSystemController class manages the administrator interface, allowing admins to create, delete, and view users
 * The class initializes the interface with the table of users, provides methods for adding and deleting users
 * handles the logic of the quit and logout buttons
 */
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
    public static ObservableList<User> usernames = FXCollections.observableArrayList();

    //@FXML
    //private ObservableList<User> usernames = AdminSystemController.usernames;

//  TableColumn<String, String> usernameListColumn = new TableColumn<>("Username List");

    @FXML
    TableColumn<User, String> usernameListColumn = new TableColumn<>("Username List");

    /**
     * Initializes the AdminSystemController by setting the cell value factory to the usernameListColumn
     * also adds the list of usernames to the table
     */
  @FXML
  public void initialize() {
    usernameListColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

    //usernameListColumn.setCellFactory(TextFieldTableCell.forTableColumn());

    // usernameListTable.setItems(userNameData);

    usernameListTable.setItems(usernames);

    //usernameListTable.getColumns().add(usernameListColumn);
    
  }

  /**
   * adds the default user to the list of users
   * @param user the default user to be added
   */
  public static void addDefaultUser(User user){
    usernames.add(user);
  }

  /**
   * Create a new user and adds them to the list of users if the entered username does not already exist in the list
   * If the username already exists, a dialog box is displayed informing the user of the error
   * @param event the ActionEvent object representing the button clcik that triggers this method
   * @return true if a new user is successfully added, false otherwise
   * @throws IOException if the userExists.fxml does not load
   */
    @FXML
    private boolean createUser (ActionEvent event) throws IOException {
        String enteredUser = usernameField.getText();

        for (User existingUser: usernames) {
            if (existingUser.getUsername().equals(enteredUser)) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PhotoAlbum/view/userExists.fxml"));
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

    /**
     * Deletes the selected user from the list of usernames
     * @param event the event that triggered this method
     */
    @FXML
    private void deleteUser(ActionEvent event) {
        User selectedUser = usernameListTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            usernames.remove(selectedUser);
        } 
    }

    /**
     * Returns the user object with the given username, or null if no such user exists
     * @param username the username to search for
     * @return the User object with the given username, or null if no such user exists
     */
    public static User getUserByUsername(String username) {
        for (User user : usernames) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
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

    /**
     * This method is called when the user clicks the quit button.
     * It displays a confirmation dialog and exits the application if the user selects yes
     * @param event the ActionEvent object representing the click of the quit button
     * @throws IOException if an error occurs in the QuitDialog.fxml file
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
    /**
     * Handles the logout action event by showing a dialog to confirm logout and redirecting to the login page
     * @param event the action event triggering the method
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

}
