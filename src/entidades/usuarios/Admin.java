package entidades.usuarios;

import vista.VistaAbstracta;
import vista.VistaAdmin;

public class Admin extends Usuario {
    public Admin(String nombre) {
        super(nombre);
    }

    @Override
    public VistaAbstracta createVistaInicial() {
        return new VistaAdmin();
    }
}
