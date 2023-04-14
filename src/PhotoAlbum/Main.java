package PhotoAlbum;
/**
 * @author Soban Chaudhry
 * @author Mannan Mishra
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class is the entry point of the photos app 
 * It launches the JavaFX application and displays the Login.fxml scene
 */
public class Main extends Application {

    /**
     * The start method is called when the JavaFX application is launched
     * It loads the Login.fcml file and displays it in the primary stage
     * @param primaryStage the primary stage for this application, onto which the application scene can be set
     * @throws Exception if an error occurs during the application startup
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/PhotoAlbum/view/Login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main method is the entry point of the photos App
     * @param args the command line arguments but there are none
     */
    public static void main(String[] args) {
        launch(args);
    }
}