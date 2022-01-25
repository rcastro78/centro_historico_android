package sv.gob.sansalvadorhistorico.modelos;

import com.google.protobuf.ApiOrBuilder;

public class LugaresCategoria {
    //{"id":"3644","post_title":"El caf\u00e9 de Don Pedro","categoria":"restaurantes","guid":"https:\/\/www.sansalvadorhistorico.com\/recorrido\/el-cafe-de-don-pedro\/","latitud":"13.700473176205923","longitud":"-89.21162974894793","url":""
    private String id;
    private String post_title;
    private String categoria;
    private String guid;
    private String latitud;
    private String longitud;
    private String url;
    private String post_content;
    private String language_code;
    public LugaresCategoria(String id, String post_title, String categoria,
                            String guid, String latitud, String longitud, String url,String post_content) {
        this.id = id;
        this.post_title = post_title;
        this.categoria = categoria;
        this.guid = guid;
        this.latitud = latitud;
        this.longitud = longitud;
        this.url = url;
        this.post_content = post_content;
    }

    public LugaresCategoria(String id, String post_title, String categoria,
                            String guid, String latitud, String longitud, String url,String post_content,
                            String language_code) {
        this.id = id;
        this.post_title = post_title;
        this.categoria = categoria;
        this.guid = guid;
        this.latitud = latitud;
        this.longitud = longitud;
        this.url = url;
        this.post_content = post_content;
        this.language_code = language_code;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
