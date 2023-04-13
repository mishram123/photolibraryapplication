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
import java.time.LocalDateTime;

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
import javafx.collections.*;
import javafx.application.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.Action;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;

public class photoDisplayController {
@FXML
private ImageView enlargedPhoto;
@FXML
private Label albumLabel;

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
public void setAlbumName(String albumName) {
    albumLabel.setText(albumName);
  }

public void initialize(){
    String albumName = UserSystemController.getCurAlbum().getName();
    setAlbumName(albumName);

}

@FXML
    private void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("albumDisplay.fxml"));
        Parent userSystemPage = loader.load();
        albumDisplayController controller = loader.getController();
        //controller.setCurrentUser(currentUser);

        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.setRoot(userSystemPage);
    }


@FXML
private void editPhoto(ActionEvent Event) throws IOException{
    FXMLLoader loader = new FXMLLoader(getClass().getResource("editPhoto.fxml"));
    Parent editPhotoPage = loader.load();
    editPhotoSystemController controller = loader.getController();

    Scene currentscene = ((Node) Event.getSource()).getScene();
    currentscene.setRoot(editPhotoPage);


}
@FXML
private void deletePhoto(ActionEvent Event) throws IOException{
    currentAlbum.deletePhoto(currentPhoto);
    
    goBack(Event);

}

@FXML
private void nextPhoto(ActionEvent event) throws IOException{
    Photo nextPhoto = null;
    int index = 0;
    boolean bool = false;
    for(int i = 0; i<currentAlbum.getPhotos().size(); i++){
        if(i+1<currentAlbum.getPhotos().size() && currentAlbum.getPhotos().get(i) == currentPhoto){
            index = i+1;
            bool = true;
            break;
        }
    }
    if(bool){
        nextPhoto = currentAlbum.getPhotos().get(index);
    }else{
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No more photos");
            alert.setHeaderText(null);
            alert.setContentText("There are no more next photos.");

            alert.showAndWait();
    }

    FXMLLoader loader = new FXMLLoader(getClass().getResource("photoDisplay.fxml"));
        Parent photoPage = loader.load();
        photoDisplayController controller = loader.getController();
        controller.setPhoto(nextPhoto);
        controller.setAlbum(currentAlbum);
    
        Scene scene = new Scene(photoPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
}

@FXML
private void prevPhoto(ActionEvent event) throws IOException{
    Photo prevPhoto = null;
    int index = 0;
    boolean bool = false;
    for(int i = 0; i<currentAlbum.getPhotos().size(); i++){
        if(i-1>=0 && currentAlbum.getPhotos().get(i) == currentPhoto){
            index = i-1;
            bool = true;
            break;
        }
    }
    if(bool){
        prevPhoto = currentAlbum.getPhotos().get(index);
    }else{
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No more photos");
            alert.setHeaderText(null);
            alert.setContentText("There are no more previous photos.");

            alert.showAndWait();
    }
    FXMLLoader loader = new FXMLLoader(getClass().getResource("photoDisplay.fxml"));
        Parent photoPage = loader.load();
        photoDisplayController controller = loader.getController();
        controller.setPhoto(prevPhoto);
        controller.setAlbum(currentAlbum);
    
        Scene scene = new Scene(photoPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
