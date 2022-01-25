package sv.gob.sansalvadorhistorico.modelos;

public class Notificacion {
    private String postId,postTitle,guid,fecha,content;

    public Notificacion(String postId, String postTitle, String content,String guid, String fecha) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.guid = guid;
        this.fecha = fecha;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
