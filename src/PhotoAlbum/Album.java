package PhotoAlbum;
/**
 * @author Soban Chaudhry
 * @author Mannan Mishra
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;


/**
 * Represents an album in the photo management system containing a name and list of photos
 * Album class provides methods to add and remove photos, and to find a photo by its filepath
 */
public class Album {
    /**
     * the name of the album
     */
    private String name;
    /**
     * list of photos in album
     */
    private List<Photo> photos;
    /**
     * number of photos in album
     */
    private int numPhotos;

    /**
     * Constructs a new album with the given name and an empty list of photos
     * @param name the name of the album
     */
    public Album(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
        this.numPhotos = photos.size();
    }
    

    /**
     * Returns the name of the album
     * @return the name of the album
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of photos in the album
     * @return the list of photos in the album
     */
    public List<Photo> getPhotos () {
        return photos;
    }

    /**
     * returns the number of photos in the album
     * @return the number of photos in the album
     */
    public int getNumPhotos(){
        return photos.size();
    }

    /**
     * Sets the name of the album to the given name
     * @param name the new name of the album
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the list of photos in the album to the given list
     * @param photos the new list of photos in the album
     */
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    /**
     * Adds the given photo to the album
     * @param photo the photo to be added to the album
     */
    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    /**
     * removes the given photo from the album
     * @param photo the photo to be removed from the album
     */
    public void deletePhoto(Photo photo) {
        photos.remove(photo);
    }

    public String getDateRange(){

        LocalDateTime maxDate = LocalDateTime.MIN;
        LocalDateTime minDate = LocalDateTime.MAX;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for(int i = 0; i<photos.size(); i++){
            LocalDateTime tempmax = photos.get(i).getDateTaken();
            LocalDateTime tempmin = photos.get(i).getDateTaken();
            
            if(tempmax.compareTo(maxDate) >= 0){
                maxDate = tempmax;
            }
            if(tempmin.compareTo(minDate) <= 0){
                minDate = tempmin;
            }
        }

        if(maxDate != LocalDateTime.MIN && minDate != LocalDateTime.MAX){
            String max = maxDate.format(formatter);
            String min = minDate.format(formatter);
            return min + " - " + max;
        }else{
            return " ";
        }
    }

    /**
     * Finds and returns the photo in the album with the given file path, or returns null if no such photo is found
     * @param filePath the filepath of the photo to be found
     * @return the photo with the given file path, or null if no such photo is found
     */
    public Photo findPhotoByFilePath(String filePath) {
        for (Photo photo : photos) {
            if (photo.getFilePath().equals(filePath)) {
                return photo;
            }
        }
        return null;
    }
}
