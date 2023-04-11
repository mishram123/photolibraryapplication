package PhotoAlbum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.time.LocalDateTime;

import java.io.File;
import java.io.IOException;

public class albumDisplayController {
    @FXML
    private Label albumNameLabel;
    @FXML
    private TilePane photoTilePane;
    @FXML
    private Button backButton;
    @FXML
    private Button addPhotoButton;

    private Album currentAlbum;
    private User currentUser;

    public void setCurrentAlbum(Album album) {
        this.currentAlbum = album;
        albumNameLabel.setText(album.getName());
        displayPhotoPreviews();
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    private void displayPhotoPreviews() {
        photoTilePane.getChildren().clear();

        for (Photo photo : currentAlbum.getPhotos()) {
            ImageView imageView = new ImageView(photo.getFilePath());
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            photoTilePane.getChildren().add(imageView);
        }
    }

    @FXML
    private void addPhoto(ActionEvent event) {
        currentAlbum = UserSystemController.getCurAlbum();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a photo");
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            String photoName = selectedFile.getName();
            String photoPath = selectedFile.toURI().toString();
            LocalDateTime photoDateTime = LocalDateTime.now();
            Photo newPhoto = new Photo(photoName, photoPath, photoDateTime);
            currentAlbum.addPhoto(newPhoto);
            displayPhotoPreviews();
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userSystem.fxml"));
        Parent userSystemPage = loader.load();
        UserSystemController controller = loader.getController();
        //controller.setCurrentUser(currentUser);

        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.setRoot(userSystemPage);
    }
}
