package itesm.mx.finalprojectmobile20;

import java.util.HashMap;

/**
 * Created by AlejandroSanchez on 4/25/15.
 */
public class Grupo_Java {
    String grupo_nombre;
    String grupo_motto;
    HashMap<String,String> grupo_usuarios;

    public Grupo_Java(String grupo_nombre,String grupo_motto, HashMap<String,String> grupo_usuarios) {
        this.grupo_motto= grupo_motto;
        this.grupo_nombre = grupo_nombre;
        this.grupo_usuarios = grupo_usuarios;
    }

    public HashMap<String,String> getGrupo_usuarios() {
        return grupo_usuarios;
    }

    public void setGrupo_usuarios(HashMap<String, String> grupo_usuarios) {
        this.grupo_usuarios = grupo_usuarios;
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

