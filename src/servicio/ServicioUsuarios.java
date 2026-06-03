package servicio;

import entidades.usuarios.Usuario;
import persistencia.EscribirException;
import persistencia.LeerException;
import persistencia.UsuarioDAO;
import persistencia.UsuariosCrudSQL;

public class ServicioUsuarios {
    private final UsuarioDAO crud = new UsuariosCrudSQL();

    public Usuario login(String username, String password) throws LeerException {
        Usuario usuario = crud.buscarPorNombre(username);
        if(!usuario.testPassword(password)) throw new InvalidPassword("Contraseña invalida");
        return usuario;
    }

    public Usuario buscarPorNombre(String username) throws Exception{
        return crud.buscarPorNombre(username);
    }

    public void crearUsuario(Usuario usuario) throws EscribirException {
        crud.escribir(usuario);
    }
}
