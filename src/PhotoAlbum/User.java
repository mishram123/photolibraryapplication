package PhotoAlbum;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private List<Album> albums;

    public User(String username) {
        this.username = username;
        albums = new ArrayList<>();
    }

    //getters

    public String getUsername() {
        return username;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    //add an album
    public void addAlbum(Album album) {
        albums.add(album);
    }

    //remove an album
    public void removeAlbum(Album album) {
        albums.remove(album);
    }

}
