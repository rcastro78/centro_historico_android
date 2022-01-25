package sv.gob.sansalvadorhistorico.modelos;

public class MisRecorridosMenu {
    private int idMenu,imagenPrincipal,icono;
    private String nombre,cantidad;

    public MisRecorridosMenu(int idMenu, int imagenPrincipal, int icono,
                             String nombre, String cantidad) {
        this.idMenu = idMenu;
        this.imagenPrincipal = imagenPrincipal;
        this.icono = icono;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }



    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getImagenPrincipal() {
        return imagenPrincipal;
    }

    public void setImagenPrincipal(int imagenPrincipal) {
        this.imagenPrincipal = imagenPrincipal;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
