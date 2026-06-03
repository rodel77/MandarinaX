package persistencia;

import entidades.usuarios.Usuario;

public interface UsuarioDAO {
    Usuario buscarPorNombre(String nombre) throws LeerException;
    void escribir(Usuario usuario) throws EscribirException;
}
