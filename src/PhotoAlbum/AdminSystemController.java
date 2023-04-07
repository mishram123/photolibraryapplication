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


public class AdminSystemController {
  @FXML
  private TextField usernameField;
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
  @FXML
  private TableView<String> usernameListTable;

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
