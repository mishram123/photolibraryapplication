package PhotoAlbum.controller;
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

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import PhotoAlbum.model.Album;
import PhotoAlbum.model.Photo;
import PhotoAlbum.model.User;
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

    String stock = "stock";

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
    User stockUser = new User(stock);

    /**
     * This method is called when the user clicks the login button.
     * It checks if the entered username is valid and displays the appropriate screen or error message.
     * @param event The ActionEvent object representing the click of the login button
     * @throws IOException if an error occurs while loading other fxml files
     */
    @FXML
    private void login(ActionEvent event) throws IOException{
        AdminSystemController.addDefaultUser(stockUser);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PhotoAlbum/view/adminSystem.fxml"));
            Parent secondPage = loader.load();

            // Get the current scene
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Set the new root node to the current scene
            currentScene.setRoot(secondPage);
        } else if (foundUser != null){
            u = foundUser;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PhotoAlbum/view/userSystem.fxml"));
            Parent secondPage = loader.load();

            // Get the current scene
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Set the new root node to the current scene
            currentScene.setRoot(secondPage);
        }else if(enteredUser.equals(stockUser.getUsername())){
            u = stockUser;
            Album currentAlbum = new Album("stockAlbum");
            u.addAlbum(currentAlbum);
            File file1 = new File("/photos04/data/big_Data.jpg");
            String photoName = file1.getName();
            String photoPath = file1.toURI().toString();
            LocalDateTime photoDateTime = LocalDateTime.now();
            Photo photo1 = new Photo(photoName, photoPath, photoDateTime);
            photo1.setCaption("Big Data Cloud");
            photo1.addTag("cloud", "data");
            currentAlbum.addPhoto(photo1);

            File file2 = new File("/photos04/data/little_robot.jpg");
            String photoName2 = file2.getName();
            String photoPath2 = file2.toURI().toString();
            LocalDateTime photoDateTime2 = LocalDateTime.now();
            Photo photo2 = new Photo(photoName2, photoPath2, photoDateTime2);
            photo2.setCaption("tiny robot");
            photo2.addTag("robot", "machine");
            currentAlbum.addPhoto(photo2);

            File file3 = new File("/photos04/data/man_looking_computer.jpg");
            String photoName3 = file3.getName();
            String photoPath3 = file3.toURI().toString();
            LocalDateTime photoDateTime3 = LocalDateTime.now();
            Photo photo3 = new Photo(photoName3, photoPath3, photoDateTime3);
            photo3.setCaption("Computer Sceintist");
            photo2.addTag("programmer", "computer");
            currentAlbum.addPhoto(photo3);

            File file4 = new File("/photos04/data/no_picture_picture.jpg");
            String photoName4 = file4.getName();
            String photoPath4 = file4.toURI().toString();
            LocalDateTime photoDateTime4 = LocalDateTime.now();
            Photo photo4 = new Photo(photoName4, photoPath4, photoDateTime4);
            photo2.setCaption("Where is the picture");
            photo2.addTag("folder", "picture");
            currentAlbum.addPhoto(photo4);

            File file5 = new File("/photos04/data/where_data.jpg");
            String photoName5 = file5.getName();
            String photoPath5 = file5.toURI().toString();
            LocalDateTime photoDateTime5 = LocalDateTime.now();
            Photo photo5 = new Photo(photoName5, photoPath5, photoDateTime5);
            photo2.setCaption("where is data");
            photo2.addTag("mystery", "device");
            currentAlbum.addPhoto(photo5);
            

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PhotoAlbum/view/userSystem.fxml"));
            Parent secondPage = loader.load();

            // Get the current scene
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Set the new root node to the current scene
            currentScene.setRoot(secondPage);
        }
          
        else{
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PhotoAlbum/view/LoginDialog.fxml"));
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
     * Returns the user object that is currently logged in
     * if no user is logged in, returns null
     * @return the User object that is currently logged in, or null if no user is logged in
     */
    public static User getU(){
        return u;
    }
   
}
