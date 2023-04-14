package PhotoAlbum.model;
/**
 * @author Soban Chaudhry
 * @author Mannan Mishra
 */

import java.util.ArrayList;
import java.util.List;

/**
 * The User class represents a user in the photo app. It contains a list of albums and a username
 */
public class User {
    private String username;
    private List<Album> albums;

    /**
     * Creates a new User object with the given username
     * @param username the username of the new User object
     */
    public User(String username) {
        this.username = username;
        albums = new ArrayList<>();
    }

    /**
     * Returns the username of the User object
     * @return the username of the User object
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns a list of albums that belong to the User object
     * @return list of albums that belong to the User object
     */
    public List<Album> getAlbums() {
        return albums;
    }

    /**
     * Adds the given album to the list of albums that belong to the User object
     * @param album the album to be added to the list of albums
     */
    public void addAlbum(Album album) {
        albums.add(album);
    }

    /**
     * Removes the given album from the list of albums that belong to the User object
     * @param album the album to be removed form the list of albums
     */
    public void removeAlbum(Album album) {
        albums.remove(album);
    }

}
