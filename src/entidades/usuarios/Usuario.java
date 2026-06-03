package entidades.usuarios;

import vista.VistaAbstracta;

public abstract class Usuario {
    private int id;
    private String username;
    private String password;

    public Usuario(String nombre) {
        this.username = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean testPassword(String password){
        return this.password.equals(password);
    }

    public abstract VistaAbstracta createVistaInicial();
}
