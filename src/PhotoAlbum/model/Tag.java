package PhotoAlbum.model;

public class Tag {
    private String key;
    private String value;

    public Tag(String key, String value){
        this.key = key;
        this.value = value;
    }

    
    /** 
     * @return String
     */
    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    
}
