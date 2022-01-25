package sv.gob.sansalvadorhistorico.modelos;

public class Categorias {
    private  String term_id;
    private String name;

    public Categorias(String term_id, String name) {
        this.term_id = term_id;
        this.name = name;
    }

    public String getTerm_id() {
        return term_id;
    }

    public void setTerm_id(String term_id) {
        this.term_id = term_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
