package PhotoAlbum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import PhotoAlbum.Photo;
import javafx.scene.image.Image;

public class photoDisplayController {
@FXML
private ImageView enlargedPhoto;

private Photo currentPhoto;
private Album currentAlbum;

// public void initialize() {
//     if (imagePath != null && !imagePath.isEmpty()) {
//         Image image = new Image(imagePath);
//         enlargedPhoto.setImage(image);
//     } else {
//         System.err.println("Image path is not set or empty.");
//     }
// }


public void setPhoto(Photo photo) {
    this.currentPhoto = photo;
    displayEnlargedPhoto();
}

public void setAlbum(Album album) {
    this.currentAlbum = album;
}

private void displayEnlargedPhoto() {
    Image image = new Image(currentPhoto.getFilePath());
    enlargedPhoto.setImage(image);
}
}
