package PhotoAlbum;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

/**
 * @author Soban Chaudhry
 * @author Mannan Mishra
 * 
 * The Photo class represents a photo object in the photos app
 * Photo object contains file name, file path, caption, and photo date taken
 */

public class Photo {
    private String fileName;
    private String filePath;
    private String caption;
    private LocalDateTime dateTaken;
    private List<Tag> tags;

    /**
     * Default constructor
     */
    public Photo() {

    }
    /**
      * Constructs a Photo object with the specified file name, file path, and date taken.
      * @param fileName the name of the photo file
      * @param filePath the path to the photo file
      * @param dateTaken the date the photo was taken
      */
    public Photo(String fileName, String filePath, LocalDateTime dateTaken) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.dateTaken = dateTaken;
        this.tags = new ArrayList<>();
    }

    /**
     * Gets the file name of the photo
     * @return the file name of the photo
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * Gets the file path of the photo
     * @return the file path of th
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Gets the caption of this photo
     * @return the caption of this photo
     */
    public String getCaption() {
        return caption;
    }
    /**
     * Gets the date the photo was taken
     * @return the date the photo was taken
     */
    public LocalDateTime getDateTaken() {
        return dateTaken;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(String key, String value) {
        Tag tag = new Tag(key, value);
        tags.add(tag);
    }

    public void removeTag(Tag tag){
        tags.remove(tag);
        tag = null;
    }

    public boolean tagEquals(Tag tag1, Tag tag2){
        if (tag1.getKey().equals(tag2.getKey()) && tag1.getValue().equals(tag2.getValue())) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public String getDateTakenString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateTaken.format(formatter);
        return date;
    }

    /**
     * Sets the file name of this photo
     * @param fileName the file name to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * Sets the file path of this photo
     * @param filePath the file path to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Sets the caption of this photo
     * @param caption the caption to set
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Sets the date the photo was taken
     * @param dateTaken the date taken to set
     */
    public void setDateTaken(LocalDateTime dateTaken) {
        this.dateTaken = dateTaken;
    }

}
