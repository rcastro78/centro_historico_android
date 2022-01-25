package sv.gob.sansalvadorhistorico.modelos;
//id,post_title,url,post_type,thumbnail,longitud,latitud,tipo
public class Lugar {
    private String ID,post_title,url,post_type,thumbnail,longitud,latitud,language_code;

    public Lugar(String id, String post_title, String url, String post_type,
                 String thumbnail, String longitud, String latitud) {
        this.ID = id;
        this.post_title = post_title;
        this.url = url;
        this.post_type = post_type;
        this.thumbnail = thumbnail;
        this.longitud = longitud;
        this.latitud = latitud;

    }


    public Lugar(String id, String post_title, String url, String post_type,
                 String thumbnail, String longitud, String latitud,
                 String language_code) {
        this.ID = id;
        this.post_title = post_title;
        this.url = url;
        this.post_type = post_type;
        this.thumbnail = thumbnail;
        this.longitud = longitud;
        this.latitud = latitud;
        this.language_code = language_code;
    }


    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getId() {
        return ID;
    }

    public void setId(String id) {
        this.ID = id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }


}
