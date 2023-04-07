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

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import javafx.application.Platform;


public class loginController {
    @FXML
    private TextField username;

    String user = "user";
    String admin = "admin";

    @FXML
    private void login(ActionEvent event) throws IOException{
        String enteredUser = username.getText();

        if (enteredUser.equals(user)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userSystem.fxml"));
            Parent secondPage = loader.load();

            // Get the current scene
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Set the new root node to the current scene
            currentScene.setRoot(secondPage);
        }
        else if (enteredUser.equals(admin)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminSystem.fxml"));
            Parent secondPage = loader.load();

            // Get the current scene
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Set the new root node to the current scene
            currentScene.setRoot(secondPage);
        }else{
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
   
}
