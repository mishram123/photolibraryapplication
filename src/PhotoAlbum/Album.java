package PhotoAlbum;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private String name;
    private List<Photo> photos;
    private int numPhotos;

    public Album(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
        this.numPhotos = photos.size();
    }
    

    //getters
    public String getName() {
        return name;
    }

    public List<Photo> getPhotos () {
        return photos;
    }

    public int getNumPhotos(){
        return photos.size();
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    //add a photo
    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    //remove a photo
    public void deletePhoto(Photo photo) {
        photos.remove(photo);
    }

    public Photo findPhotoByFilePath(String filePath) {
        for (Photo photo : photos) {
            if (photo.getFilePath().equals(filePath)) {
                return photo;
            }
        }
        return null;
    }
}
