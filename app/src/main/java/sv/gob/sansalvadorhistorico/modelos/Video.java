package sv.gob.sansalvadorhistorico.modelos;

public class Video {
    private int idVideo;
    private String videoUrl;
    private String textoVideo;
    private int imagen;
    public Video(int idVideo, String videoUrl, String textoVideo, int imagen) {
        this.idVideo = idVideo;
        this.videoUrl = videoUrl;
        this.textoVideo = textoVideo;
        this.imagen = imagen;
    }


    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(int idVideo) {
        this.idVideo = idVideo;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTextoVideo() {
        return textoVideo;
    }

    public void setTextoVideo(String textoVideo) {
        this.textoVideo = textoVideo;
    }
}
