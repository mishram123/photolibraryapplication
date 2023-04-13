package PhotoAlbum;
/**
 * @author Soban Chaudhry
 * @author Mannan Mishra
 */
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

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import javafx.application.Platform;

/**
 * This class is the controller for the login screen of the photos application
 * It handles user login and displays appropriate error messages if login fails
 */
public class loginController {
    /**
     * The Textfield object for user input of username
     */
    @FXML
    private TextField username;

    /**
     * String object representing the default user
     */
    String user = "user";

    /**
     * String object representing the admin account
     */
    String admin = "admin";

    /**
     * User object representinf the current user of the application
     */
    public static User u = null;

    /**
     * User object representing the default user
     */
    User deafultUser = new User(user);

    /**
     * This method is called when the user clicks the login button.
     * It checks if the entered username is valid and displays the appropriate screen or error message.
     * @param event The ActionEvent object representing the click of the login button
     * @throws IOException if an error occurs while loading other fxml files
     */
    @FXML
    private void login(ActionEvent event) throws IOException{
        AdminSystemController.addDefaultUser(deafultUser);
        String enteredUser = username.getText();
        User foundUser = AdminSystemController.getUserByUsername(enteredUser);

        // if (enteredUser.equals(user)) {
        //     FXMLLoader loader = new FXMLLoader(getClass().getResource("userSystem.fxml"));
        //     Parent secondPage = loader.load();

        //     // Get the current scene
        //     Scene currentScene = ((Node) event.getSource()).getScene();

        //     // Set the new root node to the current scene
        //     currentScene.setRoot(secondPage);
        // }
        if (enteredUser.equals(admin)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminSystem.fxml"));
            Parent secondPage = loader.load();

            // Get the current scene
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Set the new root node to the current scene
            currentScene.setRoot(secondPage);
        } else if (foundUser != null){
            u = foundUser;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userSystem.fxml"));
            Parent secondPage = loader.load();

            // Get the current scene
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Set the new root node to the current scene
            currentScene.setRoot(secondPage);
        }else if(enteredUser.equals(deafultUser.getUsername())){
            u = deafultUser;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userSystem.fxml"));
            Parent secondPage = loader.load();

            // Get the current scene
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Set the new root node to the current scene
            currentScene.setRoot(secondPage);
        }
          
        else{
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginDialog.fxml"));
            dialog.getDialogPane().setContent(loader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            Button closeButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
            closeButton.setOnAction(e -> dialog.close());
            dialog.showAndWait();
        }
    }

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
    /**
     * Returns the user object that is currently logged in
     * if no user is logged in, returns null
     * @return the User object that is currently logged in, or null if no user is logged in
     */
    public static User getU(){
        return u;
    }
   
}
