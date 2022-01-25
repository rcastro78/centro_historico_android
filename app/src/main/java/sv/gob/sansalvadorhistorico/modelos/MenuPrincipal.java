package sv.gob.sansalvadorhistorico.modelos;

public class MenuPrincipal {
    private int id;
    private String nombre;
    private int idImagen;
    private int idColor;

    public MenuPrincipal(int id, String nombre, int idImagen, int idColor) {
        this.id = id;
        this.nombre = nombre;
        this.idImagen = idImagen;
        this.idColor = idColor;
    }

    public MenuPrincipal(int id, String nombre, int idImagen) {
        this.id = id;
        this.nombre = nombre;
        this.idImagen = idImagen;
    }

    public MenuPrincipal(int id,  int idImagen) {
        this.id = id;

        this.idImagen = idImagen;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }
}
