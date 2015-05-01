package itesm.mx.finalprojectmobile20;

import java.util.ArrayList;

/**
 * Created by AlejandroSanchez on 4/25/15.
 */
public class Grupo_Java {
    String grupo_nombre;
    String grupo_motto;
    ArrayList<String> grupo_users;

    private Grupo_Java() {
    }

    public Grupo_Java(String grupo_nombre,String grupo_motto, ArrayList<String> grupo_users) {
        this.grupo_motto= grupo_motto;
        this.grupo_nombre = grupo_nombre;
        this.grupo_users = grupo_users;
    }

    public ArrayList<String> getGrupo_users() {
        return grupo_users;
    }

    public void setGrupo_users(ArrayList<String> grupo_users) {
        this.grupo_users = grupo_users;
    }

    public String getGrupo_nombre() {
        return grupo_nombre;
    }

    public void setGrupo_nombre(String grupo_nombre) {
        this.grupo_nombre = grupo_nombre;
    }

    public String getGrupo_motto() {
        return grupo_motto;
    }

    public void setGrupo_motto(String grupo_motto) {
        this.grupo_motto = grupo_motto;
    }
}

