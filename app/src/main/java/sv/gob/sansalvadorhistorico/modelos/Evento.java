package sv.gob.sansalvadorhistorico.modelos;

public class Evento {
    private String ID;
    private String post_id;
    private String post_name;
    private String post_title;
    private String post_content;
    private String guid;
    public Evento(String ID, String post_id, String post_name,
                  String post_title, String post_content, String guid) {
        this.ID = ID;
        this.post_id = post_id;
        this.post_name = post_name;
        this.post_title = post_title;
        this.post_content = post_content;
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getId() {
        return ID;
    }

    public void setId(String id) {
        this.ID = id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }
}
