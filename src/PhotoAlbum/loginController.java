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


public class loginController {
    @FXML
    private TextField username;

    String user = "user";

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
    }
}
