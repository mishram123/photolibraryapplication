package PhotoAlbum;
import java.time.LocalDateTime;

public class Photo {
    private String fileName;
    private String filePath;
    private String caption;
    private LocalDateTime dateTaken;

    public Photo() {

    }

    public Photo(String fileName, String filePath, LocalDateTime dateTaken) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.dateTaken = dateTaken;
    }

    //getters
    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getCaption() {
        return caption;
    }

    public LocalDateTime getDateTaken() {
        return dateTaken;
    }

    //setters
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setDateTaken(LocalDateTime dateTaken) {
        this.dateTaken = dateTaken;
    }

}
